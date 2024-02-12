module com.cst8334.cst833 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cst8334.cst833 to javafx.fxml;
    exports com.cst8334.cst833;
}