package com.project.git.com.gitproject.canvas;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.project.git.com.gitproject.R;

/**
 * 镂空文字view
 *
 * @author jx_wy
 * @date 4:09 PM 2019/2/22
 * @email wangyu@51dianshijia.com
 * @description
 */
public class NullTextView extends View {

    private Paint mPaint;
    private String textString;
    private int mBgColor;
    private float mRadius;
    private float textSize;
    private float drawY;

    public NullTextView(Context context) {
        this(context, null);
    }

    public NullTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NullTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.HollowView);
            // 获取要实现的内容
            textString = a.getString(R.styleable.HollowView_name);
            // 获取背景色
            mBgColor = a.getColor(R.styleable.HollowView_bgColor, mBgColor);
            if (a.hasValue(R.styleable.HollowView_textSize)) {
                // 获取文字大小
                textSize = a.getDimension(R.styleable.HollowView_textSize, textSize);
            }
            // 获取圆角半径
            mRadius = a.getDimension(R.styleable.HollowView_radius, mRadius);
            a.recycle();
        }

        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBgColor);

        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        // 计算绘制文字起始点的Y值
        drawY = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
    }

    private int width, height;
    private int mWidth, mHeight;
    private RectF rectF;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 获取文字宽度
        width = getTextWidth(textString, mPaint);
        // 获取文字高度
        height = getTextHeight(textString, mPaint);
        // 计算空间应占的宽度
        mWidth = width + getPaddingLeft() + getPaddingRight();
        // 计算空间应占的高度
        mHeight = height + getPaddingTop() + getPaddingBottom();
        // 背景的绘制范围
        rectF = new RectF(-mWidth / 2, -mHeight / 2, mWidth / 2, mHeight / 2);
        setMeasuredDimension(mWidth, mHeight);
    }

    private int getTextWidth(String str, Paint paint) {
        paint.measureText(str);
        float width = paint.measureText(str);
        return Math.round(width);
    }

    private int getTextHeight(String str, Paint paint) {
        paint.measureText(str);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return Math.round(fontMetrics.bottom - fontMetrics.top);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 移动原点到view中心
        canvas.translate(mWidth / 2, mHeight / 2);
        // 保存layer
        int layer = canvas.saveLayer(rectF, mPaint);
        // 绘制圆角矩形的背景
        canvas.drawRoundRect(rectF, mRadius, mRadius, mPaint);
        // 设置画笔PorterDuff.Mode.DST_OUT模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        // 绘制文字
        canvas.drawText(textString, -width / 2, drawY, mPaint);
        mPaint.setXfermode(null);
        // 还原图层
        canvas.restoreToCount(layer);
    }
}
