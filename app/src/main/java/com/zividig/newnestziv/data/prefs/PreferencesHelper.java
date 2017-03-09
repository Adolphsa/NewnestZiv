package com.zividig.newnestziv.data.prefs;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface PreferencesHelper {

    String getAccessToken();

    void setAccessToken(String accessToken);

    boolean getSaveUser();

    void setSaveUser(boolean saveUser);

    boolean getSavePassword();

    void setSavePassword(boolean savePassword);
}
