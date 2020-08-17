package com.project.git.com.gitproject.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by wangyu on 2020/8/3 8:14 PM
 * description:
 */
public class WaveView3 extends View {

    private int mWidth = 0;
    private int mHeight = 0;
    private int mStartX = 0;
    private int mCenterX = 0;
    private int mCenterY = 0;
    private Paint paint;

    private int mWaveWidth = 0;
    private int mWaveHeight = 0;

    private int mMoveUnit = 50;

    private boolean mInAnimation = false;

    public WaveView3(Context context) {
        super(context);
    }

    public WaveView3(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveView3(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInAnimation){
                    stopAnimation();
                }else {
                    setColor(0xff000000);
                    startAnimation();
                }
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = right - left;
        mHeight = bottom - top;
        mCenterX = (left + right) / 2;
        mCenterY = (top + bottom) / 2;
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(0xff0492fb);
        paint.setDither(true);
        int nMin = Math.min(mWidth, mHeight);
        if (mWaveWidth == 0 && nMin != 0) {
            mWaveWidth = nMin * 4 / 5;
        }
        if (mWaveHeight == 0 && nMin != 0) {
            mWaveHeight = nMin / 20;
        }
    }

    public void setWaveWidth(int mWaveWidth) {
        this.mWaveWidth = mWaveWidth;
    }

    public void setWaveHeight(int mWaveHeight) {
        this.mWaveHeight = mWaveHeight;
    }

    public void setMoveUnit(int mMoveUnit) {
        this.mMoveUnit = mMoveUnit;
    }

    public void setColor(int color){
        if (paint != null){
            paint.setColor(color);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mInAnimation) {
            Path cir = new Path();
            cir.addCircle(mCenterX, mCenterY, Math.min(mWidth, mHeight) / 2, Path.Direction.CW);
            canvas.clipPath(cir);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(3);
            canvas.drawCircle(mCenterX, mCenterY, Math.min(mWidth, mHeight) / 2, paint);
            paint.setStyle(Paint.Style.FILL);

            Path path = new Path();
            path.reset();
            float right = mStartX - mWaveWidth;
            path.moveTo(right, mCenterY);
            while (right < mWidth) {
                int x = mWaveWidth / 4;
                path.quadTo(right + x, mCenterY - mWaveHeight, right + x * 2, mCenterY);
                path.quadTo(right + x * 3, mCenterY + mWaveHeight, right + x * 4, mCenterY);
                right += mWaveWidth;
            }
//            path.moveTo(mWidth, mCenterY);
//            path.quadTo(mCenterX, mHeight, 0, mCenterY);
            path.lineTo(mWidth, mHeight);
            path.lineTo(0, mHeight);
            path.close();
            canvas.drawPath(path, paint);
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    mStartX += mMoveUnit;
                    mStartX %= mWaveWidth;
                    invalidate();
                }
            }, 20);
        }
    }

    public synchronized void startAnimation() {
        stopAnimation();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                mInAnimation = true;
                invalidate();
            }
        }, 88);
    }

    public void stopAnimation() {
        mInAnimation = false;
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility == GONE) {
            this.clearAnimation();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        this.clearAnimation();
        super.onDetachedFromWindow();
    }
}