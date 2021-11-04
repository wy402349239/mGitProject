package com.project.git.com.gitproject.svg;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.Log;

import java.math.BigInteger;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/6/17 8:05 下午
 */
public class ColorUtil {

    /**
     * 计算两个颜色值的中间值
     *
     * @param startColor 开始的颜色值
     * @param endColor   结束的颜色值
     * @param prop       比例
     * @return 结果颜色值
     */
    public static String getCenter(String startColor, String endColor, float prop) {
        if (prop <= 0) {
            return startColor;
        } else if (prop >= 1f) {
            return endColor;
        }
        if (startColor.contains("#") && endColor.contains("#") && startColor.length() == endColor.length() && (endColor.length() == 7 || endColor.length() == 9)) {
            String tempColor1 = startColor.replace("#", "");
            if (tempColor1.length() == 6) {
                tempColor1 = "FF" + tempColor1;
            }
            String tempColor2 = endColor.replace("#", "");
            if (tempColor2.length() == 6) {
                tempColor2 = "FF" + tempColor2;
            }
            StringBuilder stringBuffer = new StringBuilder();
            stringBuffer.append("#");
            int count = tempColor1.length() / 2;   //0    1    2    3
            for (int i = 0; i < count; i++) {      //0-1  2-3  4-5  6-7
                int startIndex = i * 2;
                int endIndex = startIndex + 2;
                int start;
                int end;
                if (endIndex >= tempColor1.length()) {
                    start = changeHex2Int(tempColor1.substring(startIndex));
                    end = changeHex2Int(tempColor2.substring(startIndex));
                } else {
                    start = changeHex2Int(tempColor1.substring(startIndex, endIndex));
                    end = changeHex2Int(tempColor2.substring(startIndex, endIndex));
                }
                String tempResult = (int) ((end - start) * prop + start) + "";
                stringBuffer.append(changeInt2Hex(tempResult));
            }
            return stringBuffer.toString();
        }
        return "#ffffffff";
    }

    private static int changeHex2Int(String temp) {
        BigInteger srch = new BigInteger(temp, 16);
        return Integer.valueOf(srch.toString());
    }

    private static String changeInt2Hex(String temp) {
        BigInteger srch = new BigInteger(temp, 10);
        String result = Integer.toHexString(Integer.parseInt(srch.toString()));
        if (TextUtils.isEmpty(result)) {
            result = "00";
        }
        if (result.length() == 1) {
            result = "0" + result;
        }
        return result;
    }

    private static String resultColor = "";

    /**
     * 根据图片加载的drawable，拾取左上角第一个像素作为背景
     *
     * @param drawable 源
     * @return 拾取出来的颜色
     */
    public static synchronized String getBgColor(Drawable drawable) {
        try {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            int pixel = bitmap.getPixel(2, 2);
            return toArgb(pixel);
        } catch (Throwable e) {
            return resultColor;
        }
    }

    /**
     * color转String 16进制
     *
     * @param color int颜色
     * @return 16进制颜色
     */
    public static String toArgb(int color) {
        String A, R, G, B;
        StringBuffer sb = new StringBuffer();
        A = Integer.toHexString(Color.alpha(color));
        R = Integer.toHexString(Color.red(color));
        G = Integer.toHexString(Color.green(color));
        B = Integer.toHexString(Color.blue(color));
        //判断获取到的R,G,B值的长度 如果长度等于1 给R,G,B值的前边添0
        A = A.length() == 1 ? "0" + A : A;
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("#");
        sb.append(A);
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }

    /**
     * color转String 16进制
     *
     * @param color int颜色
     * @return 16进制颜色
     */
    public static String toRgb(int color) {
        String R, G, B;
        StringBuilder sb = new StringBuilder();
        R = Integer.toHexString(Color.red(color));
        G = Integer.toHexString(Color.green(color));
        B = Integer.toHexString(Color.blue(color));
        //判断获取到的R,G,B值的长度 如果长度等于1 给R,G,B值的前边添0
        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;
        sb.append("#");
        sb.append(R);
        sb.append(G);
        sb.append(B);
        return sb.toString();
    }

    public static GradientDrawable getGradientBg(int color) {
        int centerColor = Color.parseColor(getCenter(toArgb(color), toArgb(Color.TRANSPARENT), 0.1f));
        int endColor = Color.TRANSPARENT;
        int[] colors = new int[]{color, color, endColor};
        return new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
    }
}
