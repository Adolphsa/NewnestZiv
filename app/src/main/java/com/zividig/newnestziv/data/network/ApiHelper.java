package com.zividig.newnestziv.data.network;

import com.zividig.newnestziv.data.network.model.LoginResponse;

import java.util.Map;

import okhttp3.RequestBody;
import rx.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

public interface ApiHelper {

    Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body);
}
