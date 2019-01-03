package com.project.git.com.gitproject.size;

import android.app.Activity;
import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 用于重新设置view的大小，textview的大小；达到不同手机上比例相同的目的
 *
 * @author jx_wy
 * @date 2018年12月21日
 */
public class ViewSizeUtil {

    private static ViewSizeUtil mUtil = null;
    private Activity mAct = null;
    /**
     * 默认的屏幕宽度  dp
     */
    private static final int mWidthDp = 360;
    /**
     * 实际屏幕宽度  px
     */
    private static int mScreenWidth = 0;
    /**
     * textview设置为dp时，每个dp所占像素
     */
    private static float mSizePx = 0;

    public ViewSizeUtil(Activity act) {
        this.mAct = act;
    }

    public static ViewSizeUtil getInstance(Activity act) {
        if (mUtil == null) {
            synchronized (ViewSizeUtil.class) {
                if (mUtil == null) {
                    mUtil = new ViewSizeUtil(act);
                }
            }
        }
        if (act != null && mScreenWidth == 0) {
            WindowManager wm = (WindowManager) act.getSystemService(Context.WINDOW_SERVICE);
            mScreenWidth = wm.getDefaultDisplay().getWidth();
            //计算每个dp对应px
            TextView tv = new TextView(act);
            tv.setText("哈");
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 100);
            float v = tv.getPaint().measureText(tv.getText().toString());
            mSizePx = v / 100;
        }
        return mUtil;
    }

    /**
     * 设置textview大小
     *
     * @param tv
     * @param sizeSp 对应设计图像素/2
     */
    public void setTxtSize(TextView tv, int sizeSp) {
        if (tv == null || sizeSp < 0) {
            return;
        }
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, sizeSp * mSizePx);
    }

    /**
     * 设置view大小
     *
     * @param view     view
     * @param widthDp  宽度,dp   对应设计图像素/2
     * @param heightDp 高度,dp   对应设计图像素/2
     * @param padding  内边距,dp 对应设计图像素/2
     * @param margin   外边距,dp 对应设计图像素/2
     */
    public void setImgSize(View view, int widthDp, int heightDp, int[] padding, int[] margin) {
        if (view == null) {
            return;
        }
        if (padding != null && padding.length > 3) {
            try {//内边距
                view.setPadding(padding[0] * mScreenWidth / mWidthDp,
                        padding[1] * mScreenWidth / mWidthDp,
                        padding[2] * mScreenWidth / mWidthDp,
                        padding[3] * mScreenWidth / mWidthDp);
            } catch (Exception e) {

            }
        }
        //根据传入的dp,与屏幕宽度 计算对应宽高
        int width = widthDp * mScreenWidth / mWidthDp;
        int height = heightDp * mScreenWidth / mWidthDp;
        ViewParent parent = view.getParent();
        if (parent instanceof LinearLayout) {//不同的父布局，未添加绝对布局和表格
            LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) view.getLayoutParams();
            param.width = width;
            param.height = height;
            if (margin != null && margin.length >= 4) {
                param.setMargins(margin[0] * mScreenWidth / mWidthDp,
                        margin[1] * mScreenWidth / mWidthDp,
                        margin[2] * mScreenWidth / mWidthDp,
                        margin[3] * mScreenWidth / mWidthDp);
            }
            view.setLayoutParams(param);
        } else if (parent instanceof RelativeLayout) {
            RelativeLayout.LayoutParams param = (RelativeLayout.LayoutParams) view.getLayoutParams();
            param.width = width;
            param.height = height;
            if (margin != null && margin.length >= 4) {
                param.setMargins(margin[0] * mScreenWidth / mWidthDp,
                        margin[1] * mScreenWidth / mWidthDp,
                        margin[2] * mScreenWidth / mWidthDp,
                        margin[3] * mScreenWidth / mWidthDp);
            }
            view.setLayoutParams(param);
        } else if (parent instanceof FrameLayout) {
            FrameLayout.LayoutParams param = (FrameLayout.LayoutParams) view.getLayoutParams();
            param.width = width;
            param.height = height;
            if (margin != null && margin.length >= 4) {
                param.setMargins(margin[0] * mScreenWidth / mWidthDp,
                        margin[1] * mScreenWidth / mWidthDp,
                        margin[2] * mScreenWidth / mWidthDp,
                        margin[3] * mScreenWidth / mWidthDp);
            }
            view.setLayoutParams(param);
        }
    }
}
