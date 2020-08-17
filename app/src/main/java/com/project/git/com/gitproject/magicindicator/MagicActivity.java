package com.project.git.com.gitproject.magicindicator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019/4/2
 * description :
 */
public class MagicActivity extends BaseActivity {

    private MagicIndicator mIndicator;
    private ViewPager mViewpager;
    MagicAdapter adapter;
    private List<Fragment> mFragments;
    private String[] mTitles;

    private Paint mPaint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magic);
        DeviceUtil.setStatuResouce(MagicActivity.this, R.drawable.jb_statu);
        mIndicator = findViewById(R.id.magic_maigicview);
        mViewpager = findViewById(R.id.magic_vp);
        initViews();
    }

    private void initViews(){
        adapter = new MagicAdapter(getSupportFragmentManager());
        mFragments = new ArrayList<>();
        mTitles = new String[10];
        for (int i = 0; i < 10; i++) {
            MagicFragment fragment = new MagicFragment();
            fragment.setContent(i + 1, 10);
            mFragments.add(fragment);
            mTitles[i] = "title_" + i;
        }
        adapter.setFragments(mFragments);
        mViewpager.setAdapter(adapter);
        CommonNavigator commonNavigator = new CommonNavigator(MagicActivity.this);
        final PrivateNavigatorAdapter navigatorAdapter = new PrivateNavigatorAdapter(mViewpager, mTitles);
        commonNavigator.setAdapter(navigatorAdapter);
        mIndicator.setNavigator(commonNavigator);
        bind(mIndicator, mViewpager);
        getPaint(navigatorAdapter);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("Tag", "onPageScrolled = " + position + " /// " + positionOffset + " //// " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int index) {
                getPaint(navigatorAdapter);
                IPagerIndicator indicatorP = navigatorAdapter.getIndicator(MagicActivity.this);
                if (indicatorP instanceof LinePagerIndicator){
                    LinePagerIndicator indicator = (LinePagerIndicator)indicatorP;
                    float nWidth = mPaint.measureText(mTitles[index]);
                    indicator.setLineWidth(nWidth);
                    indicator.invalidate();
                    Log.e("Tag", "nwidth = " + nWidth);
                }
                Log.e("Tag", "onPageSelected = " + index);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("Tag", "onPageScrollStateChanged = " + state);
            }
        });
    }

    public static void bind(final MagicIndicator magicIndicator, ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                magicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                Log.e("Tag", position + " / " + positionOffset + " === " + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                magicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                magicIndicator.onPageScrollStateChanged(state);
            }
        });
    }

    private void getPaint(PrivateNavigatorAdapter adapter){
        if (adapter.getPaint() != null){
            mPaint = adapter.getPaint();
        }else {
            if (mPaint == null){
                TextView tv = new TextView(MagicActivity.this);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                mPaint = tv.getPaint();
            }
        }
    }
}
