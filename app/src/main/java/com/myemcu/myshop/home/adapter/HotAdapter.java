package com.myemcu.myshop.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.R;
import com.myemcu.myshop.home.bean.ResultBeanData;
import com.myemcu.myshop.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */

public class HotAdapter extends BaseAdapter {

    private final Context context;
    private final List<ResultBeanData.ResultBean.HotInfoBean> hot_info;

    public HotAdapter(Context context, List<ResultBeanData.ResultBean.HotInfoBean> hot_info) {
        this.context=context;
        this.hot_info=hot_info;
    }

    @Override
    public int getCount() {
        return hot_info.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {
        private ImageView iv_hot;
        private TextView tv_name,tv_price;
    }

    @Override
    public View getView(int position, View convetView, ViewGroup group) {

        ViewHolder viewHolder = new ViewHolder();

        if (convetView == null) {
            convetView = View.inflate(context, R.layout.item_hot_grid_view,null);
            viewHolder.iv_hot = (ImageView) convetView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = (TextView) convetView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convetView.findViewById(R.id.tv_price);
            convetView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convetView.getTag();
        }

        // 显示数据(一个img，两个txt)

        Glide.with(context).load(Constants.BASE_URL_IMAGE + hot_info.get(position).getFigure()).into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(hot_info.get(position).getName());
        viewHolder.tv_price.setText("￥"+hot_info.get(position).getCover_price());

        return convetView;
    }
}
