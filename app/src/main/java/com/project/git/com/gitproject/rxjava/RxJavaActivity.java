package com.project.git.com.gitproject.rxjava;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by zztzt on 2018/10/8.
 */

public class RxJavaActivity extends BaseActivity {

    /**
     * 根布局
     */
    private LinearLayout mRootView;
    private String[] texts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_layout);
        setCj();
        mRootView = findViewById(R.id.activity_root_linear);
        texts = new String[]{"default"};
        for (int i = 0; i < texts.length; i++) {
            addBtns(texts[i]);
        }
    }

    /**
     * 添加按钮
     *
     * @param text 按钮文字
     */
    private synchronized void addBtns(String text) {
        if (mRootView == null) {
            return;
        }
        Button button = new Button(RxJavaActivity.this);
        button.setBackgroundResource(R.drawable.activity_main_item_defalt);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setPadding(0, 15, 0, 15);
        params.setMargins(0, 0, 0, 30);
        button.setText(text);
        button.setGravity(Gravity.CENTER);
//        button.setTextAlignment();
        button.setTag(text);
        button.setOnClickListener(mBtnClick);
        mRootView.addView(button, mRootView.getChildCount());
    }

    View.OnClickListener mBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                String tag = view.getTag().toString();
                if (tag != null && !TextUtils.isEmpty(tag)) {
                    if (tag.equals(texts[0])){
                        defaultRx();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * 默认的写法
     */
    private void defaultRx(){
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                int count = 0;
                while (count < 10){
                    String s = String.valueOf(count++);
                    Log.e("subscribe", s);
                    e.onNext(s);
                }
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.e("Observer", "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.e("onNext", s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError", e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("onComplete", "--onComplete--");
            }
        });
    }
}
