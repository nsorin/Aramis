package com.github.nsorin.textn;

import javafx.stage.Stage;
import org.springframework.context.ApplicationListener;

public class StageInitializer implements ApplicationListener<TextEditorApplication.StageReadyEvent> {
    @Override
    public void onApplicationEvent(TextEditorApplication.StageReadyEvent event) {
        Stage stage = event.getStage();
    }
}
