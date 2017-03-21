package com.zt.txnews.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.zt.jackone.AppConnect;
import com.zt.txnews.R;

/**
 * Created by Administrator on 2016/9/10.
 * 新闻详情页
 */
public class NewsMessageActivity extends AppCompatActivity {
    private WebView webView;
    public Toolbar toolbar;
    public ActionBar ab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsmessage_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        initView();
    }

    private void initView() {
        LinearLayout adlayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
        AppConnect.getInstance(this).showBannerAd(this, adlayout);

        webView = (WebView) findViewById(R.id.webView);
        String url = getIntent().getExtras().getString("url");
        webView.loadUrl(url);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
