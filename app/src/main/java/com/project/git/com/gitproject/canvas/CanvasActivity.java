package com.project.git.com.gitproject.canvas;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jx_wy
 * @date 4:09 PM 2019/2/22
 * @email wangyu@51dianshijia.com
 * @description
 */
public class CanvasActivity extends BaseActivity {

    private static final String fmt = "原图：正方形\n目标图：圆形\n模式：%s";

    XferModeView modeView;
    Map<String, PorterDuff.Mode> modeMap = new LinkedHashMap<>();
    PorterDuff.Mode lastMode;

    private XferModePop pop;

    private AppCompatTextView modeTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_text);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        modeView = findViewById(R.id.canvas_xfermode);
        modeTv = findViewById(R.id.canvas_xfermode_tv);
        PorterDuff.Mode[] values = PorterDuff.Mode.values();
//        for (PorterDuff.Mode mode : values) {
//            modeMap.put(mode.name(), mode);
//        }
        loadData();
    }

    private void loadData(){
        modeMap.put("所绘制不会提交到画布上", PorterDuff.Mode.CLEAR);
        modeMap.put("显示上层绘制图片", PorterDuff.Mode.SRC);
        modeMap.put("显示下层绘制图片", PorterDuff.Mode.DST);
        modeMap.put("正常绘制显示，上下层绘制叠盖", PorterDuff.Mode.SRC_OVER);
        modeMap.put("上下层都显示。下层居上显示", PorterDuff.Mode.DST_OVER);
        modeMap.put("取两层绘制交集。显示上层", PorterDuff.Mode.SRC_IN);
        modeMap.put("取两层绘制交集。显示下层", PorterDuff.Mode.DST_IN);
        modeMap.put("取上层绘制非交集部分", PorterDuff.Mode.SRC_OUT);
        modeMap.put("取下层绘制非交集部分", PorterDuff.Mode.DST_OUT);
        modeMap.put("取下层非交集部分与上层交集部分", PorterDuff.Mode.SRC_ATOP);
        modeMap.put("取上层非交集部分与下层交集部分", PorterDuff.Mode.DST_ATOP);
        modeMap.put("异或：去除两图层交集部分", PorterDuff.Mode.XOR);
        modeMap.put("取两图层全部区域，交集部分颜色加深", PorterDuff.Mode.DARKEN);
        modeMap.put("取两图层全部，点亮交集部分颜色", PorterDuff.Mode.LIGHTEN);
        modeMap.put("取两图层交集部分叠加后颜色", PorterDuff.Mode.MULTIPLY);
        modeMap.put("取两图层全部区域，交集部分变为透明色", PorterDuff.Mode.SCREEN);
        modeMap.put("将源图像素添加到目标图像素，使结果图形饱和", PorterDuff.Mode.ADD);
        modeMap.put("颜色叠加", PorterDuff.Mode.OVERLAY);
    }

    public void clickXFerMode(View v) {
        if (pop == null) {
            pop = new XferModePop(this, modeMap);
            pop.setOutsideTouchable(true);
        }
        if (pop != null) {
            pop.setFocusable(true);
        } else {
            return;
        }
        if (pop.isShowing()) {
            pop.dismiss();
        } else {
            pop.showAtLocation(v, Gravity.CENTER, 0, 0);
            pop.setMode(lastMode);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(XferModeEvent event) {
        if (event == null) {
            return;
        }
        if (pop != null){
            pop.dismiss();
        }
        lastMode = event.getMode();
        for (String key : modeMap.keySet()){
            if (modeMap.get(key) == lastMode){
                modeTv.setText(String.format(fmt, key));
                break;
            }
        }
        modeView.setMode(event.getMode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
