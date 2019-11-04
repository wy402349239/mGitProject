package com.project.git.com.gitproject.nesting;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2019-10-09
 * description :
 */
public class NestFragment extends Fragment {

    private View mRootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null){
            mRootView = inflater.inflate(R.layout.fragment_nest, container, false);
        }
        return mRootView;
    }
}
