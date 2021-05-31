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
    private TextArea inputArea;

    @Injectable
    private ApplicationState applicationState;

    public void onKeyTyped(KeyEvent keyEvent) {
        String inputText = inputArea.getText();
        applicationState.setSaved(applicationState.getTextContent().getContent().equals(inputText));
        applicationState.getTextContent().setContent(inputText);
    }

    @OnEvent
    public void onTextContentUpdated(TextContentUpdated event) {
        inputArea.setText(event.textContent().getContent());
        inputArea.requestFocus();
    }
}
