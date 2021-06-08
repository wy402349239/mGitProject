package com.project.git.com.gitproject.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;

import com.project.git.com.gitproject.R;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 4/16/21 4:26 PM
 */
public class ProgressView extends RelativeLayout {
    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        addViews();
    }

    private void addViews() {
        leftView = new View(getContext());
        paramLeft = new LayoutParams(-2, -1);
        paramLeft.addRule(RelativeLayout.ALIGN_PARENT_START);
        leftView.setBackgroundResource(R.drawable.progress_left_rect);
        this.addView(leftView, paramLeft);

        rightView = new View(getContext());
        paramRight = new LayoutParams(-1, -1);
        paramRight.addRule(RelativeLayout.ALIGN_PARENT_END);
        rightView.setBackgroundResource(R.drawable.progress_right_rect);
        this.addView(rightView, paramRight);

        progressView = new AppCompatTextView(getContext());
        progressView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        progressView.setTextColor(Color.WHITE);
        LayoutParams param = new LayoutParams(-2, -2);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);
        this.addView(progressView, param);
    }

    public void updateProgress(float progress) {
        int width = this.getWidth();
        if (width <= 0) {
            return;
        }
        int left = (int) (width * progress / 100);
        paramLeft.width = left;
        leftView.setLayoutParams(paramLeft);
        paramRight.width = width - left;
        rightView.setLayoutParams(paramRight);
        progressView.setText(String.valueOf(progress) + "%");
    }

    private View leftView, rightView;
    private AppCompatTextView progressView;
    private LayoutParams paramLeft, paramRight;

    Paint paint;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if (paint == null){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
        }
        RectF rectF = new RectF(0, 0, 0, 0);
        canvas.drawRoundRect(rectF, 66, 66, paint);
    }
}