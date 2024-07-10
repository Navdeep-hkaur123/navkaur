module com.example.navkaur {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.navkaur to javafx.fxml;
    exports com.example.navkaur;
}