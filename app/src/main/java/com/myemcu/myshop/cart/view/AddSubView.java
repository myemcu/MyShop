package com.myemcu.myshop.cart.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.myemcu.myshop.R;

/**
 * Created by Administrator on 2016/12/11 0011.
 */

public class AddSubView extends LinearLayout implements View.OnClickListener {

    private ImageView iv_sub,iv_add;
    private TextView tv_value;

    private int min = 1;
    private int value = min;
    private int max = 10;


    // 选用带两个参数的构造器
    public AddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 1 先加载自定义控件布局
        View.inflate(context, R.layout.add_sub_view, this);  // 把布局实例化并加载到AddSubView类中

        // 2 实例化
        iv_sub = (ImageView) findViewById(R.id.iv_sub);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_value = (TextView) findViewById(R.id.tv_value);


        int value = getValue();
        setValue(value);

        iv_sub.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    private void addNum() {

        if (value<max) {
            value++;
        }

        setValue(value);    // UI变化

        // 接口传值
        if (onAddSubValueListener != null) {
            onAddSubValueListener.onAddSubValue(value);
        }
    }

    private void subNum() {

        if (value>min) {
            value--;
        }

        setValue(value);    // UI变化

        // 接口传值
        if (onAddSubValueListener != null) {
            onAddSubValueListener.onAddSubValue(value);
        }
    }

    // 最大值，最小值，当前值

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getValue() {
        String valueStr =  tv_value.getText().toString().trim();
        if (!TextUtils.isEmpty(valueStr)) {
            value=Integer.parseInt(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_value.setText(value+"");
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                            addNum();
                            break;

            case R.id.iv_sub:
                            subNum();
                            break;
            default:break;
        }
    }

    //--------------接口(监听数据的变化)----------------------------------------------------------

    // 当加减值的时候回调
    public interface OnAddSubValueListener {
        void onAddSubValue(int value);  // addNum();subNum();对其传值。
    }

    private OnAddSubValueListener onAddSubValueListener;    // 接口对象

    public void setOnAddSubValueListener(OnAddSubValueListener onAddSubValueListener) {
        this.onAddSubValueListener = onAddSubValueListener;
    }
}
