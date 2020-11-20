package com.project.git.com.gitproject.tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.R;

import java.lang.reflect.Field;

/**
 * created by wangyu on 2020/11/10 11:05 AM
 * description:
 */
public class SlidingTabLayout extends TabLayout {
    private static final String TAG = "SlidingTabLayout";
    /**
     * 指示器左边坐标
     */
    private float mIndicatorLeft = -1;
    /**
     * 指示器右边坐标
     */
    private int mIndicatorRight = -1;
    /**
     * 选中tab的位置
     */
    private int mSelectedPosition = -1;
    /**
     * 选中tab的偏移量
     */
    private float mSelectionOffset;

    // private LinearLayout mTabStrip;

    private Paint mSelectedIndicatorPaint = new Paint();

    /**
     * 自定义指示器
     */
    private Bitmap mSlideIcon;
    /**
     * 指示器初始X偏移量
     */
    private int mInitTranslationX;
    /**
     * 指示器初始Y偏移量
     */
    private int mInitTranslationY;


    public SlidingTabLayout(Context context) {
        super(context);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mSlideIcon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_tab_icon);
        this.mInitTranslationY = (getBottom() - getTop() - this.mSlideIcon.getHeight());
    }

    public void setIndicatorPositionFromTabPosition(int position, float positionOffset) {
        mSelectedPosition = position;
        mSelectionOffset = positionOffset;
        updateIndicatorPosition();
    }


    /**
     * 计算滑动杆位置
     */
    private void updateIndicatorPosition() {
        LinearLayout mTabStrip = getTabStrip();
        if (mTabStrip == null) {
            return;
        }
        final View selectedTitle = mTabStrip.getChildAt(mSelectedPosition);
        int left, right;

        if (selectedTitle != null && selectedTitle.getWidth() > 0) {
            left = selectedTitle.getLeft();
            right = selectedTitle.getRight();

            if (mSelectionOffset > 0f && mSelectedPosition < mTabStrip.getChildCount() - 1) {

                View nextTitle = mTabStrip.getChildAt(mSelectedPosition + 1);
                left = (int) (mSelectionOffset * nextTitle.getLeft() +
                        (1.0f - mSelectionOffset) * left);

                right = (int) (mSelectionOffset * nextTitle.getRight() +
                        (1.0f - mSelectionOffset) * right);
            }
        } else {
            left = right = -1;
        }
        setIndicatorPosition(left, right);
    }


    void setIndicatorPosition(int left, int right) {
        if (left != mIndicatorLeft || right != mIndicatorRight) {
            mIndicatorLeft = left;
            mIndicatorRight = right;
            /*通知view重绘*/
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }


    /**
     * 绘制指示器
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mSlideIcon == null) {
            return;
        }

        //绘制指示器
        canvas.save();
        if (mIndicatorLeft >= 0 && mIndicatorRight > mIndicatorLeft) {
            // 平移到正确的位置，修正tabs的平移量
            canvas.translate(mIndicatorLeft, getBottom() - getTop() - this.mSlideIcon.getHeight());
            Matrix matrix = new Matrix();
            //设置指示器的长度与tab文字长度相同
            matrix.postScale((mIndicatorRight - mIndicatorLeft) / mSlideIcon.getWidth(), 1);
            canvas.drawBitmap(mSlideIcon, matrix, mSelectedIndicatorPaint);
        }
        canvas.restore();
        super.dispatchDraw(canvas);
    }


    /**
     * tab的父容器，注意空指针
     */
    @Nullable
    public LinearLayout getTabStrip() {
        Class<?> tabLayout = TabLayout.class;
        Field tabStrip = null;
        try {
            //这里是通过反射的获取SlidingTabStrip实例对象，不同的api，这里映射的方法名可能不一样
//            tabStrip = tabLayout.getDeclaredField("mTabStrip");
            // API 28
            tabStrip = tabLayout.getDeclaredField("slidingTabIndicator");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        LinearLayout mTabStripLLayout = null;
        if (tabStrip != null) {
            tabStrip.setAccessible(true);
            try {
                mTabStripLLayout = (LinearLayout) tabStrip.get(this);
                if (mTabStripLLayout != null) {
                    mTabStripLLayout.setClipChildren(false);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return mTabStripLLayout;
    }

}

