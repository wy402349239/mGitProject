package com.project.git.com.gitproject.levitate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.git.com.gitproject.DemoApp;
import com.project.git.com.gitproject.R;

public class FloatService extends Service {

    private RelativeLayout mToucherLayout;
    private WindowManager.LayoutParams mParams;
    private WindowManager manager;
    private FloatView mBtn;
    private int mStatuHeight = -1;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DemoApp.getInstance().setShowFloat(true);
        showFloat();
    }

    private void showFloat() {
        mParams = new WindowManager.LayoutParams();
        manager = (WindowManager) FloatService.this.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;//8.0及以后类型变化
        } else {
            mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        }
        mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mParams.gravity = Gravity.LEFT | Gravity.TOP;
        mParams.x = 0;
        mParams.y = 0;
        mParams.width = 360;
        mParams.height = 360;
        LayoutInflater inflater = LayoutInflater.from(FloatService.this.getApplicationContext());
        mToucherLayout = (RelativeLayout) inflater.inflate(R.layout.float_layout, null);
        manager.addView(mToucherLayout, mParams);
        mBtn = mToucherLayout.findViewById(R.id.float_img);
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            mStatuHeight = getResources().getDimensionPixelSize(resourceId);
        }
        mToucherLayout.findViewById(R.id.float_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DemoApp.getInstance().setShowFloat(false);
                FloatService.this.stopSelf();
            }
        });
        mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mParams.x = (int) event.getRawX() - 180;
                mParams.y = (int) event.getRawY() - 180 - mStatuHeight;
                manager.updateViewLayout(mToucherLayout, mParams);
                return true;//此处应持有焦点
            }
        });
    }

    @Override
    public void onDestroy() {
        if (mBtn != null) {
            manager.removeView(mToucherLayout);
        }
        super.onDestroy();
    }
}
