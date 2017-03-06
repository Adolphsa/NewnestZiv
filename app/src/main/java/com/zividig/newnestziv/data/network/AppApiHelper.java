package com.zividig.newnestziv.data.network;


import com.zividig.newnestziv.ZivApp;
import com.zividig.newnestziv.data.network.api.ZivApi;
import com.zividig.newnestziv.data.network.model.LoginResponse;
import com.zividig.newnestziv.utils.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by adolph
 * on 2017-02-27.
 */

@Singleton
public class AppApiHelper implements ApiHelper{

    public static final String BASE_URL = "http://api.zivdigi.com/v1/";

    private ZivApi mZivApi;

    @Inject
    public AppApiHelper() {}

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());
            if (NetworkUtil.isNetworkConnected(ZivApp.getInstance().getApplicationContext())) {
                int maxAge = 60; // 在线缓存在1分钟内可读取
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // 离线时缓存保存4周
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
    public static AppApiHelper sAppApiHelper;
    private static File httpCacheDirectory = new File(ZivApp.getInstance().getCacheDir(), "zivCache");
    private static int cacheSize = 10 * 1024 * 1024; // 10 MiB
    private static Cache cache = new Cache(httpCacheDirectory, cacheSize);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
            .cache(cache)
            .build();

    private Object zhihuMonitor = new Object();

    public static AppApiHelper getInstence() {
        if (sAppApiHelper == null) {
            synchronized (AppApiHelper.class) {
                if (sAppApiHelper == null) {
                    sAppApiHelper = new AppApiHelper();
                }
            }
        }
        return sAppApiHelper;
    }

    public ZivApi getZivApiService() {
        if (mZivApi == null) {
            synchronized (zhihuMonitor) {
                if (mZivApi == null) {
                    mZivApi = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build().create(ZivApi.class);
                }
            }
        }

        return mZivApi;
    }

    @Override
    public Observable<LoginResponse> doLoginCall(Map<String,String> options, RequestBody body) {

        return getZivApiService().getLoginInfo(options,body);
    }
}
