package com.zividig.newnestziv.di.component;

import com.zividig.newnestziv.di.PerService;
import com.zividig.newnestziv.di.module.ServiceModule;
import com.zividig.newnestziv.service.SyncService;

import dagger.Component;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);
}
