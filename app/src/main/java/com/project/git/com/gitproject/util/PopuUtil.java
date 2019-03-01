package com.project.git.com.gitproject.util;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.project.git.com.gitproject.AutoFitTextView;
import com.project.git.com.gitproject.R;
import com.project.git.com.gitproject.listener.ItemEvent;
import com.utilproject.wy.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jx_wy
 * @date 2:42 PM 2019/2/28
 * @email wangyu@51dianshijia.com
 * @description
 */
public class PopuUtil {

    public static PopupWindow showPopuBelowView(View baseView, List<String> strs, ItemEvent event){
        if (baseView == null){
            return null;
        }
        Context context = baseView.getContext();
        if (strs == null || strs.isEmpty()){
            Toast.makeText(context, "数据为空", Toast.LENGTH_SHORT).show();
        }
        View popuView = View.inflate(context, R.layout.popu_layout_recycle, null);
        RecyclerView nRv = popuView.findViewById(R.id.popu_recycler);
        GridLayoutManager manager = new GridLayoutManager(context, 2);
        nRv.setLayoutManager(manager);
        nRv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        nRv.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        TextAdapter adapter = new TextAdapter(strs);
        adapter.setEvent(event);
        nRv.setAdapter(adapter);
        PopupWindow popu = new PopupWindow(popuView, baseView.getWidth(), (int)(context.getResources().getDisplayMetrics().density * 200));
        popu.setFocusable(true);
        popu.setOutsideTouchable(true);
        popu.setBackgroundDrawable(new BitmapDrawable());
        popu.showAsDropDown(baseView, 0, 0);
        return popu;
    }

    private static class TextAdapter extends RecyclerView.Adapter<TextHolder>{

        private List<String> mStrs;
        private ItemEvent event;

        public TextAdapter(List<String> strs) {
            if (mStrs == null){
                mStrs = new ArrayList<>();
            }
            mStrs.addAll(ListUtil.deepCopy(strs));
        }

        public void setEvent(ItemEvent event) {
            this.event = event;
        }

        @Override
        public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = View.inflate(parent.getContext(), R.layout.item_popu_text, null);
            return new TextHolder(v);
        }

        @Override
        public void onBindViewHolder(TextHolder holder, final int position) {
            holder.mTv.setText(mStrs.get(position));
            holder.mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (event != null){
                        event.click(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mStrs.size();
        }
    }

    private static class TextHolder extends RecyclerView.ViewHolder{

        AutoFitTextView mTv;

        public TextHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.item_popu_tv);
        }
    }
}
