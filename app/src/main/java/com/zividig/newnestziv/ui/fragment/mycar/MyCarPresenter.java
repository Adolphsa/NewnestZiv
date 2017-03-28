package com.zividig.newnestziv.ui.fragment.mycar;

import android.text.TextUtils;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.network.model.DeviceStateBody;
import com.zividig.newnestziv.data.network.model.DeviceStateResponse;
import com.zividig.newnestziv.ui.base.BasePresenter;
import com.zividig.newnestziv.utils.CommonUtils;
import com.zividig.newnestziv.utils.GsonUtils;
import com.zividig.newnestziv.utils.SignatureUtils;
import com.zividig.newnestziv.utils.UtcTimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-03-06.
 */

public class MyCarPresenter<V extends MyCarMvpView> extends BasePresenter<V>
        implements MyCarMvpPresenter<V> {

    @Inject
    public MyCarPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }

    @Override
    public void getMyCarDeviceState(String deviceID) {

        Timber.d("调用获取我的设备状态");

        String token = getDataManager().getAccessToken();
        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                deviceID,
                token);

        //配置请求头
        Map<String, String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        //配置请求体
        DeviceStateBody deviceStateBody = new DeviceStateBody();
        deviceStateBody.devid = deviceID;
        deviceStateBody.token = token;
        String stringDeviceListBody = GsonUtils.GsonString(deviceStateBody);
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), stringDeviceListBody);

        //获取设备状态
        getCompositeDisposable().add(getDataManager()
                .doGetDeviceState(options, jsonBody)
                .subscribeOn(io.reactivex.schedulers.Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceStateResponse>() {
                    @Override
                    public void accept(DeviceStateResponse deviceStateResponse) throws Exception {
                        Timber.d("---设备状态---" + deviceStateResponse.getInfo().getWorkmode());
                        handleDeviceStateResponse(deviceStateResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.d("获取设备状态异常---" + throwable.getMessage());
                    }
                })
        );
    }

    /**
     * 设备状态信息返回
     * @param deviceStateResponse 设备状态返回
     */
    private void handleDeviceStateResponse(DeviceStateResponse deviceStateResponse){
        int status = deviceStateResponse.getStatus();
        if (200 == status){
            DeviceStateResponse.InfoBean infoBean = deviceStateResponse.getInfo();
            if (infoBean != null){
                String workMode = infoBean.getWorkmode();
                changeWorkMode(workMode);
            }
        }
    }

    /**
     * 模式英文转中文
     * @param mode 主机模式
     */
    private void changeWorkMode(String mode){
        String devid = getDeviceId();
        if (TextUtils.isEmpty(devid)){
            getMvpView().setDeviceStateTitle("ID为空");
        }else{
            if (mode.equals("NORMAL")){
                getMvpView().setDeviceStateTitle("在线");
            }else if(mode.equals("STDBY")){
                getMvpView().setDeviceStateTitle("休眠");
            }else if(mode.equals("OFF")){
                getMvpView().setDeviceStateTitle("离线");
            }else if(mode.equals("BOOTING")){
                getMvpView().setDeviceStateTitle("启动中");
            }
        }

    }

    @Override
    public void setDeviceId(String deviceId) {
        getDataManager().setDeviceId(deviceId);
    }

    @Override
    public String getDeviceId() {
        return getDataManager().getDeviceId();
    }

    /**
     * 配置options
     * @return options
     */
    private Map<String, String> setOp(){

        String token = getDataManager().getAccessToken();
        String devid = getDataManager().getDeviceId();

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                devid,
                token);

        //配置请求头
        Map<String, String> options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        return options;
    }

    /**
     * 配置jsonBody
     * @return  RequestBody
     */
    private RequestBody setBody(){

        String token = getDataManager().getAccessToken();
        String devid = getDataManager().getDeviceId();

        //配置请求体
        DeviceStateBody deviceStateBody = new DeviceStateBody();
        deviceStateBody.devid = devid;
        deviceStateBody.token = token;
        String stringDeviceListBody = GsonUtils.GsonString(deviceStateBody);
        RequestBody jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), stringDeviceListBody);

        return jsonBody;
    }

    @Override
    public void loopGetDeviceState() {

        Observable.interval(0,30, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<DeviceStateResponse>>() {
                    @Override
                    public ObservableSource<DeviceStateResponse> apply(Long aLong) throws Exception {
                        Map<String, String> options = setOp();
                        RequestBody jsonBody = setBody();
                        return getDataManager().doGetDeviceState(options,jsonBody);
                    }
                })
                .takeUntil(new Predicate<DeviceStateResponse>() {
                    @Override
                    public boolean test(DeviceStateResponse deviceStateResponse) throws Exception {
                        int index = getMvpView().getIndex();
                        if (index != 0){
                            return true;
                        }
                        return false;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DeviceStateResponse>() {
                               @Override
                               public void accept(DeviceStateResponse deviceStateResponse) throws Exception {
                                    handleDeviceStateResponse(deviceStateResponse);
                                   Timber.d("轮询出来设备状态");
                               }
                           },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Timber.d("轮询设备异常---" + throwable.getMessage());
                            }
                        }
                );
    }
}
