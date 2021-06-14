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
    void zoomInDoesNotGoAbove300() {
        zoomInNTimes(21);
        assertEquals(300.0, applicationState.getDisplayConfiguration().getZoomLevel());
    }

    private void zoomInNTimes(int n) {
        for (int i = 0; i < n; i++) {
            command.zoomIn();
        }
    }

    @Test
    void zoomOut() {
        command.zoomOut();
        assertEquals(90.0, applicationState.getDisplayConfiguration().getZoomLevel());

        command.zoomOut();
        assertEquals(80.0, applicationState.getDisplayConfiguration().getZoomLevel());
    }

    @Test
    void zoomOutDoesNotGoBelow50() {
        zoomOutNTimes(6);
        assertEquals(50.0, applicationState.getDisplayConfiguration().getZoomLevel());
    }

    private void zoomOutNTimes(int n) {
        for (int i = 0; i < n; i++) {
            command.zoomOut();
        }
    }

    @Test
    void toggleDarkMode() {
        command.toggleDarkMode();
        assertTrue(applicationState.getDisplayConfiguration().isDarkMode());

        command.toggleDarkMode();
        assertFalse(applicationState.getDisplayConfiguration().isDarkMode());
    }
}
