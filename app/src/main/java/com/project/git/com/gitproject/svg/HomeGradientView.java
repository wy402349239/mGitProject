package com.project.git.com.gitproject.svg;

import android.content.Context;
import android.graphics.*;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import io.reactivex.annotations.Nullable;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/17 8:00 下午
 */
public class HomeGradientView extends View {

    public HomeGradientView(Context context) {
        this(context, null);
    }

    public HomeGradientView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeGradientView(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundColor(Color.TRANSPARENT);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int width = v.getWidth();
                float x = event.getX();
                if (x < 0 || x > width) {
                    return true;
                }
                float xxx = x / width;
                String start = ColorUtil.toArgb(Color.BLUE);
                String end = ColorUtil.toArgb(Color.RED);
                String color = ColorUtil.getCenter(start, end, xxx);
                Log.e("Tag", "xxx = " + xxx + "  =======  " + color);
                scroll(color);
                return true;
            }
        });
    }

    private String color = null;
    private Paint paint;
    private boolean state = false;

    public void setState(boolean state) {
        this.state = state;
    }

    public void scroll(String c) {
        this.color = c;
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.mRight = right - left;
        this.mBottom = bottom - top;
    }

    private int mRight;
    private int mBottom;

    @Override
    protected void onDraw(Canvas canvas) {
        if (TextUtils.isEmpty(color) || mRight == 0 || mBottom == 0) {
            super.onDraw(canvas);
            return;
        }
        if (paint == null) {
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
        }
        int nColor = Color.parseColor(color);
        if (state) {
            paint.setColor(nColor);
            canvas.drawPaint(paint);
            return;
        }
        paint.setShader(getGradient(nColor));
        canvas.drawRect(0, 0, mRight, mBottom, paint);
    }

    private Shader getGradient(int nColor) {
        return new LinearGradient(0, 0, 0, mBottom, nColor, Color.TRANSPARENT, Shader.TileMode.CLAMP);
    }
}
