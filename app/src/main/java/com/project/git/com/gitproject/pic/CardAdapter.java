package com.project.git.com.gitproject.pic;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jx_wy
 * @date 3:57 PM 2019/2/27
 * @email wangyu@51dianshijia.com
 * @description
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder>{

    private Context mCtx;
    private List<Integer> mResources;
    private int mWidth, mHeight;

    public CardAdapter(Context ctx, List<Integer> resources, int width, int height) {
        this.mCtx = ctx;
        if (mResources == null){
            mResources = new ArrayList<>();
        }
        if (resources != null && !resources.isEmpty()){
            mResources.addAll(ListUtil.deepCopy(resources));
        }
        this.mWidth = width;
        this.mHeight = height;
    }

    @Override
    public CardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = View.inflate(mCtx, R.layout.item_picscroll_layout_bottom, null);
        v.setLayoutParams(new RecyclerView.LayoutParams(mWidth, mHeight));
        return new CardHolder(v);
    }

    @Override
    public void onBindViewHolder(CardHolder holder, int position) {
        holder.img.setImageResource(mResources.get(position));
    }

    @Override
    public int getItemCount() {
        return mResources.size();
    }

    static class CardHolder extends RecyclerView.ViewHolder{

        ImageView img;

        public CardHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.picscroll_item_bottom_img);
        }
    }
}
