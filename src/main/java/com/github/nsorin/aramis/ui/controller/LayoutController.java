package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injection.Injectable;
import com.github.nsorin.aramis.model.Text;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;

import java.io.File;
import java.io.IOException;

public class LayoutController {

    @FXML
    private Node rootNode;

    @FXML
    private TextArea inputArea;

    @FXML
    javafx.scene.text.Text fileNameHolder;

    private Text text = Text.makeNew();

    @Injectable
    private FileManager fileManager;

    @Injectable
    private FileSelector fileSelector;


    @FXML
    void onKeyCombination(KeyEvent event) {
        if (KeyboardShortcuts.NEW.match(event)) {
            onNewButtonClick(event);
        } else if (KeyboardShortcuts.OPEN.match(event)) {
            onOpenButtonClick(event);
        } else if (KeyboardShortcuts.SAVE.match(event)) {
            onSaveButtonClick(event);
        } else if (KeyboardShortcuts.SAVE_AS.match(event)) {
            onSaveAsButtonClick(event);
        }
    }

    @FXML
    void onNewButtonClick(Event e) {
        text = Text.makeNew();
        setInputAreaToTextContent();
        fileNameHolder.setText("");
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
            fileNameHolder.setText(text.getFileName());
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
                fileNameHolder.setText(text.getFileName());
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
