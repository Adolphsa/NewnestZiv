package com.zividig.newnestziv.di.component;

import android.app.Application;
import android.content.Context;

import com.zividig.newnestziv.ZivApp;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.di.ApplicationContext;
import com.zividig.newnestziv.di.module.ApplicationModule;
import com.zividig.newnestziv.service.SyncService;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(ZivApp app);

    void inject(SyncService service);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}
