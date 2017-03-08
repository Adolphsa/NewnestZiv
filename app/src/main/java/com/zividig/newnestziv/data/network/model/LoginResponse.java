package com.zividig.newnestziv.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * 登录响应
 * Created by adolph
 * on 2017-03-01.
 */

public class LoginResponse {

    /**
     * status : 200
     * token : 2b0188aac4e71abea01604c04a272f38
     * alarm_status : open
     */

    @SerializedName("status")
    private int status;

    @SerializedName("token")
    private String token;

    @SerializedName("alarm_status")
    private String alarmStatus;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAlarmStatus() {
        return alarmStatus;
    }

    public void setAlarmStatus(String alarmStatus) {
        this.alarmStatus = alarmStatus;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "status=" + status +
                ", token='" + token + '\'' +
                ", alarmStatus='" + alarmStatus + '\'' +
                '}';
    }
}
