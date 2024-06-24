module com.ais.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.ais.model to javafx.base;
    opens com.ais.client to javafx.fxml, javafx.base;
    exports com.ais.client;
}
