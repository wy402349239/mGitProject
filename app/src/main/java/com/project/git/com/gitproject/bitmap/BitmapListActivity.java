package com.project.git.com.gitproject.bitmap;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Administrator on 2018\10\5 0005.
 */

public class BitmapListActivity extends BaseActivity {

    private RecyclerView mRootRv;
    private List<BitmapListAdapter.FileStruct> fils;
    private BitmapListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap_list_layout);
        setCj();
        mRootRv = findViewById(R.id.bitmap_list_rv);
        mRootRv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        LinearLayoutManager nManagener = new LinearLayoutManager(BitmapListActivity.this);
        nManagener.setOrientation(LinearLayout.VERTICAL);
        mRootRv.setLayoutManager(nManagener);
        fils = new ArrayList<>();
        getPermission();
    }

    private void initDatas() {
        File absoluteFile = Environment.getExternalStorageDirectory().getAbsoluteFile();
        try {
            File[] files = absoluteFile.listFiles();
            for (int i = 0; i < files.length; i++) {
                BitmapListAdapter.FileStruct file = new BitmapListAdapter.FileStruct();
                file.setDec(files[i].isDirectory());
                file.setName(files[i].getName());
                fils.add(file);
            }
            Collections.sort(fils, new Comparator<BitmapListAdapter.FileStruct>() {
                @Override
                public int compare(BitmapListAdapter.FileStruct fileStruct, BitmapListAdapter.FileStruct t1) {
                    if (fileStruct.getName().compareToIgnoreCase(t1.getName()) > 0){
                        return 1;
                    }else {
                        return -1;
                    }
                }
            });
            mAdapter = new BitmapListAdapter(BitmapListActivity.this, fils);
            mRootRv.setAdapter(mAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getPermission() {
        int permissionCheck1 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionCheck2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck1 != PackageManager.PERMISSION_GRANTED || permissionCheck2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    124);
        } else {
            initDatas();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 124) {
            if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                initDatas();
            }
        }
    }
}