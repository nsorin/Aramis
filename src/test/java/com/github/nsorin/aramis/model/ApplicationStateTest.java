package com.github.nsorin.aramis.model;

import com.github.nsorin.aramis.model.event.DisplayConfigurationUpdated;
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
        assertEquals("", applicationState.getTextContent().getText());
    }

    @Test
    void constructsWithEmptyFileProperties() {
        assertNotNull(applicationState.getFileProperties());
        assertNull(applicationState.getFileProperties().getLocation());
        assertNull(applicationState.getFileProperties().getName());
    }

    @Test
    void constructsWithDefaultDisplayConfiguration() {
        assertNotNull(applicationState.getDisplayConfiguration());
        assertFalse(applicationState.getDisplayConfiguration().isFullScreen());
        assertEquals(100.0, applicationState.getDisplayConfiguration().getZoomLevel());
        assertFalse(applicationState.getDisplayConfiguration().isDarkMode());
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

        assertNotNull(mockObserver.getNthEvent(0));
        assertTrue(mockObserver.getNthEvent(0) instanceof SaveStatusUpdated);
        assertNotNull(mockObserver.getNthEvent(1));
        assertTrue(mockObserver.getNthEvent(1) instanceof FocusInput);
        assertTrue(((SaveStatusUpdated) mockObserver.getNthEvent(0)).saved());

        applicationState.setSaved(false);

        assertFalse(((SaveStatusUpdated) mockObserver.getNthEvent(2)).saved());
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
        applicationState.setFileProperties(new FileProperties("", "", false));
        assertFalse(applicationState.canCloseSafely());
    }

    @Test
    void isNewAndEmpty_falseIfNotEmptyAndUnsaved() {
        applicationState.setTextContent(new TextContent("Hello"));
        assertFalse(applicationState.canCloseSafely());
    }

    @Test
    void getAndSetDisplayConfiguration() {
        DisplayConfiguration displayConfiguration = new DisplayConfiguration();

        applicationState.setDisplayConfiguration(displayConfiguration);

        assertSame(displayConfiguration, applicationState.getDisplayConfiguration());
    }

    @Test
    void setDisplayConfigurationEmitsEvents() {
        DisplayConfiguration displayConfiguration = new DisplayConfiguration();
        displayConfiguration.setFullScreen(true);
        displayConfiguration.setZoomLevel(90.0);
        displayConfiguration.setDarkMode(true);

        applicationState.setDisplayConfiguration(displayConfiguration);

        assertNotNull(mockObserver.getNthEvent(0));
        assertTrue(mockObserver.getNthEvent(0) instanceof DisplayConfigurationUpdated);
        assertSame(displayConfiguration, ((DisplayConfigurationUpdated) mockObserver.getNthEvent(0)).displayConfiguration());

        assertNotNull(mockObserver.getNthEvent(1));
        assertTrue(mockObserver.getNthEvent(1) instanceof FocusInput);
    }
}
