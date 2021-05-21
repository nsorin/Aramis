package com.github.nsorin.textn.ui.controller;

import com.github.nsorin.textn.injection.Injected;
import com.github.nsorin.textn.service.FileLoader;
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

    private FileLoader fileLoader;

    @Injected
    public void setFileLoader(FileLoader fileLoader) {
        this.fileLoader = fileLoader;
    }

    @FXML
    void onOpenButtonClick(Event e) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        openFile(file);
    }

    public void openFile(File file) {
        try {
            textArea.setText(fileLoader.loadFile(file).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
