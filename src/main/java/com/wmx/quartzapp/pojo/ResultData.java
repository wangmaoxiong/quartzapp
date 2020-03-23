package com.wmx.quartzapp.pojo;

import com.wmx.quartzapp.enums.ResultCode;

import java.io.Serializable;

/**
 * 页面返回值对象，用于封装返回数据
 *
 * @author wangmaoxiong
 */
public class ResultData implements Serializable {
    private Integer code;
    private String message;
    private Object data;

    public ResultData(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public ResultData(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
