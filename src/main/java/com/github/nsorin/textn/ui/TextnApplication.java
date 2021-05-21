package com.github.nsorin.textn.ui;

import com.github.nsorin.textn.injection.DependencyProvider;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TextnApplication extends Application {

    private static final String STAGE_TITLE = "Textn Text Editor";
    private static final String LAYOUT_FXML_PATH = "layout.fxml";

    public static final int SCENE_WIDTH = 300;
    public static final int SCENE_HEIGHT = 275;

    public static final String FOCUSED_ELEMENT = "#inputArea";

    @Override
    public void start(Stage stage) {
        stage.setTitle(STAGE_TITLE);
        stage.setMaximized(true);
        try {
            stage.setScene(buildScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
        stage.getScene().getRoot().lookup(FOCUSED_ELEMENT).requestFocus();
    }

    private Scene buildScene() throws IOException {
        return new Scene(getFxmlLoader().load(), SCENE_WIDTH, SCENE_HEIGHT);
    }

    private FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource(LAYOUT_FXML_PATH)));
        fxmlLoader.setControllerFactory(DependencyProvider.getProvider().getControllerFactory());
        return fxmlLoader;
    }
}
