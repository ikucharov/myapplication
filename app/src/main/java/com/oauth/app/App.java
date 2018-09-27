package com.oauth.app;

import android.app.Application;

import com.oauth.app.dagger.AppComponent;
import com.oauth.app.dagger.AppModule;
import com.oauth.app.dagger.DaggerAppComponent;
import com.oauth.app.dagger.PresenterComponent;
import com.oauth.app.dagger.PresenterModule;
import com.oauth.app.util.PreferencesUtil;

import org.jetbrains.annotations.Contract;

import javax.inject.Inject;

public class App extends Application {
    private static AppComponent appComponent;
    private static PresenterComponent presenterComponent;

    @Inject
    PreferencesUtil preferencesUtil;

    @Override
    public void onCreate() {
        super.onCreate();

        AppModule appModule = new AppModule(this);
        appComponent = DaggerAppComponent.builder()
                .appModule(appModule)
                .build();
        appComponent.inject(this);

        PresenterModule presenterModule = new PresenterModule();
        presenterComponent = appComponent.plus(presenterModule);
    }

    @Contract(pure = true)
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    @Contract(pure = true)
    public static PresenterComponent getPresenterComponent() {
        return presenterComponent;
    }
}

