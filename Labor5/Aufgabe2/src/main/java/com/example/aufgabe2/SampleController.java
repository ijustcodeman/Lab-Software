package com.example.aufgabe2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SampleController {
    @FXML
    private Label helloWorld;

    @FXML
    protected void sayHelloWorld() {
        helloWorld.setText("Hello World!");
    }
}
