package com.autodoc.autodoc.api.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ilabs on 8/17/18.
 */

public class LoginRequest {
    @SerializedName("username")
    public String userName;

    @SerializedName("password")
    public String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
