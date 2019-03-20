package com.project.git.com.gitproject.staggred;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.git.com.gitproject.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * created by wangyu on 2019/3/20
 * description :
 */
public class StaggredAdapter extends RecyclerView.Adapter<StaggredAdapter.StaggredHolder> {

    private int mCount = 0;
    private int[] mColors = new int[]{Color.BLUE, Color.CYAN, R.color.systemColor};
    private Map<Integer, int[]> mDatas = new HashMap<>();

    @NonNull
    @Override
    public StaggredHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new StaggredHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_imgandtext, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull StaggredHolder staggredHolder, int i) {
        int height = 0;
        int colorIndex = 0;
        if (mDatas.containsKey(i)){
            height = mDatas.get(i)[0];
            colorIndex = mDatas.get(i)[1];
        }else {
            height = getRand(10, 300);
            colorIndex = getRand(0, mColors.length);
            mDatas.put(i, new int[]{height, colorIndex});
            Log.e(String.valueOf(height), String.valueOf(colorIndex) + "---");
        }
        staggredHolder.mTv.setText(String.valueOf(i + 1) + " --- height = " + height);
        staggredHolder.mImg.setBackgroundColor(mColors[colorIndex]);
        LinearLayout.LayoutParams param = (LinearLayout.LayoutParams) staggredHolder.mImg.getLayoutParams();
        param.height = height;
        staggredHolder.mImg.setLayoutParams(param);
    }

    private int getRand(int min, int max) {
        Random rd = new Random();
        int i = rd.nextInt(max - min);
        return min + i;
    }

    public void addItem() {
        mCount++;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    static class StaggredHolder extends RecyclerView.ViewHolder {

        private ImageView mImg;
        private TextView mTv;

        public StaggredHolder(@NonNull View itemView) {
            super(itemView);
            mImg = itemView.findViewById(R.id.item_imgandtv_img);
            mTv = itemView.findViewById(R.id.item_imgandtv_tv);
        }
    }
}
