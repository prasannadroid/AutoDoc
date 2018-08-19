package com.autodoc;

import android.app.Application;

import com.autodoc.autodoc.data.UserSession;

/**
 * Created by ilabs on 8/17/18.
 */

public class App extends Application {

    public static App app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static App getInstance() {
        return app;
    }

    public UserSession getUserSession() {
        return UserSession.getInstance();
    }
}
