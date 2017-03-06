package com.zividig.newnestziv.ui.login;

import com.zividig.newnestziv.di.PerActivity;
import com.zividig.newnestziv.ui.base.MvpPresenter;

/**
 * Created by adolph
 * on 2017-02-28.
 */
@PerActivity
public interface LoginMvpPresenter <V extends LoginMvpView> extends MvpPresenter<V> {

    void onLoginClick(String user,String password);
}
