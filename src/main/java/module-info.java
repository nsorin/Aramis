module textn {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.github.nsorin.textn.ui;
    exports com.github.nsorin.textn.ui.controller;
    exports com.github.nsorin.textn.service;

    opens com.github.nsorin.textn to javafx.graphics;
    opens com.github.nsorin.textn.ui.controller to javafx.fxml;
}
