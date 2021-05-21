package com.github.nsorin.textn.ui.controller;

import com.github.nsorin.textn.injection.Injected;
import com.github.nsorin.textn.model.Text;
import com.github.nsorin.textn.service.FileManager;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class LayoutController {

    @FXML
    private Node rootNode;

    @FXML
    private TextArea inputArea;

    private Text text = Text.makeNew();

    private FileManager fileManager;

    @Injected
    public void setFileLoader(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @FXML
    void onNewButtonClick(Event e) {
        text = Text.makeNew();
        setInputAreaToTextContent();
        inputArea.requestFocus();
    }

    @FXML
    void onOpenButtonClick(Event e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showSaveDialog(rootNode.getScene().getWindow());
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
