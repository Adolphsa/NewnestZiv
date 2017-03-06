package com.zividig.newnestziv.ui.main;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public class MainPresenter <V extends MainMvpView> extends BasePresenter<V>
        implements MainMvpPresenter<V>{


    @Inject
    public MainPresenter(DataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }
}
