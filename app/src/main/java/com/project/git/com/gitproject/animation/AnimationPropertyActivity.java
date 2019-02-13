package com.project.git.com.gitproject.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @author jx_wy
 * @date 5:31 PM 2019/1/31
 * @email wangyu@51dianshijia.com
 * @description
 */
public class AnimationPropertyActivity extends BaseActivity implements View.OnClickListener {

    private TextView mAmTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_property);
        setCj();
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        mAmTv = findViewById(R.id.propertyTv);
        findViewById(R.id.property_alpha).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.property_alpha:

                break;
        }
    }
}
