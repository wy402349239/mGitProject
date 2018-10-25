package com.project.git.com.gitproject;

import android.app.Application;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-10-25.
 */

public class DemoApp extends Application {

    Observable mObsb = null;
    private static DemoApp mApp = null;

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
    }

    public static DemoApp getInstance() {
        return mApp;
    }

    public Observable getObsb() {
        return mObsb;
    }
}
