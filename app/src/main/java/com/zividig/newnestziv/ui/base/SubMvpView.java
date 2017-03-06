package com.zividig.newnestziv.ui.base;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface SubMvpView extends MvpView{

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attachParentMvpView(MvpView mvpView);
}
