package com.github.nsorin.aramis.model;

import com.github.nsorin.aramis.event.TextContentUpdated;
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
    void setContentEmitsEvent() {
        TextContent content = new TextContent();

        applicationState.setTextContent(content);

        assertNotNull(mockObserver.getEvent());
        assertTrue(mockObserver.getEvent() instanceof TextContentUpdated);
        assertSame(content, ((TextContentUpdated) mockObserver.getEvent()).textContent());
    }

}
