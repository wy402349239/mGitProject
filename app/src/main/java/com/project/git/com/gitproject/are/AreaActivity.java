package com.project.git.com.gitproject.are;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 2020/12/14 8:53 PM
 */
public class AreaActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        setScrollOut();
        initViews();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        disPatch(ev);
        return super.dispatchTouchEvent(ev);
    }

    private void initViews() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DsjAreaEntity entity = new DsjAreaEntity();
                    JSONArray data = new JSONArray(getJson(AreaActivity.this, "province.json"));
                    if (data.length() > 0) {
                        Gson gson = new Gson();
                        for (int i = 0; i < data.length(); i++) {
                            DsjAreaEntity.DsjProvince province = gson.fromJson(data.optJSONObject(i).toString(), DsjAreaEntity.DsjProvince.class);
                            entity.getProvinces().add(province);
                        }
                    }
                    entity.initAreas();
                    addView(entity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void addView(final DsjAreaEntity entity){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                OptionsPickerView pvOptions = new OptionsPickerBuilder(AreaActivity.this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String opt1tx = entity.getProvinces().size() > 0 ?
                                entity.getProvinces().get(options1).getName() : "";
                        String opt2tx = entity.getCities().size() > 0
                                && entity.getCities().get(options1).size() > 0 ?
                                entity.getCities().get(options1).get(options2) : "";
                        String opt3tx = entity.getAreas().size() > 0
                                && entity.getAreas().get(options1).size() > 0
                                && entity.getAreas().get(options1).get(options2).size() > 0 ?
                                entity.getAreas().get(options1).get(options2).get(options3) : "";
                        String tx = opt1tx + opt2tx + opt3tx;
                        Toast.makeText(AreaActivity.this, tx, Toast.LENGTH_SHORT).show();
                    }
                })

                        .setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .build();
                pvOptions.setPicker(entity.getProvinces(), entity.getCities(), entity.getAreas());//三级选择器
                pvOptions.show();
            }
        });
    }

    public String getJson(Context context, String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}