package com.project.git.com.gitproject;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import crossoverone.statuslib.StatusUtil;

/**
 * Created by Administrator on 2018\10\5 0005.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    /**
     * 设置沉浸
     */
    protected void setCj(){
        setStatusColor();
        setSystemInvadeBlack();
    }

    protected void setStatusColor() {
        StatusUtil.setUseStatusBarColor(this, Color.WHITE);
    }

    /**
     * 设置沉浸于状态栏文字颜色
     *
     */
    protected void setSystemInvadeBlack() {
        // 第二个参数是是否沉浸,第三个参数是状态栏字体是否为黑色。
        StatusUtil.setSystemStatus(this, true, true);
    }
}
