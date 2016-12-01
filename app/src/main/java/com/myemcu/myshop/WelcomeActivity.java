package com.myemcu.myshop;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import com.myemcu.myshop.app.MainActivity;

public class WelcomeActivity extends Activity {

    private Handler handler = new Handler(); // 实现延时任务

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // 延时3s进入主页面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish(); // 关闭本页面
            }
        }, 3000);
    }
}
