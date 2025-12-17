package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.*;

import java.util.ArrayList;

public class WikiBooksController {

    public TextField searchTitle;

    public String extractedTitle = "";

    public WebView viewBook;

    public Label lastUsernameValue;
    public Label lastChangeValue;
    public Label regalValue;

    public ListView<String> viewMedien = new ListView();
    public ListView<String> viewSynonyme = new ListView();

    public Button searchWikipediaButton;

    private Zettelkasten zettelkasten = new Zettelkasten();

    private BinaryPersistency binaryPersistency = new BinaryPersistency();
    private final String DEFAULT_FILENAME = "zettelkasten_data";

    public void initialize() {
        searchTitle.setOnAction(this::onClickSearchTitle);
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getWikiBookLink());
    }

    public void onClickSearchTitle(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();
        if (extractedTitle.isEmpty()){
            warningEmptyTitle();
            return;
        }

        String safeTitle = WikiBook.getURLTitle(extractedTitle);

        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getUrl(extractedTitle));

        startWikiBookFetchTask(safeTitle);
        startWikipediaFetchTask(extractedTitle);

        updateCurrentZettelkastenMediaListView(extractedTitle);

    }

    public void onClickAddWikiBook(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();

        if (extractedTitle.isEmpty()){
            warningEmptyTitle();
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
            updateMediaListView();
        }

    }

    public void onClickSortWikiBook(ActionEvent actionEvent) {
        String sortOrder = zettelkasten.getCurrentSortOrder();

        if (sortOrder == null || sortOrder.equals("ABSTEIGEND")){
            zettelkasten.sort("AUFSTEIGEND");
            updateMediaListView();
        }

        else if (sortOrder.equals("AUFSTEIGEND")){
            zettelkasten.sort("ABSTEIGEND");
            updateMediaListView();
        }


    }

    public void onClickDeleteWikiBook(ActionEvent actionEvent) {
        String selectedTitle = viewMedien.getSelectionModel().getSelectedItem();

        if (selectedTitle == null || selectedTitle.isEmpty()){
            warningEmptyTitle();
            return;
        }
        if (zettelkasten.dropMedium(selectedTitle, true, 0)) {
            updateMediaListView();
        }


    }

    public void onClickSave(ActionEvent actionEvent) {

        if (zettelkasten.getMyZettelkasten().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Speichern Fehlgeschlagen");
            alert.setHeaderText(null);
            alert.setContentText("Du kannst keinen leeren Zettelkasten speichern.");
            alert.showAndWait();
            return;
        }
        try {
            binaryPersistency.save(zettelkasten, DEFAULT_FILENAME);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Speichern erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Der Zettelkasten wurde erfolgreich unter '" + DEFAULT_FILENAME + ".ser' gespeichert.");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER beim Speichern");
            alert.setHeaderText("Konnte Daten nicht serialisieren.");
            alert.setContentText("Fehler: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void onClickLoad(ActionEvent actionEvent) {
        Zettelkasten loadedZk = binaryPersistency.load(DEFAULT_FILENAME);

        if (loadedZk != null) {
            this.zettelkasten = loadedZk;

            updateMediaListView();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Laden erfolgreich");
            alert.setHeaderText(null);
            alert.setContentText("Der Zettelkasten wurde erfolgreich von '" + DEFAULT_FILENAME + ".ser' geladen.");
            alert.showAndWait();
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER beim Laden");
            alert.setHeaderText("Laden fehlgeschlagen.");
            alert.setContentText("Die Datei '" + DEFAULT_FILENAME + ".ser' konnte nicht gefunden oder gelesen werden.");
            alert.showAndWait();
        }
    }

    public void onClickImport(ActionEvent actionEvent) {

        String errorText = "Die Importfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Import fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

    public void onClickExport(ActionEvent actionEvent) {

        String errorText = "Die Exportfunktion wird in dieser Version nicht unterstützt.";

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("FEHLER: Funktion nicht implementiert");
        alert.setHeaderText("Export fehlgeschlagen");
        alert.setContentText(errorText);
        alert.showAndWait();

        throw new UnsupportedOperationException(errorText);
    }

    public void onSynonymListViewClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            String selectedTitle = viewSynonyme.getSelectionModel().getSelectedItem();

            if (selectedTitle != null && !selectedTitle.isEmpty()) {
                onClickSearchWikipedia(null);
            }
        }
    }

    public void onClickSearchWikipedia(ActionEvent actionEvent) {
        String selectedTitle = viewSynonyme.getSelectionModel().getSelectedItem();

        if (selectedTitle == null || selectedTitle.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNUNG");
            alert.setHeaderText(null);
            alert.setContentText("Bitte wählen Sie zuerst einen Wikipedia-Titel aus der Liste aus.");
            alert.showAndWait();
            return;
        }
        searchTitle.setText(selectedTitle);

        onClickSearchTitle(actionEvent);
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

    private void startWikipediaFetchTask(String searchKeyword) {
        if (searchKeyword.trim().isEmpty()) {
            return;
        }

        viewSynonyme.setDisable(false);
        searchWikipediaButton.setDisable(false);

        Task<ArrayList<String>> fetchTask = new Task<>() {
            @Override
            protected ArrayList<String> call() throws Exception {
                return WikipediaAPI.fetchWikipediaTitles(searchKeyword);
            }
        };

        fetchTask.setOnSucceeded(event -> {
            ArrayList<String> result = fetchTask.getValue();
            viewSynonyme.getItems().clear();

            if (result == null || result.isEmpty()){
                viewSynonyme.getItems().add("<keine>");
                viewSynonyme.setDisable(true);
                searchWikipediaButton.setDisable(true);
            } else {
                viewSynonyme.setDisable(false);
                searchWikipediaButton.setDisable(false);
                viewSynonyme.getItems().addAll(result);
            }
        });

        fetchTask.setOnFailed(event -> {
            viewSynonyme.getItems().clear();
            viewSynonyme.getItems().add("<Fehler>");
            viewSynonyme.setDisable(true);
            searchWikipediaButton.setDisable(true);

            Throwable exception = fetchTask.getException();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("FEHLER: Wikipedia Zugriff");
            alert.setHeaderText("Konnte Wikipediadaten nicht abrufen.");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();

            System.err.println("Fehler beim Abrufen der Wikipedia Daten: " + exception.getMessage());
            // zum debuggen
            exception.printStackTrace();
        });
        new Thread(fetchTask).start();
    }

    private void updateMediaListView() {
        viewMedien.getItems().clear();
        for (Medium medium : zettelkasten.getMyZettelkasten()) {
            viewMedien.getItems().add(medium.getTitel());
        }
    }

    private void updateCurrentZettelkastenMediaListView(String filterTitle) {
        viewMedien.getItems().clear();

        String normalizedFilter = filterTitle.toLowerCase();

        for (Medium medium : zettelkasten.getMyZettelkasten()) {
            if (medium.getTitel().toLowerCase().contains(normalizedFilter)) {
                viewMedien.getItems().add(medium.getTitel());
            }
        }
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

    public void warningEmptyTitle(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("WARNUNG");
        alert.setHeaderText("Leerer Titel");
        alert.setContentText("Du musst ein Titel angeben.");
        alert.showAndWait();
    }
}
