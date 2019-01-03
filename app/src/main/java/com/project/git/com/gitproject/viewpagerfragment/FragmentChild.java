package com.project.git.com.gitproject.viewpagerfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.git.com.gitproject.DemoApp;
import com.project.git.com.gitproject.R;

public class FragmentChild extends android.support.v4.app.Fragment {

    private View mRoot;
    private TextView mTv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.onlytextlayout, container, false);
        }
        mTv = mRoot.findViewById(R.id.only_txt);
        mTv.setText("第" + DemoApp.getInstance().getmFragmentIndex() + "个pager");
        return mRoot;
    }
}
