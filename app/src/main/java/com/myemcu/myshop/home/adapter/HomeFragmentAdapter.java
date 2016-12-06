package com.myemcu.myshop.home.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.R;
import com.myemcu.myshop.home.bean.ResultBeanData;
import com.myemcu.myshop.utils.Constants;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;
import com.youth.banner.listener.OnLoadImageListener;
import com.zhy.magicviewpager.transformer.RotateDownPageTransformer;
import com.zhy.magicviewpager.transformer.ScaleInTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Created by Administrator on 2016/12/3 0003.
 */


/*
* onCreateViewHolder()：创建不同条目的ViewHolder视图
* */

public class HomeFragmentAdapter extends RecyclerView.Adapter {

    private final static int BANNER    =  0;    // 横幅
    private final static int CHANNEL   =  1;    // 频道
    private final static int ACT       =  2;    // 活动
    private final static int SECKILL   =  3;    // 秒杀
    private final static int RECOMMEND =  4;    // 推荐
    private final static int HOT       =  5;    // 热卖

    private int currentType = BANNER;    // 定义当前条目的View类型(共6种)

    private long time_dt = 0;   // 秒杀起始时间(ms)

    // 构造器接收
    private final Context context;
    private final ResultBeanData.ResultBean resultBean;

    // 布局初始化
    private final LayoutInflater layoutInflater;// 直接用View.inflate的性能消耗较大

    // 构造器
    public HomeFragmentAdapter(Context context, ResultBeanData.ResultBean resultBean) {
        this.context=context;
        this.resultBean=resultBean;

        layoutInflater=LayoutInflater.from(context);   // 根据传入的上下文，创建该适配器的布局加载对象
    }

    @Override   // 创建ViewHolder(类似getView())，viewType来自getItemViewType(position)
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == BANNER) {   // 传入上下文、布局(itemview)
            return new BannerViewHolder(context, layoutInflater.inflate(R.layout.banner_viewpager,null));
        }   // 换成自己的图片(1 新图片到服务器中)(2 png格式)(3 重启Tomcat)(4 清App应用数据)

        if (viewType == CHANNEL) {
            return new ChannelViewHolder(context,layoutInflater.inflate(R.layout.channel_item,null));
        }

        if (viewType == ACT) {
            return new ActViewHolder(context,layoutInflater.inflate(R.layout.act_item,null));
        }

        if (viewType == SECKILL) {
            return new  SeckillViewHolder(context,layoutInflater.inflate(R.layout.seckill_item,null));
        }

        return null;
    }

    @Override   // 绑定ViewHolder
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position)==BANNER) {
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.showData(resultBean.getBanner_info());  // 传入Banner数据
        }

        if (getItemViewType(position)==CHANNEL) {
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.showData(resultBean.getChannel_info());
        }

        if (getItemViewType(position) == ACT) {
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.showData(resultBean.getAct_info());
        }

        if (getItemViewType(position) == SECKILL) {
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.showData(resultBean.getSeckill_info());
        }
    }

    @Override   // 根据json可知，有6条item
    public int getItemCount() {
        return 4;
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

    // 横幅ViewHolder实现类
    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private final Banner banner;

        // 构造器(传入：上下文、布局文件)
        //      (接收：上下文、布局中的控件)
        public BannerViewHolder(Context context, View itemView) {// itemview：传入的布局名
            super(itemView);
            this.context=context;
            this.banner= (Banner) itemView.findViewById(R.id.banner);// 接收布局中的控件
        }

        public void showData(List<ResultBeanData.ResultBean.BannerInfoBean> banner_info) {

            // 建立一个String集合，用来装图片地址
            List<String> imagesUrl = new ArrayList<>();

            for (int i =0; i<banner_info.size(); i++) {
                String url = banner_info.get(i).getImage(); // 得到每一条图片地址
                imagesUrl.add(url);
            }

            banner.setBannerAnimation(Transformer.Accordion);       // 手风琴动画特效
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);   // 横幅加点指示
            banner.setImages(imagesUrl, new OnLoadImageListener() {
                @Override
                public void OnLoadImage(ImageView view, Object url) {
                    // Glide联网请求图片(使用jar包)
                    // 说明：此处将banner库中的glide版本由3.6.1->3.7.0，才能烧写通过，不然就报多重定义错
                    Glide.with(context).load(Constants.BASE_URL_IMAGE + url).into(view);
                }
            });
            banner.setOnBannerClickListener(new OnBannerClickListener() {   // Banner点击事件
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(context,"你点了第"+position+"条横幅",Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 频道ViewHolder实现类
    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private GridView gv_channel;

        private ChannelAdapter adapter;

        public ChannelViewHolder(Context context, View itemView) {
            super(itemView);

            this.context=context;
            gv_channel= (GridView) itemView.findViewById(R.id.gv_channel);
        }

        public void showData(final List<ResultBeanData.ResultBean.ChannelInfoBean> channel_info) {
            // 数据过来了，需要设置GridView适配器
            adapter = new ChannelAdapter(context,channel_info); // 传入上下文和数据
            gv_channel.setAdapter(adapter);
            gv_channel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> view, View view1, int position, long l) {
                    String content = channel_info.get(position).getChannel_name();
                    Toast.makeText(context,"你点击的是："+content,Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    // 活动ViewHolder实现类
    private class ActViewHolder extends RecyclerView.ViewHolder {
        private final Context context;
        private ViewPager act_viewpager;

        private ActAdapter adapter;

        public ActViewHolder(Context context, View itemView) { // 要接收哪些就先到其itemView中去看有些神马
            super(itemView);

            this.context=context;
            this.act_viewpager= (ViewPager) itemView.findViewById(R.id.act_viewpager);
        }

        public void showData(final List<ResultBeanData.ResultBean.ActInfoBean> act_info) {
            // 数据已到位，设置适配器
            adapter = new ActAdapter(context,act_info);
            act_viewpager.setAdapter(adapter);
            act_viewpager.setPadding(10,10,10,10);

            // ViewPager第三方美化
            act_viewpager.setOffscreenPageLimit(3); // >=3
            act_viewpager.setPageTransformer(true, new RotateDownPageTransformer());    // 扇形
        }
    }

    // 秒杀viewHolder
    private class SeckillViewHolder extends RecyclerView.ViewHolder {

        private final Context context;

        private TextView tv_time_seckill,tv_more_seckill;
        private RecyclerView rv_seckill;

        private SeckillAdapter adapter;

        private int hour,min,sec;
        private String str_hour,str_min,str_sec;

        // 更新每秒秒杀时间
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                time_dt-=1000;  // 递减1s

                // 系统转换有错误，小时位总有个8
                /*SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss"); // 总ms毫秒值格式转换
                String time = format.format(new Date(time_dt));*/

                sec= (int) (time_dt/1000);  // 总ms转总s

                sec_to_hh_mm_ss();  // 总秒数转时分秒

                tv_time_seckill.setText(str_hour+":"+str_min+":"+str_sec);

                // 减后再发消息
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,1000);    // 无标志，1s

                if (time_dt <= 0 ) { // 秒杀结束
                    handler.removeCallbacksAndMessages(null);
                    Toast.makeText(context,"今日秒杀结束",Toast.LENGTH_SHORT).show();
                }
            }
        };

        public SeckillViewHolder(Context context, View itemView) {
            super(itemView);

            this.context=context;

            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            rv_seckill = (RecyclerView) itemView.findViewById(R.id.rv_seckill);

        }

        public void showData(final ResultBeanData.ResultBean.SeckillInfoBean seckill_info) {
            // 文本和RecyclerView的适配器
            adapter = new SeckillAdapter(context,seckill_info.getList());   // 只需传入集合
            rv_seckill.setAdapter(adapter);
            rv_seckill.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)); // 水平正序

            // item点击事件
            adapter.setOnSeckillRecyclerView(new SeckillAdapter.OnSeckillRecyclerView() {
                @Override
                public void onItemClick(int position, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> seckill_infoList) {
                    String content = seckill_info.getList().get(position).getName();
                    Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
                }
            });

            // 秒杀倒计时ms(范围由服务器上的起停实现来定)
            time_dt=Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());
            handler.sendEmptyMessageDelayed(0,1000);    // 无标志，1s
        }

        private void sec_to_hh_mm_ss() {
            hour=sec/3600;
            min=(sec-hour*3600)/60;
            sec=(sec-hour*3600)%60;

            if (hour<10) {
                str_hour = "0"+hour+"";
            }else {
                str_hour = hour+"";
            }

            if (min<10) {
                str_min = "0"+min+"";
            }else {
                str_min = min+"";
            }

            if (sec<10) {
                str_sec = "0"+sec+"";
            }else {
                str_sec = sec+"";
            }
        }
    }
}
