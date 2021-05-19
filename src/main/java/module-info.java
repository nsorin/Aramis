module textn {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.github.nsorin.textn.ui;
    exports com.github.nsorin.textn.ui.controller;

    opens com.github.nsorin.textn to javafx.graphics;
}
