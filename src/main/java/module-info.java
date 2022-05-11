module com.example.kreuzwortraetzel {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.example.kreuzwortraetzel to javafx.fxml;
    exports com.example.kreuzwortraetzel;
}