package com.project.git.com.gitproject.waterfall;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * created by wangyu on 2020/11/23 7:53 PM
 * description:
 */
public class TLTextView extends AppCompatTextView {

    /**
     * 最大行数
     */
    private int maxLine = 2;
    /**
     * 右侧菜单按钮预留距离
     */
    private int rightSpace = 0;

    public TLTextView(Context context) {
        super(context);
    }

    public TLTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TLTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getMaxLine() {
        return maxLine;
    }

    public void setMaxLine(int maxLine) {
        this.maxLine = maxLine;
    }

    public int getRightSpace() {
        return rightSpace;
    }

    public void setRightSpace(int rightSpace) {
        this.rightSpace = rightSpace;
    }

    private Paint mPaint;

    public void setTextContent(String content) {
        if (TextUtils.isEmpty(content)) {
            this.setText("");
            return;
        }
        if (mPaint == null) {
            mPaint = getPaint();
        }
        int maxWidth = this.getWidth() - this.getPaddingLeft() - this.getPaddingRight();

    }
}