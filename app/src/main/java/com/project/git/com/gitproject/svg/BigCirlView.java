package com.project.git.com.gitproject.svg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.project.git.com.gitproject.R;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/15 8:26 下午
 */
public class BigCirlView extends ConstraintLayout {

    public BigCirlView(@NonNull Context context) {
        this(context, null);
    }

    public BigCirlView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BigCirlView(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(0xfff5f5f5);
        this.setOnClickListener(v -> {
            setShow(!show);
            if (show){
                Glide.with(this).load(R.drawable.ic_sleep_coin).into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable @org.jetbrains.annotations.Nullable Transition<? super Drawable> transition) {
                        BigCirlView.this.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable @org.jetbrains.annotations.Nullable Drawable placeholder) {

                    }
                });
            }
        });
    }

    private boolean show = false;
    private Paint paint = null;

    public void setShow(boolean show) {
        this.show = show;
        if (show) {
            setBackgroundColor(Color.TRANSPARENT);
        }
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX = (left + right) / 2.0f - left;
        radius = MaxRadiu;
        centerY = bottom - top - MaxRadiu;
        invalidate();
    }

    private float centerX, centerY, radius;
    private static final int MaxRadiu = 3000;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (show) {
            return;
        }
        if (paint == null) {
            paint = new Paint();
            paint.setColor(Color.WHITE);
            paint.setAntiAlias(true);
        }
        if (centerX == 0 && centerY == 0) {
            return;
        }
        canvas.drawCircle(centerX, centerY, radius, paint);
    }
}
