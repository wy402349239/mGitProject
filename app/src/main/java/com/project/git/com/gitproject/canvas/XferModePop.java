package com.project.git.com.gitproject.canvas;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.Map;

/**
 * created by wangyu on 2020/8/18 3:46 PM
 * description:
 */
public class XferModePop extends PopupWindow {

    private Map<String, PorterDuff.Mode> modeMap;

    public XferModePop(Context context) {
        super(context, null);
    }

    public XferModePop(Context context, Map<String, PorterDuff.Mode> map) {
        super(context, null);
        modeMap = map;
        initViews(context);
    }

    public XferModePop(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public XferModePop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private RecyclerView mRv;
    private XFermodePopAdapter adapter;

    private void initViews(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.pop_xfermode, null);
        setContentView(contentView);
        mRv = contentView.findViewById(R.id.pop_xfermode_rv);
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new LinearLayoutManager(context));
        adapter = new XFermodePopAdapter(context, modeMap);
        mRv.setAdapter(adapter);
        this.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setWidth(DeviceUtil.dip2px(context, 200));
        setHeight(DeviceUtil.dip2px(context, 300));
    }

    public void setMode(PorterDuff.Mode mode) {
        if (adapter != null && mode != null) {
            int index = adapter.getPosition(mode);
            if (mRv != null) {
                mRv.smoothScrollToPosition(index);
            }
        }
    }
}