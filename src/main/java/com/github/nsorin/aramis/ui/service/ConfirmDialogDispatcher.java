package com.github.nsorin.aramis.ui.service;

import com.github.nsorin.aramis.injector.Injectable;
import com.github.nsorin.aramis.observer.EventObserverInterface;
import com.github.nsorin.aramis.ui.command.event.FocusInput;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class ConfirmDialogDispatcher implements ConfirmService {

    @Injectable
    EventObserverInterface eventObserver;

    public void confirm(String title, String content, Runnable yesAction, Runnable noAction) {
        confirm(title, content, yesAction, noAction, () -> {
        });
    }

    @Override
    public void confirm(String title, String content, Runnable yesAction, Runnable noAction, Runnable cancelAction) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait().ifPresent(type -> {
            if (type == ButtonType.YES) {
                yesAction.run();
            } else if (type == ButtonType.NO) {
                noAction.run();
            } else {
                cancelAction.run();
                eventObserver.emit(new FocusInput());
            }
        });
    }
}
