package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import model.WikiBook;

public class WikiBooksController {

    public TextField searchTitle;
    public String extractedTitle = "";
    public WebView viewBook;

    public void initialize() {
        searchTitle.setOnAction(this::onClickSearchTitle);
    }

    public void onClickSearchTitle(ActionEvent actionEvent) {
        extractedTitle = searchTitle.getText().trim();
        if (extractedTitle.isEmpty()){
            return;
        }
        WebEngine engine = viewBook.getEngine();
        engine.load(WikiBook.getURL(extractedTitle));
    }
}
