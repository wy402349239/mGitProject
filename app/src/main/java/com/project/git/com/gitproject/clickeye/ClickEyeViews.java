package com.project.git.com.gitproject.clickeye;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/8 11:01 上午
 */
public class ClickEyeViews extends View {
    public ClickEyeViews(@NonNull Context context) {
        this(context, null);
    }

    public ClickEyeViews(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClickEyeViews(@NonNull Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setBackgroundResource(R.drawable.img3);
    }

    private int width, height;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = right - left;
        height = bottom - top;
    }

    Paint paint = null;
    Path path = null;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if (paint == null) {
//            paint = new Paint();
//            paint.setAntiAlias(true);
//        }
//        if (path == null) {
//            path = new Path();
//        }
//        if (width > 0 && height > 0) {
//            int min = Math.min(width, height);
//            path.addCircle(width / 2, height / 2, min / 2, Path.Direction.CCW);
//            canvas.clipPath(path);
//            canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.img4), 0, 0, paint);
//        }
    }
}
