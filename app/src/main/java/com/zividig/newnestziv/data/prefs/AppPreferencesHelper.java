package com.zividig.newnestziv.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.zividig.newnestziv.di.ApplicationContext;
import com.zividig.newnestziv.di.PreferenceInfo;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * Created by adolph
 * on 2017-02-27.
 */
@Singleton
public class AppPreferencesHelper implements PreferencesHelper{

    private static final String PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN";
    private static final String PREF_KEY_SAVE_USER = "PREF_KEY_SAVE_USER";
    private static final String PREF_KEY_SAVE_PASSWORD = "PREF_KEY_SAVE_PASSWORD";

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }

    @Override
    public String getAccessToken() {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN,null);
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN,accessToken).apply();
    }

    @Override
    public boolean getSaveUser() {
        return mPrefs.getBoolean(PREF_KEY_SAVE_USER,false);
    }

    @Override
    public void setSaveUser(boolean saveUser) {
        mPrefs.edit().putBoolean(PREF_KEY_SAVE_USER,saveUser).apply();
    }

    @Override
    public boolean getSavePassword() {
        return mPrefs.getBoolean(PREF_KEY_SAVE_PASSWORD,false);
    }

    @Override
    public void setSavePassword(boolean savePassword) {
        mPrefs.edit().putBoolean(PREF_KEY_SAVE_PASSWORD,savePassword).apply();
    }
}
