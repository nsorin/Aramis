package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.event.TextContentUpdated;
import com.github.nsorin.aramis.observer.OnEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

public class InputAreaController {

    @FXML
    public TextArea inputArea;

    @Injectable
    private ApplicationState applicationState;

    public void onKeyTyped(KeyEvent keyEvent) {
        applicationState.setSaved(applicationState.getTextContent().getContent().equals(inputArea.getText()));
    }

    @OnEvent
    public void onTextContentUpdated(TextContentUpdated event) {
        inputArea.setText(event.textContent().getContent());
        inputArea.requestFocus();
    }
}
