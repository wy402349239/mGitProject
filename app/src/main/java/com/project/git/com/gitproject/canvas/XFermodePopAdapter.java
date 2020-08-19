package com.project.git.com.gitproject.canvas;

import android.content.Context;
import android.graphics.PorterDuff;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.git.com.gitproject.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * created by wangyu on 2020/8/18 3:58 PM
 * description:
 */
public class XFermodePopAdapter extends RecyclerView.Adapter<XFermodePopAdapter.XFermodeHolder> {

    private Map<String, PorterDuff.Mode> modes = null;
    private List<String> keys = new ArrayList<>();
    private Context context;

    private int selectIndex = -1;

    public XFermodePopAdapter(Context context, Map<String, PorterDuff.Mode> mode) {
        this.context = context;
        this.modes = mode;
        if (!modes.isEmpty()) {
            keys.addAll(modes.keySet());
        }
    }

    @NonNull
    @Override
    public XFermodeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new XFermodeHolder(LayoutInflater.from(context).inflate(R.layout.item_text, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull XFermodeHolder holder, int position) {
        if (position >= keys.size()) {
            return;
        }
        String key = keys.get(position);
        if (TextUtils.isEmpty(key)) {
            return;
        }
        holder.textView.setTag(R.id.tag_second, key);
        holder.textView.setText(key);
        holder.textView.setOnClickListener(click);
        if (position == selectIndex) {
            holder.textView.setBackgroundColor(0x880492fb);
        } else {
            holder.textView.setBackgroundColor(0x00000000);
        }
    }

    View.OnClickListener click = v -> {
        Object tag = v.getTag(R.id.tag_second);
        if (tag != null) {
            try {
                String key = tag.toString();
                selectIndex = keys.indexOf(key);
                notifyDataSetChanged();
                PorterDuff.Mode mode = modes.get(key);
                EventBus.getDefault().post(new XferModeEvent(mode));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int getItemCount() {
        return keys.size();
    }

    public int getPosition(PorterDuff.Mode mode) {
        int result = -1;
        if (modes.containsValue(mode)) {
            for (String key : modes.keySet()) {
                if (modes.get(key) == mode) {
                    result = keys.indexOf(key);
                    selectIndex = result;
                    notifyDataSetChanged();
                    break;
                }
            }
        }
        return result;
    }

    static class XFermodeHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textView;

        public XFermodeHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}