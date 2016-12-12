package com.myemcu.myshop.cart.fragment;

import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myemcu.myshop.R;
import com.myemcu.myshop.base.BaseFragment;
import com.myemcu.myshop.cart.adapter.CartAdapter;
import com.myemcu.myshop.cart.utils.CartStorge;
import com.myemcu.myshop.home.bean.GoodsBean;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2 0002.
 */

// 购物车Fragment
public class CartFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvShopcartEdit;
    private RecyclerView rv_cart;
    private LinearLayout llCheckAll;
    private CheckBox check_box_all;
    private TextView tv_price_total;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAll;
    private Button btnDelete;
    private Button btnCollection;

    // 内容为空时的布局
    private LinearLayout ll_empty_shopcart;

    private ImageView iv_empty;
    private TextView tv_empty_cart_tobuy;

    private CartAdapter adapter;

    @Override
    public View initView() {
        View view = View.inflate(context,R.layout.fragment_cart,null);  // 页面加载
        findViews(view);                                                // 实例化控件
        setOnClickListener();                                           // 事件监听
        return view;                                                    // 返回view
    }

    private void setOnClickListener() {
        btnCheckOut.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnCollection.setOnClickListener(this);

        // 内容为空时的监听
        iv_empty.setOnClickListener(this);
        tv_empty_cart_tobuy.setOnClickListener(this);
    }

    private void findViews(View view) {
        tvShopcartEdit  =   (TextView)      view.findViewById( R.id.tv_shopcart_edit );
        rv_cart         =   (RecyclerView)  view.findViewById( R.id.rv_cart );
        llCheckAll      =   (LinearLayout)  view.findViewById( R.id.ll_check_all );
        check_box_all   =   (CheckBox)      view.findViewById( R.id.check_box_all );
        tv_price_total  =   (TextView)      view.findViewById( R.id.tv_price_total );   // 总价
        btnCheckOut     =   (Button)        view.findViewById( R.id.btn_check_out );
        llDelete        =   (LinearLayout)  view.findViewById( R.id.ll_delete );
        cbAll           =   (CheckBox)      view.findViewById( R.id.cb_all );
        btnDelete       =   (Button)        view.findViewById( R.id.btn_delete );
        btnCollection   =   (Button)        view.findViewById( R.id.btn_collection );

        // 内容为空时的布局
        ll_empty_shopcart = (LinearLayout) view.findViewById(R.id.ll_empty_shopcart);
        iv_empty = (ImageView) view.findViewById(R.id.iv_empty);
        tv_empty_cart_tobuy = (TextView) view.findViewById(R.id.tv_empty_cart_tobuy);
    }

    @Override
    public void initData() {
        super.initData();

        showData(); // 数据的展现
    }

    private void showData() {

        // 1 读取数据
        List<GoodsBean> goodsBeanList = CartStorge.getInstance().getAllData();

        // 打印数据
        /*for (int i = 0; i < goodsBeanList.size(); i++) {
            Log.e("TAGList",goodsBeanList.get(i).toString());   // 得到列表中的每一项
        }*/

        // 2
        if (goodsBeanList != null && goodsBeanList.size()>0) {
            // 有数据的情况(隐藏数据为空的布局)，设置Recvy适配器
            ll_empty_shopcart.setVisibility(View.GONE);
            adapter = new CartAdapter(context,goodsBeanList,tv_price_total);   // 先传入上下文和数据，然后是总价
            rv_cart.setAdapter(adapter);
            // RecyclerView布局管理器(上下文、方向、数据正序)
            rv_cart.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        }else {
            // 无数据的情况(显示数据为空的布局)
            ll_empty_shopcart.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_check_out:
                    Toast.makeText(context,"结算",Toast.LENGTH_SHORT).show();
                    break;

            case R.id.btn_delete:
                    Toast.makeText(context,"删除",Toast.LENGTH_SHORT).show();
                    break;

            case R.id.btn_collection:
                    Toast.makeText(context,"收藏",Toast.LENGTH_SHORT).show();
                    break;

            case R.id.iv_empty:
                    Toast.makeText(context,"空空如也^_^",Toast.LENGTH_SHORT).show();
                    break;

            case R.id.tv_empty_cart_tobuy:
                    Toast.makeText(context,"去逛逛",Toast.LENGTH_SHORT).show();
                    break;

            default:break;
        }
    }
}
