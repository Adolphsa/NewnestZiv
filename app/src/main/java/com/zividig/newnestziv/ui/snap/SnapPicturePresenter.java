package com.zividig.newnestziv.ui.snap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.zividig.newnestziv.R;
import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.network.model.DeviceStateBody;
import com.zividig.newnestziv.data.network.model.DeviceStateResponse;
import com.zividig.newnestziv.data.network.model.SnapBody;
import com.zividig.newnestziv.data.network.model.SnapResponse;
import com.zividig.newnestziv.ui.base.BasePresenter;
import com.zividig.newnestziv.utils.CommonUtils;
import com.zividig.newnestziv.utils.GsonUtils;
import com.zividig.newnestziv.utils.SignatureUtils;
import com.zividig.newnestziv.utils.UtcTimeUtils;

import java.io.IOException;
import java.io.InputStream;
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
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-03-14.
 */

public class SnapPicturePresenter<V extends SnapPictureMvpView> extends BasePresenter<V>
        implements SnapPictureMvpPresenter<V> {

    private Map<String, String> options;
    private SnapBody body;
    private RequestBody jsonBody;
    private String imageKey = "new";

    @Inject
    public SnapPicturePresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
    }


    @Override
    public void onSnapClick() {
        getMvpView().showLoading();
        imageKey = "new";
        String devid = getDataManager().getDeviceId();
        getDeviceState(devid);
    }

    /**
     * 获取设备状态
     * @param deviceID  设备ID
     */
    @Override
    public void getDeviceState(String deviceID) {

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
                        String deviceState = deviceStateResponse.getInfo().getWorkmode();
                        if (deviceState.equals("NORMAL")){
                            getImageUrl();
                        }else {
                            getMvpView().onError(R.string.device_off);
                            getMvpView().hideLoading();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Timber.d("获取设备状态异常---" + throwable.getMessage());
                        getMvpView().showLoading();
                    }
                })
        );
    }

    private Map<String, String> setOp(String imageKeyTest){

        String devid = getDataManager().getDeviceId();
        String token = getDataManager().getAccessToken();

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                devid,
                token,
                imageKeyTest);

        //配置请求头
        options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        return options;
    }

    private RequestBody setBody(String imageKeyTest){
        String devid = getDataManager().getDeviceId();
        String token = getDataManager().getAccessToken();

        //配置请求体
        body = new SnapBody();
        body.devid = devid;
        body.imageKey = imageKeyTest;
        body.token = token;
        String snapBody = GsonUtils.GsonString(body);
        jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), snapBody);

        return jsonBody;
    }

    private void getImageUrl() {

        String devid = getDataManager().getDeviceId();
        String token = getDataManager().getAccessToken();

        //计算signature
        String timestamp = UtcTimeUtils.getTimestamp();
        String noncestr = CommonUtils.getRandomString(10);
        String signature = SignatureUtils.getSinnature(timestamp,
                noncestr,
                SignatureUtils.APP_KEY,
                devid,
                token,
                imageKey);

        //配置请求头
        options = new HashMap<>();
        options.put(SignatureUtils.SIGNATURE_APP_KEY, SignatureUtils.APP_KEY);
        options.put(SignatureUtils.SIGNATURE_TIMESTAMP, timestamp);
        options.put(SignatureUtils.SIGNATURE_NONCESTTR, noncestr);
        options.put(SignatureUtils.SIGNATURE_STRING, signature);

        //配置请求体
        body = new SnapBody();
        body.devid = devid;
        body.imageKey = imageKey;
        body.token = token;
        String snapBody = GsonUtils.GsonString(body);
        jsonBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), snapBody);

        Timber.d("访问之前的imageKey---" + imageKey);

        Observable.interval(1,TimeUnit.SECONDS)
                .observeOn(Schedulers.io())
                .take(10)
                .flatMap(new Function<Long, ObservableSource<SnapResponse>>() {
                    @Override
                    public ObservableSource<SnapResponse> apply(Long aLong) throws Exception {
                        Timber.d("第一次变换imagekey---" + imageKey);
                        return getDataManager().doGetImageUrl(options,jsonBody);
                    }
                })
                .flatMap(new Function<SnapResponse, ObservableSource<SnapResponse>>() {
                    @Override
                    public ObservableSource<SnapResponse> apply(SnapResponse snapResponse) throws Exception {
                        Timber.d("第二次变换imagekey---" + snapResponse.getKey());
                        Map<String, String> op = setOp(snapResponse.getKey());
                        RequestBody js = setBody(snapResponse.getKey());
                        return getDataManager().doGetImageUrl(op,js);
                    }
                })
                .subscribeOn(Schedulers.io())
                .takeUntil(new Predicate<SnapResponse>() {
                    @Override
                    public boolean test(SnapResponse snapResponse) throws Exception {
                        String url = snapResponse.getUrl();
                        if (!TextUtils.isEmpty(url)){
                            Timber.d("停止轮询");
                            return true;
                        }
                        return false;
                    }
                })
                .filter(new Predicate<SnapResponse>() {
                    @Override
                    public boolean test(SnapResponse snapResponse) throws Exception {
                        Timber.d("过滤");
                        String url = snapResponse.getUrl();
                        imageKey = snapResponse.getKey();
                        Timber.d("imagekey---" + imageKey);
                        if (!TextUtils.isEmpty(url)){
                            Timber.d("url不为空");
                            return true;
                        }
                        return false;
                    }
                })
                .flatMap(new Function<SnapResponse, ObservableSource<ResponseBody>>() {
                    @Override
                    public ObservableSource<ResponseBody> apply(SnapResponse snapResponse) throws Exception {
                        return getDataManager().doDownloadImage(snapResponse.getUrl());
                    }
                })
                .map(new Function<ResponseBody, Bitmap>() {
                    @Override
                    public Bitmap apply(ResponseBody responseBody) throws Exception {
                        Timber.d("map");
                        InputStream is = null;
                        try {
                            is = responseBody.byteStream();
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                if (is != null) {
                                    is.close();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (is != null) {
                            return BitmapFactory.decodeStream(is);
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Bitmap>() {
                    @Override
                    public void accept(Bitmap bitmap) throws Exception {
                        Timber.d("显示图片");
                        getMvpView().hideLoading();
                        getMvpView().showImage(bitmap);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getMvpView().hideLoading();
                        getMvpView().onError(R.string.snap_fail);
                    }
                });
    }

    @Override
    public void onSaveImage() {

    }

}
