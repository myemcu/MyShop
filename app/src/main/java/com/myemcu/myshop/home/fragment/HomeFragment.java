package com.myemcu.myshop.home.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.myemcu.myshop.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

// 首页Fragment
public class HomeFragment extends BaseFragment{

    private TextView textView;

    @Override
    public View initView() {
        textView=new TextView(context);

        Log.e("TAG","首页Fragment-UI-已初始化...");

        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLUE);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","首页Fragment-数据-已初始化...");

        textView.setText("我是首页...");
    }
}
