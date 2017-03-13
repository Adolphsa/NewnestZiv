package com.zividig.newnestziv.ui.login;

import com.zividig.newnestziv.ui.base.MvpView;

/**
 * Created by adolph
 * on 2017-02-28.
 */

public interface LoginMvpView extends MvpView {

    void openMainActivity();

    String getUserName();
}
