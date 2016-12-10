package com.myemcu.myshop.cart.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.myemcu.myshop.app.MyApplication;
import com.myemcu.myshop.home.bean.GoodsBean;
import com.myemcu.myshop.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */

/*  逻辑主线：
*           1.添加到购物车——先存储到本地，再写入内存。     (GoodsInfoActivity)
*
*           // getInstance()：本地数据转内存，addData()内存中进行数据的添加
*
*           CartStorge.getInstance().addData(goodsBean);
*
            2.点击购物车时——从本地读取到内存，再进行显示。    (CartFragment)

            // List<GoodsBean>是在内存中的

            List<GoodsBean> goodsBeanList = CartStorge.getInstance().getAllData(); //getAllData()：从本地读出；getInstance()本地转内存

* */

// 购物车存储类
public class CartStorge {

    private static final String JSON_CART = "json_cart";    // 字符串抽取(Ctrl+Alt+C)
    private SparseArray<GoodsBean> sparseArray; // SparseArray替代HashMap可使内存优化(Android特有，原生Java无)

    // 创建自己的对象，即只有单个对象被创建
    private static CartStorge instance;
    // 得到一个购物车的实例
    public static CartStorge getInstance() { // 静态方法，外部直调

        if (instance == null) {
            instance = new CartStorge(MyApplication.getContext());  // 执行构造器，得到全局上下文
        }

        return instance;
    }

    private final Context context;
    // 构造器(私有的，除了在该类中其他地方都不可以访问的)
    private CartStorge(Context context) {       // 让构造器为private，这样该类就不会被实例化
        this.context=context;                   // 接收全局上下文

        sparseArray = new SparseArray<>();      // 视频中为sparseArray = new SparseArray<>(100);
        listToSparseArray();                    // 列表转内存
    }


    private void listToSparseArray() {  // 列表转内存

        // 1 读-----------------读取商品列表(所有商品信息)
        List<GoodsBean> goodsBeanList = getAllData(); // (列表<--数据)

        // 2 存-----------------将列表保存到SparseArray内存  (内存<--列表)
        for (int i = 0; i < goodsBeanList.size(); i++) {
            GoodsBean goodsBean = goodsBeanList.get(i); // 取列表中的每一项的位置
            sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);// 将产品Id放入内存
        }
    }

    // 本地String转列表
    public List<GoodsBean> getAllData() {

        // 1 获取本地String
        String json = CacheUtils.getString(context,JSON_CART);  // 本地数据在Share持久化对象中存着的

        // 2 String转化为List
        List<GoodsBean> goodsBeanList = new ArrayList<>();

        if (!TextUtils.isEmpty(json)) { // 不为空才能解析(不然就报空指针异常崩溃)
            goodsBeanList = new Gson().fromJson(json, new TypeToken<List<GoodsBean>>(){}.getType());
        }

        return goodsBeanList;
    }

    // 数据增删改=====================================================================================

    // 增加数据
    public void addData(GoodsBean goodsBean) {
        // 1 添加数据到内存中，即：sparseArray，若当前数据已存在，就修改成number递增
        GoodsBean tempData = sparseArray.get(Integer.parseInt(goodsBean.getProduct_id()));

        if (tempData != null) {
            // 内存中有了这条数据
            tempData.setNumber(tempData.getNumber()+1);
        }else {
            tempData=goodsBean;
            tempData.setNumber(1); // 至少要有一个
        }

        // 同步到内存中
        sparseArray.put(Integer.parseInt(tempData.getProduct_id()),tempData);

        // 2 同步到本地(说明：增删改查的时候都要同步到本地)
        saveLocal();
    }

    // 删除数据
    public void delData(GoodsBean goodsBean) {
        // 1 在内存中删除
        sparseArray.delete(Integer.parseInt(goodsBean.getProduct_id()));

        // 2 删除后同步到本地
        saveLocal();
    }

    // 更新数据
    public void updateData(GoodsBean goodsBean) {
        // 1  内存中更新
        sparseArray.put(Integer.parseInt(goodsBean.getProduct_id()),goodsBean);
        // 2 同步到本地
        saveLocal();
    }

    // 保存数据到本地
    private void saveLocal() {
        // 1 把sparseArray转换成List
        List<GoodsBean> goodsBeanList = sparseToList();
        // 2 把List转换成string类型(使用Gson)
        String json = new Gson().toJson(goodsBeanList);
        // 2 把String数据进行保存
        CacheUtils.saveString(context,JSON_CART,json);
    }

    private List<GoodsBean> sparseToList() {
        List<GoodsBean> goodsBeanList = new ArrayList<>();
        for (int i = 0; i < sparseArray.size(); i++) {
            GoodsBean goodsBean = sparseArray.valueAt(i);
            goodsBeanList.add(goodsBean);
        }
        return goodsBeanList;
    }
}
