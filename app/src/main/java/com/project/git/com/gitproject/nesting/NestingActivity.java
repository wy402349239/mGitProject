package com.project.git.com.gitproject.nesting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2019-10-08
 * description :
 */
public class NestingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nest);
        initView();
    }

    private void initView(){
        NestFragment fragment = new NestFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.nest_frame, fragment, NestFragment.class.getSimpleName());
        transaction.show(fragment);
        transaction.commit();
    }

}
