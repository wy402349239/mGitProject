package com.project.git.com.gitproject.magicindicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;

/**
 * created by wangyu on 2019/4/3
 * description :
 */
public class NavigatorAdapter extends CommonNavigatorAdapter {
    private ViewPager mViewPager;
    private ArrayList<String> mTitles;

    private int typeValue = TypedValue.COMPLEX_UNIT_DIP;
    private float textSize = 16; //dip值
    private boolean isAutoFit = true; //默认是自适应

    private int mIndex = 0;
    private Paint mPaint = null;

    public NavigatorAdapter(ViewPager mViewPager, ArrayList<String> mTitles) {
        this.mViewPager = mViewPager;
        this.mTitles = mTitles;
    }

    public NavigatorAdapter(ViewPager mViewPager, String[] titles) {
        this.mViewPager = mViewPager;
        mTitles = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mTitles.add(titles[i]);
        }
    }

    /**
     * 设置切换到的tab的文字大小。
     *
     * @param type
     * @param size
     */
    public void setTextSize(int type, float size) {
        setTextSize(type, size, false);
    }

    public void setTextSize(int type, float size, boolean fit) {
        this.typeValue = type;
        this.textSize = size;
        this.isAutoFit = fit;
    }

    @Override
    public int getCount() {
        return mTitles == null ? 0 : mTitles.size();
    }

    @Override
    public IPagerTitleView getTitleView(Context context, final int index) {
        mIndex = index;
        SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
        simplePagerTitleView.setText(mTitles.get(index));
        //simplePagerTitleView.setPadding(0, AppUtil.dip2px(getContext(), 9), AppUtil.dip2px(getContext(), 33), AppUtil.dip2px(getContext(), 9));

        //simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        simplePagerTitleView.setTextSize(typeValue, textSize);

        simplePagerTitleView.setNormalColor(0xff000000);
        simplePagerTitleView.setSelectedColor(context.getResources().getColor(R.color.theme));
        simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(index);
            }
        });
        if (mPaint == null){
            simplePagerTitleView.getPaint();
        }
        return simplePagerTitleView;
    }

    public Paint getPaint() {
        return mPaint;
    }

    @Override
    public IPagerIndicator getIndicator(Context context) {
        LinePagerIndicator indicator = new LinePagerIndicator(context);
        indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
        indicator.setLineHeight(DeviceUtil.dip2px(context, 3));
        indicator.setRoundRadius(DeviceUtil.dip2px(context, 3));
        indicator.setLineWidth(DeviceUtil.dip2px(context, 30));
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        return indicator;
    }
}
