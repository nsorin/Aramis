package com.github.nsorin.aramis.model;

public class DisplayConfiguration {

    private boolean fullScreen = false;
    private double zoomLevel = 100.0;
    private boolean darkMode = false;

    public boolean isFullScreen() {
        return fullScreen;
    }

    public double getZoomLevel() {
        return zoomLevel;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
    }

    public void setZoomLevel(double zoomLevel) {
        this.zoomLevel = zoomLevel;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }
}
