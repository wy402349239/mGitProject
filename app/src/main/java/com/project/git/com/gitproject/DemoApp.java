package com.project.git.com.gitproject;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-10-25.
 */

public class DemoApp extends TinkerApplication {

    Observable mObsb = null;
    private static DemoApp mApp = null;
    private boolean mShowFloat = false;
    private int mFragmentIndex = 0;

    public DemoApp() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.project.git.com.gitproject.TinkerApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = DemoApp.this;
        if (mObsb == null) {
            mObsb = Observable.create(new ObservableOnSubscribe() {
                @Override
                public void subscribe(ObservableEmitter e) throws Exception {
                    int count = 1;
                    while (mApp != null) {
                        try {
                            e.onNext(" --- " + String.valueOf(count));
                            Thread.sleep(2000);
                        } catch (Exception e2) {

                        } finally {
                            count++;
                        }
                    }
                }
            }).subscribeOn(Schedulers.newThread());
        }
        Bugly.init(DemoApp.this.getApplicationContext(), "578d30338d", true);
    }

    public static DemoApp getInstance() {
        return mApp;
    }

    public Observable getObsb() {
        return mObsb;
    }

    public boolean isShowFloat() {
        return mShowFloat;
    }

    public void setShowFloat(boolean showFloat) {
        mShowFloat = showFloat;
    }

    public int getmFragmentIndex() {
        mFragmentIndex ++;
        return mFragmentIndex;
    }
}
