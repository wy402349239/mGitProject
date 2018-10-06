package com.project.git.com.gitproject.bitmap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
    private static final int topReq = 101;//上方图片的请求和返回标识
    private static final int buttomReq = 102;//下方图片的请求和返回标识
    private String topPath = "";//上方图片的地址
    private String buttomPath = "";//下方图片的地址

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
                intent.putExtra("result", topReq);
                BitmapActivity.this.startActivityForResult(intent, topReq);
                break;
            case R.id.bitmap_img_buttom:
                Intent intent2 = new Intent();
                intent2.setClass(BitmapActivity.this, BitmapListActivity.class);
                intent2.putExtra("result", buttomReq);
                BitmapActivity.this.startActivityForResult(intent2, buttomReq);
                break;
            case R.id.bitmap_btn:
                ShareBitmapUtil.getInstance().saveBitmap(BitmapActivity.this, topPath, buttomPath);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String path = data.getStringExtra("path");
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        switch (resultCode){
            case topReq:
                mImgTop.setImageBitmap(bitmap);
                topPath = path;
                break;
            case buttomReq:
                mImgButtom.setImageBitmap(bitmap);
                buttomPath = path;
                break;
        }
    }
}
