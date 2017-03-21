package com.zt.txnews.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.zt.jackone.AppConnect;
import com.zt.txnews.R;
import com.zt.txnews.adapter.VpAdapter;
import com.zt.txnews.utils.ShowToas;

import java.util.ArrayList;
import java.util.List;

/**
 * creat by 2016/9/9
 */

public class MainActivity extends FragmentActivity implements MenuItem.OnMenuItemClickListener {
    private List<String> categoryList;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private VpAdapter vpAdapter;
    private DrawerLayout drawerLayout;//抽屉盒子
    private NavigationView navigationView;//导航视图
    private CircularImageView mycion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTitleData();
        initView();
    }

    private void initTitleData() {
        categoryList = new ArrayList<>();
        categoryList.add("头条");
        categoryList.add("社会");
        categoryList.add("娱乐");
        categoryList.add("国际");
        categoryList.add("科技");
        categoryList.add("体育");
        categoryList.add("军事");
        categoryList.add("国内");
        categoryList.add("财经");
        categoryList.add("时尚");

    }

    private void initView() {
        AppConnect.getInstance(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.id_design_navigation_view);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        vpAdapter = new VpAdapter(getSupportFragmentManager(), categoryList);
        viewPager.setAdapter(vpAdapter);
        //程序启动后会自动加载fragemnt1 和fragment2点击和滑动都会执行相应fragment的onCreateView()方法so fragment布局和fragment都单列设计.然后在各自的fragment实现下拉刷新
        tabLayout.setupWithViewPager(viewPager);

        //tablayout设置
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mycion = (CircularImageView) findViewById(R.id.myicon);
        mycion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        //menu
        Menu menu = navigationView.getMenu();
        //item
        MenuItem yuletueijing = menu.findItem(R.id.item_yule);
        MenuItem exit = menu.findItem(R.id.item_exit);

        yuletueijing.setOnMenuItemClickListener(this);
        exit.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_yule:
                AppConnect.getInstance(this).showAppOffers(this);
                break;
            case R.id.item_exit:
                finish();
                break;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppConnect.getInstance(this).close();
    }

    private boolean isBack = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (!isBack) {
                isBack = true;
                ShowToas.showToast(this, "再按一次退出");
                myBackHandler.sendEmptyMessageDelayed(110, 3000);
                return false;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private Handler myBackHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 110) {
                isBack = false;
            }
        }
    };
}
