package com.github.nsorin.aramis.ui;

import com.github.nsorin.aramis.TextEditor;
import com.github.nsorin.aramis.injector.DependencyProvider;
import com.github.nsorin.aramis.ui.service.ConfirmService;
import com.github.nsorin.aramis.ui.service.FileSelector;
import com.github.nsorin.aramis.ui.utils.MockConfirmService;
import com.github.nsorin.aramis.ui.utils.MockFileSelector;
import com.github.nsorin.aramis.utils.TestFileUtils;
import javafx.css.Styleable;
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isFocused;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.base.StyleableMatchers.hasStyle;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

@Tag("UITest")
class TextEditorApplicationTest extends ApplicationTest {

    @BeforeEach
    public void runAppToTests() throws Exception {
        Files.deleteIfExists(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH));
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
        verifyThat("#statusBar", isVisible());
        verifyThat("#fileTypeHolder", isVisible());
        verifyThat("#fileTypeHolder", TextMatchers.hasText("AXML"));
        verifyThat("#fileNameHolder", isVisible());
        verifyThat("#fileNameHolder", TextMatchers.hasText(""));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));
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
        useShortcut(KeyCode.ENTER);
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void focusesTextAreaOnNew() {
        DependencyProvider.getProvider().provide(ConfirmService.class, MockConfirmService.class);
        clickOn("#newButton");
        useShortcut(KeyCode.ENTER);
        verifyThat("#inputArea", isFocused());
    }

    @Test
    void canOpenAndSaveFile() throws IOException {
        String expectedText = "oh " + MockFileSelector.TEMP_FILE_CONTENT;

        clickOn("#openButton");
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#fileTypeHolder", TextMatchers.hasText("Plain Text"));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveButton");
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        verifyThat("#inputArea", isFocused());
    }

    @Test
    void canOpenAndSaveFileAsWithoutChangingOriginalFile() throws IOException {
        String expectedText = "oh " + MockFileSelector.TEMP_FILE_CONTENT;

        clickOn("#openButton");
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveAsButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.NON_EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(MockFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        verifyThat("#inputArea", isFocused());
    }

    @Test
    void canSaveNewFile() throws IOException {
        clickOn("#openButton");
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        clickOn("#newButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(""));
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#saveButton");
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.NON_EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        assertEquals(MockFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("<axml><text>hello</text></axml>", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndReloadFile() {
        clickOn("#openButton");
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText("oh " + MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        clickOn("#reloadButton");
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
    }

    @Test
    void canOpenAndSaveFileWithShortcuts() throws IOException {
        String expectedText = "oh " + MockFileSelector.TEMP_FILE_CONTENT;

        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        useShortcut(KeyCode.CONTROL, KeyCode.S);
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndSaveFileAsWithShortcuts() throws IOException {
        String expectedText = "oh " + MockFileSelector.TEMP_FILE_CONTENT;

        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText(expectedText));

        useShortcut(KeyCode.CONTROL, KeyCode.SHIFT, KeyCode.S);
        assertEquals(MockFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(expectedText, Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canSaveNewFileWithShortcuts() throws IOException {
        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));

        useShortcut(KeyCode.CONTROL, KeyCode.N);
        type(KeyCode.H, KeyCode.E, KeyCode.L, KeyCode.L, KeyCode.O);
        verifyThat("#inputArea", hasText("hello"));

        useShortcut(KeyCode.CONTROL, KeyCode.S);
        assertEquals(MockFileSelector.TEMP_FILE_CONTENT, Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("<axml><text>hello</text></axml>", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void canOpenAndReloadFileWithShortcuts() {
        useShortcut(KeyCode.CONTROL, KeyCode.O);
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#fileNameHolder", TextMatchers.hasText(TestFileUtils.EXISTING_FILE_NAME));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));

        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        verifyThat("#inputArea", hasText("oh " + MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));

        useShortcut(KeyCode.CONTROL, KeyCode.R);
        verifyThat("#saveStatusHolder", TextMatchers.hasText("saved"));
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
    }

    @Test
    void showsAlertIfSavingFails() {
        clickOn("#openButton");
        File targetFile = new File(TestFileUtils.EXISTING_FILE_PATH);

        targetFile.setWritable(false);
        clickOn("#saveButton");
        verifyThat(".dialog-pane", isVisible());

        targetFile.setWritable(true);
    }

    @Test
    void showsAlertIfSavingAsFails() throws IOException {
        File targetFile = new File(TestFileUtils.NON_EXISTING_FILE_PATH);
        targetFile.createNewFile();
        targetFile.setWritable(false);

        clickOn("#openButton");
        clickOn("#saveAsButton");
        verifyThat(".dialog-pane", isVisible());

        targetFile.delete();
    }

    @Test
    void showsAlertIfReloadFails() {
        clickOn("#openButton");
        File targetFile = new File(TestFileUtils.EXISTING_FILE_PATH);
        targetFile.delete();

        clickOn("#reloadButton");
        verifyThat(".dialog-pane", isVisible());
    }

    @Test
    void showsConfirmDialogIfNewAndNotSaved_yes() throws IOException {
        clickOn("#openButton");
        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        clickOn("#newButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.ENTER);

        assertEquals(
                "oh " + MockFileSelector.TEMP_FILE_CONTENT,
                Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH))
        );

        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void showsConfirmDialogIfNewAndNotSaved_no() throws IOException {
        clickOn("#openButton");
        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        clickOn("#newButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.LEFT);
        useShortcut(KeyCode.ENTER);

        assertEquals(
                MockFileSelector.TEMP_FILE_CONTENT,
                Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH))
        );

        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void showsConfirmDialogIfNewAndNotSaved_cancel() throws IOException {
        clickOn("#openButton");
        type(KeyCode.O, KeyCode.H, KeyCode.SPACE);
        clickOn("#newButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.RIGHT);
        useShortcut(KeyCode.ENTER);

        assertEquals(
                MockFileSelector.TEMP_FILE_CONTENT,
                Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH))
        );

        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText("oh " + MockFileSelector.TEMP_FILE_CONTENT));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));
    }

    @Test
    void doesNotShowConfirmDialogIfNewAndEmptyAndUnsaved() {
        clickOn("#newButton");
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(""));
    }

    @Test
    void showsConfirmDialogIfOpenAndNotSaved_yes() throws IOException {
        type(KeyCode.O, KeyCode.H);
        clickOn("#openButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.ENTER);

        assertEquals(
                "<axml><text>oh</text></axml>",
                Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH))
        );
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
    }

    @Test
    void showsConfirmDialogIfOpenAndNotSaved_no() {
        type(KeyCode.O, KeyCode.H);
        clickOn("#openButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.LEFT);
        useShortcut(KeyCode.ENTER);

        assertTrue(Files.notExists(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
    }

    @Test
    void showsConfirmDialogIfOpenAndNotSaved_cancel() {
        type(KeyCode.O, KeyCode.H);
        clickOn("#openButton");
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.RIGHT);
        useShortcut(KeyCode.ENTER);

        assertTrue(Files.notExists(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText("oh"));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));
    }

    @Test
    void doesNotShowConfirmDialogIfOpenAndEmptyAndUnsaved() {
        clickOn("#openButton");
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText(MockFileSelector.TEMP_FILE_CONTENT));
    }

    @Test
    void showsConfirmDialogIfCloseAndNotSaved_cancel() {
        type(KeyCode.O, KeyCode.H);
        useShortcut(KeyCode.ALT, KeyCode.F4);
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.RIGHT);
        useShortcut(KeyCode.ENTER);

        assertTrue(Files.notExists(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        verifyThat("#inputArea", isFocused());
        verifyThat("#inputArea", hasText("oh"));
        verifyThat("#saveStatusHolder", TextMatchers.hasText("unsaved"));
    }

    @Test
    void showsConfirmDialogIfCloseAndNotSaved_yes() throws IOException {
        type(KeyCode.O, KeyCode.H);
        useShortcut(KeyCode.ALT, KeyCode.F4);
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.ENTER);

        assertEquals(
                "<axml><text>oh</text></axml>",
                Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH))
        );
    }

    @Test
    void showsConfirmDialogIfCloseAndNotSaved_no() {
        type(KeyCode.O, KeyCode.H);
        useShortcut(KeyCode.ALT, KeyCode.F4);
        verifyThat(".dialog-pane", isVisible());
        useShortcut(KeyCode.LEFT);
        useShortcut(KeyCode.ENTER);

        assertTrue(Files.notExists(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
    }

    @Test
    void zoomInAndOut() {
        type(KeyCode.O, KeyCode.H);

        clickOn("#zoomInButton");
        verifyThat((Styleable) lookup("#inputArea").tryQuery().orElseThrow(), hasStyle("-fx-font-size: 1.1em;"));

        clickOn("#zoomOutButton");
        clickOn("#zoomOutButton");
        verifyThat((Styleable) lookup("#inputArea").tryQuery().orElseThrow(), hasStyle("-fx-font-size: 0.9em;"));

        useShortcut(KeyCode.CONTROL, KeyCode.ADD);
        verifyThat((Styleable) lookup("#inputArea").tryQuery().orElseThrow(), hasStyle("-fx-font-size: 1.0em;"));

        useShortcut(KeyCode.CONTROL, KeyCode.SUBTRACT);
        verifyThat((Styleable) lookup("#inputArea").tryQuery().orElseThrow(), hasStyle("-fx-font-size: 0.9em;"));
    }

    private void useShortcut(KeyCode... keys) {
        press(keys);
        release(keys);
    }

    private void provideDependencyStubs() {
        DependencyProvider.getProvider().provide(FileSelector.class, MockFileSelector.class);
    }
}
