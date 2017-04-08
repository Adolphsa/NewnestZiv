package com.zividig.newnestziv.ui.fragment.mycar;

import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.base.MvpPresenter;

/**
 * Created by adolph
 * on 2017-03-06.
 */
@PerActivity
public interface MyCarMvpPresenter<V extends MyCarMvpView> extends MvpPresenter<V> {

    void setDeviceId(String deviceId);

    String getDeviceId();

    void getMyCarDeviceState(String deviceID);

    void loopGetDeviceState();

    void stopLoop();
}
