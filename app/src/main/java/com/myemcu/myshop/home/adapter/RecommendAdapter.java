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

public class RecommendAdapter extends BaseAdapter { // GridView所用的适配器

    private final Context context;
    private final List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info;

    public RecommendAdapter(Context context, List<ResultBeanData.ResultBean.RecommendInfoBean> recommend_info) {
        this.context=context;
        this.recommend_info=recommend_info;
    }

    @Override
    public int getCount() {
        return recommend_info.size();
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
      private ImageView iv_recommend;
      private TextView tv_name,tv_price;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = new ViewHolder();

        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_recommend_grid_view,null);
            viewHolder.iv_recommend = (ImageView) convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        // 接下来就该显示数据了(一个img，两个txt)

        Glide.with(context).load(Constants.BASE_URL_IMAGE+recommend_info.get(position).getFigure()).into(viewHolder.iv_recommend);
        viewHolder.tv_name.setText(recommend_info.get(position).getName());
        viewHolder.tv_price.setText("￥"+recommend_info.get(position).getCover_price());

        return convertView;
    }
}
