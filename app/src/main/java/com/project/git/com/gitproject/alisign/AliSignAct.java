package com.project.git.com.gitproject.alisign;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.alipay.sdk.app.OpenAuthTask;
import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.pmd.BtnView;
import com.project.git.com.gitproject.pmd.MddPmdView;

import java.util.HashMap;
import java.util.Map;

/**
 * created by wangyu on 2020-03-23
 * description :
 */
public class AliSignAct extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pmd);
        requestPermission();
        final MddPmdView pmd = findViewById(R.id.pmd_view);
//        pmd.setBgColor(Color.GRAY)
//                .setCirlRadiu(5)
//                .setPointCount(200)
//                .setRadiu(40)
//                .setStockColor(Color.BLACK)
//                .setStockWidth(3)
//                .setPointCount(200);
        pmd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pmd.startRun(1080, 600);
            }
        });
        final BtnView btn = findViewById(R.id.pmd_btn);
        btn.settext("看视频最高再领999金币");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(auth_code)){
                    getCode();
                }else {
                    getUserId();
                }
            }
        });
    }

    private static String auth_code = "";

    private void getCode(){
        // 传递给支付宝应用的业务参数
        final Map<String, String> bizParams = new HashMap<>();
        bizParams.put("url", "https://authweb.alipay.com/auth?auth_type=PURE_OAUTH_SDK&app_id=2021001146623196&scope=auth_user&state=init");
        final String scheme = "__alipay_git__";

        // 唤起授权业务
        final OpenAuthTask task = new OpenAuthTask(AliSignAct.this);
        task.execute(
                scheme,	// Intent Scheme
                OpenAuthTask.BizType.AccountAuth, // 业务类型
                bizParams, // 业务参数
                openAuthCallback, // 业务结果回调。注意：此回调必须被你的应用保持强引用
                false); // 是否需要在用户未安装支付宝 App 时，使用 H5 中间页中转
    }

    private void getUserId(){

    }

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。
     * 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。
     * 如果接入支付宝 SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, PERMISSIONS_REQUEST_CODE);

        }
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE: {

                // 用户取消了权限弹窗
                if (grantResults.length == 0) {
                    Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults) {
                    if (x == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(this, "权限不足", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                // 所需的权限均正常获取
//                showToast(this, getString(R.string.permission_granted));
            }
        }
    }


    /**
     * 通用跳转授权业务的回调方法。
     * 此方法在支付宝 SDK 中为弱引用，故你的 App 需要以成员变量等方式保持对 Callback 的强引用，
     * 以免对象被回收。
     * 以局部变量保持对 Callback 的引用是不可行的。
     */
    final OpenAuthTask.Callback openAuthCallback = new OpenAuthTask.Callback() {
        @Override
        public void onResult(int resultCode, String memo, Bundle bundle) {
            if (resultCode == OpenAuthTask.OK) {
                showAlert(AliSignAct.this, String.format("业务成功，结果码: %s\n结果信息: %s\n结果数据: %s", resultCode, memo, bundleToString(bundle)));
            } else {
                showAlert(AliSignAct.this, String.format("业务失败，结果码: %s\n结果信息: %s\n结果数据: %s", resultCode, memo, bundleToString(bundle)));
            }
        }
    };

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.app_name, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    private static String bundleToString(Bundle bundle) {
        if (bundle == null) {
            return "null";
        }
        final StringBuilder sb = new StringBuilder();
        for (String key: bundle.keySet()) {
            sb.append(key).append("=>").append(bundle.get(key)).append("\n");
            if (key.equals("auth_code")){
                auth_code = bundle.get(key).toString();
            }
        }
        return sb.toString();
    }
}
