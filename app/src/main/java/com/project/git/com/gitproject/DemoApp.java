package com.project.git.com.gitproject;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;

import com.project.git.com.gitproject.levitate.FloatActivity;
import com.project.git.com.gitproject.pic.PicScrollActivity;
import com.tencent.bugly.Bugly;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        addCut();
    }

    @TargetApi(Build.VERSION_CODES.N_MR1)
    private void addCut(){
        Intent intent = new Intent();//目标跳转intent
        intent.setAction(Intent.ACTION_MAIN);
        intent.setClass(DemoApp.this, PicScrollActivity.class);
        ShortcutManager manager = getSystemService(ShortcutManager.class);
        Intent intentMain = new Intent();//back stack intent
        intentMain.setAction(Intent.ACTION_MAIN);
        intentMain.setClass(DemoApp.this, MainActivity.class);
        Intent[] intents = new Intent[]{intentMain, intent};//放置顺序：先放返回的，再放目标跳转的
        ShortcutInfo infor = new ShortcutInfo.Builder(DemoApp.this, "shortId1")
                .setShortLabel("label")
                .setLongLabel("longlabel")
                .setIcon(Icon.createWithResource(DemoApp.this, R.drawable.ic_fav_login_close))
                .setIntents(intents)
                .build();

        Intent intent2 = new Intent();//目标跳转intent
        intent2.setAction(Intent.ACTION_MAIN);
        intent2.setClass(DemoApp.this, FloatActivity.class);
        Intent[] intents2 = new Intent[]{intentMain, intent2};//放置顺序：先放返回的，再放目标跳转的
        ShortcutInfo infor2 = new ShortcutInfo.Builder(DemoApp.this, "shortId2")
                .setShortLabel("label2")
                .setLongLabel("longlabel2")
                .setIcon(Icon.createWithResource(DemoApp.this, R.drawable.home_change))
                .setIntents(intents2)
                .build();
        List<ShortcutInfo> infos = new ArrayList<>();
        infos.add(infor);
        infos.add(infor2);
        manager.setDynamicShortcuts(infos);
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
