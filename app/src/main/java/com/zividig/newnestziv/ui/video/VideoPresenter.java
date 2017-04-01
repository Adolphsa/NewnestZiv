package com.zividig.newnestziv.ui.video;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-04-01.
 */

public class VideoPresenter<V extends VideoMvpView> extends BasePresenter<V>
        implements VideoMvpPresenter<V>{

    @Inject
    public VideoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
