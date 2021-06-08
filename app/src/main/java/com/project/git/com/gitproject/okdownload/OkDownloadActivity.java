package com.project.git.com.gitproject.okdownload;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.canvas.ProcessView;
import com.project.git.com.gitproject.canvas.ProgressView;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 4/15/21 7:33 PM
 */
public class OkDownloadActivity extends BaseActivity {

    RecyclerView mRv;
    Toolbar mTool;
    ProcessView mProgress;

    private static final String apkUrl = "https://cdn.llscdn.com/yy/files/xs8qmxn8-lls-LLS-5.8-800-20171207-111607.apk";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mTool = findViewById(R.id.list_toolbar);
        mRv = findViewById(R.id.list_rv);
        mProgress = findViewById(R.id.list_progress);
        setSupportActionBar(mTool);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mTool.setNavigationIcon(R.drawable.ic_common_back_black);
        mTool.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new LinearLayoutManager(OkDownloadActivity.this));
        mRv.setItemAnimator(null);
//        startDownload();
        downloadFile(apkUrl, "1098.apk", OkDownloadActivity.this.getFilesDir().getAbsolutePath());
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (progress <= 100){
//                        progress += 0.5;
//                        setProgress();
//                        Thread.sleep(60);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }

    private void downloadFile(String url, String fileName, String filePath) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                File fileParent = new File(filePath);
                if (!fileParent.exists() || !fileParent.isDirectory()) {
                    if (!fileParent.mkdirs()) {
                        return;
                    }
                }
                File file = new File(fileParent.getAbsolutePath(), fileName);
                String start = String.format(Locale.CHINESE, "bytes=%d-", file.length());
                Request request = new Request.Builder().url(url).header("range", start).build();
                Call call = new OkHttpClient().newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        RandomAccessFile accessFile = new RandomAccessFile(file, "rw");
                        ResponseBody body = response.body();
                        long nMax = body.contentLength();
                        long length = file.length();
                        if (length < nMax) {
                            if (length > 0) {
                                nMax += length;
                            }
                            accessFile.seek(length);
                            InputStream is = body.byteStream();
                            byte[] bytes = new byte[4096];
                            int len = is.read(bytes);
                            while (len != -1) {
                                accessFile.write(bytes, 0, len);
                                len = is.read(bytes);
                                progress = file.length() * 100f / nMax;
                                setProgress();
                            }
                            is.close();
                        }
                        mRv.post(new Runnable() {
                            @Override
                            public void run() {
                                installApp(file.getAbsolutePath());
                            }
                        });
                    }
                });
            }
        }).start();

//        Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(@NonNull ObservableEmitter<String> emitter) throws Exception {
//
//            }
//        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe();
    }

    public void installApp(String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkUri = FileProvider.getUriForFile(OkDownloadActivity.this, "com.dianshijia.tvlive.fileProvider", new File(apkPath));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            } else {
                intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                        "application/vnd.android.package-archive");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
//            try {
//                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//                StrictMode.setVmPolicy(builder.build());
//                Uri uri = Uri.parse("file://" + apkPath);
//                intent.setDataAndType(uri, "application/vnd.android.package-archive");
//                IntentHelper.goPage(OkDownloadActivity.this, intent);
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }
        }
    }

    protected void setProgress(){
        if (handler == null){
            handler = new Handler(Looper.getMainLooper());
        }
        Log.e("Tag", "progress = " + progress);
        handler.removeCallbacks(run);
        handler.post(run);
    }

    private final Runnable run = new Runnable() {
        @Override
        public void run() {
            mProgress.setCurrentCount(progress);
        }
    };

    protected float progress = 0;

    private Handler handler;

//    private void startDownload() {
//        File cache = new File(this.getFilesDir().getAbsolutePath());
//        DownloadTask.Builder builder = new DownloadTask.Builder(url, cache);
//        builder.setFilename("111610.apk");
//        builder.setMinIntervalMillisCallbackProcess(50);//最小回调时间
//        builder.setPassIfAlreadyCompleted(false);//已完成时是否重新下载
////        builder.setConnectionCount()设置下载线程数
////        builder.setFilenameFromResponse()是否使用服务器地址作为文件名，未提供名字的情况
//        builder.setAutoCallbackToUIThread(true);//主线程通知调用者
////        builder.setHeaderMapFields()请求头
////        builder.addHeader();添加请求头
////        builder.setPriority()优先级 越大越高
////        builder.setReadBufferSize()读取缓存区大小  默认4096
////        builder.setFlushBufferSize()写入缓存区大小  默认16384
////        builder.setSyncBufferSize()写入到文件的缓冲区大小  65535
////        builder.setSyncBufferIntervalMillis()写入文件的最小时间间隔
////        builder.setFilename()
////        builder.setWifiRequired()是否只允许WiFi下载
//
//        DownloadTask task = builder.build();//task.cancle()
//        task.enqueue(new DownloadListener() {
//            @Override
//            public void taskStart(@NonNull DownloadTask task) {
//                Log.e("Tag", "taskStart");
//            }
//
//            @Override
//            public void connectTrialStart(@NonNull DownloadTask task, @NonNull Map<String, List<String>> requestHeaderFields) {
//                Log.e("Tag", "connectTrialStart");
//            }
//
//            @Override
//            public void connectTrialEnd(@NonNull DownloadTask task, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                Log.e("Tag", "connectTrialEnd");
//            }
//
//            @Override
//            public void downloadFromBeginning(@NonNull DownloadTask task, @NonNull BreakpointInfo info, @NonNull ResumeFailedCause cause) {
//                Log.e("Tag", "downloadFromBeginning");
//            }
//
//            @Override
//            public void downloadFromBreakpoint(@NonNull DownloadTask task, @NonNull BreakpointInfo info) {
//                Log.e("Tag", "downloadFromBreakpoint");
//            }
//
//            @Override
//            public void connectStart(@NonNull DownloadTask task, int blockIndex, @NonNull Map<String, List<String>> requestHeaderFields) {
//                Log.e("Tag", "connectStart");
//            }
//
//            @Override
//            public void connectEnd(@NonNull DownloadTask task, int blockIndex, int responseCode, @NonNull Map<String, List<String>> responseHeaderFields) {
//                Log.e("Tag", "connectEnd");
//            }
//
//            @Override
//            public void fetchStart(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                Log.e("Tag", "fetchStart : " + contentLength);
//            }
//
//            @Override
//            public void fetchProgress(@NonNull DownloadTask task, int blockIndex, long increaseBytes) {
////                task.getInfo().getTotalLength()
//                Log.e("Tag", "fetchProgress " + task.getFile().length() + ", " + increaseBytes);
//            }
//
//            @Override
//            public void fetchEnd(@NonNull DownloadTask task, int blockIndex, long contentLength) {
//                Log.e("Tag", "fetchEnd");
//            }
//
//            @Override
//            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause) {
//                Log.e("Tag", "taskEnd");
//            }
//        });
//    }
}