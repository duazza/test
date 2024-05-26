module com.mycompany.qproperty {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires java.base;

    opens com.mycompany.qproperty to javafx.fxml;
    opens com.mycompany.qproperty.Model to javafx.base;
    exports com.mycompany.qproperty;
}
