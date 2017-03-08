package com.zividig.newnestziv.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adolph
 * on 2017-03-08.
 */

public class DeviceListBody {

    @SerializedName("username")
    public String userName;

    @SerializedName("token")
    public String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
