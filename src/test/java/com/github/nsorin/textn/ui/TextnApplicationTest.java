package com.github.nsorin.textn.ui;

import com.github.nsorin.textn.Textn;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@Tag("UITest")
class TextnApplicationTest extends ApplicationTest {

    @BeforeEach
    public void runAppToTests() throws Exception {
        Textn.provideDependencies();
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TextnApplication::new);
        FxToolkit.showStage();
        WaitForAsyncUtils.waitForFxEvents(100);
    }

    @AfterEach
    public void stopApp() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.toFront();
    }

    @Test
    void hasToolBarAndTextArea() {
        verifyThat(".tool-bar", isVisible());
        verifyThat("#textArea", isVisible());
    }

    @Test
    void startsWithEmptyText() {
        verifyThat("#textArea", hasText(""));
    }

    @Test
    void focusesTextAreaOnStart() {
        verifyThat("#textArea", isFocused());
    }

    @Test
    void canTypeInTextArea() {
        clickOn("#textArea");
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);

        verifyThat("#textArea", hasText("hello"));
    }

    @Test
    void newButtonResetsText() {
        clickOn("#textArea");
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);

        clickOn("#newButton");
        verifyThat("#textArea", hasText(""));
    }

    @Test
    void focusesTextAreaOnNew() {
        clickOn("#newButton");
        verifyThat("#textArea", isFocused());
    }
}
