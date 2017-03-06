package com.zividig.newnestziv.ui.base;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface MvpPresenter<V extends MvpView> {

    void onAttach(V mvpView);

    void onDetach();

    void handleApiError();
}
