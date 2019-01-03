package com.project.git.com.gitproject.statu;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.project.git.com.gitproject.R;

import java.lang.reflect.Field;

public class TransStatuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            try {
//                Class decorViewClazz = Class.forName("com.android.internal.policy.DecorView");
//                Field field = decorViewClazz.getDeclaredField("mSemiTransparentStatusBarColor");
//                field.setAccessible(true);
//                field.setInt(getWindow().getDecorView(), Color.TRANSPARENT);  //改为透明
//            } catch (Exception e) {
//            }
//        }
        setContentView(R.layout.activity_null_layout_notfit);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = this.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
//            act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//等于 android:fitsSystemWindows="true"
            this.getWindow().setNavigationBarColor(Color.TRANSPARENT);
            this.getWindow().setStatusBarColor(Color.TRANSPARENT);//必须设置颜色,不然下面的代码无效
//            this.getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//                @Override
//                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                    int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
//                    View statuBar = getWindow().findViewById(identifier);
//                    if (statuBar != null) {
//                        statuBar.setBackgroundResource(R.drawable.jb_statu);
//                    }
//                }
//            });
        }
    }
}
