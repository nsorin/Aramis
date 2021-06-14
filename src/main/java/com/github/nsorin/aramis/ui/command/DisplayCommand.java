package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.DisplayConfiguration;

public class DisplayCommand {
    private static final double ZOOM_STEP = 10.0;
    private static final double MIN_ZOOM_LEVEL = 50.0;
    private static final double MAX_ZOOM_LEVEL = 300.0;

    private final ApplicationState applicationState;

    @Injectable
    public DisplayCommand(ApplicationState applicationState) {
        this.applicationState = applicationState;
    }

    public void toggleFullScreen() {
        DisplayConfiguration configuration = getConfigurationCopy();
        configuration.setFullScreen(!configuration.isFullScreen());
        applicationState.setDisplayConfiguration(configuration);
    }

    public void zoomIn() {
        DisplayConfiguration configuration = getConfigurationCopy();
        double desiredZoomLevel = configuration.getZoomLevel() + ZOOM_STEP;
        configuration.setZoomLevel(Math.min(desiredZoomLevel, MAX_ZOOM_LEVEL));
        applicationState.setDisplayConfiguration(configuration);
    }

    public void zoomOut() {
        DisplayConfiguration configuration = getConfigurationCopy();
        double desiredZoomLevel = configuration.getZoomLevel() - ZOOM_STEP;
        configuration.setZoomLevel(Math.max(desiredZoomLevel, MIN_ZOOM_LEVEL));
        applicationState.setDisplayConfiguration(configuration);
    }

    public void toggleDarkMode() {
        DisplayConfiguration configuration = getConfigurationCopy();
        configuration.setDarkMode(!configuration.isDarkMode());
        applicationState.setDisplayConfiguration(configuration);
    }

    private DisplayConfiguration getConfigurationCopy() {
        DisplayConfiguration displayConfiguration = applicationState.getDisplayConfiguration();
        DisplayConfiguration newConfiguration = new DisplayConfiguration();
        newConfiguration.setFullScreen(displayConfiguration.isFullScreen());
        newConfiguration.setZoomLevel(displayConfiguration.getZoomLevel());
        newConfiguration.setDarkMode(displayConfiguration.isDarkMode());
        return newConfiguration;
    }
}
