package com.project.git.com.gitproject.bitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by wy on 2018/9/13.
 * 截图拼接工具类
 * 个股分享
 */

public class ShareBitmapUtil {

    private static ShareBitmapUtil util = null;

    public static ShareBitmapUtil getInstance() {
        if (util == null) {
            util = new ShareBitmapUtil();
        }
        return util;
    }


    /**
     * 保存bitmao到本地文件
     *
     * @param context 上下文
     * @return 保存文件的路径
     */
    public String saveBitmap(Activity context, String pathOne, String pathTwo) {
        if (context == null || TextUtils.isEmpty(pathOne) || TextUtils.isEmpty(pathTwo)) {
            return "";
        }
        // 首先保存图片路径
        String nPath = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            nPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        }else {
            nPath = context.getCacheDir().getPath();
        }
        File appDir = new File(nPath,
                "demo");
        if (!appDir.exists()) {
            appDir.mkdir();
        }else if (appDir.isDirectory()){
            File[] files = appDir.listFiles();
            if (null != files && files.length > 0){
                for (int i = 0; i < files.length; i++) {
                    try {
                        files[i].delete();//删除原有文件，避免占用过多空间
                    }catch (Exception e){

                    }
                }
            }
        }
        Bitmap bitmapOne = BitmapFactory.decodeFile(pathOne);
        int nScreenWidth = bitmapOne.getWidth();
        int nScreenHeight = bitmapOne.getHeight();//获取宽高
        Bitmap bitmapTwo = BitmapFactory.decodeFile(pathTwo);
        int nHeight = bitmapTwo.getHeight() * nScreenWidth / bitmapTwo.getWidth();//根据宽高比  计算需要的宽高
        bitmapTwo = setImgSize(bitmapTwo, nScreenWidth, nHeight);
//        int navigation = getNavigationHeight(context);
        Bitmap bitmap = addBitmap(bitmapOne, bitmapTwo, 0);
        //当前时间来命名图片
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return file.getAbsolutePath();
    }

//    /**
//     * 保存bitmao到本地文件
//     *
//     * @param context 上下文
//     * @return 保存文件的路径
//     */
//    public Bitmap getCapBitmap(Activity context) {
//        if (context == null) {
//            return null;
//        }
//        WindowManager wm = context.getWindowManager();
//        int nScreenWidth = wm.getDefaultDisplay().getWidth();
//        int nScreenHeight = wm.getDefaultDisplay().getHeight();//获取屏幕宽高
//        Bitmap bitmapIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_stock_share_buttom);
//        int nHeight = bitmapIcon.getHeight() * nScreenWidth / bitmapIcon.getWidth();//根据宽高比  计算需要的宽高
//        bitmapIcon = setImgSize(bitmapIcon, nScreenWidth, nHeight);
//        int navigation = getNavigationHeight(context);
//        Bitmap bitmap = addBitmap(captureScreen(context), bitmapIcon, navigation);
//        return bitmap;
//    }

    /**
     * 获取屏幕截图
     *
     * @param context 上下文
     * @return 屏幕截图bitmap
     */
    public Bitmap captureScreen(Activity context) {
        View cv = context.getWindow().getDecorView();
        cv.setDrawingCacheEnabled(true);
        cv.buildDrawingCache();
        Bitmap bmp = cv.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        bmp.setHasAlpha(false);
        bmp.prepareToDraw();
        return bmp;
    }

    /**
     * 修改bitmap尺寸
     *
     * @param bm        原bitmap
     * @param newWidth  新bitmap宽
     * @param newHeight 新bitmap高
     * @return 新bitmap
     */
    public Bitmap setImgSize(Bitmap bm, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return newbm;
    }

    /**
     * bitmap拼接
     *
     * @param first  第一张
     * @param second 第二张
     * @return 拼接后的bitmap
     */
    public Bitmap addBitmap(Bitmap first, Bitmap second, int navigationHeight) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight() - navigationHeight;
        Bitmap result = Bitmap.createBitmap(width, height, first.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight() - navigationHeight, null);
        return result;
    }
}
