package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.service.FileSelector;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;

public class LayoutController {

    @FXML
    private Node rootNode;

    @FXML
    private TextArea inputArea;

    @FXML
    Text fileNameHolder;

    @FXML
    Text saveStatusHolder;

    @Injectable
    private ApplicationState applicationState;

    @Injectable
    private FileManager fileManager;

    @Injectable
    private FileSelector fileSelector;

    @FXML
    void onKeyPressed(KeyEvent event) {
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
    public void onKeyTyped(KeyEvent keyEvent) {
        updateSaveStatus();
    }

    @FXML
    void onNewButtonClick(Event e) {
        applicationState.setTextContent(new TextContent());
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
        if (applicationState.getTextContent().isNew()) {
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
            applicationState.setTextContent(fileManager.loadFile(file));
            setInputAreaToTextContent();
            fileNameHolder.setText(applicationState.getTextContent().getFileName());
            inputArea.requestFocus();
            updateSaveStatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        setTextContentToInputArea();
        try {
            fileManager.saveFile(applicationState.getTextContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        inputArea.requestFocus();
        updateSaveStatus();
    }

    private void saveFileAs() {
        File file = fileSelector.selectFileToSave(rootNode.getScene().getWindow());
        if (file != null) {
            setTextContentToInputArea();
            try {
                applicationState.setTextContent(fileManager.saveToFile(applicationState.getTextContent(), file));
                fileNameHolder.setText(applicationState.getTextContent().getFileName());
            } catch (IOException e) {
                e.printStackTrace();
            }
            inputArea.requestFocus();
            updateSaveStatus();
        }
    }

    private void updateSaveStatus() {
        if (applicationState.getTextContent().getContent().equals(inputArea.getText())) {
            fileNameHolder.setStyle("-fx-font-style: normal;");
            saveStatusHolder.setStyle("-fx-font-style: normal;");
            saveStatusHolder.setText("saved");
        } else {
            fileNameHolder.setStyle("-fx-font-style: italic;");
            saveStatusHolder.setStyle("-fx-font-style: italic;");
            saveStatusHolder.setText("unsaved");
        }
    }

    private void setTextContentToInputArea() {
        applicationState.getTextContent().setContent(inputArea.getText());
    }

    private void setInputAreaToTextContent() {
        inputArea.setText(applicationState.getTextContent().getContent());
    }
}
