module com.example.dataapiproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens com.example.dataapiproject to javafx.fxml;
    exports com.example.dataapiproject;
}
