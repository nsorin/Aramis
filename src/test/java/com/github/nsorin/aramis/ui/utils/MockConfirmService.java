package com.github.nsorin.aramis.ui.utils;

import com.github.nsorin.aramis.ui.service.ConfirmService;

public class MockConfirmService implements ConfirmService {
    @Override
    public void confirm(String title, String content, Runnable yesAction, Runnable noAction) {
        noAction.run();
    }
}
