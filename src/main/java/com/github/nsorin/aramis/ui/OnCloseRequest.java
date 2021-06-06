package com.github.nsorin.aramis.ui;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.model.ApplicationState;
import com.github.nsorin.aramis.ui.command.FileCommand;
import com.github.nsorin.aramis.ui.service.ConfirmService;
import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class OnCloseRequest implements EventHandler<WindowEvent> {

    public static final String UNSAVED_CONFIRM_TITLE = "Closing application with unsaved changes";
    public static final String UNSAVED_CONFIRM_CONTENT = "Current file is not saved. Do you want so save it before closing?";

    @Injectable
    ApplicationState applicationState;

    @Injectable
    ConfirmService confirmService;

    @Injectable
    FileCommand fileCommand;

    @Override
    public void handle(WindowEvent windowEvent) {
        if (!applicationState.canCloseSafely()) {
            confirmService.confirm(
                    UNSAVED_CONFIRM_TITLE,
                    UNSAVED_CONFIRM_CONTENT,
                    () -> fileCommand.saveFile((Window) windowEvent.getSource()),
                    () -> {
                    },
                    windowEvent::consume
            );
        }
    }
}
