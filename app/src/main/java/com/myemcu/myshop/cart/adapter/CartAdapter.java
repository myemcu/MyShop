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
import com.myemcu.myshop.cart.utils.CartStorge;
import com.myemcu.myshop.cart.view.AddSubView;
import com.myemcu.myshop.home.bean.GoodsBean;
import com.myemcu.myshop.utils.CacheUtils;
import com.myemcu.myshop.utils.Constants;

import java.util.List;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    // 构造器成员
    private final Context context;
    private final List<GoodsBean> goodsBeanList;    // List<>集合中装的是商品Bean对象
    private final TextView tv_price_total;          // 总价
    private final CheckBox check_box_all,cbAll;     // 编辑态、完成态

    // 适配器的构造器
    public CartAdapter(Context context, List<GoodsBean> goodsBeanList, TextView tv_price_total, final CheckBox check_box_all, final CheckBox cbAll) {

        this.context=context;
        this.goodsBeanList=goodsBeanList;

        this.tv_price_total=tv_price_total;
        this.check_box_all=check_box_all;
        this.cbAll=cbAll;

        showTotalPrice();       // 显示总价
        setItemListener();      // Item设置监听

        checkAll_none(true);    // 初次进入购物车，保证列表项全选
        checkAll();             // 校验列表项的全选
        showTotalPrice();       // 初始计算总价

        // 设置全选Check_Box的点击事件(编辑态)
        check_box_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1 得到全选Check_Box状态
                boolean isChecked = check_box_all.isChecked();
                // 2 根据状态设置全选与非全选
                checkAll_none(isChecked);
                // 3 计算商品总价
                showTotalPrice();
            }
        });

        // 设置全选Check_Box的点击事件(完成态)
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 1 得到全选Check_Box状态
                boolean isChecked = cbAll.isChecked();
                // 2 根据状态设置全选与非全选
                checkAll_none(isChecked);
            }
        });
    }

    // 设置Item的全选和非全选
    public void checkAll_none(boolean isChecked) {  // public外部要用
        if (goodsBeanList != null && goodsBeanList.size()>0) {
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i); // 从列表中得到某一具体的商品Bean
                goodsBean.setChecked(isChecked);            // isChecked为true，则每一项为true，反之亦然
                notifyItemChanged(i);
            }
        }
    }

    // Item监听
    private void setItemListener() {
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // 1 根据位置找到对应的Bean对象
                GoodsBean goodsBean = goodsBeanList.get(position);
                // 2 设置CheckBox取反状态
                goodsBean.setChecked(!goodsBean.isChecked());
                // 3 刷新Item状态
                notifyItemChanged(position);
                // 4 校验是否全选
                checkAll();
                // 5 重新计算总价
                showTotalPrice();
            }
        });
    }

    // Item项的全选校验
    public void checkAll() {    // public 在适配器外可调用
        if (goodsBeanList != null && goodsBeanList.size()>0) {
            int cnt = 0;
            for (int i = 0; i < goodsBeanList.size(); i++) {
                GoodsBean goodsBean = goodsBeanList.get(i); // 从列表中得到某一具体的商品Bean
                if (!goodsBean.isChecked()) {   // 只要有一个没选中
                    // 设置非全选
                    check_box_all.setChecked(false);
                    cbAll.setChecked(false);
                }else {
                    cnt++;  // 选中的
                }
            }
            if (cnt==goodsBeanList.size()) {
                // 设置全选
                check_box_all.setChecked(true);
                cbAll.setChecked(true);
            }
        }else {
            check_box_all.setChecked(false);
            cbAll.setChecked(false);
        }
    }

    // 显示商品总价
    public void showTotalPrice() {  // public 外部要用
        tv_price_total.setText("合计金额："+getTotalPrice());
    }

    // 获取商品总价
    private double getTotalPrice() {

        double totalPrice = 0;  // 初始化总价

        if (goodsBeanList != null && goodsBeanList.size()>0) {
            for (int i = 0; i < goodsBeanList.size() ; i++) {
                GoodsBean goodsBean = goodsBeanList.get(i); // 得到列表中的每一项
                if (goodsBean.isChecked()) { // 计算选中项
                    totalPrice += Double.valueOf(goodsBean.getNumber()) * Double.valueOf(goodsBean.getCover_price());   // 数量* 单价
                }
            }
        }

        return totalPrice;
    }

    // 删除item项
    public void deleteData() {
        if (goodsBeanList != null && goodsBeanList.size()>0) {
            for (int i = 0; i < goodsBeanList.size() ; i++) {
                // 删除选中项
                GoodsBean goodsBean = goodsBeanList.get(i);
                if (goodsBean.isChecked()) {
                    // 从List中移除
                    goodsBeanList.remove(goodsBean);    // remove是List<>中的方法
                    // 更新到本地
                    CartStorge.getInstance().delData(goodsBean);
                    // 刷新
                    notifyItemRemoved(i);
                    // 更新数量
                    i--;
                }
            }
        }
    }

    // 因为,Item只有一种类型，所以先写ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {   // 必须public

        private CheckBox   cb_gov;
        private ImageView  iv_gov;
        private TextView   tv_desc_gov,tv_price_gov;
        private AddSubView addSubView_number;

        public ViewHolder(View itemView) {
            super(itemView);

            cb_gov = (CheckBox) itemView.findViewById(R.id.cb_gov);
            iv_gov = (ImageView) itemView.findViewById(R.id.iv_gov);
            tv_desc_gov = (TextView) itemView.findViewById(R.id.tv_desc_gov);
            tv_price_gov = (TextView) itemView.findViewById(R.id.tv_price_gov);
            addSubView_number = (AddSubView) itemView.findViewById(R.id.addSubView_number);

            // 设置Item的点击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {  // 接口对象不为空
                        onItemClickListener.onItemClick(getLayoutPosition());   // Item项的位置
                    }
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = View.inflate(context, R.layout.item_shop_cart,null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // 当item布局写完并在ViewHolder中实例化后就绑定数据

        // 1 根据位置得到对应的Bean对象
        final GoodsBean goodsBean = goodsBeanList.get(position);

        // 2 设置显示数据
        holder.cb_gov.setChecked(goodsBean.isChecked());                                                // 状态
        Glide.with(context).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(holder.iv_gov);   // 图片
        holder.tv_desc_gov.setText(goodsBean.getName());                                                // 名称
        holder.tv_price_gov.setText("￥"+goodsBean.getCover_price());                                    // 价格

        // 3 设置从商品页传来的商品数量
        holder.addSubView_number.setValue(goodsBean.getNumber());                                       // 数量
        holder.addSubView_number.setMin(1);
        holder.addSubView_number.setMax(10000);
        holder.addSubView_number.setOnAddSubValueListener(new AddSubView.OnAddSubValueListener() {
            @Override
            public void onAddSubValue(int value) {
                // 1 回调数量更新到商品列表
                goodsBean.setNumber(value);
                // 2 更新列表到内存及本地
                CartStorge.getInstance().updateData(goodsBean); // getInstance()单例方法
                // 3 刷新适配器
                notifyItemChanged(position);
                // 4 再次计算总价
                showTotalPrice();
            }
        });
    }

    @Override
    public int getItemCount() {
        return goodsBeanList.size();
    }

    // 又来定义RecyclerView点击事件的回调接口
    public interface OnItemClickListener {
        void onItemClick(int position); // 回传Item项的位置
    }

    private OnItemClickListener onItemClickListener;    // 接口对象

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {   // Setter方法
        this.onItemClickListener = onItemClickListener;
    }
}
