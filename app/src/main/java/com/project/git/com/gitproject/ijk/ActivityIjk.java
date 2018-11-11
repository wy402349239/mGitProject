package com.project.git.com.gitproject.ijk;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.rxjava.IjkFragment;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by 40234 on 2018/11/11.
 */

public class ActivityIjk extends Activity {

    private SurfaceView mSurface;
    private IjkMediaPlayer mPlayer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ijk);
        mSurface = findViewById(R.id.ijk_playerview);
        mSurface.getHolder().addCallback(mCallBack);
        startPlay();
    }

    private void startPlay(){
        mPlayer = new IjkMediaPlayer();
        try {
            mPlayer.setDataSource("http://covertness.qiniudn.com/android_zaixianyingyinbofangqi_test_baseline.mp4");
        }catch (Exception e){

        }
        mPlayer.prepareAsync();
        mPlayer.start();
    }

    SurfaceHolder.Callback mCallBack = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mPlayer != null){
                mPlayer.setDisplay(holder);
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };
//
    @Override
    protected void onPause() {
        super.onPause();
        if (mPlayer != null) {
            mPlayer.pause();
        }
    }
//
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
        }
    }
}
