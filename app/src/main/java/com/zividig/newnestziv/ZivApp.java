package com.zividig.newnestziv;

import android.app.Application;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.di.component.ApplicationComponent;
import com.zividig.newnestziv.di.component.DaggerApplicationComponent;
import com.zividig.newnestziv.di.module.ApplicationModule;
import com.zividig.newnestziv.utils.MvpLoggers;

import javax.inject.Inject;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public class ZivApp extends Application{

    @Inject
    DataManager mDataManager;

    private static ZivApp instance;

    private ApplicationComponent mApplicationComponent;

    public static synchronized ZivApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        mApplicationComponent.inject(this);

        MvpLoggers.init();
    }


    public ApplicationComponent getComponent() {
        return mApplicationComponent;
    }


    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
