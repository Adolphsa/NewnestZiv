package com.zividig.newnestziv.ui.carlocation;

import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.base.MvpPresenter;

/**
 * Created by adolph
 * on 2017-03-28.
 */
@PerActivity
public interface CarLocationMvpPresenter<V extends CarLocationMvpView> extends MvpPresenter<V> {

    void getCarLocation();
}
