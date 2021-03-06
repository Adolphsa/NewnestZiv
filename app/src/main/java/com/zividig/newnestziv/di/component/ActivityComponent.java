package com.zividig.newnestziv.di.component;

import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.di.module.ActivityModule;
import com.zividig.newnestziv.ui.carinfo.CarInfoActivity;
import com.zividig.newnestziv.ui.carlocation.CarLocationActivity;
import com.zividig.newnestziv.ui.fence.FenceActivity;
import com.zividig.newnestziv.ui.fragment.message.MessageFragment;
import com.zividig.newnestziv.ui.fragment.my.MyFragment;
import com.zividig.newnestziv.ui.fragment.mycar.MyCarFragment;
import com.zividig.newnestziv.ui.fragment.setting.SettingFragment;
import com.zividig.newnestziv.ui.login.LoginActivity;
import com.zividig.newnestziv.ui.main.MainActivity;
import com.zividig.newnestziv.ui.snap.SnapPictureActivity;
import com.zividig.newnestziv.ui.track.TrackActivity;
import com.zividig.newnestziv.ui.video.VideoActivity;

import dagger.Component;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

    void inject(MyCarFragment fragment);

    void inject(MessageFragment fragment);

    void inject(SettingFragment fragment);

    void inject(MyFragment fragment);

    void inject(SnapPictureActivity activity);

    void inject(CarLocationActivity activity);

    void inject(TrackActivity activity);

    void inject(VideoActivity activity);

    void inject(CarInfoActivity activity);

    void inject(FenceActivity activity);
}
