package com.project.git.com.gitproject.waterfall;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * created by wangyu on 2020/11/20 4:47 PM
 * description:
 */
public class WaterfallAct extends AppCompatActivity {

    private Toolbar mTool;
    private RecyclerView mRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        mTool = findViewById(R.id.waterfall_toolbar);
        mRv = findViewById(R.id.waterfall_rv);
        initViews();
    }

    private void initViews() {
        setSupportActionBar(mTool);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mTool.setNavigationIcon(R.drawable.ic_back_black_svg);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        mRv.addItemDecoration(mStaggredItemDecortation);
        StaggeredGridLayoutManager nManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        nManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRv.setLayoutManager(nManager);
        mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //nManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);  与下面代码一起，避免StaggeredGridLayoutManager重新排序
                if (recyclerView.getLayoutManager() != null && recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                    layoutManager.invalidateSpanAssignments();
                }
            }
        });
        WaterFallAdapter adapter = new WaterFallAdapter(new ArrayList<>());
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mRv.setAdapter(adapter);

        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 99; i++) {
            datas.add(String.valueOf(i + 1));
        }
        adapter.setNewData(datas);
//        adapter.click
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter ad, View view, int position) {
                if (view.getId() == R.id.item_waterfall_img){
                    adapter.notifyItemRemoved(position);
                    adapter.getData().remove(position);
                }else {
                    Toast.makeText(WaterfallAct.this, adapter.getData().get(position) + "，点击小圈移除", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}