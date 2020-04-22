package com.project.git.com.gitproject.tangram;

import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.tmall.wireless.tangram.MVHelper;
import com.tmall.wireless.tangram.structure.BaseCell;

import org.json.JSONObject;

public class GridCell extends BaseCell<TangramRv> {

    public String data = "";

    @Override
    public void parseWith(@NonNull JSONObject obj, @NonNull MVHelper resolver) {
        super.parseWith(obj, resolver);
        try {
            data = obj.getString("data");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void bindView(@NonNull TangramRv view) {
        super.bindView(view);
        view.load(data);
    }

    @Override
    public void postBindView(@NonNull TangramRv view) {
        super.postBindView(view);
    }

    @Override
    public void unbindView(@NonNull TangramRv view) {
        super.unbindView(view);
    }
}
