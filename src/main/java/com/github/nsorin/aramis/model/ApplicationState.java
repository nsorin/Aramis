package com.github.nsorin.aramis.model;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.event.DisplayConfigurationUpdated;
import com.github.nsorin.aramis.model.event.FilePropertiesUpdated;
import com.github.nsorin.aramis.model.event.SaveStatusUpdated;
import com.github.nsorin.aramis.model.event.TextContentUpdated;
import com.github.nsorin.aramis.observer.EventObserverInterface;
import com.github.nsorin.aramis.ui.command.event.FocusInput;

public class ApplicationState {
    private TextContent textContent;
    private FileProperties fileProperties;
    private DisplayConfiguration displayConfiguration;
    private final EventObserverInterface eventObserver;
    private boolean saved;

    @Injectable
    public ApplicationState(EventObserverInterface eventObserver) {
        this.eventObserver = eventObserver;
        textContent = new TextContent();
        fileProperties = new FileProperties();
        displayConfiguration = new DisplayConfiguration();
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
        this.eventObserver.emit(new TextContentUpdated(textContent));
        this.eventObserver.emit(new FocusInput());
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
        this.eventObserver.emit(new FilePropertiesUpdated(fileProperties));
    }

    public FileProperties getFileProperties() {
        return fileProperties;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
        eventObserver.emit(new SaveStatusUpdated(saved));
        eventObserver.emit(new FocusInput());
    }

    public boolean isSaved() {
        return saved;
    }

    public boolean canCloseSafely() {
        return saved || isNewAndEmpty();
    }

    private boolean isNewAndEmpty() {
        return fileProperties.isNew() && textContent.getText().equals("");
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return displayConfiguration;
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.displayConfiguration = displayConfiguration;
        eventObserver.emit(new DisplayConfigurationUpdated(displayConfiguration));
        eventObserver.emit(new FocusInput());
    }
}
