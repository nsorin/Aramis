package com.github.nsorin.aramis.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationStateTest {

    private ApplicationState applicationState;

    @BeforeEach
    void setUp() {
        applicationState = new ApplicationState();
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

}
