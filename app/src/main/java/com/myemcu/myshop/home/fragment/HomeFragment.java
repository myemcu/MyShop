package com.myemcu.myshop.home.fragment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.myemcu.myshop.R;
import com.myemcu.myshop.base.BaseFragment;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

// 首页Fragment
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recy_home;
    private ImageView iv_top;

    private TextView tv_search_home,tv_message_home;

    @Override
    public View initView() {

        View view = View.inflate(context, R.layout.fragment_home,null);

        recy_home = (RecyclerView) view.findViewById(R.id.recy_home);

        iv_top = (ImageView) view.findViewById(R.id.iv_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);

        iv_top.setOnClickListener(this);
        tv_search_home.setOnClickListener(this);
        tv_message_home.setOnClickListener(this);

        Log.e("TAG","首页Fragment-UI-已初始化...");

        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.iv_top:           Toast.makeText(context,"回顶部",Toast.LENGTH_SHORT).show();
                                        recy_home.scrollToPosition(0);
                                        break;

            case R.id.tv_search_home:   Toast.makeText(context,"搜索",Toast.LENGTH_SHORT).show();
                                        break;

            case R.id.tv_message_home:  Toast.makeText(context,"消息",Toast.LENGTH_SHORT).show();
                                        break;
        }
    }

    @Override
    public void initData() {
        super.initData();

        Log.e("TAG","首页Fragment-数据-已初始化...");
    }
}
