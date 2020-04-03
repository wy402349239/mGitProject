package com.project.git.com.gitproject.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * created by wy on 2020-03-26
 * description :
 */
public class TaijiView extends View {

    private Paint bPaint;
    private Paint wPaint;
    private Paint gPaint;

    private int padding = 40;
    private int radius = 0;
    private int width, height;
    private PointF center = new PointF();

    public TaijiView(Context context) {
        this(context, null);
    }

    public TaijiView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TaijiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        bPaint = new Paint();
        bPaint.setColor(Color.BLACK);
        bPaint.setAntiAlias(true);
        wPaint = new Paint();
        wPaint.setColor(Color.WHITE);
        wPaint.setAntiAlias(true);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = right - left;
        height = bottom - top;
        padding = width / 20;
        radius = (Math.min(width, height) - padding * 2) / 2;
        center.x = width / 2;
        center.y = height / 2;
    }

    private int angle = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RadialGradient rgl = new RadialGradient(center.x, center.y, radius + padding / 2, new int[]{Color.DKGRAY, Color.LTGRAY, Color.TRANSPARENT},
                new float[]{0.8f, 0.95f, 1f}, Shader.TileMode.CLAMP);
        gPaint = new Paint();
        gPaint.setAntiAlias(true);
        gPaint.setShader(rgl);
        RectF rfsd1 = new RectF(0, 0, width, height);
        canvas.drawArc(rfsd1, 0, 360, true, gPaint);//阴影

        Matrix matrix = new Matrix();
        matrix.setRotate(angle, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        canvas.setMatrix(matrix);

        RectF rv = new RectF(padding, padding, width - padding, height - padding);
        canvas.drawArc(rv, 180, 180, true, wPaint);//上半
        canvas.drawArc(rv, 0, 180, true, bPaint);//下半
        RectF wHead = new RectF(padding, center.y - radius / 2 - 1, center.x, center.y + radius / 2 - 1);//top和bottom - 1是为了完全闭合，右边半圆同理+1
        canvas.drawArc(wHead, 0, 180, true, wPaint);//左小半圆
        RectF bHead = new RectF(center.x, center.y - radius / 2 + 1, width - padding, center.y + radius / 2 + 1);
        canvas.drawArc(bHead, 180, 180, true, bPaint);//右小半圆
        canvas.drawCircle(center.x - radius / 2, center.y, padding, bPaint);
        canvas.drawCircle(center.x + radius / 2, center.y, padding, wPaint);
        angle += 2;
        angle %= 360;
        invalidate();
    }
}
