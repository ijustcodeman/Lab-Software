module com.example.aufgabe2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aufgabe2 to javafx.fxml;
    exports com.example.aufgabe2;
}