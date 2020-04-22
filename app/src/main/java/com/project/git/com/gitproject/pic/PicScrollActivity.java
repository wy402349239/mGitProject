package com.project.git.com.gitproject.pic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.project.git.com.gitproject.BaseActivity;
import com.project.git.com.gitproject.R;
import com.utilproject.wy.DeviceUtil;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

/**
 * 借助第三方，实现画廊效果
 *
 * @author jx_wy
 * @date 10:42 AM 2019/2/27
 * @email wangyu@51dianshijia.com
 * @description
 */
public class PicScrollActivity extends BaseActivity {

    private RecyclerView mTopRv;
    private List<String> mUrls;
    private TabLayout mTab;

    private CardScrollRecyclerView mBottonRv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic);
        DeviceUtil.setStatuResouce(this, R.drawable.jb_statu);
        initTopRv();

        initBottomRv();
    }

    private void initTopRv(){
        mTopRv = findViewById(R.id.pic_rv_top);
        mTab = findViewById(R.id.pic_tab);
        GalleryLayoutManager manager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        manager.attach(mTopRv);
        ScrollTransformer transformer = new ScrollTransformer();
        manager.setItemTransformer(transformer);
        mUrls = new ArrayList<>();
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551099264457&di=3a26cd6cd473e9163f70ec79b4d63e07&imgtype=0&src=http%3A%2F%2Faliyunzixunbucket.oss-cn-beijing.aliyuncs.com%2Fjpg%2F483765c596a6ec51a9cfcf06197e5b79.jpg%3Fx-oss-process%3Dimage%2Fresize%2Cp_100%2Fauto-orient%2C1%2Fquality%2Cq_90%2Fformat%2Cjpg%2Fwatermark%2Cimage_eXVuY2VzaGk%3D%2Ct_100");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551099304344&di=a68cb5efacc1cfe1656f214af4c0a89a&imgtype=0&src=http%3A%2F%2Fp2.so.qhimgs1.com%2Ft0196587edd5fb09d82.jpg");
        mUrls.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1459761901,1870564289&fm=26&gp=0.jpg");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551694132&di=884d5348a465575e71982ca7d4cdb046&imgtype=jpg&er=1&src=http%3A%2F%2Fpic4.qiyipic.com%2Fcommon%2Flego%2F20150319%2Fd37ff060cb434e66a9ca80b0e4ffa560.jpg%3Fsrc%3Dfocustat_21_20130410_1");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551694124&di=ebc0d667f317fbd2fdba8d595a0a1b6c&imgtype=jpg&er=1&src=http%3A%2F%2Fpic12.photophoto.cn%2F20090910%2F0035035993595153_b.jpg");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551099497293&di=4453d4f44456f226ddd7ea297dd120e6&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01fdbd59102f7ca801216a3eeae036.jpg%401280w_1l_2o_100sh.jpg");
        mUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551099528754&di=c8c47a8cafd1682f317339f7ff123d26&imgtype=0&src=http%3A%2F%2Fww2.sinaimg.cn%2Flarge%2F71b2cbb3gw1f5i55vwmjxj21kw1b8the.jpg");

        mTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        for (int i = 0; i < mUrls.size(); i++) {
            TabLayout.Tab tab = mTab.newTab();
            View root = View.inflate(PicScrollActivity.this, R.layout.item_hl_tab, null);
            AppCompatTextView title = root.findViewById(R.id.tab_title);
            title.setText(String.valueOf(i + 1));
            title.setTag(R.id.tag_second, i);
            title.setOnClickListener(listener);
            tab.setCustomView(root);
            mTab.addTab(tab);
        }

        final ScrollAdapter adapter = new ScrollAdapter(PicScrollActivity.this, mUrls);
        mTopRv.setAdapter(adapter);
        manager.setOnItemSelectedListener(new GalleryLayoutManager.OnItemSelectedListener() {
            @Override
            public void onItemSelected(RecyclerView recyclerView, View item, int position) {
                mTab.setScrollPosition(position, 0, true);
            }
        });
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Object tag = v.getTag(R.id.tag_second);
            if (tag != null){
                int index = Integer.parseInt(tag.toString());
                mTopRv.smoothScrollToPosition(index);
            }
        }
    };

    private void initBottomRv(){
        int[] rs = new int[]{R.drawable.img1, R.drawable.img2, R.drawable.img3, R.drawable.img4};
        List<Integer> resources = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            resources.add(rs[i % 4]);
        }
        mBottonRv = findViewById(R.id.pic_rv_bottom);
        mBottonRv.setFling(false);
        GalleryLayoutManager manager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
        manager.attach(mBottonRv);
        manager.setItemTransformer(new CardTransformer());

        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.35f);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.4f);
        CardAdapter adapter = new CardAdapter(PicScrollActivity.this, resources, width, height);
        mBottonRv.setAdapter(adapter);
    }
}
