package com.project.git.com.gitproject.pictureinpicture;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.Rational;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.levitate.FloatView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PicActivity extends BaseActivity {

    private FloatView mFloat;
    private TextView mTimeTv;
    private Button mBtn;
    private SimpleDateFormat mSdf;
    private PictureInPictureParams.Builder mPictureInPictureParamsBuilder;

    private BroadcastReceiver mReceiver;

    /**
     * Intent action for media controls from Picture-in-Picture mode.
     */
    private static final String ACTION_MEDIA_CONTROL = "media_control";

    /**
     * Intent extra for media controls from Picture-in-Picture mode.
     */
    private static final String EXTRA_CONTROL_TYPE = "control_type";

    /**
     * The request code for play action PendingIntent.
     */
    private static final int REQUEST_PLAY = 1;

    /**
     * The request code for pause action PendingIntent.
     */
    private static final int REQUEST_PAUSE = 2;

    /**
     * The request code for info action PendingIntent.
     */
    private static final int REQUEST_X = 3;

    /**
     * The intent extra value for play action.
     */
    private static final int CONTROL_TYPE_PLAY = 1;

    /**
     * The intent extra value for pause action.
     */
    private static final int CONTROL_TYPE_PAUSE = 2;

    /**
     * The intent extra value for pause action.
     */
    private static final int CONTROL_TYPE_X = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initViews();
    }

    @TargetApi(26)
    private void initViews() {
        if (mPictureInPictureParamsBuilder == null) {
            mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
        }
        mFloat = findViewById(R.id.float_view);
        mTimeTv = findViewById(R.id.time_tv);
        mBtn = findViewById(R.id.picture_btn);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!PicActivity.this.isFinishing()) {
                    try {
                        Thread.sleep(10);
                        if (mTimeTv != null) {
                            mTimeTv.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if (mSdf == null) {
                                        mSdf = new SimpleDateFormat("HH:mm:ss:SSS");
                                    }
                                    String format = mSdf.format(new Date());
                                    mTimeTv.setText("time = : " + format);
                                }
                            }, 1);
                        }
                    } catch (Exception e) {

                    }
                }
            }
        }).start();
        updatePictureInPictureActions(
                R.drawable.picture_btn, "pause");
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minimize();
            }
        });
    }

    @TargetApi(26)
    void minimize() {
        if (mFloat == null) {
            return;
        }
        // Calculate the aspect ratio of the PiP screen.
        Rational aspectRatio = new Rational(mFloat.getWidth(), mFloat.getHeight());
        mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
    }

    @TargetApi(26)
    void updatePictureInPictureActions(
            @DrawableRes int iconId, String title) {
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        final PendingIntent intent =
                PendingIntent.getBroadcast(
                        PicActivity.this,
                        REQUEST_PLAY,
                        new Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, REQUEST_PLAY),
                        0);
        final Icon icon = Icon.createWithResource(PicActivity.this, iconId);
        actions.add(new RemoteAction(icon, title, title, intent));

        final PendingIntent intent2 =
                PendingIntent.getBroadcast(
                        PicActivity.this,
                        REQUEST_PAUSE,
                        new Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, REQUEST_PAUSE),
                        0);
        actions.add(new RemoteAction(icon, title, title, intent2));
        final PendingIntent intent3 =
                PendingIntent.getBroadcast(
                        PicActivity.this,
                        REQUEST_X,
                        new Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, REQUEST_X),
                        0);
        actions.add(new RemoteAction(icon, title, title, intent3));

        mPictureInPictureParamsBuilder.setActions(actions);

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        setPictureInPictureParams(mPictureInPictureParamsBuilder.build());
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        if (isInPictureInPictureMode) {
            // Starts receiving events from action items in PiP mode.
            mReceiver =
                    new BroadcastReceiver() {
                        @Override
                        public void onReceive(Context context, Intent intent) {
                            if (intent == null
                                    || !ACTION_MEDIA_CONTROL.equals(intent.getAction())) {
                                return;
                            }

                            // This is where we are called back from Picture-in-Picture action
                            // items.
                            final int controlType = intent.getIntExtra(EXTRA_CONTROL_TYPE, 0);
                            switch (controlType) {
                                case CONTROL_TYPE_PLAY:
                                    Toast.makeText(PicActivity.this, "play-1", Toast.LENGTH_SHORT).show();
                                    break;
                                case CONTROL_TYPE_PAUSE:
                                    Toast.makeText(PicActivity.this, "play-2", Toast.LENGTH_SHORT).show();
                                    break;
                                case CONTROL_TYPE_X:
                                    Toast.makeText(PicActivity.this, "play-X", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    };
            registerReceiver(mReceiver, new IntentFilter(ACTION_MEDIA_CONTROL));
        } else {
            // We are out of PiP mode. We can stop receiving events from it.
            unregisterReceiver(mReceiver);
            mReceiver = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
    }
}
