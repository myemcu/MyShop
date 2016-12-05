package com.myemcu.myshop.home.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.home.bean.ResultBeanData;
import com.myemcu.myshop.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
// ViewPager适配器
public class ActAdapter extends PagerAdapter {  // PagerAdapter(Ctrl+Q，看至少需要实现几种方法)
    private final Context context;
    private final List<ResultBeanData.ResultBean.ActInfoBean> act_info;


    // 构造器
    public ActAdapter(Context context, List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
      this.context=context;
      this.act_info=act_info;
    }

    @Override
    public int getCount() {
        return act_info.size(); // 返回总条数
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;  // 套路(object，这是下边这方法的返回值)
    }

    @Override   // container：ViewPager；position：对应页面位置
    public Object instantiateItem(ViewGroup container, final int position) {  // 创建页面
        ImageView img = new ImageView(context);         // 创建ImageView对象
        img.setScaleType(ImageView.ScaleType.FIT_XY);   // 设置拉伸

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = act_info.get(position).getName();
                Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
            }
        });

        // 显示图片
        Glide.with(context)
                .load(Constants.BASE_URL_IMAGE+act_info.get(position).getIcon_url())
                .into(img);

        // 添加到容器中(ViewPage自身)
        container.addView(img);
        return img;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
