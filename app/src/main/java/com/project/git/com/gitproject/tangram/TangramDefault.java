package com.project.git.com.gitproject.tangram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import com.project.git.com.gitproject.R;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

public class TangramDefault extends FrameLayout implements ITangramViewLifeCycle {

    public AppCompatImageView mImg;
    public AppCompatTextView mTitle, mDesc;
    public BaseCell cell;

    public TangramDefault(@NonNull Context context) {
        this(context, null);
    }

    public TangramDefault(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TangramDefault(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.item_tangram_default, TangramDefault.this);
        mImg = findViewById(R.id.tangram_item_default_img);
        mTitle = findViewById(R.id.tangram_item_default_title);
        mDesc = findViewById(R.id.tangram_item_default_desc);
    }

    @Override
    public void cellInited(BaseCell cell) {
        setOnClickListener(cell);
        this.cell = cell;
    }

    @Override
    public void postBindView(BaseCell cell) {

    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }
}
