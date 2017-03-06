package com.zividig.newnestziv.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.zividig.newnestziv.di.ApplicationContext;
import com.zividig.newnestziv.di.PreferenceInfo;

import javax.inject.Inject;

/**
 *
 * Created by adolph
 * on 2017-02-27.
 */

public class AppPreferencesHelper implements PreferencesHelper{

    private final SharedPreferences mPrefs;

    @Inject
    public AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefFileName) {
        mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE);
    }
}
