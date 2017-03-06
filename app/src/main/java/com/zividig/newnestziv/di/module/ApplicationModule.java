package com.zividig.newnestziv.di.module;

import android.app.Application;
import android.content.Context;

import com.zividig.newnestziv.data.AppDataManager;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.db.AppDbHelper;
import com.zividig.newnestziv.data.db.DbHelper;
import com.zividig.newnestziv.data.network.ApiHelper;
import com.zividig.newnestziv.data.network.AppApiHelper;
import com.zividig.newnestziv.data.prefs.AppPreferencesHelper;
import com.zividig.newnestziv.data.prefs.PreferencesHelper;
import com.zividig.newnestziv.di.ApiInfo;
import com.zividig.newnestziv.di.ApplicationContext;
import com.zividig.newnestziv.di.DatabaseInfo;
import com.zividig.newnestziv.di.PreferenceInfo;
import com.zividig.newnestziv.utils.AppConstants;
import com.zividig.newnestziv.utils.SignatureUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Module
public class ApplicationModule {

    private final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }

    @Provides
    @ApiInfo
    String provideApiKey() {
        return SignatureUtils.APP_KEY;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }
}
