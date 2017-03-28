package com.zividig.newnestziv.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adolph
 * on 2017-03-28.
 */

public class CarLocationBody {

    @SerializedName("devid")
    public String devid;

    @SerializedName("token")
    public String token;

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
