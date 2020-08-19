package com.project.git.com.gitproject.canvas;

import android.graphics.PorterDuff;

/**
 * created by wangyu on 2020/8/18 4:37 PM
 * description:
 */
public class XferModeEvent {

    PorterDuff.Mode mode;

    public XferModeEvent(PorterDuff.Mode mode) {
        this.mode = mode;
    }

    public PorterDuff.Mode getMode() {
        return mode;
    }
}