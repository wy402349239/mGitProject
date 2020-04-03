package com.project.git.com.gitproject.lock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * created by wy on 2020-03-26
 * description :
 */
public class LockView extends View {

    public LockView(Context context) {
        this(context, null);
    }

    public LockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int radius = 0;
    private Paint mPaint = null;
    private Paint StokPaint = null;
    private int width, height;
    private int padding;
    private PointF center = new PointF();
    private Handler handler = null;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        StokPaint = new Paint();
        StokPaint.setAntiAlias(true);
        width = right - left;
        height = bottom - top;
        padding = Math.min(width, height) / 20;
        radius = Math.min(width, height) / 2 - padding;
        center.x = (left + right) / 2;
        center.y = (bottom - top) / 2;
        Disposable subscribe = Observable.intervalRange(0, Integer.MAX_VALUE, 0, 20, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(final Long aLong) throws Exception {
                        LockView.this.invalidate();
                    }
                });
    }

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(0xff303F9F);
        canvas.drawCircle(center.x, center.y, radius, mPaint);//背景

        Matrix matrix = new Matrix();
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {
            matrix.setRotate(i * 6, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
            canvas.setMatrix(matrix);
            if (i % 15 == 0) {
                //  0 3 6 9
                canvas.drawRect(center.x - 2, padding, center.x + 2, padding + 40, mPaint);
            } else if (i % 5 == 0) {
                canvas.drawRect(center.x - 1.8f, padding, center.x + 1.8f, padding + 24, mPaint);
            } else {
                canvas.drawRect(center.x - 1f, padding, center.x + 1f, padding + 12, mPaint);
            }
        }
        matrix.setRotate(0, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        canvas.setMatrix(matrix);
        mPaint.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < 4; i++) {
            String txt = String.valueOf(i == 0 ? 12 : i * 3);
            mPaint.setTextSize(40);
            float w = mPaint.measureText(txt);
            Paint.FontMetrics fm = mPaint.getFontMetrics();
            float des = fm.descent - fm.top;
            float x = 0;
            float y = 0;
            switch (i) {
                case 0:
                    x = center.x;
                    y = padding + 45 + des;
                    break;
                case 1:
                    x = center.x + radius - 45 - w;
                    y = center.y + (fm.bottom - fm.top) / 4;
                    break;
                case 2:
                    x = center.x;
                    y = center.y + radius - 45 - (fm.bottom - fm.top) / 2;
                    break;
                default:
                    x = center.x - radius + 45 + w;
                    y = center.y + (fm.bottom - fm.top) / 4;
                    break;
            }
            canvas.drawText(txt, x, y, mPaint);//画时刻数字
        }

        Path path = new Path();
        path.addArc(new RectF(center.x - 200, padding + 150, center.x + 200, center.y - 150),
                180, 180);//设置扇形区域边框，以及旋转的角度
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(40);
        citePaint.setStrokeWidth(3);
//        canvas.drawPath(path, citePaint);可放开查看扇形区域
        canvas.drawTextOnPath("lock view from canvas", path, 0, 0, citePaint);//顶部文字
        canvas.drawText(dateFormat.format(new Date()), center.x, center.y + radius - 150, mPaint);//当前时间

        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        canvas.drawCircle(center.x, center.y, radius, mPaint); //画圆圈
        mPaint.setStyle(Paint.Style.FILL);

        long mTime = 60 * 1000;
        long hTime = mTime * 60;
        long halfDay = 12 * hTime;
        long time = getNowTime() % halfDay;
        float hRotate = time * 360.0f / halfDay - 90;
        float mRotate = (time % hTime) * 360.0f / hTime - 90;
        float sRotate = (time % mTime) * 360.0f / mTime - 90;

        mPaint.setColor(0xffFF4081);
        matrix.setRotate(hRotate, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        canvas.setMatrix(matrix);
        mPaint.setStrokeWidth(12);
        canvas.drawLine(center.x - 30, center.y, center.x + radius / 2, center.y, mPaint);//时

        mPaint.setColor(0xff3F51B5);
        matrix.setRotate(mRotate, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        canvas.setMatrix(matrix);
        mPaint.setStrokeWidth(10);
        canvas.drawLine(center.x - 30, center.y, center.x + radius * 2 / 3, center.y, mPaint);//分

        mPaint.setColor(0xFFFFBB33);
        matrix.setRotate(sRotate, center.x, center.y);//以绘制区域中心为远点旋转，并将旋转角度递增，达到转圈的效果
        canvas.setMatrix(matrix);
        mPaint.setStrokeWidth(7);
        canvas.drawLine(center.x - 30, center.y, center.x + radius * 3 / 4, center.y, mPaint);//秒

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(center.x, center.y, 15, mPaint);//
    }

    /**
     * 获取今天的开始时间
     *
     * @return 时间戳long
     */
    public static long getNowTime() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return System.currentTimeMillis() - c.getTimeInMillis();
    }
}
