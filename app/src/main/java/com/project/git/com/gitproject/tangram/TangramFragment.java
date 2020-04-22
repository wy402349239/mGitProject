package com.project.git.com.gitproject.tangram;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.git.com.gitproject.R;
import com.tmall.wireless.tangram.TangramBuilder;
import com.tmall.wireless.tangram.TangramEngine;
import com.tmall.wireless.tangram.dataparser.concrete.Card;
import com.tmall.wireless.tangram.dataparser.concrete.Cell;
import com.tmall.wireless.tangram.structure.BaseCell;
import com.tmall.wireless.tangram.support.ExposureSupport;
import com.tmall.wireless.tangram.support.SimpleClickSupport;
import com.tmall.wireless.tangram.support.async.CardLoadSupport;

import org.json.JSONArray;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Map;

public class TangramFragment extends Fragment {

    private View mRoot;
    private RecyclerView mRv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mRoot == null) {
            mRoot = inflater.inflate(R.layout.frag_tangram, container, false);
        }
        mRv = mRoot.findViewById(R.id.tangram_rv);
        return mRoot;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            loadData();
        }
    }

    TangramBuilder.InnerBuilder builder = null;
    TangramEngine engine = null;
    private Handler mMainHandler;

    private void loadData() {
        if (builder == null && mRv != null){
            mMainHandler = new Handler(Looper.getMainLooper());
            builder = TangramBuilder.newInnerBuilder(getActivity());
            builder.registerCell("996", DefaultCell.class, TangramDefault.class);
            builder.registerCell("997", GridCell.class, TangramRv.class);
            engine = builder.build();
            engine.register(SimpleClickSupport.class, new SimpleClickSupport(){
                @Override
                public void setOptimizedMode(boolean optimizedMode) {
                    super.setOptimizedMode(optimizedMode);
                }

                @Override
                public void onClick(View targetView, BaseCell cell, int eventType) {
                    super.onClick(targetView, cell, eventType);
                }

                @Override
                public void onClick(View targetView, Cell cell, int eventType) {
                    super.onClick(targetView, cell, eventType);
                }

                @Override
                public void onClick(View targetView, BaseCell cell, int eventType, Map<String, Object> params) {
                    super.onClick(targetView, cell, eventType, params);
                }

                @Override
                public void defaultClick(View targetView, BaseCell cell, int eventType) {
                    super.defaultClick(targetView, cell, eventType);
                    if (cell instanceof DefaultCell){
                        DefaultCell defaultCell = (DefaultCell)cell;
                    }
                }

                @Override
                public void destroy() {
                    super.destroy();
                }
            });
            engine.register(CardLoadSupport.class, new CardLoadSupport());
            engine.register(ExposureSupport.class, new ExposureSupport() {
                @Override
                public void onExposure(@NonNull Card card, int offset, int position) {
                    if (position == engine.getGroupBasicAdapter().getItemCount() - 1){
                        String json = new String(getAssertsFile("data.json"));
                        JSONArray data = null;
                        try {
                            data = new JSONArray(json);
                            engine.appendData(data);
                            engine.refresh();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            engine.enableAutoLoadMore(false);
            engine.bindView(mRv);
            mRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    engine.onScrolled();
                }
            });

            engine.setPreLoadNumber(3);
            String json = new String(getAssertsFile("data.json"));
            JSONArray data = null;
            try {
                data = new JSONArray(json);
                engine.setData(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] getAssertsFile(String fileName) {
        InputStream inputStream = null;
        AssetManager assetManager = TangramFragment.this.getActivity().getAssets();
        try {
            inputStream = assetManager.open(fileName);
            if (inputStream == null) {
                return null;
            }
            BufferedInputStream bis = null;
            int length;
            try {
                bis = new BufferedInputStream(inputStream);
                length = bis.available();
                byte[] data = new byte[length];
                bis.read(data);

                return data;
            } catch (Exception e) {

            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (Exception e) {

                    }
                }
            }

            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (engine != null){
            engine.destroy();
        }
    }
}
