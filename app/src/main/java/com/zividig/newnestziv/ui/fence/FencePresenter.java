package com.zividig.newnestziv.ui.fence;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by adolph
 * on 2017-04-01.
 */

public class FencePresenter<V extends FenceMvpView> extends BasePresenter<V>
        implements FenceMvpPresenter<V> {

    @Inject
    public FencePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void getLocation() {

    }
}
