package com.project.git.com.gitproject.bitmap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

/**
 * Created by Administrator on 2018\10\5 0005.
 */

public class BitmapActivity extends BaseActivity implements View.OnClickListener{

    private ImageView mImgTop, mImgButtom;
    private Button mBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        setCj();
        initViews();
    }

    private void initViews(){
        mImgTop = findViewById(R.id.bitmap_img_top);
        mImgButtom = findViewById(R.id.bitmap_img_buttom);
        mBtn = findViewById(R.id.bitmap_btn);
        mImgTop.setOnClickListener(this);
        mImgButtom.setOnClickListener(this);
        mBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bitmap_img_top:
                Intent intent = new Intent();
                intent.setClass(BitmapActivity.this, BitmapListActivity.class);
                BitmapActivity.this.startActivity(intent);
                break;
            case R.id.bitmap_img_buttom:
                break;
            case R.id.bitmap_btn:
                break;
        }
    }
}
