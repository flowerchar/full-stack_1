package com.example.spingboot.POJO;

import lombok.Data;

@Data
public class R {
    private String code;
    private String msg;
    private Object data;

    public static final String SUCCESS_CODE = "200";
    public static final String ERROR_CODE = "-1";
    public static final String SUCCESS_MSG = "请求成功";

    public static R success() {
        R r = new R();
        r.setCode(SUCCESS_CODE);
        r.setMsg(SUCCESS_MSG);
        return r;
    }

    public static R success(Object data) {
        R r = success();
        r.setData(data);
        return r;
    }

    public static R error(String msg) {
        R r = new R();
        r.setCode(ERROR_CODE);
        r.setMsg(msg);
        return r;
    }
}

