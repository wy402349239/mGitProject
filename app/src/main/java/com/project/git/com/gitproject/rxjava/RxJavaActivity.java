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
import com.project.git.com.gitproject.DemoApp;
import com.project.git.com.gitproject.R;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by zztzt on 2018/10/8.
 */

public class RxJavaActivity extends BaseActivity {

    /**
     * 根布局
     */
    private LinearLayout mRootView;
    private String[] texts;
    private Disposable mD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null_layout);
        setCj();
        DemoApp.getInstance().getObsb()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d("app === ", "onSubscribe");
                        mD = d;
                    }

                    @Override
                    public void onNext(Object o) {
                        Log.d("app === onNext", o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("app === onError", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d("app === onComplete", "app === onComplete");
                    }
                });
        mRootView = findViewById(R.id.activity_root_linear);
        texts = new String[]{"default", "thread", "map"};
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
        button.setText(text);
        button.setGravity(Gravity.CENTER);
//        button.setTextAlignment();
        button.setTag(text);
        button.setOnClickListener(mBtnClick);
        mRootView.addView(button, mRootView.getChildCount());
        mRootView.addView(new View(RxJavaActivity.this), new LinearLayout.LayoutParams(1, 30));
    }

    View.OnClickListener mBtnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                String tag = view.getTag().toString();
                if (tag != null && !TextUtils.isEmpty(tag)) {
                    if (tag.equals(texts[0])) {
                        defaultRx();
                    } else if (tag.equals(texts[1])) {
                        threadUse();
                    } else if (tag.equals(texts[2])) {
                        map();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    /**
     * map操作符
     */
    private void map() {
        Observable.create(new ObservableOnSubscribe<Response>() {
            @Override
            public void subscribe(ObservableEmitter<Response> e) throws Exception {
                Request.Builder builder = new Request.Builder().url("https://www.baidu.com").get();
                Request request = builder.build();
                Call call = new OkHttpClient().newCall(request);
                Response response = call.execute();
                e.onNext(response);
            }
        })
                .map(new Function<Response, String>() {
                    @Override
                    public String apply(Response response) throws Exception {
                        if (response.isSuccessful()) {
                            ResponseBody body = response.body();
                            if (body != null) {
                                String nMsg = body.string();
                                Log.e("body === ", nMsg);
                                return nMsg;//必须返回对应类型
                            }
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e("doOnNext === ", o);
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String o) throws Exception {
                        Log.e("subscribe === ", o);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("subscribe === ", "error");
                    }
                });
    }

    /**
     * 线程调用
     */
    private void threadUse() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.e("subscribe", "subscribe === " + Thread.currentThread().getName());
                e.onNext("x");
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())//此处两次执行，只有第一次有效
                .observeOn(Schedulers.io())//该方法多次有效
                .doOnNext(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("doOnNext", "Consumer === " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(2000);
                        } catch (Exception e) {
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.e("subscribe", "Consumer----- === " + Thread.currentThread().getName() + s);
                    }
                });
    }

    /**
     * 默认的写法
     */
    private void defaultRx() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                int count = 0;
                while (count < 10) {
                    String s = String.valueOf(count++);
                    Log.e("subscribe", s);
                    e.onNext(s);
                }
            }
        }).subscribe(new Observer<String>() {

            Disposable nDisp = null;

            @Override
            public void onSubscribe(Disposable d) {
                Log.e("Observer", "onSubscribe");
                if (nDisp == null) {
                    nDisp = d;
                }
            }

            @Override
            public void onNext(String s) {
                Log.e("onNext", s);
                int count = Integer.parseInt(s);
                if (count >= 4 && nDisp != null) {
                    nDisp.dispose();
                }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mD != null){
            mD.dispose();
        }
    }
}
