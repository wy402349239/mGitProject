package com.project.git.com.gitproject.pic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.OverScroller;

import java.lang.reflect.Field;

/**
 * created by wangyu on 2020/4/26 6:11 PM
 * description:
 */
public class HlRecycler extends RecyclerView {

    private SpeedScroll scroll;

    public HlRecycler(@NonNull Context context) {
        super(context);
        initSpeed();
    }

    public HlRecycler(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSpeed();
    }

    public HlRecycler(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSpeed();
    }

    private void initSpeed(){
        if (scroll == null) {
            scroll = new SpeedScroll(getContext());
        }
    }

    @Override
    public void smoothScrollBy(int dx, int dy, @Nullable Interpolator interpolator) {
        super.smoothScrollBy(dx, dy, interpolator);
        loadSpeed(500, interpolator);
    }

    public void loadSpeed(int duration, Interpolator interpolator) {
        try {
            Field mViewFlinger = RecyclerView.class.getDeclaredField("mViewFlinger");
            mViewFlinger.setAccessible(true);
            Class viewFlingerClass = Class.forName("android.support.v7.widget" +
                    ".RecyclerView$ViewFlinger");
            Field mScroller = viewFlingerClass.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            Field mInterpolator = viewFlingerClass.getDeclaredField("mInterpolator");
            mInterpolator.setAccessible(true);
            Field mDecelerateInterpolator = LinearSmoothScroller.class.getDeclaredField(
                    "mDecelerateInterpolator");
            mDecelerateInterpolator.setAccessible(true);
            mInterpolator.set(mViewFlinger.get(this),
                    mDecelerateInterpolator.get(scroll));
            if (duration >= 0) {
                SpeedScroll overScroller = new SpeedScroll(getContext(), interpolator);
                overScroller.setDrt(duration);
                mScroller.set(mViewFlinger.get(this), overScroller);
            } else {
                OverScroller overScroller = new OverScroller(getContext(), interpolator);
                mScroller.set(mViewFlinger.get(this), overScroller);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class SpeedScroll extends OverScroller {

        private int drt = 500;

        public SpeedScroll(Context context) {
            super(context);
        }

        public SpeedScroll(Context context, Interpolator interpolator) {
            super(context, interpolator);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy) {
            super.startScroll(startX, startY, dx, dy, drt);
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, drt);
        }

        public void setDrt(int drt) {
            this.drt = drt;
        }
    }
}