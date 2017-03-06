package com.zividig.newnestziv.data;

import android.content.Context;

import com.zividig.newnestziv.data.db.DbHelper;
import com.zividig.newnestziv.data.network.ApiHelper;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.data.prefs.PreferencesHelper;
import com.zividig.newnestziv.di.ApplicationContext;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Singleton
public class AppDataManager implements DataManager{

    private final Context mContext;
    private final DbHelper mDbHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final ApiHelper mApiHelper;

    @Inject
    public AppDataManager(@ApplicationContext Context context,
                          DbHelper dbHelper,
                          PreferencesHelper preferencesHelper,
                          ApiHelper apiHelper) {
        mContext = context;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
        mApiHelper = apiHelper;
    }

    @Override
    public Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body) {
        return mApiHelper.doLoginCall(options,body);
    }
}
