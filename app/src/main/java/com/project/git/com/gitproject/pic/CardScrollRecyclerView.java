package com.project.git.com.gitproject.pic;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * @author jx_wy
 * @date 3:35 PM 2019/2/27
 * @email wangyu@51dianshijia.com
 * @description
 */
public class CardScrollRecyclerView extends RecyclerView {

    private boolean mIsFling = true;

    public CardScrollRecyclerView(Context context) {
        super(context);
    }

    public CardScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CardScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        if (mIsFling) {
            return super.fling(velocityX, velocityY);
        } else {
            return false;
        }
    }

    public void setFling(boolean isFling) {
        this.mIsFling = isFling;
    }


}
