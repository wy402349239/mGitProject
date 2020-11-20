package com.project.git.com.gitproject.waterfall;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.project.git.com.gitproject.R;

import java.util.List;

/**
 * created by wangyu on 2020/11/20 5:19 PM
 * description:
 */
public class WaterFallAdapter extends BaseQuickAdapter<String, WaterFallAdapter.WaterHolder> {

    public WaterFallAdapter(@Nullable List<String> data) {
        super(R.layout.item_waterfall, data);
    }

    @Override
    protected void convert(@NonNull WaterHolder holder, String item) {
        int adapterPosition = holder.getAdapterPosition() + 1;
        holder.button.setText(item);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) holder.button.getLayoutParams();
        params.height = 100 + 10 * (adapterPosition % 10);
        holder.addOnClickListener(R.id.item_waterfall_img, R.id.item_waterfall_layer);
    }

    static class WaterHolder extends BaseViewHolder {

        AppCompatTextView button;

        public WaterHolder(View view) {
            super(view);
            button = view.findViewById(R.id.item_waterfall_tv);
        }
    }
}