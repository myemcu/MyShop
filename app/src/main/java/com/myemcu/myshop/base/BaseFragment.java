package com.myemcu.myshop.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/12/1 0001.
 */

/*基类Fragment，各子Fragment(首页、分类、发现、购物车、我的)都要继承它*/
public abstract class BaseFragment extends Fragment {

    protected Context context;  // 上下文(各子Fragment要用)

    @Override// 该类被系统创建时回调
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();
    }

    public abstract View initView();    // 由继承它的子Fragment强制实现，当然基类也必须是抽象的

    @Override   // 当Activity被创建的时候，回调该方法
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    // 当子Fragment需要联网请求数据的时候，可以重写该方法
    public void initData() {

    }
}
