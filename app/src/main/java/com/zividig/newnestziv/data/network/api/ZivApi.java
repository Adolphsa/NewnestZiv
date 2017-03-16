package com.zividig.newnestziv.data.network.api;

import com.zividig.newnestziv.data.network.model.DeviceListResponse;
import com.zividig.newnestziv.data.network.model.DeviceStateResponse;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.data.network.model.SnapResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by adolph
 * on 2017-03-01.
 */

public interface ZivApi {

    //登录
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/login")
    Observable<LoginResponse> getLoginInfo(@QueryMap Map<String,String> options, @Body RequestBody body);

    //获取设备列表信息
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("device/mine")
    Observable<DeviceListResponse> getDeviceListInfo(@QueryMap Map<String,String> options, @Body RequestBody body);

    //获取设备状态
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("device/info")
    Observable<DeviceStateResponse> getDeviceStateInfo(@QueryMap Map<String,String> options, @Body RequestBody body);

    //图片抓拍
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("device/snap")
    Observable<SnapResponse> getImageUrl(@QueryMap Map<String,String> options, @Body RequestBody body);

    //下载图片
    @GET
    Observable<ResponseBody> downLoadImage(@Url String imageUrl);
}
