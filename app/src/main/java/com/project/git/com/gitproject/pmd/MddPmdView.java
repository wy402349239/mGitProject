package com.project.git.com.gitproject.pmd;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * created by wangyu on 2019-12-09
 * description : 跑马灯View
 */
public class MddPmdView extends View {

    private Paint mPaint = null;
    private int mW = 0;
    private int mH = 0;
    /**
     * 背景圆角半径
     */
    private float mRadiu = 0;
    /**
     * 边框线宽度
     */
    private float mStockWidth = 0;
    /**
     * 点半径
     */
    private float mCirlRadiu = 0;
    /**
     * 背景颜色
     */
    private int mBgColor = Color.BLACK;
    /**
     * 边框线颜色
     */
    private int mStockColor = 0xffd88341;
    /**
     * 线上点的个数
     */
    private int mPointCount = 0;

    private long duration = 0;
    private List<PointF> mPoints = new ArrayList<>();
    private List<Integer> colors = new ArrayList<>();
    private Disposable mDisposable;
    private int mRunIndex = -1;

    public MddPmdView(Context context) {
        this(context, null);
    }

    public MddPmdView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MddPmdView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initDatas(context, attrs);
    }

    private void initDatas(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MddPmdView);
        mBgColor = a.getInt(R.styleable.MddPmdView_pmdBgColor, Color.GRAY);
        mCirlRadiu = DeviceUtil.dip2px(context, a.getInt(R.styleable.MddPmdView_pmdCirlRadiu, 0));
        mStockColor = a.getColor(R.styleable.MddPmdView_pmdStockColor, 0xfff3a33c);
        mRadiu = DeviceUtil.dip2px(context, a.getInt(R.styleable.MddPmdView_pmdRadiu, 0));
        mStockWidth = DeviceUtil.dip2px(context, a.getInt(R.styleable.MddPmdView_pmdStockWidth, 0));
        mPointCount = a.getInt(R.styleable.MddPmdView_pmdPointCount, 0);
        duration = a.getInt(R.styleable.MddPmdView_pmdDuration, 300);
        a.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        colors.add(Color.RED);
        colors.add(Color.YELLOW);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
    }

    /**
     * 计算数据(线上的所有点)
     */
    private void loadDatas() {
        mPoints.clear();
        if (mPointCount <= 0 || mW <= 0 || mH <= 0) {
            return;
        }
        float nCirl = (float) (Math.PI * mRadiu);//圆长度
        float nTotalLengh = nCirl + mW * 2 + mH * 2 - mRadiu * 8;//内圈总长度
        float nMult = nTotalLengh / mPointCount;
        List<Integer> sizes = new ArrayList<>();

        //上边
        float w1 = mW - mRadiu * 2;
        float y = w1 % nMult;//每条边计算完成后的余值
        int size = (int) ((w1 - y) / nMult);
        sizes.add(size);
        PointF nFirstPoint = new PointF();//起始位置，第一个点
        nFirstPoint.x = mRadiu;
        nFirstPoint.y = mRadiu / 2;
        mPoints.add(nFirstPoint);
        for (int i = 0; i < size; i++) {
            //上边时  第一个点为计算的起点，之后的点Y左边与起点相等，X为根据单位递加
            PointF point = new PointF();
            point.y = mRadiu / 2;
            point.x = mRadiu + (i + 1) * nMult;
            mPoints.add(point);
        }
        float offset = y;//主要用于下一条边计算
        double nA = -90;

        //右上圆弧
        float w2 = nCirl / 4 + y;
        y = w2 % nMult;
        size = (int) ((w2 - y) / nMult);
        sizes.add(size);
        for (int i = 0; i < size; i++) {
            float nw = nMult - offset;//计算圆弧上的长度
            offset = 0;

            nA += nw * 90 / (nCirl / 4);//根据长度  计算角度
            PointF point = new PointF();
            point.x = mW - mRadiu + (float) (mRadiu / 2 * Math.cos(nA * Math.PI / 180));
            point.y = mRadiu + (float) (mRadiu / 2 * Math.sin(nA * Math.PI / 180));
            mPoints.add(point);
        }
        offset = y;

        //右边
        float w3 = mH - mRadiu * 2 + y;
        y = w3 % nMult;
        size = (int) ((w3 - y) / nMult);
        sizes.add(size);
        for (int i = 0; i < size; i++) {
            PointF point = new PointF();
            point.x = mW - mRadiu / 2;
            if (i == 0) {
                point.y = mRadiu + nMult - offset;
            } else {
                point.y = mPoints.get(mPoints.size() - 1).y + nMult;
            }
            mPoints.add(point);
        }
        offset = y;

        //右下圆弧
        float w4 = nCirl / 4 + y;
        y = w4 % nMult;
        size = (int) ((w4 - y) / nMult);
        sizes.add(size);
        nA = 0;
        for (int i = 0; i < size; i++) {
            float nw = nMult - offset;
            offset = 0;
            nA += nw * 90 / (nCirl / 4);//根据长度  计算角度
            PointF point = new PointF();
            point.x = mW - mRadiu + (float) (mRadiu / 2 * Math.cos(nA * Math.PI / 180));
            point.y = mH - mRadiu + (float) (mRadiu / 2 * Math.sin(nA * Math.PI / 180));
            mPoints.add(point);
        }
        offset = y;

        //下边
        float w5 = mW - mRadiu * 2 + y;
        y = w5 % nMult;
        size = (int) ((w5 - y) / nMult);
        sizes.add(size);
        for (int i = 0; i < size; i++) {
            PointF point = new PointF();
            point.y = mH - mRadiu / 2;
            if (i == 0) {
                point.x = mW - mRadiu - (nMult - offset);
            } else {
                point.x = mPoints.get(mPoints.size() - 1).x - nMult;
            }
            mPoints.add(point);
        }
        offset = y;

        //左下圆弧
        float w6 = nCirl / 4 + y;
        y = w6 % nMult;
        size = (int) ((w6 - y) / nMult);
        sizes.add(size);
        nA = 90;
        for (int i = 0; i < size; i++) {
            float nw = nMult - offset;
            offset = 0;
            nA += nw * 90 / (nCirl / 4);//根据长度  计算角度
            PointF point = new PointF();
            point.x = mRadiu + (float) (mRadiu / 2 * Math.cos(nA * Math.PI / 180));
            point.y = mH - mRadiu + (float) (mRadiu / 2 * Math.sin(nA * Math.PI / 180));
            mPoints.add(point);
        }
        offset = y;

        //左边
        float w7 = mH - mRadiu * 2 + y;
        y = w7 % nMult;
        size = (int) ((w7 - y) / nMult);
        sizes.add(size);
        for (int i = 0; i < size; i++) {
            PointF point = new PointF();
            point.x = mRadiu / 2;
            if (i == 0) {
                point.y = mH - mRadiu - (nMult - offset);
            } else {
                point.y = mPoints.get(mPoints.size() - 1).y - nMult;
            }
            mPoints.add(point);
        }
        offset = y;

        //左上圆弧
        float w8 = nCirl / 4 + y;
        y = w8 % nMult;
        size = (int) ((w8 - y) / nMult);
        sizes.add(size);
        nA = 180;
        for (int i = 0; i < size; i++) {
            float nw = nMult - offset;
            offset = 0;
            nA += nw * 90 / (nCirl / 4);//根据长度  计算角度
            PointF point = new PointF();
            point.x = mRadiu + (float) (mRadiu / 2 * Math.cos(nA * Math.PI / 180));
            point.y = mRadiu + (float) (mRadiu / 2 * Math.sin(nA * Math.PI / 180));
            mPoints.add(point);
        }
        sizes.clear();
    }

    /**
     * 入口，开始绘制
     *
     * @param width  总宽
     * @param height 总高
     */
    public void startRun(int width, int height) {
        mW = width;
        mH = height;
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        loadDatas();
        mDisposable = Observable.intervalRange(0, Integer.MAX_VALUE, 0, duration, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long aLong) throws Exception {
                        try {
                            mRunIndex += 1;
                            invalidate();
                        } catch (Exception e) {
                            Log.e("Tag", Log.getStackTraceString(e));
                        }
                    }
                });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mPoints.isEmpty()) {
            //无数据时不绘制
            return;
        }
        mPaint.setColor(mBgColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(new RectF(0, 0, mW, mH), mRadiu, mRadiu, mPaint);//画背景

        mPaint.setColor(mStockColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStockWidth);
        RectF rect = new RectF(mRadiu / 2, mRadiu / 2, mW - mRadiu / 2, mH - mRadiu / 2);
        canvas.drawRoundRect(rect, mRadiu / 2, mRadiu / 2, mPaint);
        //画边线，此时rect的四条边的值，为实际效果4条边的中心值，即为边线宽度1时的位置，大于1时则以1为中心

        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mPoints.size(); i++) {
            //轮询切换颜色  并且每次切换时，修改对应坐标点绘制颜色的下标，以达到一种动态的效果
            mPaint.setColor(colors.get((i + mRunIndex) % colors.size()));
            canvas.drawCircle(mPoints.get(i).x, mPoints.get(i).y, mCirlRadiu, mPaint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public MddPmdView setRadiu(float radiu) {
        this.mRadiu = radiu;
        return MddPmdView.this;
    }

    public MddPmdView setStockWidth(float stockWidth) {
        this.mStockWidth = stockWidth;
        return MddPmdView.this;
    }

    public MddPmdView setCirlRadiu(float cirlRadiu) {
        this.mCirlRadiu = cirlRadiu;
        return MddPmdView.this;
    }

    public MddPmdView setBgColor(int bgColor) {
        this.mBgColor = bgColor;
        return MddPmdView.this;
    }

    public MddPmdView setStockColor(int stockColor) {
        this.mStockColor = stockColor;
        return MddPmdView.this;
    }

    public MddPmdView setPointCount(int count) {
        this.mPointCount = count;
        return MddPmdView.this;
    }

    public void setColors(List<Integer> colors) {
        this.colors.clear();
        if (colors != null && !colors.isEmpty()) {
            this.colors.addAll(colors);
        }
    }
}
