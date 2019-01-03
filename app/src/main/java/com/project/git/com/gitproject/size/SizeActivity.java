package com.project.git.com.gitproject.size;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

public class SizeActivity extends BaseActivity {

    private ImageView img1;
    private TextView txt1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size);
        setCj();
        img1 = findViewById(R.id.size_img_1);
        txt1 = findViewById(R.id.size_txt_1);
        ViewSizeUtil.getInstance(SizeActivity.this).setImgSize(img1, 20, 20, null, null);
        ViewSizeUtil.getInstance(SizeActivity.this).setTxtSize(txt1, 20);
        img1.postDelayed(new Runnable() {
            @Override
            public void run() {
                int width = img1.getWidth();
                int height = img1.getHeight();
                int width1 = txt1.getWidth();
                int height1 = txt1.getHeight();
                Toast.makeText(SizeActivity.this, width + "/" + height + "\n" + width1 + "/" + height1, Toast.LENGTH_LONG).show();
            }
        }, 1000);
    }
}
