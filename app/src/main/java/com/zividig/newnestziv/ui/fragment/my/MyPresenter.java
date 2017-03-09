package com.zividig.newnestziv.ui.fragment.my;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyPresenter<V extends MyMvpView> extends BasePresenter<V>
        implements MyMvpPresenter<V> {

    @Inject
    public MyPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
