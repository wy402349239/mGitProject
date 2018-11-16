package com.project.git.com.gitproject.levitate;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class FloatService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        showFloat();
        return super.onStartCommand(intent, flags, startId);
    }

    private void showFloat(){

    }
}
