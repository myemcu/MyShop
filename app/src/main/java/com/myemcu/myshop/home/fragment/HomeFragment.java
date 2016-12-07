package com.myemcu.myshop.home.fragment;

import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.myemcu.myshop.R;
import com.myemcu.myshop.base.BaseFragment;
import com.myemcu.myshop.home.adapter.HomeFragmentAdapter;
import com.myemcu.myshop.home.bean.ResultBeanData;
import com.myemcu.myshop.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

// 首页Fragment
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView recy_home;
    private ImageView iv_top;

    private TextView tv_search_home,tv_message_home;

    private ResultBeanData.ResultBean resultBean;   // 结果Bean
    private HomeFragmentAdapter adapter;

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

        getDataFromNet();   // 联网请求首页数据
    }

    private void getDataFromNet() {
        String url = Constants.HOME_URL;
        OkHttpUtils
                .get()
                .url(url)
                /*.addParams("username", "hyman")
                .addParams("password", "123")*/
                .build()
                .execute(new StringCallback()
                {
                    @Override   // 联网失败回调它
                    public void onError(Call call, Exception e, int id) {
                        Log.e("TAG","首页请求失败，原因："+e.getMessage());
                    }

                    @Override   // 联网成功回调它(response：请求成功的数据；id：区分 http(100) / https(101))
                    public void onResponse(String response, int id) {

                        Log.e("TAG","首页请求成功，数据为："+response);

                        processData(response);  // 解析数据
                    }
                });
    }

    private void processData(String json) {
        ResultBeanData resultBeanData = JSON.parseObject(json,ResultBeanData.class);    // 解析json到后边的类中
        resultBean = resultBeanData.getResult();
        //Log.e("TAG","解析成功=="+resultBean.getHot_info().get(0).getName());
        if (resultBean != null) {

            // 数据来了，设置RecyclerView适配器
            adapter = new HomeFragmentAdapter(context,resultBean);    // 传入上下文和数据
            recy_home.setAdapter(adapter);

            GridLayoutManager manager = new GridLayoutManager(context, 1);
            recy_home.setLayoutManager(manager);   // 1列

            // 设置跨度大小的监听(GridView独有，Linearlayout无)
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {

                    Log.e("TAGpos","position=="+position);

                    if (position>=5) {
                        iv_top.setVisibility(View.VISIBLE);
                    }else {
                        iv_top.setVisibility(View.GONE);
                    }
                    return 1;
                }
            });
        }
    }
}
