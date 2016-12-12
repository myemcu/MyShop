package com.myemcu.myshop.home.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/8 0008.
 */

// “热卖”商品对象
// Intent中传递对象，对象需要被序列化
public class GoodsBean implements Serializable {

    /*这里声明的是每一件商品的属性**********************************************************************/
    /*
    *   目前，每一件商品都有如下这些属性：
    *                               1 价格
    *                               2 图片路径
    *                               3 商品名称
    *                               4 商品Id
    *                               5 商品数量
    *                               6 商品状态
    *
    *   每一个成员都设置了其Getter()、Setter()方法、tostring()方法。
    ***********************************************************************************************/

    // 热卖中的4个对象
    private String cover_price,figure,name,product_id; // 价格，图片路径，名称，产品Id

    // 数量
    private int number = 1; // 商品数量(最少要有一个)，记得重新tostring.
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    // 状态
    private boolean isChecked = true;   // 选中状态，默认选中，然后Setter、Getter、重新toString
    public boolean isChecked() {    // Getter
        return isChecked;
    }
    public void setChecked(boolean checked) {   // Setter
        isChecked = checked;
    }

    //----------------------------------------------------------------------------------------------

    // 生成赋值取值方法(alt+Insert)
    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    // toString()方法

    @Override
    public String toString() {
        return "GoodsBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                ", number=" + number +
                ", isChecked=" + isChecked +
                '}';
    }
}
