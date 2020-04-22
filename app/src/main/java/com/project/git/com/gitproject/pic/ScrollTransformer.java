package com.project.git.com.gitproject.pic;

import android.view.View;

import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

/**
 * 用于实现画廊效果
 *
 * @author jx_wy
 * @date 11:00 AM 2019/2/27
 * @email wangyu@51dianshijia.com
 * @description
 */
public class ScrollTransformer implements GalleryLayoutManager.ItemTransformer {

    private float mScaleSize = 0.16f;//未在中间显示的元素，比中间元素小的比例

    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
        item.setPivotY(item.getHeight() / 2.0f);//中心点
        item.setPivotX(item.getWidth() / 2.0f);
        float nScale = 1 - mScaleSize * Math.abs(fraction);
        item.setScaleX(nScale);
        item.setScaleY(nScale);
    }

    public void setScaleSize(float scaleSize) {
        this.mScaleSize = scaleSize;
    }
}
