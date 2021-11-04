package com.project.git.com.gitproject.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 语音动画
 * @Author: jx_wy
 * @Date: 2021/10/13 11:24 上午
 */
public class VoiceWave extends View {
    public VoiceWave(Context context) {
        this(context, null);
    }

    public VoiceWave(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceWave(Context context, @Nullable @org.jetbrains.annotations.Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initDatas() {
        if (paint == null) {
            paint = new Paint();
            paint.setColor(0xff2469fe);
            paint.setAntiAlias(true);
        }
        if (!points.isEmpty()) {
            points.clear();
        }
        if (!positions.isEmpty()) {
            positions.clear();
        }
        if (!adds.isEmpty()) {
            adds.clear();
        }
        for (int i = 0; i < 11; i++) {
            PointF point = new PointF();
            point.y = max - (max - min) * (i <= 5 ? i : (10 - i)) / 5f;
            point.x = centerX + (i - 5) * centerX / 10;
            points.add(point);
        }
    }

    private Paint paint;
    private final List<PointF> points = new ArrayList<>();
    private final Map<Integer, Float> positions = new HashMap<>();
    private final Map<Integer, Boolean> adds = new HashMap<>();

    private boolean playing = false;

    public void resetStatu(boolean play) {
        playing = play;
        if (play) {
            initDatas();
        }
        invalidate();
    }

    public void playAnimation() {
        resetStatu(true);
    }

    public void stopAnimation() {
        resetStatu(false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        centerX = (right - left) / 2f;
        centerY = (bottom - top) / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paint == null) {
            initDatas();
        }
        if (playing) {
            drawAnimation(canvas);
        } else {
            drawDefault(canvas);
        }
    }

    private void drawDefault(Canvas canvas) {
        if (paint == null || canvas == null || points.isEmpty()) {
            return;
        }
        for (PointF point : points) {
            canvas.drawCircle(point.x, centerY, radius, paint);
        }
    }

    private void getOffset(PointF point) {
        int index = points.indexOf(point);
        boolean add;
        if (!adds.containsKey(index) || adds.get(index) == null) {
            add = true;
        } else {
            add = Boolean.TRUE.equals(adds.get(index));
        }
        int count = 5;
        if (point.y <= min) {
            add = true;
        } else if (point.y >= max) {
            add = false;
        }
        adds.put(index, add);
        point.y += (add ? 2 : -2);
        int offset = (max - min) * (index <= count ? index : (count * 2 - index)) / count;
        float prop = (max - point.y) / (max - min);
        float y = max - offset * prop - 20 * prop * Math.abs(index - count) / count;
        positions.put(index, y);
    }

    private void drawAnimation(Canvas canvas) {
        if (paint == null || canvas == null || points.isEmpty()) {
            return;
        }
        for (int i = 0; i < points.size(); i++) {
            PointF point = points.get(i);
            getOffset(point);
            float top = positions.get(i);
            float bottom = 240 - top;
            canvas.drawCircle(point.x, top, radius, paint);
            canvas.drawCircle(point.x, bottom, radius, paint);
            RectF rectF = new RectF(point.x - radius, top, point.x + radius, bottom);
            canvas.drawRect(rectF, paint);
        }
        invalidate();
    }

    private static final float radius = 8;
    private float centerX = 540, centerY = 120;
    private final int max = 120, min = 60;

    public float getCenterX() {
        return centerX;
    }

    public void setCenterX(float centerX) {
        this.centerX = centerX;
    }

    public float getCenterY() {
        return centerY;
    }

    public void setCenterY(float centerY) {
        this.centerY = centerY;
    }
}
