package com.zividig.newnestziv.ui.fragment.mycar;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyCarPresenter <V extends MyCarMvpView> extends BasePresenter<V>
        implements MyCarMvpPresenter<V> {

    @Inject
    public MyCarPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
