package com.myemcu.myshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

// 缓存工具类
public class CacheUtils {

    private static final String GOODS_BEAN = "goodsInfo";

    // 获取String类型数据
    public static String getString(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(GOODS_BEAN,Context.MODE_PRIVATE);
        return sp.getString(key,"");// 没有数据就返回空字符串
    }

    // 保存String类型数据
    public static void saveString(Context context, String key, String value) {

        SharedPreferences sp = context.getSharedPreferences(GOODS_BEAN,Context.MODE_PRIVATE);
        sp.edit().putString(key,value).apply();
    }
}
