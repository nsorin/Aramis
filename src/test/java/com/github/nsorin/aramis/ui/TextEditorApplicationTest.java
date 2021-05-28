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
import org.testfx.matcher.control.TextMatchers;
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

    Stage stage;

    @BeforeEach
    public void runAppToTests() throws Exception {
        TextEditor.provideDependencies();
        provideDependencyStubs();
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(TextEditorApplication::new);
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
    void startsCorrectly() {
        verifyThat("#menu", isVisible());
        verifyThat("#inputArea", isVisible());
        verifyThat("#inputArea", hasText(""));
        verifyThat("#inputArea", isFocused());
        verifyThat("#footer", isVisible());
        verifyThat("#fileNameHolder", isVisible());
        verifyThat("#fileNameHolder", TextMatchers.hasText(""));
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
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveButton");
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndSaveFileAsWithoutChangingOriginalFile() throws IOException {
        String expectedText = "oh " + SkipChooserFileSelector.TEMP_FILE_CONTENT;

        clickOn("#openButton");
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveAsButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.NON_EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canSaveNewFile() throws IOException {
        clickOn("#openButton");
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        clickOn("#newButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(""));
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.NON_EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("hello", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndSaveFileWithShortcuts() throws IOException {
        String expectedText = "oh " + SkipChooserFileSelector.TEMP_FILE_CONTENT;

        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        useShortcut(KeyCode.CONTROL, KeyCode.S);
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndSaveFileAsWithShortcuts() throws IOException {
        String expectedText = "oh " + SkipChooserFileSelector.TEMP_FILE_CONTENT;

        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        useShortcut(KeyCode.CONTROL, KeyCode.SHIFT, KeyCode.S);
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canSaveNewFileWithShortcuts() throws IOException {
        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(SkipChooserFileSelector.TEMP_FILE_CONTENT));

        useShortcut(KeyCode.CONTROL, KeyCode.N);
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);
        verifyThat("#inputArea", hasText("hello"));

        useShortcut(KeyCode.CONTROL, KeyCode.S);
        assertEquals(SkipChooserFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("hello", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    private void useShortcut(KeyCode... keys) {
        press(keys);
        release(keys);
    }

    private void provideDependencyStubs() {
        DependencyProvider.getProvider().provide(FileSelector.class, SkipChooserFileSelector.class);
    }
}
