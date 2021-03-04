package com.project.git.com.gitproject.pmd;

import android.graphics.drawable.PictureDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.svg.SvgSoftwareLayerSetter;

/**
 * created by wangyu on 2019-12-09
 * description :
 */
public class PmdAct extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmd);
        final MddPmdView pmd = findViewById(R.id.pmd_view);
//        pmd.setBgColor(Color.GRAY)
//                .setCirlRadiu(5)
//                .setPointCount(200)
//                .setRadiu(40)
//                .setStockColor(Color.BLACK)
//                .setStockWidth(3)
//                .setPointCount(200);
        pmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pmd.startRun(1080, 600);
            }
        });
        final BtnView btn = findViewById(R.id.pmd_btn);
        btn.settext("看视频最高再领999金币");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Tag", btn.getWidth() + " ===== ");
            }
        });
        AppCompatImageView img = findViewById(R.id.pmd_svg);
        //http://test.cdn.dianshihome.com/static/ad/1067b99dfe6c744a7c2a8584f042881b.svg
        String url = "http://test.cdn.dianshihome.com/static/task/396fe07dcf3c56e08dd839e0829d7c23.svg";
        Glide.with(this).as(PictureDrawable.class)
                .listener(new SvgSoftwareLayerSetter())
                .load(url)
                .into(img);
    }
}
