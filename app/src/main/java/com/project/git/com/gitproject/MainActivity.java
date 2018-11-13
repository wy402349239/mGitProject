package com.project.git.com.gitproject;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.git.com.gitproject.Sqlite.SqliteActivity;
import com.project.git.com.gitproject.bitmap.BitmapActivity;
import com.project.git.com.gitproject.ijk.ActivityIjk;
import com.project.git.com.gitproject.rxjava.RxJavaActivity;

import java.util.ArrayList;
import java.util.List;

import crossoverone.statuslib.StatusUtil;

public class MainActivity extends BaseActivity {

    RecyclerView mRv;
    private List<String> mItems = new ArrayList<>();
    MainRvAdapter mAdapter;
    static final int mItemSpanCount = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setCj();
        mRv = findViewById(R.id.demo_main_recycler);
        mRv.addItemDecoration(new MainItemDecoration());
        mAdapter = new MainRvAdapter();
//        LinearLayoutManager nManagener = new LinearLayoutManager(MainActivity.this);
//        nManagener.setOrientation(LinearLayout.VERTICAL);
//        mRv.setLayoutManager(nManagener);
        GridLayoutManager nManager = new GridLayoutManager(MainActivity.this, mItemSpanCount);
        nManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position % (mItemSpanCount + 1) == 0) {
                    return mItemSpanCount;
                } else {
                    return 1;
                }
            }
        });
        mRv.setLayoutManager(nManager);
        mRv.setAdapter(mAdapter);
        addItems();
    }

    private void addItems() {
        mItems.add("bitmap");
        mItems.add("RxJava");
        mItems.add("IjkPlayer");
        mItems.add("Sqlite");
        mAdapter.notifyDataSetChanged();
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
            }
        }
    };

    class MainItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (position % (mItemSpanCount + 1) == 0) {
                outRect.left = outRect.right = 30;
            } else {
                if (position % (mItemSpanCount + 1) == 1) {
                    outRect.left = 30;
                    outRect.right = 15;
                } else {
                    outRect.left = 15;
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
            if (position == 0){
                outRect.top = 30;
            }else {
                outRect.top = 15;
            }
        }
    }
}
