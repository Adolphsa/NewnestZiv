package com.zividig.newnestziv.ui.login;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.ui.base.BasePresenter;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by adolph
 * on 2017-02-28.
 */

public class LoginPresenter <V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V>{


    @Inject
    public LoginPresenter(DataManager dataManager, CompositeSubscription compositeSubscription) {
        super(dataManager, compositeSubscription);
    }

    @Override
    public void onLoginClick(String user, String password) {

        getMvpView().openMainActivity();

//        if (user == null || user.isEmpty()){
//            getMvpView().onError(R.string.login_user_not_null);
//            Timber.d("用户名为空");
//            return;
//        }
//        if (password == null || password.isEmpty()){
//            getMvpView().onError(R.string.login_password_not_null);
//            return;
//        }
//
//        getMvpView().showLoading();
//
//        //计算signature
//        String timestamp = UtcTimeUtils.getTimestamp();
//        String noncestr = CommonUtils.getRandomString(10);
//        String signature = SignatureUtils.getSinnature(timestamp,
//                noncestr,
//                SignatureUtils.APP_KEY,
//                user,
//                MD5.getMD5(password),
//                "294ee93b6f33ee95b2b1892a91eb29c0");
//
//        //配置请求头
//        Map<String,String> options = new HashMap<>();
//        options.put(SignatureUtils.SIGNATURE_APP_KEY,SignatureUtils.APP_KEY);
//        options.put(SignatureUtils.SIGNATURE_TIMESTAMP,timestamp);
//        options.put(SignatureUtils.SIGNATURE_NONCESTTR,noncestr);
//        options.put(SignatureUtils.SIGNATURE_STRING,signature);
//
//        //配置请求体
//        LoginBody bodyObject = new LoginBody();
//        bodyObject.userName = user;
//        bodyObject.password = MD5.getMD5(password);
//        bodyObject.getuiId = "294ee93b6f33ee95b2b1892a91eb29c0";
//        String loginBody = GsonUtils.GsonString(bodyObject);
//        RequestBody jsonBody =RequestBody.create(MediaType.parse("application/json; charset=utf-8"),loginBody);
//
//        //获取登录信息
//        getCompositeSubscription().add(getDataManager()
//                .doLoginCall(options,jsonBody)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<LoginResponse>() {
//                    @Override
//                    public void onCompleted() {}
//
//                    @Override
//                    public void onError(Throwable e) {}
//
//                    @Override
//                    public void onNext(LoginResponse loginResponse) {
//                        Timber.d("token---" + loginResponse.getToken());
//                        if (!isViewAttached()){
//                            return;
//                        }
//                        getMvpView().hideLoading();
//                        getMvpView().openMainActivity();
//                    }
//                })
//        );
    }
}
