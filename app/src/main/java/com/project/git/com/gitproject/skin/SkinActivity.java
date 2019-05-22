package com.project.git.com.gitproject.skin;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;
import com.utilproject.wy.SpUtil;

import skin.support.SkinCompatManager;
import skin.support.widget.SkinCompatSupportable;

/**
 * created by wangyu on 2019-05-21
 * description :
 */
public class SkinActivity extends BaseActivity implements SkinCompatSupportable {

    private static final String SkinSwitch = "SkinSwitch";
    private static final String NightSkin = "night";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        findViewById(R.id.settingImg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpUtil instance = SpUtil.getInstance(SkinActivity.this);
                boolean bool = instance.getBool(SkinSwitch, false);
                if (bool) {
                    SkinCompatManager.getInstance().restoreDefaultTheme();
                } else {
                    SkinCompatManager.getInstance().loadSkin(NightSkin, null, SkinCompatManager.SKIN_LOADER_STRATEGY_BUILD_IN);
                }
                instance.putValue(SkinSwitch, !bool);
            }
        });
    }

    @Override
    public void applySkin() {
        String curSkinName = SkinCompatManager.getInstance().getCurSkinName();
        if (curSkinName.equals(NightSkin)) {
            changeStatuResource(R.drawable.jb_statu_night);
        } else {
            changeStatuResource(R.drawable.jb_statu);
        }
    }
}
