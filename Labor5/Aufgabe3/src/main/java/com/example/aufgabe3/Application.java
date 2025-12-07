package com.example.aufgabe3;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("list-design.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 350, 200);
        stage.setResizable(false);
        stage.setTitle("Wundervolle Liste");

        stage.setScene(scene);
        stage.show();
    }
}
