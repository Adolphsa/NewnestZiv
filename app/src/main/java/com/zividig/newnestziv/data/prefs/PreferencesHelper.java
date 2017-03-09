package com.zividig.newnestziv.data.prefs;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    String getUser();

    void setUser(String user);

    String getPassword();

    void setPassword(String password);

    boolean getSaveUser();

    void setSaveUser(boolean saveUser);

    boolean getSavePassword();

    void setSavePassword(boolean savePassword);

    String getAlarmStateSwitch();

    void setAlarmStateSwitch(String alarmStateSwitch);
}
