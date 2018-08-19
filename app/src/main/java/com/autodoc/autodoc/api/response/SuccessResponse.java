package com.autodoc.autodoc.api.response;

import com.autodoc.autodoc.data.User;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ilabs on 8/17/18.
 */

public class SuccessResponse {
    public boolean success;
    public String message;
    public String token;

    @SerializedName("user")
    public User user;
}
