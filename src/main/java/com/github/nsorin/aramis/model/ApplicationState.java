package com.github.nsorin.aramis.model;

import com.github.nsorin.aramis.event.TextContentUpdated;
import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.observer.EventObserverInterface;

public class ApplicationState {
    private TextContent textContent;
    private FileProperties fileProperties;
    private final EventObserverInterface eventObserver;

    @Injectable
    public ApplicationState(EventObserverInterface eventObserver) {
        this.eventObserver = eventObserver;
        textContent = new TextContent();
        fileProperties = new FileProperties();
    }

    public void setTextContent(TextContent textContent) {
        this.textContent = textContent;
        this.eventObserver.emit(new TextContentUpdated(textContent));
    }

    public TextContent getTextContent() {
        return textContent;
    }

    public void setFileProperties(FileProperties fileProperties) {
        this.fileProperties = fileProperties;
    }

    public FileProperties getFileProperties() {
        return fileProperties;
    }
}
