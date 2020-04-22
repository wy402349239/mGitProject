package com.project.git.com.gitproject.tangram;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONObject;

public class DefaultCell extends BaseCell<TangramDefault> {

    public String titleStr = "";
    public String descStr = "";
    public String urlStr = "";

    @Override
    public void parseWith(@NonNull JSONObject data, @NonNull MVHelper resolver) {
        super.parseWith(data, resolver);
        try {
            titleStr = data.getString("title");
            descStr = data.getString("desc");
            urlStr = data.getString("url");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void bindView(@NonNull TangramDefault view) {
        super.bindView(view);
        Glide.with(view.getContext())
                .load(urlStr)
                .into(view.mImg);
        view.mTitle.setText(titleStr);
        view.mDesc.setText(descStr);
    }

    @Override
    public void postBindView(@NonNull TangramDefault view) {
        super.postBindView(view);
    }

    @Override
    public void unbindView(@NonNull TangramDefault view) {
        super.unbindView(view);
    }
}
