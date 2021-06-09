package com.project.git.com.gitproject.clickeye;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.widget.LinearLayout;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/8 10:57 上午
 */
public class ClickEyeActivity extends BaseActivity {

    private LinearLayout root;
    ClickEyeViews clickEyeView = null;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        setCj();
        root = findViewById(R.id.activity_null_lay);
        root.removeAllViews();
        clickEyeView = new ClickEyeViews(ClickEyeActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(1080, 1080);
        params.gravity = Gravity.CENTER;
        root.addView(clickEyeView, params);
        clickEyeView.setOnClickListener(v -> {
            setOutLine(v);
        });
    }

    private void setOutLine(View view){
        int width = view.getWidth();
        int height = view.getHeight();
        int radius = Math.min(width, height) / 2;
        ObjectAnimator oa = ObjectAnimator.ofFloat(view, "alpha",  1, 0.5f);
        ObjectAnimator oSx = ObjectAnimator.ofFloat(view, "scaleX",  1, 0.2f);
        ObjectAnimator oSy = ObjectAnimator.ofFloat(view, "scaleY",  1, 0.2f);
        ObjectAnimator oTx = ObjectAnimator.ofFloat(view, "translationX",  0, width * 2 / 5);
        ObjectAnimator oTy = ObjectAnimator.ofFloat(view, "translationY",  0, height * 2 / 5);
        AnimatorSet set = new AnimatorSet();
        set.play(oa).with(oSx).with(oSy).with(oTx).with(oTy);
        set.setDuration(500);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                clip(view, radius);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    private void clip(View view, int radius){
        Disposable subscribe = Observable.intervalRange(1, radius / 10 - 1, 0, 10, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e("Tag", "along = " + aLong);
                    }
                });
//        view.setOutlineProvider(new ViewOutlineProvider() {
//            @Override
//            public void getOutline(View view, Outline outline) {
//                int r = 100;
//                while (r <= radius){
//                    outline.setRoundRect(0, 0, width, height, r);
//                    r += 10;
//                }
//                view.setClipToOutline(true);
//            }
//        });
//        view.setClipToOutline(true);
    }
}
