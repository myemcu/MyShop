package com.myemcu.myshop.cart.fragment;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.myemcu.myshop.base.BaseFragment;
import com.myemcu.myshop.cart.utils.CartStorge;
import com.myemcu.myshop.home.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

// 购物车Fragment
public class CartFragment extends BaseFragment{

    private TextView textView;

    @Override
    public View initView() {
        textView=new TextView(context);

        Log.e("TAG","购物车Fragment-UI-已初始化...");

        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(25);
        textView.setTextColor(Color.BLUE);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","购物车Fragment-数据-已初始化...");

        textView.setText("我是购物车...");

        // List<GoodsBean>是在内存中的
        List<GoodsBean> goodsBeanList = CartStorge.getInstance().getAllData(); //getAllData()：从本地读出；getInstance()本地转内存

        for (int i = 0; i < goodsBeanList.size(); i++) {
            Log.e("TAGList",goodsBeanList.get(i).toString());   // 得到列表中的每一项
        }

    }
}
