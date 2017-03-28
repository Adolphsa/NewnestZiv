package com.zividig.newnestziv.ui.carlocation;

import com.zividig.newnestziv.data.DataManager;
import com.zividig.newnestziv.data.network.model.CarLocationResponse;
import com.zividig.newnestziv.data.network.model.DeviceStateBody;
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
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import timber.log.Timber;

/**
 * Created by adolph
 * on 2017-03-28.
 */

public class CarLocationPresenter<V extends CarLocationMvpView> extends BasePresenter<V>
    implements CarLocationMvpPresenter<V>{

    Disposable mDisposable;

    @Inject
    public CarLocationPresenter(DataManager dataManager, CompositeDisposable compositeDisposable) {
        super(dataManager, compositeDisposable);
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
    public void getCarLocation() {

        mDisposable = Observable.interval(0,1, TimeUnit.SECONDS)
                .flatMap(new Function<Long, ObservableSource<CarLocationResponse>>() {
                    @Override
                    public ObservableSource<CarLocationResponse> apply(Long aLong) throws Exception {
                        Map<String, String> options = setOp();
                        RequestBody jsonBody = setBody();
                        return getDataManager().doGetCarLocationInfo(options,jsonBody);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CarLocationResponse>() {
                    @Override
                    public void accept(CarLocationResponse carLocationResponse) throws Exception {
                        Timber.d("GPS信息---" + carLocationResponse.toString());
                        handLocationInfo(carLocationResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                       Timber.d("GPS信息异常---" + throwable.getMessage());

                    }
                });

    }

    private void handLocationInfo(CarLocationResponse carLocationResponse){
        int status = carLocationResponse.getStatus();
        if (200 == status){
            CarLocationResponse.GpsBean gpsBean = carLocationResponse.getGps();
            if (gpsBean != null){
                double lat = gpsBean.getLat();
                double lon = gpsBean.getLon();
                long unixTime  = Long.parseLong(gpsBean.getTi());
                String maptime =  UtcTimeUtils.unixTimeToDate(unixTime);
                getMvpView().initMap(lat,lon,maptime);
            }
        }
    }

    @Override
    public void unsubscribe() {
        if (mDisposable != null){
            mDisposable.dispose();
        }
    }
}
