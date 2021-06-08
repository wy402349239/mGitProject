package com.project.git.com.gitproject;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.project.git.com.gitproject.Sqlite.SqliteActivity;
import com.project.git.com.gitproject.actAnimation.TurnActivity;
import com.project.git.com.gitproject.alisign.AliSignAct;
import com.project.git.com.gitproject.animation.AnimationPropertyActivity;
import com.project.git.com.gitproject.animation.AnimationTweenActivity;
import com.project.git.com.gitproject.are.AreaActivity;
import com.project.git.com.gitproject.bezier.BezierAct;
import com.project.git.com.gitproject.bitmap.BitmapActivity;
import com.project.git.com.gitproject.canvas.CanvasActivity;
import com.project.git.com.gitproject.ijk.ActivityIjk;
import com.project.git.com.gitproject.levitate.FloatActivity;
import com.project.git.com.gitproject.lock.LockAct;
import com.project.git.com.gitproject.magicindicator.MagicActivity;
import com.project.git.com.gitproject.nesting.NestingActivity;
import com.project.git.com.gitproject.okdownload.OkDownloadActivity;
import com.project.git.com.gitproject.pic.PicScrollActivity;
import com.project.git.com.gitproject.pictureinpicture.PicActivity;
import com.project.git.com.gitproject.pmd.PmdAct;
import com.project.git.com.gitproject.rxjava.RxJavaActivity;
import com.project.git.com.gitproject.size.SizeActivity;
import com.project.git.com.gitproject.skin.SkinActivity;
import com.project.git.com.gitproject.staggred.StaggredActivity;
import com.project.git.com.gitproject.statu.GradintActivity;
import com.project.git.com.gitproject.statu.TransStatuActivity;
import com.project.git.com.gitproject.step.StepCountAct;
import com.project.git.com.gitproject.tangram.HomePageAct;
import com.project.git.com.gitproject.viewpagerfragment.PagerActivity;
import com.project.git.com.gitproject.waterfall.WaterfallAct;
import com.project.git.com.gitproject.wave.WaveActivity;
import com.project.git.com.gitproject.web.WebActivity;
import com.tencent.mmkv.MMKV;
import com.utilproject.wy.DeviceUtil;
import com.utilproject.wy.NetUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    static final int mItemSpanCount = 2;
    RecyclerView mRv;
    MainRvAdapter mAdapter;
    RecyclerView.ItemDecoration mStaggredItemDecortation = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            StaggeredGridLayoutManager.LayoutParams param = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            int spanIndex = param.getSpanIndex();
            outRect.top = 10;
            if (spanIndex == 0) {
                outRect.left = 20;
                outRect.right = 10;
            } else {
                outRect.left = 10;
                outRect.right = 20;
            }
        }
    };
    View.OnClickListener mOnItemclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final int positon = Integer.parseInt(view.getTag().toString());
            switch (positon) {
                case 0:
                    startActivity(new Intent(MainActivity.this, BitmapActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(MainActivity.this, RxJavaActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(MainActivity.this, ActivityIjk.class));
                    break;
                case 3:
                    startActivity(new Intent(MainActivity.this, SqliteActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(MainActivity.this, FloatActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(MainActivity.this, TransStatuActivity.class));
                    break;
                case 6:
                    startActivity(new Intent(MainActivity.this, GradintActivity.class));
                    break;
                case 7:
                    startActivity(new Intent(MainActivity.this, PagerActivity.class));
                    break;
                case 8:
                    startActivity(new Intent(MainActivity.this, SizeActivity.class));
                    break;
                case 9:
                    startActivity(new Intent(MainActivity.this, PicActivity.class));
                    break;
                case 10:
                    startActivity(new Intent(MainActivity.this, AnimationTweenActivity.class));
                    break;
                case 11:
                    startActivity(new Intent(MainActivity.this, AnimationPropertyActivity.class));
                    break;
                case 12:
                    startActivity(new Intent(MainActivity.this, CanvasActivity.class));
                    break;
                case 13:
                    startActivity(new Intent(MainActivity.this, PicScrollActivity.class));
                    break;
                case 14:
                    startActivity(new Intent(MainActivity.this, WaveActivity.class));
                    break;
                case 15:
                    Intent intentJdy = new Intent(MainActivity.this, WebActivity.class);
                    intentJdy.putExtra("url", "https://link.jiandaoyun.com/f/5c777f6c46fd3c26447509c6");
                    startActivity(intentJdy);
                    break;
                case 16:
                    startActivity(new Intent(MainActivity.this, StaggredActivity.class));
                    break;
                case 17:
                    startActivity(new Intent(MainActivity.this, MagicActivity.class));
                    break;
                case 18:
                    Intent AnimationIntent = new Intent(MainActivity.this, TurnActivity.class);
                    startActivity(AnimationIntent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
                    break;
                case 19:
                    Intent SkinIntent = new Intent(MainActivity.this, SkinActivity.class);
                    startActivity(SkinIntent);
                    break;
                case 20:
                    Intent NestIntent = new Intent(MainActivity.this, NestingActivity.class);
                    startActivity(NestIntent);
                    break;
                case 21:
                    Intent StepIntent = new Intent(MainActivity.this, StepCountAct.class);
                    startActivity(StepIntent);
                    break;
                case 22:
                    Intent bezierIntent = new Intent(MainActivity.this, BezierAct.class);
                    startActivity(bezierIntent);
                    break;
                case 23:
                    Intent pmdIntent = new Intent(MainActivity.this, PmdAct.class);
                    startActivity(pmdIntent);
                    break;
                case 24:
                    Intent aliIntent = new Intent(MainActivity.this, AliSignAct.class);
                    startActivity(aliIntent);
                    break;
                case 25:
                    Intent lockIntent = new Intent(MainActivity.this, LockAct.class);
                    startActivity(lockIntent);
                    break;
                case 26:
                    Intent homepageIntent = new Intent(MainActivity.this, HomePageAct.class);
                    startActivity(homepageIntent);
                    break;
                case 27:
                    Intent waterfall = new Intent(MainActivity.this, WaterfallAct.class);
                    startActivity(waterfall);
                    break;
                case 28:
                    Intent areaPickerIntent = new Intent(MainActivity.this, AreaActivity.class);
                    startActivity(areaPickerIntent);
                    break;
                case 29:
                    startActivity(new Intent(MainActivity.this, OkDownloadActivity.class));
                    break;

            }
        }
    };
    private List<String> mItems = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        mRv = findViewById(R.id.demo_main_recycler);
        mRv.addItemDecoration(mStaggredItemDecortation);
        mAdapter = new MainRvAdapter();
//        LinearLayoutManager nManagener = new LinearLayoutManager(MainActivity.this);
//        nManagener.setOrientation(LinearLayout.VERTICAL);
//        mRv.setLayoutManager(nManagener);
        StaggeredGridLayoutManager nManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRv.setLayoutManager(nManager);
        mRv.setAdapter(mAdapter);
        addItems();
        findViewById(R.id.popu_btn).setVisibility(View.GONE);
//        String appVersionCode = AppMessageUtil.getAppVersionCode(this);
//        String appVersionName = AppMessageUtil.getAppVersionName(this);
//        Log.e("Tag", appVersionCode + " --- " + appVersionName);
//        String myname = EncryptUtil.getMD5_32("myname");
//        String wy40234 = EncryptUtil.SHA1("Wy40234");//10.22.64.192
//        Log.e("Tag", myname + "\n" + wy40234);
        String IP = NetUtil.generateIPAddress(this);
        Log.e("Tag", "IP = " + IP);
//        Toast.makeText(this, IP, Toast.LENGTH_SHORT).show();
        String macAddr = NetUtil.getMacAddrNew(this);
        Log.e("Tag", "mac = " + macAddr);

        Log.e("Tag", MMKV.initialize(this));
//        readSp();
//        writeSp();
        dataMove();
        logAllPkg();

//        final LottieAnimationView lottieAnimationView = findViewById(R.id.demo_main_lottie);
//        lottieAnimationView.setImageAssetsFolder("images/");
//        lottieAnimationView.setAnimation("rp_head_feature_left_anim.json");
////        lottieAnimationView.playAnimation();
//        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                lottieAnimationView.cancelAnimation();
////                lottieAnimationView.playAnimation();
//            }
//        });
//        lottieAnimationView.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                Log.e("Tag", String.valueOf(animation.getAnimatedFraction()));
//            }
//        });

//        AtomicBoolean atomicBoolean = null;
//        Log.e("Tag", " --- " + (atomicBoolean == null));
//        atomicBoolean = new AtomicBoolean(false);
//        Log.e("Tag", " --- " + (atomicBoolean == null));
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + "/Download/Browser/八方战神.apk";
//        PackageManager pm = this.getPackageManager();
//        PackageInfo info = pm.getPackageArchiveInfo(path,
//                PackageManager.GET_ACTIVITIES);
//        if (info != null) {
//            String packageName = info.packageName;
//            Log.e("Tag", packageName);
//        }
        testList();
        List<String> list = new ArrayList<>();
        list.add("a.apk");
        list.add("a-mapping.txt");
        list.add("a-R.txt");
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });
        Log.e("Tag", list.get(0) + " --- " + list.get(1) + " ---- " + list.get(2));
        mourn();
    }

    private void mourn(){
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        getWindow().getDecorView().setLayerType(View.LAYER_TYPE_HARDWARE,paint);
    }

    private void testList() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add("homeIcon" + (i + 1));
        }
        Collections.shuffle(list);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                if (o1.length() != o2.length()) {
                    return o1.length() > o2.length() ? 1 : -1;
                }
                return o1.compareTo(o2);
            }
        });
        Log.e("Tag", list.size() + " ---- ");
    }

    private void logAllPkg() {
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            Log.e("tag", name + "----" + packageName + "----" + cls);
        }
    }

    /**
     * 数据迁移及删除
     */
    private void dataMove() {
        MMKV kv = MMKV.mmkvWithID("sp");
        kv.importFromSharedPreferences(getSp());
        String[] strings = kv.allKeys();
        if (strings != null && strings.length > 0) {
            for (int i = 0; i < strings.length; i++) {
                Log.e("key : ", strings[i]);
            }
        }
        Log.e("Tag", Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + MainActivity.this.getPackageName());
        String path = MainActivity.this.getFilesDir().getParentFile() + File.separator + "shared_prefs" + File.separator + "Pro.xml";
        File file = new File(path);
        try {
            Log.e("Tag", "delete result = " + file.delete());
        } catch (Exception e) {
            Log.e("Tag", Log.getStackTraceString(e));
        }
    }

    /**
     * 读取sharedprefrences
     */
    private void readSp() {
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                SharedPreferences sp = getSp();
                long time = System.currentTimeMillis();
                for (int i = 0; i < 3000; i++) {
                    sp.getInt("write - " + i, 0);
                }
                e.onNext(time);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                long time = System.currentTimeMillis();
                Log.e("Tag", "read sp time = " + (time - aLong));
                readMmkv();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private SharedPreferences getSp() {
        return MainActivity.this.getSharedPreferences("Pro", Context.MODE_PRIVATE);
    }

    /**
     * 写sharedprefrences
     */
    private void writeSp() {
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                SharedPreferences sp = getSp();
                SharedPreferences.Editor edit = sp.edit();
                long time = System.currentTimeMillis();
                for (int i = 0; i < 3000; i++) {
                    edit.putInt("write - " + i, i).apply();
                }
                e.onNext(time);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                long time = System.currentTimeMillis();
                Log.e("Tag", "write sp time = " + (time - aLong));
                writeMmkv();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 读mmkv
     */
    private void readMmkv() {
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                MMKV mmkv = MMKV.defaultMMKV();
                long time = System.currentTimeMillis();
                for (int i = 0; i < 3000; i++) {
                    mmkv.decodeInt("write2 - " + i, i);
                }
                e.onNext(time);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                long time = System.currentTimeMillis();
                Log.e("Tag", "read mmkv time2 = " + (time - aLong));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 写mmkv
     */
    private void writeMmkv() {
        Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                MMKV mmkv = MMKV.defaultMMKV();
                long time = System.currentTimeMillis();
                for (int i = 0; i < 3000; i++) {
                    mmkv.encode("write2 - " + i, i);
                }
                e.onNext(time);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                long time = System.currentTimeMillis();
                Log.e("Tag", "write mmkv time2 = " + (time - aLong));
                readSp();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void addItems() {
        mItems.add("bitmap");
        mItems.add("RxJava");
        mItems.add("IjkPlayer");
        mItems.add("Sqlite");
        mItems.add("悬浮窗");
        mItems.add("透明状态栏");
        mItems.add("渐变状态栏");
        mItems.add("SimpleViewpagerFragment");
        mItems.add("size");
        mItems.add("画中画");
        mItems.add("TweenAnimation");
        mItems.add("PropertyAnimation");
        mItems.add("CanVas");
        mItems.add("PicScroll");
        mItems.add("wave(水波纹)");
        mItems.add("web(简道云)");
        mItems.add("Staggered(瀑布流)");
        mItems.add("magicIndicator");
        mItems.add("转场动画");
        mItems.add("换肤Skin");
        mItems.add("嵌套");
        mItems.add("计步");
        mItems.add("贝塞尔曲线");
        mItems.add("跑马灯");
        mItems.add("alipay");
        mItems.add("lock");
        mItems.add("tangram\nHomePage");
        mItems.add("瀑布流");
        mItems.add("省市区三级联动\n仿微信右划关闭");
        mItems.add("下载任务");
//        mAdapter.notifyDataSetChanged();
    }

    class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MainHolder> {

        @Override
        public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.item_main_rvadapter_layout, parent, false);
            MainHolder holder = new MainHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(MainHolder holder, int position) {
            String s = mItems.get(position);
            holder.nButton.setText(s);
            holder.nButton.setTag(position);
            holder.nButton.setOnClickListener(mOnItemclick);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        class MainHolder extends RecyclerView.ViewHolder {

            Button nButton;

            public MainHolder(View itemView) {
                super(itemView);
                nButton = itemView.findViewById(R.id.activity_main_item_btn);
            }
        }
    }

    class MainItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (position % (mItemSpanCount + 1) == 0) {
                outRect.left = outRect.right = 30;//单行  左右30
            } else {
                if (position % (mItemSpanCount + 1) == 1) {
                    outRect.left = 30;
                    outRect.right = 15;//两个的行   左边的右边距
                } else {
                    outRect.left = 15;//两个的行   右边的左边距
                    outRect.right = 30;
                }
            }
            int itemCount = parent.getAdapter().getItemCount();
            int nTotalRaw = 0;
            if (itemCount % (mItemSpanCount + 1) == 0) {
                nTotalRaw = itemCount * 2 / 3;
            } else {
                nTotalRaw = (itemCount - itemCount % (mItemSpanCount + 1)) * 2 / 3 + itemCount % (mItemSpanCount + 1);
            }
            int nRaw = 0;
            if (position % (mItemSpanCount + 1) == 0) {
                nRaw = position * 2 / 3;
            } else {
                nRaw = (position - position % (mItemSpanCount + 1)) * 2 / 3 + position % (mItemSpanCount + 1);
            }
            if (nRaw < nTotalRaw) {
                outRect.bottom = 15;
            } else {
                outRect.bottom = 30;
            }
            if (position == 0) {
                outRect.top = 30;
            } else {
                outRect.top = 15;
            }
        }
    }
}
