package com.zividig.newnestziv.ui.fragment.setting;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class SettingPresenter<V extends SettingMvpView> extends BasePresenter<V>
        implements SettingMvpPresenter<V> {

    @Inject
    public SettingPresenter(DataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }
}
