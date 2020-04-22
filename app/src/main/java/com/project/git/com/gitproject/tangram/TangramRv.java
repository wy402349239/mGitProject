package com.project.git.com.gitproject.tangram;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.project.git.com.gitproject.R;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.structure.view.ITangramViewLifeCycle;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TangramRv extends FrameLayout implements ITangramViewLifeCycle {

    private RecyclerView mRv;
    private GridAdapter mAdapter;

    public TangramRv(@NonNull Context context) {
        this(context, null);
    }

    public TangramRv(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TangramRv(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_rv, TangramRv.this);
        mRv = findViewById(R.id.rv_fill);
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        GridLayoutManager nManager = new GridLayoutManager(getContext(), 2);
        nManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1;
            }
        });
        mRv.setLayoutManager(nManager);
        mRv.setItemAnimator(null);
        if (mRv.getItemDecorationCount() == 0) {
            mRv.addItemDecoration(new SignItemDecoration());
        }
        mAdapter = new GridAdapter(TangramRv.this.getContext(), null);
        mRv.setAdapter(mAdapter);
    }

    public void load(final String data) {
        try {
            Observable.create(new ObservableOnSubscribe<List<GridItem>>() {
                @Override
                public void subscribe(ObservableEmitter<List<GridItem>> e) throws Exception {
                    List<GridItem> items = new ArrayList<>();
                    if (!TextUtils.isEmpty(data)){
                        JSONArray array = new JSONArray(data);
                        Gson gson = new Gson();
                        if (array.length() > 0){
                            for (int i = 0; i < array.length(); i++) {
                                String str = array.getString(i);
                                GridItem item = gson.fromJson(str, GridItem.class);
                                items.add(item);
                            }
                        }
                    }
                    e.onNext(items);
                    e.onComplete();
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<List<GridItem>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(List<GridItem> list) {
                            if (mAdapter != null){
                                mAdapter.reset(list);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class SignItemDecoration extends RecyclerView.ItemDecoration {
        private int spacing = 0;

        private SignItemDecoration() {
            spacing = 10;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            outRect.right = position % 2 == 0 ? spacing : 0;
            outRect.left = position % 2 == 0 ? 0 : spacing;
        }
    }

    @Override
    public void cellInited(BaseCell cell) {

    }

    @Override
    public void postUnBindView(BaseCell cell) {

    }

    @Override
    public void postBindView(BaseCell cell) {

    }
}
