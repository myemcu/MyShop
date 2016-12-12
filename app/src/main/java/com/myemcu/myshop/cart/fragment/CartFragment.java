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

    private static final int STATUS_EDIT   = 1; // 编辑状态
    private static final int STATUS_FINISH = 2; // 完成状态

    private TextView tv_cart_edit;
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

        // 设置默认监听状态
        tv_cart_edit.setTag(STATUS_EDIT);
        tv_cart_edit.setOnClickListener(this);
    }

    private void findViews(View view) {
        tv_cart_edit    =   (TextView)      view.findViewById( R.id.tv_cart_edit);      // 编辑
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
            adapter = new CartAdapter(context,goodsBeanList,tv_price_total,check_box_all);   // 先传入上下文和数据，然后是总价
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

            case R.id.tv_cart_edit:
                    int action = (int) tv_cart_edit.getTag();   // 获取状态
                    if (action == STATUS_EDIT) {
                        // 切换到完成状态
                        showDelete();
                    }else {
                        // 切换为编辑状态
                        hideDelete();
                    }
                    break;

            default:break;
        }
    }

    private void showDelete() {
        // 1 设置状态为完成
        tv_cart_edit.setTag(STATUS_FINISH);
        tv_cart_edit.setText("完成");

        // 2 变成非勾选
        if (adapter != null) {
            adapter.checkAll_none(false);   // 设置所有为非勾选
        }
        // 3 删除视图的显示
        llDelete.setVisibility(View.VISIBLE);
        // 4 结算视图的隐藏
        llCheckAll.setVisibility(View.GONE);
    }

    private void hideDelete() {
        // 1 设置状态为编辑
        tv_cart_edit.setTag(STATUS_EDIT);
        tv_cart_edit.setText("编辑");

        // 2 变成勾选
        if (adapter != null) {
            adapter.checkAll_none(true);    // 设置所有为勾选
        }
        check_box_all.setChecked(true);     // 这也是
        adapter.showTotalPrice();           // 同上

        // 3 删除视图的隐藏
        llDelete.setVisibility(View.GONE);
        // 4 结算视图的显示
        llCheckAll.setVisibility(View.VISIBLE);
    }
}
