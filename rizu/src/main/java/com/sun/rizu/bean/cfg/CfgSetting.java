package com.sun.rizu.bean.cfg;

/**
 * Created by qxh on 2015/11/19.
 */
public class CfgSetting {
    private String cfg_key = "";
    private String des = "";
    private String val = "";
    private String ext_val = "";
    private int sort = 0;

    public String getCfg_key() {
        return cfg_key;
    }

    public void setCfg_key(String cfg_key) {
        this.cfg_key = cfg_key;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public String getExt_val() {
        return ext_val;
    }

    public void setExt_val(String ext_val) {
        this.ext_val = ext_val;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
