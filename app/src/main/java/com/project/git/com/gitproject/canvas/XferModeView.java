package com.project.git.com.gitproject.canvas;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2020/8/17 9:14 PM
 * description:
 */
public class XferModeView extends View {

    public XferModeView(Context context) {
        this(context, null);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XferModeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private Bitmap cirBitmap, recBitmap;
    private RectF cirRect, recRect;
    private int w, h;
    private PorterDuff.Mode mode = PorterDuff.Mode.SRC_OVER;

    public void setMode(PorterDuff.Mode mode) {
        this.mode = mode;
        invalidate();
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paint = new Paint();
        paint.setAntiAlias(true);
        cirBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cir);
        recBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_rec);
        w = right - left;
        h = bottom - top;
        int centerX = (right - left) / 2;
        int centerY = (bottom - top) / 2;
        int multp = Math.min(w, h) / 4;
        cirRect = new RectF(0, 0, centerX + multp, centerY + multp);
        recRect = new RectF(centerX - multp, centerY - multp, w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xffcccccc);
        int layer = canvas.saveLayer(new RectF(0, 0, w, h), paint, Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(cirBitmap, null, cirRect, paint);
        paint.setXfermode(new PorterDuffXfermode(mode));
        canvas.drawBitmap(recBitmap, null, recRect, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(layer);
    }
}