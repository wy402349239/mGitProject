package com.project.git.com.gitproject.wave;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.project.git.com.gitproject.R;

import java.util.ArrayList;
import java.util.List;

import skin.support.SkinCompatManager;
import skin.support.widget.SkinCompatBackgroundHelper;
import skin.support.widget.SkinCompatSupportable;

/**
 * @author jx_wy
 * @date 2:34 PM 2019/3/1
 * @email wangyu@51dianshijia.com
 * @description
 */
public class WaveView extends View implements SkinCompatSupportable {

    private Paint mCenterPaint; //中心圆paint
    private int mRadius = 0; //中心圆半径  默认0
    private Paint mSpreadPaint; //扩散圆paint
    private Paint mBackgroundPaint; //扩散圆paint
    private float centerX;//圆心x
    private float centerY;//圆心y
    private int mMaxRadius = 0; //最大圆半径，默认扩展到屏幕两边
    private List<Integer> spreadRadius = new ArrayList<>();//扩散圆层级数，元素为扩散的距离
    private List<Integer> alphas = new ArrayList<>();//对应每层圆的透明度
    private int mAlphaScale = 3;
    private static final String NightSkin = "night";

    public WaveView(Context context) {
        this(context, null);
    }

    public WaveView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        radius = (int) getResources().getDisplayMetrics().density * 50;
        mMaxRadius = getResources().getDisplayMetrics().widthPixels / 2;
        int centerColor = context.getResources().getColor(R.color.colorWave);
        int spreadColor = context.getResources().getColor(R.color.colorWave);

        mCenterPaint = new Paint();
        mCenterPaint.setColor(centerColor);
        mCenterPaint.setAntiAlias(true);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(getResources().getColor(R.color.systemcolor));
        mCenterPaint.setAntiAlias(true);

        mSpreadPaint = new Paint();
        mSpreadPaint.setAntiAlias(true);
        mSpreadPaint.setAlpha(255);
        mSpreadPaint.setColor(spreadColor);
    }

    /**
     * 设置相关参数
     *
     * @param nradius     中心园半径,起始位置
     * @param maxwidth    最大扩展距离
     * @param alphaScale  每次透明度变化的大小
     * @param centerColor 中心园颜色
     * @param spreadColor 扩散颜色
     */
    public void setparams(int nradius, int maxwidth, int alphaScale, int centerColor, int spreadColor) {
        mRadius = nradius;
        if (maxwidth < nradius) {
            mMaxRadius = nradius;
        } else {
            mMaxRadius = maxwidth;
        }
        mAlphaScale = alphaScale;
        mCenterPaint.setColor(centerColor);
        mSpreadPaint.setColor(spreadColor);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = w / 2;
        centerY = h / 2;
    }

    public void click() {
        if (alphas != null) {
            alphas.add(255);
        }
        if (spreadRadius != null)
            spreadRadius.add(0);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, WaveView.this.getWidth(), WaveView.this.getHeight(), mBackgroundPaint);
        super.onDraw(canvas);
        if (spreadRadius.isEmpty()) {
            return;
        }
        for (int i = 0; i < spreadRadius.size(); i++) {//一层一层绘制渐变扩散
            int alpha = alphas.get(i);
            mSpreadPaint.setAlpha(alpha);
            int width = spreadRadius.get(i);
            //绘制扩散的圆
            canvas.drawCircle(centerX, centerY, mRadius + width, mSpreadPaint);

            //每次扩散圆半径递增，圆透明度递减
            if (alpha > 0) {
                alpha = alpha - mAlphaScale > 0 ? alpha - mAlphaScale : 0;
                alphas.set(i, alpha);
                spreadRadius.set(i, width + (mMaxRadius - mRadius) * mAlphaScale / 255);
            }
        }
        //当最外层扩散圆半径达到最大半径时添加新扩散圆
        if (spreadRadius.get(spreadRadius.size() - 1) >= mMaxRadius) {
            //扩散到最大  移除最外圈
            alphas.remove(0);
            spreadRadius.remove(0);
        }
        invalidate();
//        postInvalidateDelayed(10);//不能延时  不然会有卡顿感
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            click();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void applySkin() {
        String curSkinName = SkinCompatManager.getInstance().getCurSkinName();
        if (curSkinName.equals(NightSkin)) {
            mBackgroundPaint.setColor(getResources().getColor(R.color.systemcolor_night));
            mCenterPaint.setColor(getResources().getColor(R.color.colorWave_night));
            mSpreadPaint.setColor(getResources().getColor(R.color.colorWave_night));
        } else {
            mBackgroundPaint.setColor(getResources().getColor(R.color.systemcolor));
            mCenterPaint.setColor(getResources().getColor(R.color.colorWave));
            mSpreadPaint.setColor(getResources().getColor(R.color.colorWave));
        }
        invalidate();
    }
}
