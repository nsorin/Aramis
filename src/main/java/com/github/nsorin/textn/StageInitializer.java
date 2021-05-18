package com.github.nsorin.textn;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

@Component
public class StageInitializer implements ApplicationListener<TextEditorApplication.StageReadyEvent> {

    private static final String STAGE_TITLE = "Textn Text Editor";
    private static final String LAYOUT_FXML_PATH = "layout.fxml";

    @Override
    public void onApplicationEvent(TextEditorApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
        stage.setTitle(STAGE_TITLE);
        stage.setMaximized(true);
        try {
            stage.setScene(buildScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.show();
    }

    private Scene buildScene() throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(LAYOUT_FXML_PATH)));
        return new Scene(root, 300, 275);
    }
}
