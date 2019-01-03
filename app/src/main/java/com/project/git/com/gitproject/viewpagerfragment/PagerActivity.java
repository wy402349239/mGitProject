package com.project.git.com.gitproject.viewpagerfragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends BaseActivity {

    private ViewPager pager;
    private List<FragmentChild> mFragments = new ArrayList<>();
    private BaseAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
        setCj();
        pager = findViewById(R.id.pager_viewpager);
        for (int i = 0; i < 4; i++) {
            FragmentChild child = new FragmentChild();
            mFragments.add(child);
        }
        adapter = new BaseAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(99);//全部加载
        findViewById(R.id.pager_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FragmentChild> mlists = new ArrayList<>();
                mlists.addAll(mFragments);
                mFragments.remove(3);
                mFragments.remove(2);
                mFragments.remove(1);
                mFragments.add(mlists.get(3));
                mFragments.add(mlists.get(1));
                mFragments.add(mlists.get(2));
                adapter.notifyDataSetChanged();
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
