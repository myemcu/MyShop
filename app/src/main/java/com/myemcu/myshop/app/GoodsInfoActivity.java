package com.myemcu.myshop.app;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myemcu.myshop.R;
import com.myemcu.myshop.home.bean.GoodsBean;
import com.myemcu.myshop.utils.Constants;

import java.io.Serializable;

public class GoodsInfoActivity extends Activity implements View.OnClickListener {

    private static final String GOODSBEAN = "goodsbean";    // Intent字段

    // 网页生成viewId
    private ImageButton ibGoodInfoBack;     // 顶部返回(<)
    private ImageButton ibGoodInxfoMore;    // 顶部更多(...)
    private ImageView ivGoodInfoImage;
    private TextView tvGoodInfoName;
    private TextView tvGoodInfoDesc;
    private TextView tvGoodInfoPrice;
    private TextView tvGoodInfoStore;
    private TextView tvGoodInfoStyle;
    private WebView wbGoodInfoMore;
    private LinearLayout llGoodsRoot;
    private TextView tvGoodInfoCallcenter;  // 底部(联系客服)
    private TextView tvGoodInfoCollection;  // 底部(收藏)
    private TextView tvGoodInfoCart;        // 底部(购物车)
    private Button btnGoodInfoAddcart;      // 底部(加入购物车)

    //xml末端，more_layout需手动实例
    private TextView tv_more_share,tv_more_search,tv_more_home;
    private Button btn_more;

    private GoodsBean goodsBean;

    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);

        findViews();

        // 接收HomeFragmentAdapter的Intent数据
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(GOODSBEAN);
        if (goodsBean != null) {
            // Toast.makeText(this, "goodsBean=="+goodsBean.toString(), Toast.LENGTH_SHORT).show();
            showGoodsData(goodsBean);
        }
    }

    private void showGoodsData(GoodsBean goodsBean) {
        // 1 显示图片(iv_good_info_image)
        Glide.with(this).load(Constants.BASE_URL_IMAGE+goodsBean.getFigure()).into(ivGoodInfoImage);// into到的是对象，不是Id
        // 2 显示名称
        tvGoodInfoName.setText("    "+goodsBean.getName()+"。");
        // 3 设置价格
        tvGoodInfoPrice.setText("￥"+goodsBean.getCover_price());
        // 4 显示WebView
        showWebView(goodsBean.getProduct_id());
    }

    private void showWebView(String product_id) {
        if (product_id != null) {
            wbGoodInfoMore.loadUrl("http://www.baidu.com");
            // 设置JS支持
            WebSettings webSettings = wbGoodInfoMore.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            wbGoodInfoMore.setWebViewClient(new WebViewClient() {

                /*@Override   // 兼容低版本
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }*/

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.loadUrl(request.getUrl().toString());
                    }
                    return true;
                }
            });
        }
    }

    //----------------------------------------------------------------------------------------------

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2016-12-08 11:15:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        // 顶部
        ibGoodInfoBack = (ImageButton)findViewById( R.id.ib_good_info_back );
        ibGoodInxfoMore = (ImageButton)findViewById( R.id.ib_good_inxfo_more );

        ivGoodInfoImage = (ImageView)findViewById( R.id.iv_good_info_image );
        tvGoodInfoName = (TextView)findViewById( R.id.tv_good_info_name );
        tvGoodInfoDesc = (TextView)findViewById( R.id.tv_good_info_desc );
        tvGoodInfoPrice = (TextView)findViewById( R.id.tv_good_info_price );
        tvGoodInfoStore = (TextView)findViewById( R.id.tv_good_info_store );
        tvGoodInfoStyle = (TextView)findViewById( R.id.tv_good_info_style );
        wbGoodInfoMore = (WebView)findViewById( R.id.wb_good_info_more );
        llGoodsRoot = (LinearLayout)findViewById( R.id.ll_goods_root );

        // 底部
        tvGoodInfoCallcenter = (TextView)findViewById( R.id.tv_good_info_callcenter );
        tvGoodInfoCollection = (TextView)findViewById( R.id.tv_good_info_collection );
        tvGoodInfoCart = (TextView)findViewById( R.id.tv_good_info_cart );
        btnGoodInfoAddcart = (Button)findViewById( R.id.btn_good_info_addcart );

        // more顶部更多隐藏项
        tv_more_share = (TextView) findViewById(R.id.tv_more_share);
        tv_more_search = (TextView) findViewById(R.id.tv_more_search);
        tv_more_home = (TextView) findViewById(R.id.tv_more_home);
        btn_more = (Button) findViewById(R.id.btn_more);

        // 顶部(< 与...)
        ibGoodInfoBack.setOnClickListener(this);
        ibGoodInxfoMore.setOnClickListener(this);

        // 底部4个
        tvGoodInfoCallcenter.setOnClickListener(this);  // 联系客服
        tvGoodInfoCollection.setOnClickListener(this);  // 收藏
        tvGoodInfoCart.setOnClickListener(this);        // 购物车
        btnGoodInfoAddcart.setOnClickListener(this);    // 加入购物车

        // 顶部更多的隐藏事件
        tv_more_share.setOnClickListener(this);         // 分享
        tv_more_search.setOnClickListener(this);        // 搜索
        tv_more_home.setOnClickListener(this);          // 首页
        btn_more.setOnClickListener(this);              // 按钮
    }

    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2016-12-08 11:15:55 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.ib_good_info_back:    // 返回(<)
                                            finish();
                                            break;

            case R.id.ib_good_inxfo_more:   // 更多(...)
                                            Toast.makeText(this, "更多", Toast.LENGTH_SHORT).show();
                                            break;



            case R.id.tv_good_info_callcenter:  // 联系客服
                                                Toast.makeText(this, "联系客服", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.tv_good_info_collection:  // 收藏
                                                Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.tv_good_info_cart:        // 购物车
                                                Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.btn_good_info_addcart:    // 添加购物车
                                                Toast.makeText(this, "添加到购物车", Toast.LENGTH_SHORT).show();
                                                break;


            case R.id.tv_more_share:            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.tv_more_search:           Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.tv_more_home:             Toast.makeText(this, "首页", Toast.LENGTH_SHORT).show();
                                                break;

            case R.id.btn_more:                 Toast.makeText(this, "按钮", Toast.LENGTH_SHORT).show();
                                                break;

            default:break;
        }

    }
}
