package com.project.git.com.gitproject.tab;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.viewpagerfragment.FragmentChild;
import com.project.git.com.gitproject.viewpagerfragment.PagerActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created by wangyu on 2020/11/10 11:01 AM
 * description:
 */
public class TabIconAct extends BaseActivity {

    @BindView(R.id.tab_table)
    SlidingTabLayout tab;
    @BindView(R.id.tab_vp)
    ViewPager mVp;

    private List<FragmentChild> mFragments = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabicon);
        ButterKnife.bind(this);
        ImmersionBar.with(this).statusBarView(R.id.tab_state).init();
        if (ImmersionBar.isSupportStatusBarDarkFont()) {
            ImmersionBar.with(this).statusBarDarkFont(true, 0.2f)
                    .init();
        }
        for (int i = 0; i < 4; i++) {
            FragmentChild child = new FragmentChild();
            mFragments.add(child);
        }
        adapter = new BaseAdapter(getSupportFragmentManager());
        mVp.setAdapter(adapter);
        tab.setupWithViewPager(mVp);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int i1) {
                tab.setIndicatorPositionFromTabPosition(position, positionOffset);
            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    class BaseAdapter extends FragmentStatePagerAdapter {

        public BaseAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        }
    }
}