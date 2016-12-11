package com.myemcu.myshop.cart.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.R;
import com.myemcu.myshop.cart.view.AddSubView;
import com.myemcu.myshop.home.bean.GoodsBean;
import com.myemcu.myshop.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private final Context context;
    private final List<GoodsBean> goodsBeanList;

    // 适配器的构造器
    public CartAdapter(Context context, List<GoodsBean> goodsBeanList) {
        this.context=context;
        this.goodsBeanList=goodsBeanList;
    }

    // 因为,Item只有一种类型，所以先写ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {   // 必须public

        private CheckBox   cb_gov;
        private ImageView  iv_gov;
        private TextView   tv_desc_gov,tv_price_gov;
        private AddSubView numberAddSubView;

        public ViewHolder(View itemView) {
            super(itemView);

            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            numberAddSubView = (AddSubView) itemView.findViewById(R.id.numberAddSubView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 当item布局写完并在ViewHolder中实例化后就绑定数据

        // 1 根据位置得到对应的Bean对象
        GoodsBean goodsBean = goodsBeanList.get(position);
        // 2 设置数据
        holder.cb_gov.setChecked(goodsBean.isChecked());    // 参数1(CheckBox)
        Glide.with(context).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.iv_gov);
        holder.tv_desc_gov.setText(goodsBean.getName());
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());

    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }
}
