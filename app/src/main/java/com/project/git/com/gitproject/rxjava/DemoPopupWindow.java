package com.project.git.com.gitproject.rxjava;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.project.git.com.gitproject.R;

public class DemoPopupWindow extends PopupWindow {


    protected Context mContext;


    public DemoPopupWindow(Context context) {
        super(context);
        mContext = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View contentView = layoutInflater.inflate(R.layout.popup_lay, null);
        setContentView(contentView);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(getBackgroundDrawable());
        setOutsideTouchable(true);
    }

    protected ColorDrawable getBackgroundDrawable() {
        return new ColorDrawable(Color.TRANSPARENT);
    }
}
