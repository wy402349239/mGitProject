package com.project.git.com.gitproject.tangram;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.magicindicator.LinePagerIndicator;
import com.project.git.com.gitproject.magicindicator.PrivateNavigatorAdapter;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.support.async.AsyncLoader;
import com.tmall.wireless.tangram.support.async.AsyncPageLoader;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;
import com.tmall.wireless.tangram.util.IInnerImageSetter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomePageAct extends BaseActivity {

    @BindView(R.id.home_state)
    View homeState;
    @BindView(R.id.home_title)
    AppCompatTextView homeTitle;
    @BindView(R.id.home_back)
    AppCompatImageView homeBack;
    @BindView(R.id.home_page2)
    HomeViewPager homePage;
    @BindView(R.id.home_magic)
    MagicIndicator homeMagic;

    private Paint mPaint;
    private static final String[] mTitles = new String[]{"first", "second", "thrid"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_homepage);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarView(homeState);
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
                    .init();
        }
        homeTitle.setText("Tangram");
        loadData();
    }

    private void loadData() {
        homePage.setAdapter(new statePager(getSupportFragmentManager()));
        CommonNavigator commonNavigator = new CommonNavigator(HomePageAct.this);
        final PrivateNavigatorAdapter navigatorAdapter = new PrivateNavigatorAdapter(homePage, mTitles);
        navigatorAdapter.setDefaultColor(0xFFF3AC57);
        commonNavigator.setAdapter(navigatorAdapter);
        homeMagic.setNavigator(commonNavigator);
        ViewPagerHelper.bind(homeMagic, homePage);
        getPaint(navigatorAdapter);
        homePage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                getPaint(navigatorAdapter);
                IPagerIndicator indicatorP = navigatorAdapter.getIndicator(HomePageAct.this);
                if (indicatorP instanceof LinePagerIndicator) {
                    LinePagerIndicator indicator = (LinePagerIndicator) indicatorP;
                    float nWidth = mPaint.measureText(mTitles[i]);
                    indicator.setLineWidth(nWidth);
                    indicator.invalidate();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private void getPaint(PrivateNavigatorAdapter adapter) {
        if (adapter.getPaint() != null) {
            mPaint = adapter.getPaint();
        } else {
            if (mPaint == null) {
                TextView tv = new TextView(HomePageAct.this);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
                mPaint = tv.getPaint();
            }
        }
    }

    @OnClick({R.id.home_state, R.id.home_title, R.id.home_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_state:
                break;
            case R.id.home_title:
                break;
            case R.id.home_back:
                onBackPressed();
                break;
        }
    }

    static class statePager extends FragmentStatePagerAdapter {

        public statePager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return new TangramFragment();
        }

        @Override
        public int getCount() {
            return mTitles.length;
        }
    }
}
