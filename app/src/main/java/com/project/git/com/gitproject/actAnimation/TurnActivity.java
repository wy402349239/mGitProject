package com.project.git.com.gitproject.actAnimation;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.transition.Fade;
import android.util.Pair;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.DemoApp;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

/**
 * created by wangyu on 2019/4/11
 * description :
 */
public class TurnActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        setContentView(R.layout.activity_turnanimation);
        getWindow().setEnterTransition(new Fade().setDuration(500));
        getWindow().setExitTransition(new Fade().setDuration(500));
        findViewById(R.id.turn_fenjie).setOnClickListener(this);
        findViewById(R.id.turn_move).setOnClickListener(this);
        findViewById(R.id.turn_share).setOnClickListener(this);
        findViewById(R.id.turn_share_2).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(TurnActivity.this, Turn2Activity.class);
        switch (v.getId()) {
            case R.id.turn_fenjie:
                intent.putExtra("type", 0);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this).toBundle());
                break;
            case R.id.turn_move:
                intent.putExtra("type", 1);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this).toBundle());
                break;
            case R.id.turn_share:
                intent.putExtra("type", 2);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this, v, "btn").toBundle());
                break;
            case R.id.turn_share_2:
                intent.putExtra("type", 2);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(TurnActivity.this, Pair.create(v, "btn"), Pair.create(v, "btn2")).toBundle());
                break;
        }
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(TbUrl));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        DemoApp.getInstance().startActivity(intent);
    }

    private static final String Alipay = "alipays://platformapi/startapp?appId=20000067&url=https%3a%2f%2frender.alipay.com%2fp%2fopx%2fnormal-k89zo22y%2fa.html%3fpartnerId%3dszjy411%26sceneCode%3dKF_ZHCPA%26benefit%3ddnsffl200908%26shareChannel%3dQRCode%26shareUserId%3d2088931839886135%26sharedUserId%3d%26webview_options%3d%26growthScene%3dIN_INVITE_UNTARGET_USER%26hookId%3d";
    private static final String JdUrl = "openapp.jdmobile://virtual?params={\"category\":\"jump\",\"sourceType\":\"sourceType_test\",\"des\":\"m\",\"url\":\"https://u.jd.com/iF2WFqu\",\"unionSource\":\"Awake\",\"channel\":\"d1337e4d3f8446e5b7653d9b147563f1\",\"union_open\":\"union_cps\"}";
    private static final String TbUrl = "tbopen://m.taobao.com/tbopen/index.html?source=auto&action=ali.open.nav&module=h5&bootImage=0&spm=2014.ugdhh.2200606446343.227036-484807-32768&bc_fl_src=growth_dhh_2200606446343_227036-484807-32768&materialid=227036&h5Url=https%3A%2F%2Fstar-link.taobao.com%3Fslk_actid%3D100000000323%26spm%3D2014.ugdhh.2200606446343.227036-484807-32768%26bc_fl_src%3Dgrowth_dhh_2200606446343_227036-484807-32768";
}
