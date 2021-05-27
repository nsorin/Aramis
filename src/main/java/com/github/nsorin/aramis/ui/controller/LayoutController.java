package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.model.Text;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;

public class LayoutController {

    @FXML
    private Node rootNode;

    @FXML
    private TextArea inputArea;

    private Text text = Text.makeNew();

    @Injectable
    private FileManager fileManager;

    @Injectable
    private FileSelector fileSelector;

    private static final KeyCodeCombination newShortcut = new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_ANY);
    private static final KeyCodeCombination openShortcut = new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_ANY);
    private static final KeyCodeCombination saveShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
    private static final KeyCodeCombination saveAsShortcut = new KeyCodeCombination(KeyCode.S, KeyCombination.SHIFT_ANY, KeyCombination.CONTROL_ANY);

    @FXML
    void onKeyCombination(KeyEvent event) {
        if (newShortcut.match(event)) {
            onNewButtonClick(event);
        } else if (openShortcut.match(event)) {
            onOpenButtonClick(event);
        } else if (saveShortcut.match(event)) {
            onSaveButtonClick(event);
        } else if (saveAsShortcut.match(event)) {
            onSaveAsButtonClick(event);
        }
    }

    @FXML
    void onNewButtonClick(Event e) {
        text = Text.makeNew();
        setInputAreaToTextContent();
        inputArea.requestFocus();
    }

    @FXML
    void onOpenButtonClick(Event e) {
        File file = fileSelector.selectFileToOpen(rootNode.getScene().getWindow());
        openFile(file);
    }

    @FXML
    void onSaveButtonClick(Event e) {
        if (text.isNew()) {
            saveFileAs();
        } else {
            saveFile();
        }
    }

    @FXML
    void onSaveAsButtonClick(Event e) {
        saveFileAs();
    }

    private void openFile(File file) {
        try {
            text = fileManager.loadFile(file);
            setInputAreaToTextContent();
            inputArea.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        setTextContentToInputArea();
        try {
            fileManager.saveFile(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputArea.requestFocus();
    }

    private void saveFileAs() {
        File file = fileSelector.selectFileToSave(rootNode.getScene().getWindow());
        if (file != null) {
            setTextContentToInputArea();
            try {
                text = fileManager.saveToFile(text, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputArea.requestFocus();
        }
    }

    private void setTextContentToInputArea() {
        text.setContent(inputArea.getText());
    }

    private void setInputAreaToTextContent() {
        inputArea.setText(text.getContent());
    }
}
