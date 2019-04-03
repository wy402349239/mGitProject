package com.project.git.com.gitproject.magicindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2019/4/2
 * description :
 */
public class MagicFragment extends Fragment {

    private View mRootView;
    private View mLayoutView;
    private TextView mTv;
    private int nIndex = 0;
    private int nMax = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_layout, container, false);
        }
        mLayoutView = mRootView.findViewById(R.id.fragment_layout);
        mTv = mRootView.findViewById(R.id.fragment_tv);
        return mRootView;
    }

    public void setContent(int i, int max){
        nIndex = i;
        nMax = max;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser){
            if (mTv != null){
                mTv.setText(String.valueOf(nIndex) + "/" + String.valueOf(nMax));
            }
        }
    }
}
