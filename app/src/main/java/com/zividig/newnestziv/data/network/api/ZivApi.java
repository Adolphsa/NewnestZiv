package com.zividig.newnestziv.data.network.api;

import com.zividig.newnestziv.data.network.model.LoginResponse;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by adolph
 * on 2017-03-01.
 */

public interface ZivApi {

    //登录
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/login")
    Observable<LoginResponse> getLoginInfo(@QueryMap Map<String,String> options, @Body RequestBody body);
}
