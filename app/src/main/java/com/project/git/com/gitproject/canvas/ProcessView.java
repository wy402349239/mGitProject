package com.project.git.com.gitproject.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 4/16/21 5:27 PM
 */
public class ProcessView extends AppCompatTextView {

    /**
     * 进度条当前值
     */
    private float progress = 0;
    /**
     * 画笔
     */
    private Paint mPaint;

    private int mWidth, mHeight;

    private static final int bgColor = 0xffaba7be;
    private static final int fillColor = 0xff599c3c;
    private static final int txtColor = 0xffffffff;

    public ProcessView(Context context) {
        super(context);
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProcessView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***
     * 设置当前的进度值
     * @param currentCount
     */
    public void setCurrentCount(float currentCount) {
        progress = currentCount;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        if (mPaint == null) {
            mPaint = new Paint();
            //设置抗锯齿效果
            mPaint.setAntiAlias(true);
            //设置画笔颜色
            mPaint.setTextSize(sp2px(getContext(), 15));
        }
        String txt = getTxt();
        txtWidth = getPaint().measureText(txt);
        mPaint.setColor(bgColor);
        int round = mHeight / 2;
        RectF rf = new RectF(0, 0, mWidth, mHeight);
        canvas.drawRoundRect(rf, round, round, mPaint);

        //设置进度条进度及颜色
        RectF leftRectf = new RectF(0, 0, mHeight, mHeight);//左半圆
        mPaint.setColor(fillColor);
        float arcLeft = getLeftProgress();
        canvas.drawArc(leftRectf, 180 - arcLeft, arcLeft * 2, false, mPaint);


        if (progress > arc) {//中间矩形
            float left = mHeight / 2.0f - 1;
            float right = Math.min(progress * mWidth / 100, mWidth - left);
            RectF centerRectf = new RectF(left, 0, right, mHeight);
            canvas.drawRect(centerRectf, mPaint);
        }


        if (progress > center) {//右半圆
            RectF rightBg = new RectF(mWidth - mHeight, 0, mWidth, mHeight);
            canvas.drawArc(rightBg, 270, 180, false, mPaint);
            if (progress < 100) {
                mPaint.setColor(bgColor);
                RectF rightRectf = new RectF(mWidth - mHeight, 0, mWidth, mHeight);
                float arcRight = getRightProgress();
                canvas.drawArc(rightRectf, -arcRight, arcRight * 2, false, mPaint);
            }
        }

        mPaint.setColor(txtColor);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float y = (fontMetrics.bottom - fontMetrics.top + mHeight) / 2 - fontMetrics.bottom;
        canvas.drawText(txt, (mWidth - txtWidth) / 2, y, mPaint);
    }

    private float getLeftProgress() {
        return Math.min(progress / arc, 1) * 90;
    }

    private float getRightProgress() {
        return Math.min((100 - progress) / arc, 1) * 90;
    }

    private String getTxt() {
        return Math.min(progress, 100) + "%";
    }

    private float sp2px(Context context, float spValue) {
        return spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }

    private float arc = 0;
    private float center = 0;
    private float txtWidth = 0;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
        arc = mHeight * 50f / mWidth;
        center = 100 - arc;
    }
}