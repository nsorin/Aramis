package com.github.nsorin.aramis.ui.command;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.model.FileProperties;
import com.github.nsorin.aramis.model.TextContent;
import com.github.nsorin.aramis.service.FileManager;
import com.github.nsorin.aramis.ui.service.FileSelector;

public class FileCommand {
    private final ApplicationState applicationState;
    private final FileSelector fileSelector;
    private final FileManager fileManager;

    @Injectable
    public FileCommand(ApplicationState applicationState, FileSelector fileSelector, FileManager fileManager) {
        this.applicationState = applicationState;
        this.fileSelector = fileSelector;
        this.fileManager = fileManager;
    }

    public void newFile() {
        this.applicationState.setTextContent(new TextContent());
        this.applicationState.setFileProperties(new FileProperties());
        this.applicationState.setSaved(false);
    }
}
