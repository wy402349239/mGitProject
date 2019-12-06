package com.project.git.com.gitproject.bezier;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

/**
 * created by wangyu on 2019-12-02
 * description :
 */
public class BezierAct extends BaseActivity {

    LinearLayout mRoot = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        mRoot = findViewById(R.id.activity_null_lay);
//        final BezierViewNew bezierView = new BezierViewNew(this);
//        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(-1, 0);
//        param1.weight = 1;
//        mRoot.addView(bezierView, param1);
//        bezierView.startDraw(true);

//        final BezierViewNew bezierView2 = new BezierViewNew(this);
//        LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(-1, 0);
//        param2.weight = 1;
//        mRoot.addView(bezierView2, param2);
//        bezierView2.startDraw(false);

        BezierView bezierview3 = new BezierView(this);
        LinearLayout.LayoutParams param3 = new LinearLayout.LayoutParams(-1, 0);
        param3.weight = 1;
        mRoot.addView(bezierview3, param3);
        bezierview3.startDraw();

    }
}
