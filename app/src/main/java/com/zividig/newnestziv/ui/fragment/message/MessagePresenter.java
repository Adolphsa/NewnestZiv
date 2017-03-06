package com.zividig.newnestziv.ui.fragment.message;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MessagePresenter<V extends MessageMvpView> extends BasePresenter<V>
        implements MessageMvpPresenter<V> {

    @Inject
    public MessagePresenter(DataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }
}
