package com.github.nsorin.aramis.ui.controller;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.model.event.FilePropertiesUpdated;
import com.github.nsorin.aramis.model.event.TextContentUpdated;
import com.github.nsorin.aramis.observer.OnEvent;
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

public class MainController {

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
        applicationState.setFileProperties(new FileProperties());
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
            applicationState.setFileProperties(new FileProperties(
                    applicationState.getTextContent().getFileLocation(),
                    applicationState.getTextContent().getFileName()
            ));
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
                applicationState.setFileProperties(new FileProperties(
                        applicationState.getTextContent().getFileLocation(),
                        applicationState.getTextContent().getFileName()
                ));
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    @OnEvent
    public void onTextContentUpdated(TextContentUpdated event) {
        inputArea.setText(event.textContent().getContent());
        inputArea.requestFocus();
    }

    @OnEvent
    public void onFilePropertiesUpdated(FilePropertiesUpdated event) {
        fileNameHolder.setText(event.fileProperties().getName());
    }
}
