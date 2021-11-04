package com.project.git.com.gitproject.toast;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @Description:
 * @Author: jx_wy
 * @Date: 2021/11/2 3:17 下午
 */
public class EnterToast {

    private static EnterToast toastUtil = null;

    public static void touchToast(Context context, String msg) {
        if (toastUtil == null) {
            toastUtil = new EnterToast();
        }
        toastUtil.showToast(context, msg);
    }

    private Toast toast = null;
    private long time = 0;

    public synchronized void showToast(Context context, String msg) {
        long cTime = System.currentTimeMillis();
        if (toast == null){
            time = cTime;
            toast = createToast(context, msg);
            toast.show();//.....
            Log.e("Tag", "normal show --> " + cTime);
        }else {
            Log.e("Tag", cTime + " ---> " + time);
            if (cTime - time >= 3500){
                time = cTime;
                toast = createToast(context, msg);
                toast.show();
                Log.e("Tag", "time show");
            }else {
                toast.cancel();
                Log.e("Tag", " cancle");
            }
        }
    }

    private Toast createToast(Context context, String msg){
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 100);
        return toast;
    }
}