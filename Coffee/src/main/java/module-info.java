module com.example.coffee {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kordamp.bootstrapfx.core;
    requires com.microsoft.sqlserver.jdbc;
    requires java.desktop;
    requires java.sql.rowset;

    opens com.example.coffee to javafx.fxml;
    exports com.example.coffee;
    exports com.example.coffee.DAO;
    opens com.example.coffee.DAO to javafx.fxml;
    exports com.example.coffee.DTO;
    opens com.example.coffee.DTO to javafx.fxml;
}