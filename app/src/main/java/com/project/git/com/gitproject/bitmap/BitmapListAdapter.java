package com.project.git.com.gitproject.bitmap;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.git.com.gitproject.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\10\5 0005.
 */

public class BitmapListAdapter extends RecyclerView.Adapter<BitmapListAdapter.BitmapHolder> {

    private Context mCtx;
    private List<FileStruct> mFils;
    private BitmapClickInterface mInter = null;

    class BitmapHolder extends RecyclerView.ViewHolder {

        ImageView nImg;
        TextView nTv;

        public BitmapHolder(View itemView) {
            super(itemView);
            nImg = itemView.findViewById(R.id.bitmap_list_item_img);
            nTv = itemView.findViewById(R.id.bitmap_list_item_tv);
        }
    }

    public void setInterface(BitmapClickInterface nInterface) {
        this.mInter = nInterface;
    }

    public BitmapListAdapter(Context mCtx, List<FileStruct> fils) {
        this.mCtx = mCtx;
        this.mFils = new ArrayList<>();
        if (fils != null) {
            mFils.addAll(fils);
        }
    }

    @Override
    public BitmapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(mCtx).inflate(R.layout.bitmap_list_item_layout, parent, false);
        BitmapHolder holder = new BitmapHolder(item);
        return holder;
    }

    @Override
    public void onBindViewHolder(BitmapHolder holder, final int position) {
        if (mFils.get(position).isDec()) {
            holder.nImg.setBackgroundColor(Color.RED);
        } else {
            holder.nImg.setBackgroundColor(Color.GRAY);
        }
        holder.nTv.setText(mFils.get(position).getName());
        holder.nTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInter != null){
                    mInter.click(mFils.get(position).getPath());
                }
            }
        });
    }

    public void resetData(List<BitmapListAdapter.FileStruct> files){
        this.mFils.clear();
        this.mFils.addAll(files);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mFils.size();
    }

    static class FileStruct {
        boolean isDec;//是否文件夹
        String name;//名称
        String path;//路劲

        public boolean isDec() {
            return isDec;
        }

        public void setDec(boolean dec) {
            isDec = dec;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
