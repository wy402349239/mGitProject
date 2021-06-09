package com.project.git.com.gitproject.svga;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.LinearLayout;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import java.net.URL;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/9 3:56 下午
 */
public class SvgaActivity extends BaseActivity {

    private static final String url = "http://test.cdn.dianshihome.com/static/ad/a9265981485f1130c7e7fd2f2be1252a.svga";

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga);
        LinearLayout root = findViewById(R.id.svga_root);
        root.setOnClickListener(v -> {
            SVGAImageView ftb = root.findViewById(R.id.svga_ftb);
            ftb.startAnimation();
            try {
                SVGAImageView imageView = new SVGAImageView(SvgaActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, 0);
                params.weight = 1;
                root.addView(imageView, params);
                SVGAParser parser = new SVGAParser(SvgaActivity.this);
                parser.decodeFromURL(new URL(url), new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(@NonNull SVGAVideoEntity svgaVideoEntity) {
                        SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                        imageView.setImageDrawable(drawable);
                        imageView.setLoops(1);
                        imageView.setClearsAfterStop(false);
                        imageView.setFillMode(SVGAImageView.FillMode.Forward);
                        imageView.startAnimation();
                    }

                    @Override
                    public void onError() {
                        Log.e("Tag", "svgaImageview error");
                    }
                });
            }catch (Exception e){
                Log.e("Tag", "svgaImageview error : " + Log.getStackTraceString(e));
            }
        });
    }
}
