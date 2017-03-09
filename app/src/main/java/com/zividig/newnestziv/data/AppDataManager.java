package com.zividig.newnestziv.data;

import android.content.Context;

import com.zividig.newnestziv.data.db.DbHelper;
import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.data.network.ApiHelper;
import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.data.prefs.PreferencesHelper;
import com.zividig.newnestziv.di.ApplicationContext;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.RequestBody;
import io.reactivex.Observable;

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

    //----------------------ApiHelper----------------------------
    @Override
    public Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body) {
        return mApiHelper.doLoginCall(options,body);
    }

    @Override
    public Observable<DeviceListResponse> doGetDeviceList(Map<String, String> options, RequestBody body) {
        return mApiHelper.doGetDeviceList(options,body);
    }



    //-----------------------DbHelper-----------------------------
    @Override
    public io.reactivex.Observable<Long> insertUser(Users user) {
        return mDbHelper.insertUser(user);
    }


    //----------------------PreferencesHelper----------------------
    @Override
    public String getAccessToken() {
        return mPreferencesHelper.getAccessToken();
    }

    @Override
    public void setAccessToken(String accessToken) {
        mPreferencesHelper.setAccessToken(accessToken);
    }

    @Override
    public boolean getSaveUser() {
        return mPreferencesHelper.getSaveUser();
    }

    @Override
    public void setSaveUser(boolean saveUser) {
        mPreferencesHelper.setSaveUser(saveUser);
    }

    @Override
    public boolean getSavePassword() {
        return mPreferencesHelper.getSavePassword();
    }

    @Override
    public void setSavePassword(boolean savePassword) {
        mPreferencesHelper.setSavePassword(savePassword);
    }
}
