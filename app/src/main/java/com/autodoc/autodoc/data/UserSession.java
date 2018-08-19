package com.autodoc.autodoc.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.autodoc.App;
import com.google.gson.Gson;

/**
 * Created by ilabs on 8/19/18.
 */

public class UserSession {

    private static final String SP_NAME = "AUTO_DOC";
    private static final String AUTH_TOKEN = "AUTH_TOKEN";
    private static final String USER = "USER";
    private static UserSession userSession;
    private SharedPreferences sharedPreferences;

    private UserSession() {
        sharedPreferences = App.getInstance().getApplicationContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public static UserSession getInstance() {
        if (userSession == null) {
            userSession = new UserSession();
            return userSession;
        }
        return userSession;
    }

    public boolean setAuthToken(String authToken) {
        return sharedPreferences.edit().putString(AUTH_TOKEN, authToken).commit();
    }

    public String getAuthToken() {
        return sharedPreferences.getString(AUTH_TOKEN, null);
    }

    public void saveUser(User user) {
        String userJson = new Gson().toJson(user);
        sharedPreferences.edit().putString(USER, userJson).apply();
    }

    public User getUser() {
        String userJon = sharedPreferences.getString(USER, null);
        return new Gson().fromJson(userJon, User.class);
    }

    public void logoutUser(){
        sharedPreferences.edit().clear().commit();
    }
}
