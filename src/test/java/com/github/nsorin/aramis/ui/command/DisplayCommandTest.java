package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.utils.MockEventObserver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DisplayCommandTest {
    ApplicationState applicationState;
    DisplayCommand command;

    @BeforeEach
    void setUp() {
        applicationState = new ApplicationState(new MockEventObserver());
        command = new DisplayCommand(applicationState);
    }

    @Test
    void toggleFullScreen() {
        command.toggleFullScreen();
        assertTrue(applicationState.getDisplayConfiguration().isFullScreen());

        command.toggleFullScreen();
        assertFalse(applicationState.getDisplayConfiguration().isFullScreen());
    }

    @Test
    void zoomIn() {
        command.zoomIn();
        assertEquals(110.0, applicationState.getDisplayConfiguration().getZoomLevel());

        command.zoomIn();
        assertEquals(120.0, applicationState.getDisplayConfiguration().getZoomLevel());
    }

    @Test
    void zoomOut() {
        command.zoomOut();
        assertEquals(90, applicationState.getDisplayConfiguration().getZoomLevel());

        command.zoomOut();
        assertEquals(80, applicationState.getDisplayConfiguration().getZoomLevel());
    }

    @Test
    void toggleDarkMode() {
        command.toggleDarkMode();
        assertTrue(applicationState.getDisplayConfiguration().isDarkMode());

        command.toggleDarkMode();
        assertFalse(applicationState.getDisplayConfiguration().isDarkMode());
    }
}
