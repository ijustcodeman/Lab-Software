package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.WikiBook;
import model.Zettelkasten;

public class WikiBooksController {

    public TextField searchTitle;

    public String extractedTitle = "";

    public WebView viewBook;

    public Label lastUsernameValue;
    public Label lastChangeValue;
    public Label regalValue;

    public ListView viewMedien = new ListView();
    public ListView viewSynonyme;

    private Zettelkasten zettelkasten = new Zettelkasten();

    public void initialize() {
        searchTitle.setOnAction(this::onClickSearchTitle);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getWikiBookLink());
    }

    public void onClickSearchTitle(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();
        if (extractedTitle.isEmpty()){
            return;
        }

        String safeTitle = WikiBook.getURLTitle(extractedTitle);

        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getUrl(extractedTitle));

        startWikiBookFetchTask(safeTitle);

    }

    private void startWikiBookFetchTask(String title) {
        lastUsernameValue.setText("Wird geladen...");
        lastChangeValue.setText("Wird geladen...");
        regalValue.setText("Wird geladen...");
        searchTitle.setDisable(true);

        Task<WikiBook> fetchTask = new Task<>() {
            @Override
            protected WikiBook call() throws Exception {
                WikiBook book = zettelkasten.fetchWikiBook(title);

                if (book == null) {
                    throw new Exception("Das WikiBook '" + title + "' konnte nicht abgerufen werden.");
                }
                return book;
            }
        };

        fetchTask.setOnSucceeded(event -> {
            WikiBook resultBook = fetchTask.getValue();
            updateUI(resultBook);
            searchTitle.setDisable(false);

            zettelkasten.addMedium(resultBook);
        });

        fetchTask.setOnFailed(event -> {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER");
            alert.setHeaderText(null);
            alert.setContentText(fetchTask.getException().getMessage());
            alert.showAndWait();

            System.err.println("Fehler beim Abrufen des WikiBooks: " + fetchTask.getException().getMessage());
            lastUsernameValue.setText("Fehler");
            lastChangeValue.setText("Fehler");
            regalValue.setText("Fehler");
            searchTitle.setDisable(false);
        });

        new Thread(fetchTask).start();
    }

    private void updateUI(WikiBook book) {
        if (book == null) {
            lastUsernameValue.setText("N/A");
            lastChangeValue.setText("N/A");
            regalValue.setText("N/A");
            return;
        }

        lastUsernameValue.setText(book.getDisplayContributor());

        lastChangeValue.setText(book.getFormattedLastModifiedDate());

        if (book.getRegale().isEmpty()) {
            regalValue.setText("N/A");
        } else {
            regalValue.setText(String.join(", ", book.getRegale()));
        }
    }

    public void onClickAddWikiBook(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();

        if (extractedTitle.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNUNG");
            alert.setHeaderText("Lehrer Titel");
            alert.setContentText("Du musst ein Titel übergeben.");
            alert.showAndWait();
            return;
        }
        boolean success = zettelkasten.addWikiBook(extractedTitle);

        if (!success){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER");
            alert.setHeaderText("Hinzufügen fehlgeschlagen");
            alert.setContentText("Das folgende WikiBook konnte nicht hinzugefügt werden: " + extractedTitle);
            alert.showAndWait();
        }

        else{
            viewMedien.getItems().add(extractedTitle);
        }

    }

    public void onClickSortWikiBook(ActionEvent actionEvent) {

    }

    public void onClickDeleteWikiBook(ActionEvent actionEvent) {
    }

    public void onClickSucheSynonym(ActionEvent actionEvent) {
    }
}
