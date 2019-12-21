package com.project.git.com.gitproject.pmd;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019-12-16
 * description :
 */
public class BtnView extends View {

    private Paint mPaint = null;
    private Paint mOvPaint = null;
    private Paint mTxtPaint = null;
    private float mW, mH;
    private Context mCtx;
    private String mTxt = "";
    private List<String> mTxts = new ArrayList<>();

    private boolean showAll = true;

    public BtnView(Context context) {
        this(context, null);
    }

    public BtnView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BtnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCtx = context;
    }

    private void init() {
        mTxts.clear();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(1);
        mPaint.setColor(Color.BLACK);
        mOvPaint = new Paint();
        mOvPaint.setAntiAlias(true);
        mOvPaint.setAlpha(1);
        mOvPaint.setColor(Color.WHITE);
        mTxtPaint = new Paint();
        mTxtPaint.setTextSize(sp2px(mCtx, 16));
        mTxtPaint.setColor(0xffd43306);
        mTxtPaint.setAntiAlias(true);

        mW = mTxtPaint.measureText(mTxt) + DeviceUtil.dip2px(mCtx, 60);
        int max = DeviceUtil.getScreenWidth(mCtx) - +DeviceUtil.dip2px(mCtx, 30);
        if (mW > max) {
            mW = max;//超出长度   需要计算显示
            final String nLast = "%s...";
            char[] chars = mTxt.toCharArray();
            float nTxtWidth = mW - DeviceUtil.dip2px(mCtx, 60);//单行最大宽度
            int index = 0;
            if (!showAll) {
                for (int i = 0; i < chars.length; i++) {
                    if (mTxtPaint.measureText(String.format(nLast, mTxt.substring(0, i))) > nTxtWidth) {
                        break;
                    } else {
                        index = i;//循环获取最大宽度
                    }
                }
                mTxt = String.format(nLast, mTxt.substring(0, index));
            } else {
                for (int i = 0; i < chars.length; i++) {
                    if (mTxtPaint.measureText(String.format(nLast, mTxt.substring(index, i))) > nTxtWidth) {
                        if (i > 0) {
                            mTxts.add(mTxt.substring(index, i - 1));
                            index = i;
                        } else {
                            break;
                        }
                    } else if (i == mTxt.length() - 1) {
                        mTxts.add(mTxt.substring(index, i));
                    }
                }
            }
        }

        Paint.FontMetrics fontMetrics = mTxtPaint.getFontMetrics();
        mH = (fontMetrics.bottom - fontMetrics.top) * (mTxts.size() > 0 ? mTxts.size() : 1) + DeviceUtil.dip2px(mCtx, 20) + padding;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) BtnView.this.getLayoutParams();
        params.width = Math.round(mW);
        params.height = Math.round(mH);
        BtnView.this.setLayoutParams(params);
        invalidate();
    }

    public void settext(String str) {
        mTxt = str;
        init();
    }

    private int padding = 12;
    private int sd = 12;
    private int sdColor = 0xffECA048;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mW == 0 || TextUtils.isEmpty(mTxt)) {
            return;
        }
        RectF rectBg = new RectF(0, 0, mW, mH);
        LinearGradient gradient = new LinearGradient(0, 0, 0, mH, new int[]{0xffffca3d, 0xfff88f23}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        canvas.drawRoundRect(rectBg, mH / 2, mH / 2, mPaint);//外层

        RadialGradient rgl = new RadialGradient(padding + (mH - padding) / 2, (mH - padding + sd) / 2,
                (mH - padding + sd) / 2, new int[]{Color.TRANSPARENT, sdColor, Color.TRANSPARENT},
                new float[]{0, (mH - padding) / (mH - padding + sd), 1f}, Shader.TileMode.CLAMP);
        mPaint.setShader(rgl);
        RectF rfsd1 = new RectF(padding - sd / 2, 0, padding + mH - padding + sd / 2, mH - padding + sd);
        canvas.drawArc(rfsd1, 90, 180, true, mPaint);//左阴影

        LinearGradient rgb = new LinearGradient(padding + (mH - padding) / 2, mH - padding - (mH - padding) / 2,
                padding + (mH - padding) / 2, mH - padding + sd,
                new int[]{Color.TRANSPARENT, sdColor, Color.TRANSPARENT}, new float[]{0, (mH - padding) / (mH - padding + sd), 1f}, Shader.TileMode.CLAMP);
        mPaint.setShader(rgb);
        RectF rfb = new RectF(padding + (mH - padding) / 2, mH - padding - (mH - padding) / 2,
                mW - padding - (mH - padding) / 2, mH - padding + sd);
        canvas.drawRect(rfb, mPaint);//下阴影

        RadialGradient rgr = new RadialGradient(mW - padding - (mH - padding + sd) / 2, (mH - padding + sd) / 2,
                (mH - padding + sd) / 2, new int[]{Color.TRANSPARENT, sdColor, Color.TRANSPARENT},
                new float[]{0, (mH - padding) / (mH - padding + sd), 1f}, Shader.TileMode.CLAMP);
        mPaint.setShader(rgr);
        RectF rfr = new RectF(mW - padding - (mH - padding) - sd / 2, 0, mW - padding + sd / 2, mH - padding + sd);
        canvas.drawArc(rfr, -90, 180, true, mPaint);//右阴影

        LinearGradient gradient2 = new LinearGradient(padding, 1, padding, mH - padding, new int[]{0xffffed69, 0xffffc83b}, null, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient2);
        RectF rectBg2 = new RectF(padding, 1, mW - padding, mH - padding);
        canvas.drawRoundRect(rectBg2, (mH - padding) / 2, (mH - padding) / 2, mPaint);//内层

        Paint.FontMetrics fontMetrics = mTxtPaint.getFontMetrics();
        if (mTxts.isEmpty()) {
            float start = (mW - mTxtPaint.measureText(mTxt)) / 2;
            start = start < 0 ? 0 : start;
            float y = DeviceUtil.dip2px(mCtx, 10) - fontMetrics.top;
            y = y > mH ? mH : y;
            canvas.drawText(mTxt, start, y, mTxtPaint);
        } else {
            for (int i = 0; i < mTxts.size(); i++) {
                String nTxt = mTxts.get(i);
                float start = (mW - mTxtPaint.measureText(nTxt)) / 2;
                start = start < 0 ? 0 : start;
                float y = DeviceUtil.dip2px(mCtx, 10) - fontMetrics.top + i * (fontMetrics.bottom - fontMetrics.top);
                y = y > mH ? mH : y;
                canvas.drawText(nTxt, start, y, mTxtPaint);
            }
        }//文字

        float left = padding + mH / 2 - padding;
        float line = (mH - padding) / 3;
        mOvPaint.setColor(Color.WHITE);
        RectF rfs1 = new RectF(left - padding, line, left, line + padding * 5 / 7);
        canvas.drawOval(rfs1, mOvPaint);
        RectF rfs2 = new RectF(left, line - padding * 3 / 2, left + padding * 2, line);
        canvas.drawOval(rfs2, mOvPaint);//反光气泡
    }

    private float sp2px(Context context, float spValue) {
        return spValue * context.getResources().getDisplayMetrics().scaledDensity + 0.5f;
    }
}