package com.project.git.com.gitproject.pmd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by wangyu on 2019-12-12
 * description :
 */
public class MddPmdView2 extends View {

    Paint mPaint;
    private int mW;
    private int mH;
    private int mStockWidth = 10;
    private int mRadius = 50;
    private float mTrans = 0.05f;

    private float angle = 0;

    private int[] colors = null;

    public MddPmdView2(Context context) {
        this(context, null);
    }

    public MddPmdView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MddPmdView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStockWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        MddPmdView2.this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startPlay(new int[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE});
            }
        });
    }

    public void startPlay(int[] colors) {
        this.colors = new int[colors.length * 2 + 4];
        //double  两截渐变颜色 + 两截透明间隔
        for (int i = 0; i < this.colors.length; i++) {
            int nIndex = i % (this.colors.length / 2);
            this.colors[i] = nIndex < colors.length ? colors[nIndex] : Color.TRANSPARENT;
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mW = w;
        mH = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (colors == null || colors.length <= 1) {
            return;
        }
        int nHalfWidth = mStockWidth / 2;//线宽的一半，以此计算绘制区域，避免出现被截断或者遮挡
        SweepGradient sweepGradient = new SweepGradient(mW / 2, mH / 2, colors, getPositions());
        Matrix matrix = new Matrix();
        matrix.setRotate(angle, mW / 2, mH / 2);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        sweepGradient.setLocalMatrix(matrix);
        mPaint.setShader(sweepGradient);
        //绘制边框
        canvas.drawRoundRect(new RectF(nHalfWidth, nHalfWidth, mW - nHalfWidth, mH - nHalfWidth), mRadius, mRadius, mPaint);

        angle += 1;//递增旋转角度
        invalidate();
    }

    /**
     * 计算每个颜色对应的区域，控制渐变和透明区域
     * @return
     */
    private float[] getPositions() {
        float[] result = new float[colors.length];
        int half = result.length / 2;
        float mult = (0.5f - mTrans) / (half - 3);
        for (int i = 0; i < result.length; i++) {
            float start = i < half ? 0 : 0.5f;
            if (colors[i] == Color.TRANSPARENT) {
                if (colors[i - 1] == Color.TRANSPARENT && i != result.length - 1) {
                    //第一个截断区域，最后一个   透明
                    result[i] = 0.5f;
                } else {
                    //非第一个截断区域最后一个，取值与集合内上一个值相同，可以避免出现渐变至透明的问题
                    result[i] = result[i - 1];
                }
            } else {
                //有颜色值时，按比例取值
                result[i] = (i % half) * mult + start;
            }
        }
        return result;
    }
}
