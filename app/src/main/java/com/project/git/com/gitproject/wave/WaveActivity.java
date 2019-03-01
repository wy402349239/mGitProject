package com.project.git.com.gitproject.wave;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @author jx_wy
 * @date 11:12 AM 2019/3/1
 * @email wangyu@51dianshijia.com
 * @description
 */
public class WaveActivity extends AppCompatActivity {

    private RelativeLayout layoutAnimation;
    private int mAnimationCount = 0;
    private float mAnimationScaleSize = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wave_layout);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        layoutAnimation = findViewById(R.id.relativeLayout2);
        findViewById(R.id.wave_img_bottom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
    }

    /**
     * 启动一个水波纹动画
     */
    private void startAnimation() {
        int nWidth = (int) getResources().getDisplayMetrics().density * 50;//动画view初始大小
        if (mAnimationScaleSize == 0) {
            //根据宽高,计算缩放的比例
            int max = layoutAnimation.getWidth() < layoutAnimation.getHeight() ?
                    layoutAnimation.getHeight() : layoutAnimation.getWidth();
            mAnimationScaleSize = max * 1.0f / nWidth;
        }
        final ImageView nImg = new ImageView(WaveActivity.this);
        layoutAnimation.addView(nImg);//动态添加
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(nWidth, nWidth);
        param.addRule(RelativeLayout.CENTER_IN_PARENT);//居中
        nImg.setImageResource(R.drawable.wave_bg);//背景
        nImg.setLayoutParams(param);
        AnimationSet set = new AnimationSet(true);
        ScaleAnimation sAni = new ScaleAnimation(1.0f, mAnimationScaleSize, 1.0f, mAnimationScaleSize,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                ScaleAnimation.RELATIVE_TO_SELF, 0.5f);//缩放动画
        AlphaAnimation aAni = new AlphaAnimation(1.0f, 0.0f);//透明度动画
        sAni.setDuration(1000);
        set.setDuration(1000);
        set.addAnimation(sAni);
        set.addAnimation(aAni);
        nImg.startAnimation(set);//开启动画
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                layoutAnimation.post(new Runnable() {
                    @Override
                    public void run() {
                        nImg.clearAnimation();
                        layoutAnimation.removeView(nImg);
                        //动画完成之后  清除，移除
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
