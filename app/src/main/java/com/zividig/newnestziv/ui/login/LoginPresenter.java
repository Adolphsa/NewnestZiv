package com.zividig.newnestziv.ui.login;

import android.text.TextUtils;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.db.model.DeviceInfo;
import com.zividig.newnestziv.data.db.model.Users;
import com.zividig.newnestziv.data.network.model.DeviceListBody;
import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.LoginBody;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.ui.base.BasePresenter;
import com.zividig.newnestziv.utils.CommonUtils;
import com.zividig.newnestziv.utils.GsonUtils;
import com.zividig.newnestziv.utils.MD5;
import com.zividig.newnestziv.utils.RxBus;
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

public class LoginPresenter<V extends LoginMvpView> extends BasePresenter<V> implements LoginMvpPresenter<V> {

    @Inject
    public LoginPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public Users getUsers() {

        String user = getDataManager().getUser();
        return getDataManager().queryUser(user);
    }

    @Override
    public void onLoginClick(String user, String password) {

        if (user == null || user.isEmpty()) {
            getMvpView().onError(R.string.login_user_not_null);
            Timber.d("用户名为空");
            return;
        }
        if (password == null || password.isEmpty()) {
            getMvpView().onError(R.string.login_password_not_null);
            return;
        }

        getMvpView().showLoading();

        doLogin(user, password);

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
     * @param user     用户名
     * @param password 密码
     */
    private void doLogin(final String user, final String password) {

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
        Map<String, String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        //配置请求体
        LoginBody bodyObject = new LoginBody();
        bodyObject.userName = user;
        bodyObject.password = MD5.getMD5(password);
        bodyObject.getuiId = "294ee93b6f33ee95b2b1892a91eb29c0";
        String loginBody = GsonUtils.GsonString(bodyObject);
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), loginBody);

        //获取登录信息
        getCompositeDisposable().add(getDataManager()
                .doLoginCall(options, jsonBody)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LoginResponse>() {
                               @Override
                               public void accept(LoginResponse loginResponse) throws Exception {
                                   Timber.d(loginResponse.toString());
                                   //登陆后的处理
                                   getMvpView().hideLoading();
                                   HandleLoginResponse(user, password, loginResponse);
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().hideLoading();
                                Timber.d(throwable.getMessage());
                            }
                        },
                        new Action() {
                            @Override
                            public void run() throws Exception {
                                getMvpView().hideLoading();
                            }
                        }));
    }

    /**
     * 对登录返回信息的处理
     *
     * @param user  用户名
     * @param password  密码
     * @param loginResponse 登录返回信息
     */
    private void HandleLoginResponse(String user, String password, LoginResponse loginResponse) {
        int status = loginResponse.getStatus();
        if (200 == status) {

            //判断账号是否改换
            userNameIsChange();

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
            Users dataUser = getDataManager().queryUser(user);
            if (dataUser == null) {
                Users users = new Users(null, user, password);
                getDataManager().insertUser(users);
            } else {
                dataUser.setPassword(password);
                getDataManager().updateUser(dataUser);
            }

            String currentToken = loginResponse.getToken();
            getDeviceList(user, currentToken);
            getMvpView().openMainActivity();

        } else if (404 == status) {
            getMvpView().onError("手机号不存在");
        } else if (403 == status) {
            getMvpView().onError("手机号或密码错误");
        }
    }

    /**
     * 检测账号是否更换
     */
    public void userNameIsChange(){
        //换账号之后删除devid
        String userName = getDataManager().getUser();
        String currentUserName = getMvpView().getUserName();
        if (!currentUserName.equals(userName)){
            Timber.d("换账号了");
            getDataManager().setDeviceId(null);
        }
    }

    /**
     * 获取设备列表
     *
     * @param user  用户名
     * @param token token
     */
    private void getDeviceList(final String user, String token) {

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                user,
                token);

        //配置请求头
        Map<String, String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        //配置请求体
        DeviceListBody deviceListBody = new DeviceListBody();
        deviceListBody.userName = user;
        deviceListBody.token = token;
        String stringDeviceListBody = GsonUtils.GsonString(deviceListBody);
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), stringDeviceListBody);

        //获取设备列表
        getCompositeDisposable().add(getDataManager()
                        .doGetDeviceList(options, jsonBody)
                        .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<DeviceListResponse>() {
                            @Override
                            public void accept(DeviceListResponse deviceListResponse) throws Exception {
                                Timber.d(deviceListResponse.toString());
//                        addDeviceList(user, deviceListResponse);
                                //处理返回回来的数据
                                handleDeviceListResponse(user, deviceListResponse);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.d(throwable.getMessage());
                            }
                        })
        );
    }

    /**
     * 增加设备列别至数据库
     *
     * @param user               用户名
     * @param deviceListResponse 设备列表返回
     */
    private void addDeviceList(String user, DeviceListResponse deviceListResponse) {
        List<DeviceListResponse.DevinfoBean> devinfoList = deviceListResponse.getDevinfo();
        //获取数据库中当前账号的设备，如果查询不到，表示是一个新账号，如果能查询到，表示该账号已经登陆过
        List<DeviceInfo> dataBaseDeviceInfo = getDataManager().getAllDevice(user);
        if (dataBaseDeviceInfo.size() <= 0) {
            Timber.d("添加设备到数据库---" + devinfoList.size());
            for (DeviceListResponse.DevinfoBean devinfoBean : devinfoList) {
                String devid = devinfoBean.getDevid();
                String devtype = (String) devinfoBean.getDevtype();
                String carid = devinfoBean.getCarid();
                String alias = devinfoBean.getAlias();
                DeviceInfo deviceInfo = new DeviceInfo(null, user, devid, devtype, alias, carid);
                getDataManager().insertDevice(deviceInfo);
            }
        }
    }

    /**
     * 处理返回的设备列表信息
     * @param user  用户名
     * @param deviceListResponse    设备列表返回
     */
    private void handleDeviceListResponse(String user, DeviceListResponse deviceListResponse) {
        int status = deviceListResponse.getStatus();
        List<DeviceListResponse.DevinfoBean> devinfoList = deviceListResponse.getDevinfo();

        if (200 == status) {
            String devid = getDataManager().getDeviceId();
            if (TextUtils.isEmpty(devid)) {
                if (devinfoList != null && devinfoList.size() > 0) {
                    //保存设备到数据库
                    addDeviceList(user, deviceListResponse);
                    //传递第0个设备ID出去
                    String devid0 =  devinfoList.get(0).getDevid();
                    DeviceInfo DefaultDeviceInfo0 = getDataManager().queryDevice(devid0);
                    RxBus.getDefault().post(DefaultDeviceInfo0);
                    Timber.d("---发送第0个，devid为空");
                    getDataManager().setDeviceId(devid0);
                }
            } else {
                if (devinfoList != null && devinfoList.size() > 0) {
                    DeviceInfo deviceInfo = getDataManager().queryDevice(devid);
                    if (deviceInfo == null) {
                        //发射第0个出去
                        String defaultDevid = devinfoList.get(0).getDevid();
                        DeviceInfo defaultDeviceInfo = getDataManager().queryDevice(defaultDevid);
                        RxBus.getDefault().post(defaultDeviceInfo);
                        Timber.d("---发送第0个，devid不为空");
                        getDataManager().setDeviceId(defaultDevid);
                    } else {
                        //发射当前这个ID出去
                        RxBus.getDefault().post(deviceInfo);
                        Timber.d("---发送当前设备ID，devid不为空");
                        getDataManager().setDeviceId(deviceInfo.getDeviceId());
                    }

                }
            }
        }
    }
}
