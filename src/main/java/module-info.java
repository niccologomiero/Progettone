module com.gomiero.progettonegomiero {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gomiero.progettonegomiero to javafx.fxml;
    exports com.gomiero.progettonegomiero;
    exports com.gomiero.progettonegomiero.controllers;
    opens com.gomiero.progettonegomiero.controllers to javafx.fxml;
}