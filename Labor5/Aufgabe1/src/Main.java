import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            GridPane root = new GridPane();
            Scene scene = new Scene(root,400,400);

            TextField textField1 = new TextField();
            textField1.setPromptText("Schreibe hier");

            TextField textField2 = new TextField();
            textField2.setPromptText("Schreibe hier");


            final EventHandler<KeyEvent> keyEventHandler =
                    new EventHandler<KeyEvent>() {
                        public void handle(final KeyEvent keyEvent) {
                            switch (keyEvent.getCode()) {
                                case F1:
                                    textField2.setText("F1 wurde gedrückt.");
                                    break;
                                case F2:
                                    textField2.setText("F2 wurde gedrückt.");
                                    break;
                                case F3:
                                    textField2.setText("F3 wurde gedrückt.");
                                    break;
                                default:
                                    return;
                            }
                            keyEvent.consume();
                        }
                    };

            textField1.setOnKeyPressed(keyEventHandler);
            textField2.setOnKeyPressed(keyEventHandler);

            root.setConstraints(textField1, 0,0);
            root.setConstraints(textField2, 0,1);

            root.getChildren().addAll(textField1,textField2);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


