package controller;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
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
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getUrl(extractedTitle));

        startWikiBookFetchTask(extractedTitle);

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

        String username = book.getLastUsername();
        String ip = book.getLastIP();
        String contributor = "N/A";

        if (username != null && !username.isBlank()) {
            contributor = username;
        } else if (ip != null && !ip.isBlank()) {
            contributor = ip;
        }
        lastUsernameValue.setText(contributor);

        lastChangeValue.setText(book.getFormattedLastModifiedDate());

        if (book.getRegale().isEmpty()) {
            regalValue.setText("N/A");
        } else {
            regalValue.setText(String.join(", ", book.getRegale()));
        }
    }
}
