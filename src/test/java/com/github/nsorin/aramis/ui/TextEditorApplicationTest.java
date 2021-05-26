package com.github.nsorin.aramis.ui;

import com.github.nsorin.aramis.TextEditor;
import com.github.nsorin.aramis.injection.DependencyProvider;
import com.github.nsorin.aramis.ui.service.FileSelector;
import com.github.nsorin.aramis.ui.utils.SkipChooserFileSelector;
import com.github.nsorin.aramis.utils.TestFileUtils;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@Tag("UITest")
class TextEditorApplicationTest extends ApplicationTest {

    @BeforeEach
    public void runAppToTests() throws Exception {
        TextEditor.provideDependencies();
        provideDependencyStubs();
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
        verifyThat("#inputArea", isVisible());
    }

    @Test
    void startsWithEmptyText() {
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void focusesTextAreaOnStart() {
        verifyThat("#inputArea", isFocused());
    }

    @Test
    void canTypeInTextArea() {
        clickOn("#inputArea");
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);

        verifyThat("#inputArea", hasText("hello"));
    }

    @Test
    void newButtonResetsText() {
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);

        clickOn("#newButton");
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void focusesTextAreaOnNew() {
        clickOn("#newButton");
        verifyThat("#inputArea", isFocused());
    }

    @Test
    void canOpenAndSaveFile() throws IOException {
        String expectedText = "oh " + SkipChooserFileSelector.TEMP_FILE_CONTENT;

        clickOn("#openButton");
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        clickOn("#saveButton");
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndSaveFileAsWithoutChangingOriginalFile() throws IOException {
        String expectedText = "oh " + SkipChooserFileSelector.TEMP_FILE_CONTENT;

        clickOn("#openButton");
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        clickOn("#saveAsButton");
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canSaveNewFile() throws IOException {
        clickOn("#openButton");
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        clickOn("#newButton");
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);

        clickOn("#saveButton");
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("hello", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    private void provideDependencyStubs() {
        DependencyProvider.getProvider().provide(FileSelector.class, SkipChooserFileSelector.class);
    }
}
