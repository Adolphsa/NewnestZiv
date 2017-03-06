package com.zividig.newnestziv.di.module;

import android.app.Service;

import dagger.Module;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
