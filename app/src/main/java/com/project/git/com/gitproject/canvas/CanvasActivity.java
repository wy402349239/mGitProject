package com.project.git.com.gitproject.canvas;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * @author jx_wy
 * @date 4:09 PM 2019/2/22
 * @email wangyu@51dianshijia.com
 * @description
 */
public class CanvasActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_text);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
    }
}
