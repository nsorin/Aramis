package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.axml.AXMLReader;
import com.github.nsorin.aramis.axml.AXMLWriter;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManagerFilesystem;
import com.github.nsorin.aramis.ui.service.AlertDispatcher;
import com.github.nsorin.aramis.ui.utils.MockConfirmService;
import com.github.nsorin.aramis.ui.utils.MockFileSelector;
import com.github.nsorin.aramis.utils.MockEventObserver;
import com.github.nsorin.aramis.utils.TestFileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileCommandTest {

    ApplicationState applicationState;
    FileCommand command;

    @BeforeEach
    void setUp() {
        applicationState = new ApplicationState(new MockEventObserver());
        command = new FileCommand(
                applicationState,
                new MockFileSelector(),
                new FileManagerFilesystem(new AXMLReader(), new AXMLWriter()),
                new AlertDispatcher(),
                new MockConfirmService()
        );
    }

    @Test
    void newFile() {
        applicationState.setTextContent(new TextContent("content"));
        applicationState.setFileProperties(new FileProperties("location", "name", false));

        command.newFile(null);

        assertNull(applicationState.getFileProperties().getLocation());
        assertNull(applicationState.getFileProperties().getName());
        assertEquals("", applicationState.getTextContent().getText());
        assertFalse(applicationState.isSaved());
    }

    @Test
    void openFile() {
        command.openFile(null);

        assertEquals(MockFileSelector.TEMP_FILE_CONTENT, applicationState.getTextContent().getText());
        assertEquals(TestFileUtils.EXISTING_FILE_PATH, applicationState.getFileProperties().getLocation());
        assertEquals(TestFileUtils.EXISTING_FILE_NAME, applicationState.getFileProperties().getName());
        assertTrue(applicationState.isSaved());
    }

    @Test
    void saveFileAs() throws IOException {
        TestFileUtils.createExistingTempFile("old content");

        applicationState.setTextContent(new TextContent("new content"));
        applicationState.setFileProperties(new FileProperties(TestFileUtils.EXISTING_FILE_PATH, TestFileUtils.EXISTING_FILE_NAME, false));
        applicationState.setSaved(false);

        command.saveFileAs(null);

        assertEquals("old content", Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals("new content", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        assertEquals(TestFileUtils.NON_EXISTING_FILE_PATH, applicationState.getFileProperties().getLocation());
        assertEquals(TestFileUtils.NON_EXISTING_FILE_NAME, applicationState.getFileProperties().getName());
        assertTrue(applicationState.isSaved());
    }

    @Test
    void saveFileExistingFile() throws IOException {
        TestFileUtils.createExistingTempFile("old content");

        applicationState.setTextContent(new TextContent("new content"));
        applicationState.setFileProperties(new FileProperties(TestFileUtils.EXISTING_FILE_PATH, TestFileUtils.EXISTING_FILE_NAME, false));
        applicationState.setSaved(false);

        command.saveFile(null);

        assertEquals("new content", Files.readString(Path.of(TestFileUtils.EXISTING_FILE_PATH)));
        assertEquals(TestFileUtils.EXISTING_FILE_PATH, applicationState.getFileProperties().getLocation());
        assertEquals(TestFileUtils.EXISTING_FILE_NAME, applicationState.getFileProperties().getName());
        assertTrue(applicationState.isSaved());
    }

    @Test
    void saveFileNonExistingFile() throws IOException {
        applicationState.setTextContent(new TextContent("new content"));
        applicationState.setFileProperties(new FileProperties());
        applicationState.setSaved(false);

        command.saveFile(null);

        assertEquals("<axml><text>new content</text></axml>", Files.readString(Path.of(TestFileUtils.NON_EXISTING_FILE_PATH)));
        assertEquals(TestFileUtils.NON_EXISTING_FILE_PATH, applicationState.getFileProperties().getLocation());
        assertEquals(TestFileUtils.NON_EXISTING_FILE_NAME, applicationState.getFileProperties().getName());
        assertTrue(applicationState.isSaved());
    }

    @Test
    void reloadFile() throws IOException {
        TestFileUtils.createExistingTempFile("old content");

        applicationState.setTextContent(new TextContent("new content"));
        applicationState.setFileProperties(new FileProperties(TestFileUtils.EXISTING_FILE_PATH, TestFileUtils.EXISTING_FILE_NAME, false));
        applicationState.setSaved(false);

        command.reloadFile();

        assertEquals("old content", applicationState.getTextContent().getText());
        assertEquals(TestFileUtils.EXISTING_FILE_PATH, applicationState.getFileProperties().getLocation());
        assertEquals(TestFileUtils.EXISTING_FILE_NAME, applicationState.getFileProperties().getName());
        assertTrue(applicationState.isSaved());
    }
}
