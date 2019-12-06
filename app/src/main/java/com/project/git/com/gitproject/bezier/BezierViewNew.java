package com.project.git.com.gitproject.bezier;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019-12-05
 * description : 金币加减动画
 */
public class BezierViewNew extends View {

    private List<Point> points = new ArrayList<>();
    private List<Point> lines = new ArrayList<>();
    private float duration = 3000;
    private float waitTime = 50;
    private long startTime = 0;
    private int maxWh = 0;

    private Paint paint;
    private boolean mIsAdd = true;

    List<Integer> xs = new ArrayList<>();
    List<Integer> ys = new ArrayList<>();

    public BezierViewNew(Context context) {
        this(context, null);
    }

    public BezierViewNew(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BezierViewNew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        maxWh = DeviceUtil.dip2px(context, 60);
        paint = new Paint();
        paint.setAntiAlias(true);
    }

    private void initPoints() {
        points.clear();
        int w = getWidth();
        int h = getHeight();
        int mult = h / 6;
        if (mIsAdd) {
            h += (mult * 4);
            int minY = maxWh / 2;
            for (int i = 0; i < 4; i++) {
                Point point = new Point();
                point.x = w / 2 + (i % 3 == 0 ? 0 : (i > 1 ? w / 2 : -w / 2));
                h += (mult * (i - 4));
                point.y = h > minY ? h : minY;
                points.add(point);
            }
        } else {
            for (int i = 0; i < 4; i++) {
                Point point = new Point();
                point.x = w / 2 + (i % 3 == 0 ? 0 : (i > 1 ? -w / 2 : w / 2));
                point.y = i == 0 ? h - maxWh / 2 : h - i * 2 * mult;
                points.add(point);
            }
        }
    }

    public void setDuration(float duration) {
        this.duration = duration;
        startTime = System.currentTimeMillis();
        lines.clear();
        continueDraw();
    }

    public void startDraw(boolean isUp) {
        this.mIsAdd = isUp;
        initPoints();
        continueDraw();
    }

    private void continueDraw() {
        if (lines.isEmpty()) {
            BezierViewNew.this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startTime = System.currentTimeMillis();
                    lines.clear();
                    lines.add(points.get(0));
                    invalidate();
                }
            }, (long) waitTime);
        } else {
            lines.clear();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (lines.isEmpty()) {
            return;
        }
        float nDuration = duration - waitTime;
        if (System.currentTimeMillis() - startTime > nDuration) {
            startTime += nDuration;
            lines.clear();
            continueDraw();
            return;
        }
        float t = (System.currentTimeMillis() - startTime) % nDuration / nDuration;
        xs.clear();
        ys.clear();
        while (xs.size() != 1 && ys.size() != 1) {
            if (xs.isEmpty()) {
                for (int i = 0; i < points.size() - 1; i++) {
                    xs.add((int) (points.get(i).x * (1 - t) + points.get(i + 1).x * t));
                    ys.add((int) (points.get(i).y * (1 - t) + points.get(i + 1).y * t));
                }
            } else {
                List<Integer> nXs = new ArrayList<>();
                List<Integer> nYs = new ArrayList<>();
                nXs.addAll(xs);
                nYs.addAll(ys);
                xs.clear();
                ys.clear();
                for (int i = 0; i < nXs.size() - 1; i++) {
                    xs.add((int) (nXs.get(i) * (1 - t) + t * nXs.get(i + 1)));
                    ys.add((int) (nYs.get(i) * (1 - t) + t * nYs.get(i + 1)));
                }
                nXs.clear();
                nYs.clear();
            }
        }
        Point point = new Point();
        point.x = xs.get(0);
        point.y = ys.get(0);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_sleep_coin);
        int left = Math.round(xs.get(0) - maxWh * t / 2);
        int top = Math.round(ys.get(0) - maxWh * t / 2);
        canvas.drawBitmap(drawable2Bitmap(drawable, t), left, top, paint);
        lines.add(point);
        invalidate();
    }

    /*
     * Drawable → Bitmap
     */
    private Bitmap drawable2Bitmap(Drawable drawable, float d) {
        if (drawable == null) {
            return null;
        }
        float f = mIsAdd ? d : 1 - d;
        // 取 drawable 的长宽
        int w = Math.round(maxWh * f);
        w = w > 0 ? w : 1;
        int h = Math.round(maxWh * f);
        h = h > 0 ? h : 1;
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
