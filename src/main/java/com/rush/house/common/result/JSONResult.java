package com.rush.house.common.result;


import com.alibaba.fastjson.JSON;
import com.rush.house.common.constant.ResultConst;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * JSONResult : Response JSONResult for RESTful,封装返回JSON格式的数据
 *
 * @author StarZou
 * @since 2014年5月26日 上午10:51:46
 */

public class JSONResult<T> extends Result {

    private static final long serialVersionUID = 7880907731807860636L;

    /**
     * 数据
     */
    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JSONResult() {
        super();
    }

    /**
     * 自定义返回的结果
     *
     * @param data
     * @param message
     * @param success
     */
    public JSONResult(T data, String message, boolean success) {
        this.setData(data);
        super.setMessage(message);
        super.setSuccess(success);
    }

    /**
     * 成功返回数据和消息
     *
     * @param data
     * @param message
     */
    public JSONResult(T data, String message) {
        this.setData(data);
        super.setMessage(message);
        super.setSuccess(true);
    }

    private void setDataWithDeal (T data) {
        if (data != null) {
            String dataStr = JSON.toJSONString(data);
            Pattern pattern = Pattern.compile("[\\d]{19}");
            Matcher matcher = pattern.matcher(dataStr);
            while (matcher.find()) {
                String re = matcher.group();
                dataStr = dataStr.replaceAll(re, "\"" + re + "\"");
            }
            if (dataStr.startsWith("{")) {
                data = (T) JSON.parseObject(dataStr);
            } else if (dataStr.startsWith("[")) {
                data = (T) JSON.parseArray(dataStr);
            }
            this.setData(data);
        } else {
            this.setData(data);
        }
    }

    /**
     * 成功返回数据
     *
     * @param data
     */
    public JSONResult(T data) {
        this.setData(data);
        super.setSuccess(true);
    }

    public JSONResult (boolean success, String message) {
        super.setMessage(message);
        super.setSuccess(success);
        super.setStatusCode(success ? ResultConst.STATUS_CODE_SUCCESS : ResultConst.STATUS_CODE_FAILED);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}