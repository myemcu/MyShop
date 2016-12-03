package com.myemcu.myshop.app;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2016/12/3 0003.
 */

// 作用：整个软件
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        initOKHttpClient();
    }

    private void initOKHttpClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //.addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
