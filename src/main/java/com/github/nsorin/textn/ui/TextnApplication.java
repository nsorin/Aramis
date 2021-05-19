package com.github.nsorin.textn.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TextnApplication extends Application {

	private static final String STAGE_TITLE = "Textn Text Editor";
	private static final String LAYOUT_FXML_PATH = "layout.fxml";

	@Override
	public void start(Stage stage) {
		stage.setTitle(STAGE_TITLE);
		stage.setMaximized(true);
		try {
			stage.setScene(buildScene());
		} catch (IOException e) {
			e.printStackTrace();
		}
		stage.show();
	}

	private Scene buildScene() throws IOException {
		Parent root;
		root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(LAYOUT_FXML_PATH)));
		return new Scene(root, 300, 275);
	}

}
