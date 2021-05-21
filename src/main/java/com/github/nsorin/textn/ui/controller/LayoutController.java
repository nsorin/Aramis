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

    private TextFile textFile;

    @FXML
    void onOpenButtonClick(Event e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        openFile(file);
    }

    @FXML
    void onSaveButtonClick(Event e) {
        textFile.setContent(textArea.getText());
        saveFile();
    }

    private void openFile(File file) {
        try {
            textFile = fileManager.loadFile(file);
            textArea.setText(textFile.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveFile() {
        try {
            fileManager.saveFile(textFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
