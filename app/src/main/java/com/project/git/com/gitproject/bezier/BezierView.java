package com.project.git.com.gitproject.bezier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019-12-03
 * description : wy
 */
public class BezierView extends View {

    /**
     * 用于定位的点
     */
    private List<PointF> points = new ArrayList<>();
    private List<PointF> mFrameRects = new ArrayList<>();
    /**
     * 曲线上的点
     */
    private List<PointF> lines = new ArrayList<>();
    /**
     * 动画持续时间
     */
    private float duration = 5000;
    /**
     * 单次动画的开始时间
     */
    private long startTime = 0;

    /**
     * 曲线画笔
     */
    private Paint paint;

    /**
     * 边框、点等画笔
     */
    private Paint mRectPaint = null;

    /**
     * 与ys一起，用于计算当前最新的一个点
     */
    List<Float> xs = new ArrayList<>();
    List<Float> ys = new ArrayList<>();

    public BezierView(Context context) {
        this(context, null);
    }

    public BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);

        mRectPaint = new Paint();
        mRectPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        points.clear();
        int maxHeight = h - 100;
        int height = maxHeight / 3;
        int wid = DeviceUtil.getScreenWidth(getContext()) / 2 - 50;
        for (int i = 0; i < 4; i++) {
            PointF point = new PointF();
//            point.x = w / 2 + (i % 3 == 0 ? 0 : (i > 1 ? wid : -wid));
//            point.y = h - 50 - i * height;
            point.x = i % 3 == 0 ? 50 : w - 50;
            point.y = i < 2 ? 50 : i == 3 ? h / 2 - 50 : h - 50;
            points.add(point);
        }
        for (int i = 0; i < 4; i++) {
            PointF point = new PointF();
            point.x = i % 3 == 0 ? 50 : w - 50;
            point.y = i < 2 ? 50 : h - 50;
            mFrameRects.add(point);
        }
    }

    public void startDraw() {
        BezierView.this.postDelayed(new Runnable() {
            @Override
            public void run() {
                startTime = System.currentTimeMillis();
                lines.clear();
                lines.add(points.get(0));
                invalidate();
            }
        }, 50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mRectPaint.setColor(getResources().getColor(R.color.systemColor));
        mRectPaint.setStrokeWidth(2);
        for (int i = 0; i < mFrameRects.size(); i++) {
            //画边框
            int index = (i + 1) % mFrameRects.size();
            canvas.drawLine(mFrameRects.get(i).x, mFrameRects.get(i).y, mFrameRects.get(index).x, mFrameRects.get(index).y, mRectPaint);
        }
        if (lines.isEmpty()) {
            return;
        }
        if (System.currentTimeMillis() - startTime > (duration - 50)) {
            //如果当前时间与本次动画的开始时间，超过单次动画时长，开始新一次动画
            lines.clear();
            startDraw();
            return;
        }
        float t = (System.currentTimeMillis() - startTime) % duration / (duration - 50);//计算当前位置
        xs.clear();
        ys.clear();
        while (xs.size() != 1 && ys.size() != 1) {
            //根据当前位置(时间)，递归计算出当前位置坐标
            //计算方法，根据当前位置(时间)与单次动画的持续时间的比例，循环计算出points中连续两个点中间的坐标，
            // 然后再将本次结果存入xs,ys，作为类似points的集合，继续循环计算，并重新保存到xs,ys中，
            // 直到最终只有一个坐标点时，即当前位置(时间)的结果，作为最新的一个点，存入lines中
            if (xs.isEmpty()) {
                //为空时，根据points计算第一轮中间值
                for (int i = 0; i < points.size() - 1; i++) {
                    xs.add(points.get(i).x * (1 - t) + points.get(i + 1).x * t);
                    ys.add(points.get(i).y * (1 - t) + points.get(i + 1).y * t);
                }
                for (int i = 0; i < points.size(); i++) {
                    //最外层的线与点
                    int index = (i + 1) % points.size();
                    mRectPaint.setColor(Color.BLUE);
                    canvas.drawLine(points.get(i).x, points.get(i).y, points.get(index).x, points.get(index).y, mRectPaint);
                    float x = points.get(i).x + (points.get(index).x - points.get(i).x) * t;
                    float y = points.get(i).y + (points.get(index).y - points.get(i).y) * t;
                    mRectPaint.setColor(Color.RED);
                    canvas.drawCircle(x, y, 7, mRectPaint);
                }
            } else {
                //非空时，新建集合缓存并循环计算新一轮的中间值
                List<Float> nXs = new ArrayList<>();
                List<Float> nYs = new ArrayList<>();
                nXs.addAll(xs);
                nYs.addAll(ys);
                for (int i = 0; i < nXs.size() - 1 && nXs.size() > 1; i++) {
                    //里层的线与点
                    int index = (i + 1) % nXs.size();
                    mRectPaint.setColor(0xff62C655);
                    canvas.drawLine(nXs.get(i), nYs.get(i), nXs.get(index), nYs.get(index), mRectPaint);
                    float x = nXs.get(i) + (nXs.get(index) - nXs.get(i)) * t;
                    float y = nYs.get(i) + (nYs.get(index) - nYs.get(i)) * t;
                    mRectPaint.setColor(Color.RED);
                    canvas.drawCircle(x, y, 7, mRectPaint);
                }
                xs.clear();
                ys.clear();
                for (int i = 0; i < nXs.size() - 1; i++) {
                    xs.add(nXs.get(i) + (nXs.get(i + 1) - nXs.get(i)) * t);
                    ys.add(nYs.get(i) + (nYs.get(i + 1) - nYs.get(i)) * t);
                }
                nXs.clear();
                nYs.clear();
            }
        }
        PointF point = new PointF();
        point.x = xs.get(0);
        point.y = ys.get(0);
        lines.add(point);
        if (lines.size() > 1) {
            paint.setStrokeWidth(4);
            for (int i = 0; i < lines.size() - 1; i++) {
                //曲线
                canvas.drawLine(lines.get(i).x, lines.get(i).y, lines.get(i + 1).x, lines.get(i + 1).y, paint);
            }
        }
        mRectPaint.setColor(Color.BLACK);
        canvas.drawCircle(point.x, point.y, 7, mRectPaint);
        invalidate();
    }

    /*
     * Drawable → Bitmap
     */
    private static Bitmap drawable2Bitmap(Drawable drawable, float f) {
        if (drawable == null) {
            return null;
        }
        // 取 drawable 的长宽
        int w = Math.round(drawable.getIntrinsicWidth() * f);
        int h = Math.round(drawable.getIntrinsicHeight() * f);
        // 取 drawable 的颜色格式
        Bitmap.Config config = drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565;
        // 建立对应 bitmap
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        // 建立对应 bitmap 的画布
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        // 把 drawable 内容画到画布中
        drawable.draw(canvas);
        return bitmap;
    }
}
