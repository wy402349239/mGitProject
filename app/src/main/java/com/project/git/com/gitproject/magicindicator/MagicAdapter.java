package com.project.git.com.gitproject.magicindicator;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.ViewGroup;

import com.utilproject.wy.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2019/4/2
 * description :
 */
public class MagicAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public MagicAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Fragment> fragments) {
        if (mFragments == null) {
            mFragments = new ArrayList<>();
        }
        mFragments.addAll(fragments);
        notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public Fragment getItem(int i) {
        if (mFragments != null && mFragments.size() > i) {
            return mFragments.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        if (mFragments != null) {
            return mFragments.size();
        }
        return 0;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
