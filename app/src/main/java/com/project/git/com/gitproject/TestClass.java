package com.project.git.com.gitproject;

import java.io.Serializable;

/**
 * @author jx_wy
 * @date 5:59 PM 2019/2/28
 * @email wangyu@51dianshijia.com
 * @description
 */
public class TestClass implements Serializable {

    private static final long serialVersionUID = 9143355710385296653L;

    private String str = "";

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }
}
