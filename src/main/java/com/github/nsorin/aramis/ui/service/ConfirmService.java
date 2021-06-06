package com.github.nsorin.aramis.ui.service;

public interface ConfirmService {
    void confirm(String title, String content, Runnable yesAction, Runnable noAction);

    void confirm(String title, String content, Runnable yesAction, Runnable noAction, Runnable cancelAction);
}
