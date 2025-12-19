package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class WikiBooksApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("WikiBooksUI.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 900, 650);

        stage.setTitle("Mein WikibooksBrowser");

        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/img.png")));
        } catch (Exception e) {
            System.out.println("Icon konnte nicht geladen werden: " + e.getMessage());
        }

        stage.setMinWidth(900);
        stage.setMinHeight(650);

        stage.setScene(scene);
        stage.show();

    }
}
