package com.project.git.com.gitproject.staggred;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * created by wangyu on 2019/3/20
 * description :
 */
public class StaggredActivity extends BaseActivity {

    private RecyclerView mRv;
    private Button mAddBtn;
    private StaggredAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relative);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        RelativeLayout mRootView = findViewById(R.id.layout_root_relative);
        addView(mRootView);
    }

    private void addView(RelativeLayout relativeLayout){
        mRv = new RecyclerView(this);
        relativeLayout.addView(mRv, new RelativeLayout.LayoutParams(-1, -1));
        mRv.setHasFixedSize(true);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);//2列  垂直方向排列
        mRv.setLayoutManager(manager);
        mRv.setItemAnimator(new DefaultItemAnimator());
        mRv.setPadding(30, 30, 30, 30);
        mAdapter = new StaggredAdapter();
        mRv.setAdapter(mAdapter);

        mAddBtn = new Button(this);
        mAddBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        mAddBtn.setTextColor(Color.BLACK);
        mAddBtn.setText("Add Random");
        mAddBtn.setPadding(30, 15, 30, 15);
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(-2, -2);
        param.setMargins(60, 0, 0, 30);
        param.addRule(RelativeLayout.ALIGN_PARENT_START);
        param.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mAddBtn.setLayoutParams(param);
        mAddBtn.setBackgroundResource(R.drawable.activity_main_item_bg);
        relativeLayout.addView(mAddBtn);
        mAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAdapter.addItem();
            }
        });
    }

}
