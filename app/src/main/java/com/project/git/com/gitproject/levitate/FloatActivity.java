package com.project.git.com.gitproject.levitate;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.DemoApp;
import com.project.git.com.gitproject.R;

import java.lang.reflect.Field;

public class FloatActivity extends AppCompatActivity {

    private LinearLayout mRoot;
    private Button mBtn, mStatu;
    private boolean mHavePermission = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_layout);
        initViews();
    }

    private void initViews() {
        mRoot = findViewById(R.id.activity_root_linear);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHavePermission = Settings.canDrawOverlays(this);
        } else {
            mHavePermission = true;
        }
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        param.setMargins(30, 30, 30, 0);
        mStatu = getBtn(mHavePermission ? "已申请权限" : "未申请权限");
        mRoot.addView(mStatu, param);
        mBtn = getBtn("申请权限");
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, PermissionRequestCode);
            }
        });
        mRoot.addView(mBtn, param);
        Button nStart = getBtn("ShowFloat");
        mRoot.addView(nStart, param);
        nStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mHavePermission = Settings.canDrawOverlays(FloatActivity.this);
                } else {
                    mHavePermission = true;
                }
                if (!DemoApp.getInstance().isShowFloat() && mHavePermission) {
                    startService(new Intent(FloatActivity.this, FloatService.class));
                } else {
                    String nMsg = "";
                    if (!mHavePermission) {
                        nMsg = "未开通悬浮窗权限";
                    } else if (DemoApp.getInstance().isShowFloat()) {
                        nMsg = "悬浮窗已开启";
                    } else {
                        nMsg = "--";
                    }
                    Toast.makeText(FloatActivity.this, nMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBtn.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && requestCode == PermissionRequestCode) {
                    if (Settings.canDrawOverlays(FloatActivity.this)) {
                        mStatu.setText("已获得权限");
                    } else {
                        mStatu.setText("未获得权限");
                    }
                }
            }
        }, 500);
    }

    private static final int PermissionRequestCode = 10;

    private Button getBtn(String text) {
        Button nBtn = new Button(FloatActivity.this);
        nBtn.setGravity(Gravity.CENTER);
        nBtn.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
        nBtn.setTextColor(Color.BLACK);
        nBtn.setText(text);
        nBtn.setPadding(30, 10, 30, 10);
        nBtn.setBackgroundResource(R.drawable.activity_main_item_defalt);
        return nBtn;
    }
}
