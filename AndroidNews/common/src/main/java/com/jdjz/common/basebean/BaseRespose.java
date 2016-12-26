package com.jdjz.common.basebean;

import java.io.Serializable;

/**
 * des:封装服务器返回数据
 * Created by tchl on 2016-11-23.
 */
public class BaseRespose<T> implements Serializable {
    public String code;
    public String msg;

    public T data;

    public boolean success() {
        return "1".equals(code);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ",msg='" + msg + '\'' +
                ",data=" + data +
                '}';
    }
}
