package com.project.git.com.gitproject;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.project.git.com.gitproject.Sqlite.SqliteActivity;
import com.project.git.com.gitproject.animation.AnimationPropertyActivity;
import com.project.git.com.gitproject.animation.AnimationTweenActivity;
import com.project.git.com.gitproject.bitmap.BitmapActivity;
import com.project.git.com.gitproject.canvas.CanvasActivity;
import com.project.git.com.gitproject.common.util.PopuUtil;
import com.project.git.com.gitproject.ijk.ActivityIjk;
import com.project.git.com.gitproject.levitate.FloatActivity;
import com.project.git.com.gitproject.listener.ItemEvent;
import com.project.git.com.gitproject.magicindicator.MagicActivity;
import com.project.git.com.gitproject.pic.PicScrollActivity;
import com.project.git.com.gitproject.pictureinpicture.PicActivity;
import com.project.git.com.gitproject.rxjava.RxJavaActivity;
import com.project.git.com.gitproject.size.SizeActivity;
import com.project.git.com.gitproject.staggred.StaggredActivity;
import com.project.git.com.gitproject.statu.GradintActivity;
import com.project.git.com.gitproject.statu.TransStatuActivity;
import com.project.git.com.gitproject.viewpagerfragment.PagerActivity;
import com.project.git.com.gitproject.wave.WaveActivity;
import com.project.git.com.gitproject.web.WebActivity;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    RecyclerView mRv;
    private List<String> mItems = new ArrayList<>();
    MainRvAdapter mAdapter;
    static final int mItemSpanCount = 2;
    private PopupWindow mPopu;

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
        findViewById(R.id.popu_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopu = PopuUtil.showPopuBelowView(v, mItems, new ItemEvent() {
                    @Override
                    public void click(int position) {
                        jumpClick(position);
                    }
                });
            }
        });
        findViewById(R.id.popu_btn).setVisibility(View.GONE);
    }

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
//        mAdapter.notifyDataSetChanged();
    }

    class MainRvAdapter extends RecyclerView.Adapter<MainRvAdapter.MainHolder> {

        class MainHolder extends RecyclerView.ViewHolder {

            Button nButton;

            public MainHolder(View itemView) {
                super(itemView);
                nButton = itemView.findViewById(R.id.activity_main_item_btn);
            }
        }

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
    }

    private void jumpClick(int positon) {
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
        }
        if (mPopu != null) {
            mPopu.dismiss();
        }
    }

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
            }
        }
    };

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
