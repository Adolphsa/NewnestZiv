package com.zividig.newnestziv.ui.carinfo;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-04-01.
 */

public class CarInfoPresenter<V extends CarInfoMvpView> extends BasePresenter<V>
        implements CarInfoMvpPresenter<V> {

    @Inject
    public CarInfoPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }
}
