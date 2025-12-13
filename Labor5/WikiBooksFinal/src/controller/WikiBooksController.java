package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.WikiBook;

public class WikiBooksController {

    public TextField searchTitle;

    public String extractedTitle = "";

    public WebView viewBook;

    public Label lastUsernameValue;
    public Label lastChangeValue;
    public Label regalValue;

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

    }
}
