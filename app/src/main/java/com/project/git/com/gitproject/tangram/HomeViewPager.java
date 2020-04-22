package com.project.git.com.gitproject.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class HomeViewPager extends ViewPager {
    public HomeViewPager(@NonNull Context context) {
        super(context);
    }

    public HomeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    private float x = 0;//初始化按下时坐标变量
    private float y = 0;//初始化按下时坐标变量
    private static final int canScrollMax = 100;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = ev.getX();
                y = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float dX = Math.abs(x - ev.getX());
                float dY = Math.abs(y - ev.getY());
                if (dY < canScrollMax && dY < dX) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
