package com.myemcu.myshop.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.myemcu.myshop.R;
import com.myemcu.myshop.base.BaseFragment;
import com.myemcu.myshop.cart.fragment.CartFragment;
import com.myemcu.myshop.community.fragment.CommunityFragment;
import com.myemcu.myshop.home.fragment.HomeFragment;
import com.myemcu.myshop.type.fragment.TypeFragment;
import com.myemcu.myshop.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {  // 1 去标题栏 2 支持getSupportFragmentManager()

    private ArrayList<BaseFragment> fragments;   // 声明BaseFragment集合对象fragments，用来装多个Fragment
    private int postion = 0;

    private BaseFragment tempFragment;              // 临时Fragment(缓存Fragment)
    private BaseFragment currentFragment;       // 当前Fragment

    @Bind(R.id.frame_layout)
    FrameLayout frameLayout;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);     // 自动生成

        initFragment();             // 初始化所有Fragment
        initListener();             // 初始化页面控件监听
    }

    // 5个Fragment页面(按顺序添加)
    private void initFragment() {
        fragments = new ArrayList<>();          // 新建集合对象
        fragments.add(new HomeFragment());      // 添加   首页    Fragment
        fragments.add(new TypeFragment());      // 添加   分类    Fragment
        fragments.add(new CommunityFragment()); // 添加   社区    Fragment
        fragments.add(new CartFragment());      // 添加   购物车  Fragment
        fragments.add(new UserFragment());      // 添加   用户   Fragment
    }

    private void initListener() {   // 首页中的各种监听

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkIid) {
                switch (checkIid) {
                    case R.id.rb_home:      postion = 0;
                        break;

                    case R.id.rb_type:      postion = 1;
                        break;

                    case R.id.rb_community: postion = 2;
                        break;

                    case R.id.rb_cart:      postion = 3;
                        break;

                    case R.id.rb_user:      postion = 4;
                        break;

                    default:  postion = 0;  break;
                }

                // 根据位置取当前Fragment
                currentFragment = getFragment(postion);
                // 切换Fragment
                switchFragment(tempFragment, currentFragment);    // 临时Fragment，当前取出的Fragment
            }
        });

        rgMain.check(R.id.rb_home); // 默认选中首页(用8.4.0的Butterknife这里要崩)
    }

    // 根据位置，得到相应Fragment
    private BaseFragment getFragment(int postion) {

        if (fragments != null && fragments.size()>0) {
            BaseFragment baseFragment = fragments.get(postion);
            return baseFragment;
        }else {
            return null;
        }
    }

    // 根据上次preFragment与当前的Fragment进行切换
    private void switchFragment(Fragment preFragment, BaseFragment currentFragment) {
        if (tempFragment != currentFragment) {  // Fragment一样，就不需要处理了。
            tempFragment = currentFragment;
            if (currentFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!currentFragment.isAdded()) {
                    if (preFragment != null) {
                        transaction.hide(preFragment);
                    }
                    transaction.add(R.id.frame_layout,currentFragment).commit();
                }else {
                    if (preFragment != null) {
                        transaction.hide(preFragment);
                    }
                    transaction.show(currentFragment).commit();
                }
            }

            // 验证同一个地方只加载一次Fragment
            // Toast.makeText(MainActivity.this,"我进来了",Toast.LENGTH_SHORT).show();
        }
    }
}
