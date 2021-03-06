package com.zividig.newnestziv.ui.track;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-04-01.
 */

public class TrackPresenter<V extends TrackMvpView> extends BasePresenter<V>
    implements TrackMvpPresenter<V> {

    @Inject
    public TrackPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
