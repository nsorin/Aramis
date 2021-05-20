package com.github.nsorin.textn;

import com.github.nsorin.textn.injection.DependencyProvider;
import com.github.nsorin.textn.service.Service;
import com.github.nsorin.textn.service.ServiceImpl;
import com.github.nsorin.textn.ui.TextnApplication;
import javafx.application.Application;

public class Textn {
    public static void main(String[] args) {
        provideDependencies();
        Application.launch(TextnApplication.class, args);
    }

    private static void provideDependencies() {
        DependencyProvider.getProvider().provide(Service.class, ServiceImpl.class);
    }
}
