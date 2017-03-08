package com.zividig.newnestziv.data.network;

import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.LoginResponse;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface ApiHelper {

    //登录
    Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body);

    //获取设备列表
    Observable<DeviceListResponse> doGetDeviceList(Map<String,String> options, RequestBody body);
}
