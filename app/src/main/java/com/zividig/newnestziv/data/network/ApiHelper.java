package com.zividig.newnestziv.data.network;

import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.DeviceStateResponse;
import com.zividig.newnestziv.data.network.model.LoginResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface ApiHelper {

    //登录
    Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body);

    //获取设备列表
    Observable<DeviceListResponse> doGetDeviceList(Map<String,String> options, RequestBody body);

    //获取设备状态
    Observable<DeviceStateResponse> doGetDeviceState(Map<String,String> options, RequestBody body);
}
