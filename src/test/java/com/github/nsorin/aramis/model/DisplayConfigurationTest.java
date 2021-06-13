package com.github.nsorin.aramis.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayConfigurationTest {

    @Test
    void constructConfiguration() {
        DisplayConfiguration config = new DisplayConfiguration();
        assertFalse(config.isFullScreen());
        assertEquals(100.0, config.getZoomLevel());
        assertFalse(config.isDarkMode());
    }

    @Test
    void getAndSetFullScreen() {
        DisplayConfiguration config = new DisplayConfiguration();
        config.setFullScreen(true);
        assertTrue(config.isFullScreen());
    }

    @Test
    void getAndSetZoomLevel() {
        DisplayConfiguration config = new DisplayConfiguration();
        config.setZoomLevel(90.0);
        assertEquals(90.0, config.getZoomLevel());
    }

    @Test
    void getAndSetDarkMode() {
        DisplayConfiguration config = new DisplayConfiguration();
        config.setDarkMode(true);
        assertTrue(config.isDarkMode());
    }
}
