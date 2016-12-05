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
 * Created by Administrator on 2016/12/5 0005.
 */

// 频道GridView适配器
public class ChannelAdapter extends BaseAdapter {   // GridView的适配器，用的是BaseAdapter

    private final Context context;
    private final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context context, List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
        this.context=context;
        this.channel_info=channel_info;
    }

    @Override
    public int getCount() {
        return channel_info.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private static class ViewHolder {
        private ImageView iv_channel;
        private TextView tv_channel;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;

        if (convertView==null) {
            convertView = View.inflate(context,R.layout.item_channel,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_channel = (ImageView) convertView.findViewById(R.id.iv_channel);
            viewHolder.tv_channel = (TextView) convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 显示图片
        Glide.with(context)
             .load(Constants.BASE_URL_IMAGE+channel_info.get(position).getImage())
             .into(viewHolder.iv_channel);

        // 显示文字
        viewHolder.tv_channel.setText(channel_info.get(position).getChannel_name());

        return convertView; // 千万不要忘了这
    }
}
