package com.github.nsorin.aramis.ui;

import com.github.nsorin.aramis.injector.DependencyProvider;
import com.github.nsorin.aramis.observer.EventAutoSubscriber;
import com.github.nsorin.aramis.observer.EventObserver;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TextEditorApplication extends Application {

    private static final String STAGE_TITLE = "Aramis Text Editor";
    private static final String LAYOUT_FXML_PATH = "main.fxml";

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
        setCloseListener(stage);
    }

    private Scene buildScene() throws IOException {
        return new Scene(getFxmlLoader().load(), SCENE_WIDTH, SCENE_HEIGHT);
    }

    private FXMLLoader getFxmlLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();

        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getClassLoader().getResource(LAYOUT_FXML_PATH)));
        fxmlLoader.setControllerFactory(controllerClass ->
                new EventAutoSubscriber(EventObserver.getObserver()).apply(
                        DependencyProvider.getProvider().getControllerFactory().call(controllerClass)
                )
        );
        return fxmlLoader;
    }

    private void setCloseListener(Stage stage) {
        stage.setOnCloseRequest(DependencyProvider.getProvider().resolve(OnCloseRequest.class));
    }
}
