module AdminPanel {
    requires com.google.gson;
    requires java.net.http;
    requires javafx.controls;
    requires javafx.graphics;
    requires static lombok;
    requires java.sql;

    exports com.example.adminpanel to javafx.graphics;
    opens com.example.adminpanel.entity to com.google.gson, javafx.base;
}