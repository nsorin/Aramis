package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManagerFilesystem;
import com.github.nsorin.aramis.ui.utils.MockFileSelector;
import com.github.nsorin.aramis.utils.MockEventObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
                new FileManagerFilesystem()
        );
    }

    @Test
    void newFile() {
        applicationState.setTextContent(new TextContent("location", "name", "content"));
        applicationState.setFileProperties(new FileProperties("location", "name"));

        command.newFile();

        assertNull(applicationState.getFileProperties().getLocation());
        assertNull(applicationState.getFileProperties().getName());
        assertEquals("", applicationState.getTextContent().getContent());
        assertFalse(applicationState.isSaved());
    }
}
