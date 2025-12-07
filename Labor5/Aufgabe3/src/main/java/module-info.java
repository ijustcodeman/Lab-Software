module com.example.aufgabe3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.aufgabe3 to javafx.fxml;
    exports com.example.aufgabe3;
}