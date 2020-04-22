package com.project.git.com.gitproject.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.project.git.com.gitproject.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridHolder> {

    private Context mCtx;
    private List<GridItem> items = new ArrayList<>();

    public GridAdapter(Context mCtx, List<GridItem> item) {
        this.mCtx = mCtx;
        if (item != null && !item.isEmpty())
            this.items.addAll(item);
    }

    public void reset(List<GridItem> list) {
        this.items.clear();
        if (list != null && !list.isEmpty())
            this.items.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GridHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mCtx).inflate(R.layout.layout_item_common, viewGroup, false);
        return new GridHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GridHolder holder, int position) {
        GridItem item = items.get(position);
        Glide.with(mCtx).load(item.getUrl())
                .into(holder.img);
        holder.titleTv.setText(item.getTitle());
        holder.descTv.setText(item.getDesc());
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    static class GridHolder extends RecyclerView.ViewHolder {

        AppCompatImageView img;
        AppCompatTextView titleTv;
        AppCompatTextView descTv;

        public GridHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.item_common_img);
            titleTv = itemView.findViewById(R.id.item_common_title);
            descTv = itemView.findViewById(R.id.item_common_desc);
        }
    }
}
