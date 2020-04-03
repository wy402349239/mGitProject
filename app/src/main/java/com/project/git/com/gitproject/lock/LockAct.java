package com.project.git.com.gitproject.lock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2020-03-26
 * description :
 */
public class LockAct extends BaseActivity {

    LinearLayout root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        initView();
    }

    private void initView(){
        root = findViewById(R.id.activity_null_lay);
        root.addView(new TaijiView(LockAct.this), new LinearLayout.LayoutParams(600, 600));
        root.addView(new LockView(LockAct.this), new LinearLayout.LayoutParams(1080, 1080));
    }
}
