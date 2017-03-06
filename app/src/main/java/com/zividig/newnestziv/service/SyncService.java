package com.zividig.newnestziv.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.zividig.newnestziv.ZivApp;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.di.component.DaggerServiceComponent;
import com.zividig.newnestziv.di.component.ServiceComponent;

import javax.inject.Inject;

public class SyncService extends Service {

    @Inject
    DataManager mDataManager;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ServiceComponent component = DaggerServiceComponent.builder()
                .applicationComponent(((ZivApp) getApplication()).getComponent())
                .build();
        component.inject(this);
    }
}
