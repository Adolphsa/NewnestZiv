package com.zividig.newnestziv.ui.base;

import android.support.annotation.StringRes;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface MvpView {

    void showLoading();

    void hideLoading();

    void onError(@StringRes int resId);

    void onError(String message);

    boolean isNetworkConnected();

    void hideKeyboard();

    void setStatusBar();

    void initView();

}
