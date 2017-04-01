package com.zividig.newnestziv.ui.snap;

import android.content.Context;

import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.base.MvpPresenter;

/**
 * Created by adolph
 * on 2017-03-14.
 */
@PerActivity
public interface SnapPictureMvpPresenter<V extends SnapPictureMvpView> extends MvpPresenter<V> {

    void onSnapClick();

    void onSaveImage(Context context);

    void getDeviceState(String deviceID);
}
