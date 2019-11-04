package com.project.git.com.gitproject.magicindicator;

import android.content.Context;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.project.git.com.gitproject.R;

import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019-08-14
 * description :
 */
public class PrivateNavigatorAdapter extends CommonNavigatorAdapter {

    private ViewPager mViewPager;
    private ArrayList<String> mTitles;

    private int typeValue = TypedValue.COMPLEX_UNIT_DIP;
    private float textSize = 17; //dip值
    private boolean isAutoFit = true; //默认是自适应

    private int mIndex = 0;
    private Paint mPaint = null;

    public PrivateNavigatorAdapter(ViewPager viewPager, List<String> titles) {
        this.mViewPager = viewPager;
        mTitles = new ArrayList<>();
        mTitles.addAll(titles);
    }

    public PrivateNavigatorAdapter(ViewPager mViewPager, String[] titles) {
        this.mViewPager = mViewPager;
        mTitles = new ArrayList<>();
        for (String title : titles){
            mTitles.add(title);
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
        SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
        simplePagerTitleView.setText(mTitles.get(index));
        simplePagerTitleView.setTextSize(typeValue, textSize);
        simplePagerTitleView.setNormalColor(0xff000000);
        simplePagerTitleView.setSelectedColor(0xff0492fb);
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
        indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
        indicator.setLineHeight(5);
        indicator.setRoundRadius(5);
        indicator.setColors(context.getResources().getColor(R.color.colorPrimary),
                context.getResources().getColor(R.color.colorAccent),
                context.getResources().getColor(R.color.colorPrimaryDark));
//        indicator.setLineWidth(30);
        indicator.setStartInterpolator(new AccelerateInterpolator());
        indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
        return indicator;
    }
}