package com.project.git.com.gitproject.pic;

import android.content.Context;
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

    public ScrollAdapter(Context ctx, List<String> urls) {
        this.mCtx = ctx;
        if (mUrls == null){
            mUrls = new ArrayList<>();
        }
        if (urls != null && !urls.isEmpty()){
            mUrls.addAll(ListUtil.deepCopy(urls));
        }
        mImgWidth = mCtx.getResources().getDisplayMetrics().widthPixels / 3;
        mIngHeight = mImgWidth * 4 / 3;
    }

    @Override
    public ScrollHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mCtx, R.layout.item_picscroll_layout_top, null);
        v.setLayoutParams(new RecyclerView.LayoutParams(mImgWidth, mIngHeight));
        return new ScrollHolder(v);
    }

    @Override
    public void onBindViewHolder(ScrollHolder holder, int position) {
        Glide.with(mCtx).load(mUrls.get(position)).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return mUrls.size();
    }

    static class ScrollHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public ScrollHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picscroll_item_top_img);
        }
    }
}
