package com.project.git.com.gitproject;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import crossoverone.statuslib.StatusUtil;

/**
 * Created by Administrator on 2018\10\5 0005.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * DecorView左边滑出间距占屏幕宽度PRESENT_TO_FINISH时表示用户需要退出当前Activity
     */
    private float PRESENT_TO_FINISH = 0.3f;

    /**
     * 用户距离左边MIN_EDGE_DISTANCE内拖动有效
     */
    private int MIN_EDGE_DISTANCE = 100;

    /**
     * 屏幕宽度
     */
    private float mScreenW = -1f;

    /**
     * 计算用户在屏幕滑动的距离
     */
    private float mStartX = 0f;

    /**
     * 当前是否允许拖动
     */
    private boolean mIsScrollEnable = false;

    /**
     * 当前DecorView
     */
    private View mDecorView = null;

    protected void setScrollOut() {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        mDecorView = getWindow().getDecorView();
        if (mScreenW == -1f) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getMetrics(dm);
            mScreenW = dm.widthPixels;
        }
    }

    protected void disPatch(MotionEvent ev) {
        if (ev == null || mDecorView == null) {
            return;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = ev.getX();
                if (mStartX < MIN_EDGE_DISTANCE) {
                    mIsScrollEnable = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mIsScrollEnable) {
                    float offset = ev.getX() - mStartX;
                    mStartX = ev.getX();
                    mDecorView.scrollBy(Math.round(-offset), 0);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsScrollEnable = false;
                if (-mDecorView.getScrollX() / mScreenW > PRESENT_TO_FINISH) {
                    mDecorView.scrollTo((int) mScreenW, 0);
                    finish();
                } else {
                    mDecorView.scrollBy(-mDecorView.getScrollX(), 0);
                }
                break;
        }
    }

    /**
     * 设置沉浸
     */
    protected void setCj() {
        setStatusColor();
        setSystemInvadeBlack();
    }

    protected void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.WHITE);
    }

    /**
     * 设置沉浸于状态栏文字颜色
     */
    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }

    public void changeStatuResource(int resourceId) {
        int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
        View statuBar = getWindow().findViewById(identifier);
        if (statuBar != null) {
            statuBar.setBackgroundColor(Color.TRANSPARENT);
            statuBar.setBackgroundResource(resourceId);
        }
    }
}
