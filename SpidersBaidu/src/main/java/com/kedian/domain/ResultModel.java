package com.kedian.domain;

import java.util.Map;

/**
 * @author happy
 * @version 0
 * @Package com.kedian.domain
 * @date 2018/6/11 15:20
 * @Description:
 */
public class ResultModel {
    private int errcode;// 返回码
    private String errmsg;// 返回消息
    private Map<String, Object> data;// 数据源

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
