package com.bwie.search.home.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bwie.search.R;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import cn.sharesdk.onekeyshare.OnekeyShare;

public class WebActivity extends AppCompatActivity {

    private WebView webview;
    private String mSreach;
    private ImageView share;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        initView();
        initData();

    }


    private void initView() {
        webview = (WebView) findViewById(R.id.webview);
        share = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare();
            }
        });
    }

    /**
     * 使用webView进行搜索
     */
    private void initData() {
        //获取EditText传过来的值
        Intent intent = getIntent();
        mSreach = intent.getStringExtra("sreach");
        //和百度网址进行拼接
        webview.loadUrl("https://www.baidu.com/s?wd=" + mSreach);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * mob第三方分享
     */
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("波雅·汉库克");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://baike.baidu.com/link?url=XKjfqVwK3Z2EMWm3zgcdlYY6Np3j6xDqIn497Rhf9hRAmhSrQBN9zfYmhhNWktr02DKsNAEgUOSvTiE0POVRZhRz9k2BmsPAehRLv-LH3GDSFSXWbOIFoU-KOw5WajS9MYK9jQ7IbE3puY4f0PGgbp7E-BVU6HhNG5577VX3qhOu6wTFAbDMrYujpdDNo9yd70eFtpRh4pAf04EeC3PSs_ocGn0lTrOvlVKTvNFdYbwrcZFLM3iV79fXjXFelAVe");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("日本漫画《海贼王》中的角色");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("http://b.hiphotos.baidu.com/baike/w%3D268%3Bg%3D0/sign=b866ba4eb951f819f125044ce28f2dd0/ae51f3deb48f8c54159af94a33292df5e0fe7f0d.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("但愿能分享吧！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://baike.baidu.com/link?url=XKjfqVwK3Z2EMWm3zgcdlYY6Np3j6xDqIn497Rhf9hRAmhSrQBN9zfYmhhNWktr02DKsNAEgUOSvTiE0POVRZhRz9k2BmsPAehRLv-LH3GDSFSXWbOIFoU-KOw5WajS9MYK9jQ7IbE3puY4f0PGgbp7E-BVU6HhNG5577VX3qhOu6wTFAbDMrYujpdDNo9yd70eFtpRh4pAf04EeC3PSs_ocGn0lTrOvlVKTvNFdYbwrcZFLM3iV79fXjXFelAVe");
        // 启动分享GUI
        oks.show(this);
    }
}
