package com.project.git.com.gitproject.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * created by wangyu on 2020/6/22 7:50 PM
 * description:阴影，根据次方，计算阴影，平滑过渡(效果不理想)
 */
public class BgAppcompatImageview extends AppCompatImageView {

    public BgAppcompatImageview(Context context) {
        super(context);
    }

    public BgAppcompatImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BgAppcompatImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mCenter = new RectF(0, 0, right - left, bottom - top);
        loadStops();
    }

    private void loadStops() {
        double pow = Math.pow(1 / 0.7, 0.125);
        for (int i = 0; i < 8; i++) {
            double x = 0.7 * Math.pow(pow, (i + 1));
            mStops[i] = (float) (x - 0.05);
        }
        mStops[8] = 0.97f;
        mStops[9] = 0.99f;
        loadColors();
    }

    private void loadColors() {
        double pow = Math.pow(1 / 0.47, 1.0 / 9);
        int last = 0;
        int[] nOffset = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        for (int i = 1; i < 10; i++) {
            double x = 0.47 * Math.pow(pow, i);
            x = 1 - x;
            int y = (int) (x * 256);
            if (last == 0) {
                nOffset[i] = 0;
            } else {
                nOffset[i] = nOffset[i - 1] + (last - y);
            }
            last = y;
        }
        for (int i = 0; i < nOffset.length; i++) {
            mColors[i] = Color.argb(nOffset[nOffset.length - 1 - i], 229, 142, 50);
        }
    }

    private float[] mStops = new float[10];
    private int[] mColors = new int[10];
    private RectF mCenter;

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);
        float centerX = mCenter.centerX();
        float centerY = mCenter.centerY() + (mCenter.bottom - mCenter.top) / 48;

        float rat = Math.min(mCenter.right - mCenter.left, mCenter.bottom - mCenter.top) / 2;
        RadialGradient gradient = new RadialGradient(centerX, centerY, rat,
                mColors, mStops, Shader.TileMode.CLAMP);
        paint.setShader(gradient);
        canvas.drawCircle(centerX, centerY, rat, paint);
        super.onDraw(canvas);
    }
}