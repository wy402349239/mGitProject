package com.project.git.com.gitproject.step;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.airbnb.lottie.LottieAnimationView;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * created by wangyu on 2019-11-18
 * description :
 */
public class StepCountAct extends BaseActivity implements SensorEventListener {

    SensorManager manager;
    Sensor sensor;
    Sensor sensor2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_null);
        long time = System.currentTimeMillis() - SystemClock.elapsedRealtimeNanos() / 1000000;
        long time2 = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
        Log.e("Tag", sdf.format(new Date(time2)));
        time /= 1000;
        time2 /= 1000;
        init();
    }

    private void init(){
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        sensor2 = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor != null && manager != null)
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        if (sensor2 != null && manager != null)
            manager.registerListener(this, sensor2, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensor != null && manager != null)
            manager.unregisterListener(this, sensor);
        if (sensor2 != null && manager != null)
            manager.unregisterListener(this, sensor2);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            for (int i = 0; i < event.values.length; i++) {
                Log.e("Tag", event.values[i] + "-----");
            }
        }
        if (event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            for (int i = 0; i < event.values.length; i++) {
                Log.e("Tag", event.values[i] + "-----");
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
