package com.myemcu.myshop.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.R;
import com.myemcu.myshop.home.bean.ResultBeanData;
import com.myemcu.myshop.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */

public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.ViewHolder> {   // 传入的就是下面的这个ViewHolder

    // viewHolder的
    private ImageView iv_figure;
    private TextView tv_cover_price,tv_origin_price;

    // 构造器的
    private final Context context;
    private final List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> seckill_infoList;

    // 1 定义点击事件接口
    public interface OnSeckillRecyclerView {
        void onItemClick(int position,List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> seckill_infoList); // 某条被点击的时候回调
    }

    // 2 定义接口对象
    private OnSeckillRecyclerView onSeckillRecyclerView;

    // 3 setter接口对象(Alt+Insert)
    public void setOnSeckillRecyclerView(OnSeckillRecyclerView onSeckillRecyclerView) {
        this.onSeckillRecyclerView = onSeckillRecyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder { // 必须是public

        public ViewHolder(View itemView) {
            super(itemView);

            iv_figure = (ImageView) itemView.findViewById(R.id.iv_figure);
            tv_cover_price = (TextView) itemView.findViewById(R.id.tv_cover_price);
            tv_origin_price = (TextView) itemView.findViewById(R.id.tv_origin_price);

            // 设置点击事件(内部实现)
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context,"你点击的是位置："+getLayoutPosition(),Toast.LENGTH_SHORT).show();
                }
            });*/

            // 设置点击事件(外部实现)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // 4 接口对象判空
                    if (onSeckillRecyclerView != null) {
                        onSeckillRecyclerView.onItemClick(getLayoutPosition(),seckill_infoList); // 对象接口中的方法(与 1 呼应)
                    }
                }
            });
        }
    }

    // 构造器
    public SeckillAdapter(Context context, List<ResultBeanData.ResultBean.SeckillInfoBean.ListBean> seckill_infoList) {
        this.context=context;
        this.seckill_infoList=seckill_infoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_seckill,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 1 根据位置得到对应数据
        final ResultBeanData.ResultBean.SeckillInfoBean.ListBean seckill_list = seckill_infoList.get(position);

        // 2 绑定数据

        // 服务器中的图片格式必须与json解析为准
        Glide.with(context).load(Constants.BASE_URL_IMAGE+seckill_list.getFigure()).into(iv_figure);

        tv_cover_price.setText(seckill_list.getCover_price());
        tv_origin_price.setText(seckill_list.getOrigin_price());

        // 3 设置图片监听(可用，但我们用RecyclerView的自定义监听)
        /*iv_figure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = seckill_list.getName();
                Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return seckill_infoList.size(); // 得到集合大小
    }
}
