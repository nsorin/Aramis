package com.github.nsorin.textn.ui;

import com.github.nsorin.textn.Textn;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.concurrent.TimeoutException;

import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

@Tag("UITest")
class TextnApplicationTest  extends ApplicationTest {

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
    public void start(Stage primaryStage){
        primaryStage.toFront();
    }

    @Test
    void hasToolBarAndTextArea() {
        FxAssert.verifyThat(".tool-bar", isVisible());
        FxAssert.verifyThat("#textArea", isVisible());
    }

    @Test
    void startsWithEmptyText() {
        FxAssert.verifyThat("#textArea", hasText(""));
    }
}
