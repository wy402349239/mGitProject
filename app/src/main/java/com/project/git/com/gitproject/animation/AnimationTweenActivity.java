package com.project.git.com.gitproject.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @author jx_wy
 * @date 1:59 PM 2019/1/30
 * @email wangyu@51dianshijia.com
 * @description
 */
public class AnimationTweenActivity extends BaseActivity implements View.OnClickListener {

    private ImageView imgAnimation;
    private Animation mRotateAnimation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_tween);
        setCj();
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        imgAnimation = findViewById(R.id.img_animation);
        findViewById(R.id.btn_rotate).setOnClickListener(this);
        findViewById(R.id.btn_alpha).setOnClickListener(this);
        findViewById(R.id.btn_scale).setOnClickListener(this);
        findViewById(R.id.btn_trans).setOnClickListener(this);
        findViewById(R.id.btn_animation).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rotate:
                mRotateAnimation = AnimationUtils.loadAnimation(AnimationTweenActivity.this, R.anim.animation_rotate);
                LinearInterpolator li = new LinearInterpolator();
                mRotateAnimation.setInterpolator(li);
                mRotateAnimation.setDuration(400);
                mRotateAnimation.setRepeatCount(5);
                mRotateAnimation.setFillAfter(true);
                mRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        imgAnimation.clearAnimation();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                imgAnimation.startAnimation(mRotateAnimation);
                break;
            case R.id.btn_alpha:
                AlphaAnimation animation = new AlphaAnimation(0, 1);
                animation.setDuration(2000);
                imgAnimation.startAnimation(animation);
                break;
            case R.id.btn_scale:
                ScaleAnimation animationScale = new ScaleAnimation(0, 1, 0, 1);
                animationScale.setDuration(1000);
                imgAnimation.startAnimation(animationScale);
                break;
            case R.id.btn_trans:
                TranslateAnimation animationTrans = new TranslateAnimation(0, 100, 0, 100);
                animationTrans.setDuration(1000);
                imgAnimation.startAnimation(animationTrans);
                break;
            case R.id.btn_animation:
                AnimationSet set = new AnimationSet(true);
                RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                //上述参数解释分别为：旋转起始角度，旋转结束角度，相对与自身，x轴方向的一半，相对于自身，y轴方向的一半
                rotateAnimation.setDuration(1000);
                set.addAnimation(rotateAnimation);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1, 0, 1, 0);
                scaleAnimation.setDuration(1000);
                set.addAnimation(scaleAnimation);
                imgAnimation.startAnimation(set);
                break;
        }
    }
}
