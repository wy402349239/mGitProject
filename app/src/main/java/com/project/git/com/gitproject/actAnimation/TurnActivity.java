package com.project.git.com.gitproject.actAnimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * created by wangyu on 2019/4/11
 * description :
 */
public class TurnActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        setContentView(R.layout.activity_turnanimation);
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setExitTransition(new Fade().setDuration(500));
        findViewById(R.id.turn_fenjie).setOnClickListener(this);
        findViewById(R.id.turn_move).setOnClickListener(this);
        findViewById(R.id.turn_share).setOnClickListener(this);
        findViewById(R.id.turn_share_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(TurnActivity.this, Turn2Activity.class);
        switch (v.getId()) {
            case R.id.turn_fenjie:
                intent.putExtra("type", 0);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this).toBundle());
                break;
            case R.id.turn_move:
                intent.putExtra("type", 1);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this).toBundle());
                break;
            case R.id.turn_share:
                intent.putExtra("type", 2);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this, v, "btn").toBundle());
                finish();
                break;
            case R.id.turn_share_2:
                intent.putExtra("type", 2);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this, Pair.create(v, "btn"), Pair.create(v, "btn2")).toBundle());
                finish();
                break;
        }
    }
}
