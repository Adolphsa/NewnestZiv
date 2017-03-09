package com.zividig.newnestziv.ui.login;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.data.network.model.DeviceListBody;
import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.LoginBody;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.ui.base.BasePresenter;
import com.zividig.newnestziv.utils.CommonUtils;
import com.zividig.newnestziv.utils.GsonUtils;
import com.zividig.newnestziv.utils.MD5;
import com.zividig.newnestziv.utils.SignatureUtils;
import com.zividig.newnestziv.utils.UtcTimeUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-02-28.
 */

public class LoginPresenter <V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V>{


    @Inject
    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    private String token;
    private String userTest;

    @Override
    public Users getUsers() {

        String user = getDataManager().getUser();
        return getDataManager().queryUser(user);
    }

    @Override
    public void onLoginClick(String user, String password) {

        if (user == null || user.isEmpty()){
            getMvpView().onError(R.string.login_user_not_null);
            Timber.d("用户名为空");
            return;
        }
        if (password == null || password.isEmpty()){
            getMvpView().onError(R.string.login_password_not_null);
            return;
        }

        getMvpView().showLoading();

        doLogin(user,password);

    }

    @Override
    public void setCheckSaveUser(boolean checkSaveUser) {
        getDataManager().setSaveUser(checkSaveUser);
    }

    @Override
    public void setCheckSavePassword(boolean checkSavePassword) {
        getDataManager().setSavePassword(checkSavePassword);
    }

    @Override
    public boolean getCheckSaveUser() {
        return getDataManager().getSaveUser();
    }

    @Override
    public boolean getCheckSavePassword() {
        return getDataManager().getSavePassword();
    }

    @Override
    public String getUser() {
        return getDataManager().getUser();
    }

    @Override
    public String getPassword() {
        return getDataManager().getPassword();
    }

    @Override
    public List<Users> getUsersList() {
        return getDataManager().getAllUsers();
    }


    /**
     * 登录
     * @param user  用户名
     * @param password  密码
     */
    private void doLogin(final String user, final String password){

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                user,
                MD5.getMD5(password),
                "294ee93b6f33ee95b2b1892a91eb29c0");

        //配置请求头
        Map<String,String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY,SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP,timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR,noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING,signature);

        //配置请求体
        LoginBody bodyObject = new LoginBody();
        bodyObject.userName = user;
        bodyObject.password = MD5.getMD5(password);
        bodyObject.getuiId = "294ee93b6f33ee95b2b1892a91eb29c0";
        String loginBody = GsonUtils.GsonString(bodyObject);
        RequestBody jsonBody =RequestBody.create(MediaType.parse("application/json; charset=utf-8"),loginBody);

        //获取登录信息
        getCompositeDisposable().add(getDataManager()
                .doLoginCall(options,jsonBody)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                               @Override
                               public void accept(LoginResponse loginResponse) throws Exception {
                                   Timber.d(loginResponse.toString());
                                   if (200 == loginResponse.getStatus()){

                                       //保存token
                                       String token = loginResponse.getToken();
                                       getDataManager().setAccessToken(token);

                                       //保存用户名和密码
                                       getDataManager().setUser(user);
                                       getDataManager().setPassword(password);

                                       //保存震动免打扰开关状态
                                       String alarmStateSwitch = loginResponse.getAlarmStatus();
                                       getDataManager().setAlarmStateSwitch(alarmStateSwitch);

                                       //存放用户和密码到数据库
                                       Users users = new Users(null,user,password);
                                       getDataManager().insertUser(users);

                                        getMvpView().hideLoading();
                                        getMvpView().openMainActivity();
                                   }

                               }
                           },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {
                                    Timber.d(throwable.getMessage());
                                }
                            },
                            new Action() {
                                @Override
                                public void run() throws Exception {
                                    String currentToken =  getDataManager().getAccessToken();
                                    getDeviceList(user,currentToken);
                                }
                            }));
    }


    private void getDeviceList(String user,String token){

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                user,
                token);

        //配置请求头
        Map<String,String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY,SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP,timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR,noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING,signature);

        //配置请求体
        DeviceListBody deviceListBody = new DeviceListBody();
        deviceListBody.userName = user;
        deviceListBody.token = token;
        String stringDeviceListBody = GsonUtils.GsonString(deviceListBody);
        RequestBody jsonBody =RequestBody.create(MediaType.parse("application/json; charset=utf-8"),stringDeviceListBody);

        //获取设备列表
        getCompositeDisposable().add(getDataManager()
                .doGetDeviceList(options,jsonBody)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceListResponse>() {
                    @Override
                    public void accept(DeviceListResponse deviceListResponse) throws Exception {
                        Timber.d(deviceListResponse.toString());
                    }
                })
        );
    }
}
