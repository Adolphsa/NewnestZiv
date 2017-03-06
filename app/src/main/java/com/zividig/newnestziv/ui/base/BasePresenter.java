package com.zividig.newnestziv.ui.base;

import com.zividig.newnestziv.data.DataManager;

import javax.inject.Inject;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public class BasePresenter<V extends MvpView> implements MvpPresenter<V> {

    private final DataManager mDataManager;

    private final CompositeSubscription mCompositeSubscription;

    private V mMvpView;

    @Inject
    public BasePresenter(DataManager dataManager,CompositeSubscription compositeSubscription) {
        this.mDataManager = dataManager;
        this.mCompositeSubscription = compositeSubscription;
    }

    //增加订阅
    protected void addSubscription(Subscription s){
        this.mCompositeSubscription.add(s);
    }

    @Override
    public void onAttach(V mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void onDetach() {
        //解除订阅
        mCompositeSubscription.unsubscribe();
        mMvpView = null;
    }

    public boolean isViewAttached() {
        return mMvpView != null;
    }

    public V getMvpView() {
        return mMvpView;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }

    public CompositeSubscription getCompositeSubscription() {
        return mCompositeSubscription;
    }

    @Override
    public void handleApiError() {

    }

}
