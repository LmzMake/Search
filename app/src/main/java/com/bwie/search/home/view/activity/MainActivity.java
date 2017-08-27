package com.bwie.search.home.view.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.bwie.search.R;
import com.bwie.search.home.modul.utils.CustomVideoView;

public class MainActivity extends AppCompatActivity {

    private CustomVideoView videoview;

    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //视频欢迎页
        initVideo();

    }

    /**
     * 视频播放引导页
     */
    private void initVideo() {

        //设置视频播放
        videoview = (CustomVideoView) findViewById(R.id.videoview);
        //设置播放加载路径
        videoview.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.mv));
        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
            }
        });

        //延时3秒跳转到主界面
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, NetPanDuan.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


}


