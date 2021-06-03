package com.github.nsorin.aramis.model;

import com.github.nsorin.aramis.model.event.FilePropertiesUpdated;
import com.github.nsorin.aramis.model.event.SaveStatusUpdated;
import com.github.nsorin.aramis.model.event.TextContentUpdated;
import com.github.nsorin.aramis.ui.command.event.FocusInput;
import com.github.nsorin.aramis.utils.MockEventObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationStateTest {

    private ApplicationState applicationState;
    private MockEventObserver mockObserver;

    @BeforeEach
    void setUp() {
        mockObserver = new MockEventObserver();
        applicationState = new ApplicationState(mockObserver);
    }

    @Test
    void constructsWithEmptyTextContent() {
        assertNotNull(applicationState.getTextContent());
        assertEquals("", applicationState.getTextContent().getContent());
    }

    @Test
    void constructsWithEmptyFileProperties() {
        assertNotNull(applicationState.getFileProperties());
        assertNull(applicationState.getFileProperties().getLocation());
        assertNull(applicationState.getFileProperties().getName());
    }

    @Test
    void getAndSetTextContent() {
        TextContent content = new TextContent();

        applicationState.setTextContent(content);

        assertSame(content, applicationState.getTextContent());
    }

    @Test
    void getAndSetFileProperties() {
        FileProperties properties = new FileProperties();

        applicationState.setFileProperties(properties);

        assertSame(properties, applicationState.getFileProperties());
    }

    @Test
    void getAndSetSaved() {
        applicationState.setSaved(true);
        assertTrue(applicationState.isSaved());

        applicationState.setSaved(false);
        assertFalse(applicationState.isSaved());
    }

    @Test
    void setTextContentEmitsEvent() {
        TextContent content = new TextContent();

        applicationState.setTextContent(content);

        assertNotNull(mockObserver.getLastEvent());
        assertTrue(mockObserver.getNthEvent(0) instanceof TextContentUpdated);
        assertTrue(mockObserver.getNthEvent(1) instanceof FocusInput);
        assertSame(content, ((TextContentUpdated) mockObserver.getNthEvent(0)).textContent());
    }

    @Test
    void setFilePropertiesEmitsEvent() {
        FileProperties fileProperties = new FileProperties();

        applicationState.setFileProperties(fileProperties);

        assertNotNull(mockObserver.getLastEvent());
        assertTrue(mockObserver.getLastEvent() instanceof FilePropertiesUpdated);
        assertSame(fileProperties, ((FilePropertiesUpdated) mockObserver.getLastEvent()).fileProperties());
    }

    @Test
    void setSavedEmitsEvent() {
        applicationState.setSaved(true);

        assertNotNull(mockObserver.getLastEvent());
        assertTrue(mockObserver.getLastEvent() instanceof SaveStatusUpdated);
        assertTrue(((SaveStatusUpdated) mockObserver.getLastEvent()).saved());

        applicationState.setSaved(false);

        assertFalse(((SaveStatusUpdated) mockObserver.getLastEvent()).saved());
    }

    @Test
    void canCloseSafely_trueIfNewAndEmpty() {
        assertTrue(applicationState.canCloseSafely());
    }

    @Test
    void canCloseSafely_trueSaved() {
        applicationState.setTextContent(new TextContent("Hello"));
        applicationState.setSaved(true);
        assertTrue(applicationState.canCloseSafely());
    }

    @Test
    void isNewAndEmpty_falseIfNotNewAndUnsaved() {
        applicationState.setFileProperties(new FileProperties("", ""));
        assertFalse(applicationState.canCloseSafely());
    }

    @Test
    void isNewAndEmpty_falseIfNotEmptyAndUnsaved() {
        applicationState.setTextContent(new TextContent("Hello"));
        assertFalse(applicationState.canCloseSafely());
    }

}
