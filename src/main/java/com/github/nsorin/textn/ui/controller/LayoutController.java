package com.github.nsorin.textn.ui.controller;

import com.github.nsorin.textn.injection.Injected;
import com.github.nsorin.textn.model.TextFile;
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
    private TextArea textArea;

    private FileManager fileManager;

    @Injected
    public void setFileLoader(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    private TextFile textFile = TextFile.makeNew();

    @FXML
    void onNewButtonClick(Event e) {
        textFile = TextFile.makeNew();
        setTextToFileContent();
        textArea.requestFocus();
    }

    @FXML
    void onOpenButtonClick(Event e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        openFile(file);
        textArea.requestFocus();
    }

    @FXML
    void onSaveButtonClick(Event e) {
        if (textFile.isNew()) {
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
            textFile = fileManager.loadFile(file);
            setTextToFileContent();
            textArea.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        setFileContentToText();
        try {
            fileManager.saveFile(textFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        textArea.requestFocus();
    }

    private void saveFileAs() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showSaveDialog(rootNode.getScene().getWindow());
        if (file != null) {
            setFileContentToText();
            try {
                textFile = fileManager.saveToFile(textFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textArea.requestFocus();
        }
    }

    private void setFileContentToText() {
        textFile.setContent(textArea.getText());
    }

    private void setTextToFileContent() {
        textArea.setText(textFile.getContent());
    }
}
