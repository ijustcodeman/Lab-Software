package com.example.aufgabe3;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class Controller {
    public TextField addText;
    public ListView viewText = new ListView();
    public String extractedText = "";


    public void onClickAdd(ActionEvent actionEvent) {
        extractedText = addText.getText();
        if (extractedText.isBlank()){
            return;
        }
        addText.clear();
        viewText.getItems().add(extractedText);
    }

    public void onClickReset(ActionEvent actionEvent) {
        viewText.getItems().clear();
    }
}
