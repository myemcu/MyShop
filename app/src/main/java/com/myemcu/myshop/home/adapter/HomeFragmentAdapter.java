package com.myemcu.myshop.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.myemcu.myshop.home.bean.ResultBeanData;

/**
 * Created by Administrator on 2016/12/3 0003.
 */

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private final static int BANNER    =  0;    // 横幅
    private final static int CHANNEL   =  1;    // 频道
    private final static int ACT       =  2;    // 活动
    private final static int SECKILL   =  3;    // 秒杀
    private final static int RECOMMEND =  4;    // 推荐
    private final static int HOT       =  5;    // 热卖

    private int currentType = BANNER;    // 定义当前条目的View类型(共6种)

    // 构造器接收
    private final Context context;
    private final ResultBeanData.ResultBean resultBean;

    // 布局初始化
    private final LayoutInflater layoutInflater;// 直接用View.inflate的性能消耗较大

    // 构造器
    public HomeFragmentAdapter(Context context, ResultBeanData.ResultBean resultBean) {
        this.context=context;
        this.resultBean=resultBean;
        layoutInflater=LayoutInflater.from(context);   // 用这个初始化布局
    }

    @Override   // 创建ViewHolder(类似getView())
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override   // 根据json可知，有6条item
    public int getItemCount() {
        return 1;
        //return 6; // 一条条做，最后才是这个，为6就是返回0~5
    }

    @Override // 获取条目的View类型(因为有6条)
    public int getItemViewType(int position) {

        switch (position) {
            case BANNER:    currentType=BANNER;
                            break;

            case CHANNEL:   currentType=CHANNEL;
                            break;

            case ACT:       currentType=ACT;
                            break;

            case SECKILL:   currentType=SECKILL;
                            break;

            case RECOMMEND: currentType=RECOMMEND;
                            break;

            case HOT:       currentType=HOT;
                            break;
        }

        return currentType;
    }
}
