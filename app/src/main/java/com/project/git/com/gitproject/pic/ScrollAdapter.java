package com.project.git.com.gitproject.pic;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jx_wy
 * @date 11:09 AM 2019/2/27
 * @email wangyu@51dianshijia.com
 * @description
 */
public class ScrollAdapter extends RecyclerView.Adapter<ScrollAdapter.ScrollHolder>{

    private Context mCtx;
    private List<String> mUrls;
    private int mImgWidth, mIngHeight;

    private View.OnClickListener listener = null;

    public ScrollAdapter(Context ctx, List<String> urls) {
        this.mCtx = ctx;
        if (mUrls == null){
            mUrls = new ArrayList<>();
        }
        if (urls != null && !urls.isEmpty()){
            mUrls.addAll(ListUtil.deepCopy(urls));
        }
        int widthPixels = mCtx.getResources().getDisplayMetrics().widthPixels;
        mImgWidth = widthPixels * 3 / 4;
        mIngHeight = widthPixels;
    }

    public int getImgWidth() {
        return mImgWidth;
    }

    @Override
    public ScrollHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mCtx, R.layout.item_picscroll_layout_top, null);
        v.setLayoutParams(new RecyclerView.LayoutParams(mImgWidth, mIngHeight));
        return new ScrollHolder(v);
    }

    @Override
    public void onBindViewHolder(ScrollHolder holder, int position) {
        Glide.with(mCtx).load(mUrls.get(position))
                .into(holder.img);
        holder.desc.setText(mUrls.get(position));
        if (listener != null){
            holder.desc.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    static class ScrollHolder extends RecyclerView.ViewHolder{

        ImageView img;
        AppCompatTextView desc;

        public ScrollHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picscroll_item_top_img);
            desc = itemView.findViewById(R.id.picscroll_item_top_desc);
        }
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }
}
