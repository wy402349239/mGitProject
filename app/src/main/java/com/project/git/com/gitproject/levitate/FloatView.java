package com.project.git.com.gitproject.levitate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class FloatView extends View {

    private List<Point> points = new ArrayList<>();
    private Paint paint;
    int nCount = 1;

    public FloatView(Context context) {
        super(context, null);
    }

    public FloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public FloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            paint = new Paint();
        }
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(0xff0492fb);
        drawLins(canvas);
    }

    private void drawLins(final Canvas canvas) {
        int nStartX = 10;
        int nEndX = getWidth() - 10;
        int nStartY = 10;
        int nXUnit = (nEndX - nStartX) / 10;
        int nYUnit = (getHeight() - 20) / 10;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Point point = new Point();
                if (i % 2 == 0) {
                    point.x = nStartX + nXUnit * j;
                } else {
                    point.x = nEndX - nXUnit * (j + 1);
                }
                point.y = nStartY + nYUnit * i;
                points.add(point);
            }
        }
        for (int i = 0; i < nCount; i++) {
            canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i).x + nXUnit, points.get(i).y, paint);
            if (points.get(i).x < nEndX - 10 && points.get(i).y < getHeight() - 10) {
                canvas.drawLine(points.get(i).x, points.get(i).y, points.get(i).x, points.get(i).y + nYUnit, paint);
                canvas.drawLine(points.get(i).x + nXUnit, points.get(i).y, points.get(i).x + nXUnit, points.get(i).y + nYUnit, paint);
                canvas.drawLine(points.get(i).x, points.get(i).y + nYUnit, points.get(i).x + nXUnit, points.get(i).y + nYUnit, paint);
            }
        }
        nCount++;
        if (nCount <= points.size()) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    invalidate();
                }
            }, 100);
        }
    }

    static class Point {
        int x;
        int y;
    }
}
