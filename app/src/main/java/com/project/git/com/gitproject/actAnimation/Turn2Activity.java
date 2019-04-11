package com.project.git.com.gitproject.actAnimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Explode;
import android.transition.Slide;
import android.view.View;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.MainActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * created by wangyu on 2019/4/11
 * description :
 */
public class Turn2Activity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        int type = getIntent().getIntExtra("type", 0);
        switch (type) {
            case 0:
                setContentView(R.layout.turn_fenjie_layout);
                getWindow().setEnterTransition(new Explode().setDuration(500));
                getWindow().setExitTransition(new Explode().setDuration(500));
                break;
            case 1:
                setContentView(R.layout.turn_fenjie_layout);
                getWindow().setEnterTransition(new Slide().setDuration(500));
                getWindow().setExitTransition(new Slide().setDuration(500));
                break;
            case 2:
                setContentView(R.layout.turn_fenjie_layout);
                getWindow().setEnterTransition(new Explode().setDuration(500));
                getWindow().setExitTransition(new Explode().setDuration(500));
                findViewById(R.id.trun_share_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Turn2Activity.this, TurnActivity.class),
                                ActivityOptions.makeSceneTransitionAnimation
                                        (Turn2Activity.this, v, "btn").toBundle());
                        finish();
                    }
                });
                findViewById(R.id.turn_share_2).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Turn2Activity.this, TurnActivity.class),
                                ActivityOptions.makeSceneTransitionAnimation
                                        (Turn2Activity.this, v, "btn2").toBundle());
                        finish();
                    }
                });
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent AnimationIntent = new Intent(Turn2Activity.this, TurnActivity.class);
        startActivity(AnimationIntent, ActivityOptions.makeSceneTransitionAnimation(Turn2Activity.this).toBundle());
        finish();
    }
}
