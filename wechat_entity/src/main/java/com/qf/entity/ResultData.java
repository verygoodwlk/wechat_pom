package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> implements Serializable {

    //结果码
    private String code;
    //结果的描述信息
    private String msg;
    //结果的返回值
    private T data;

    /**
     * 返回一个成功的标识
     * @return
     */
    public static <T> ResultData<T> createSuccResultData(T t){
        ResultData resultData = new ResultData();
        resultData.setCode("0000");
        resultData.setMsg(null);
        resultData.setData(t);
        return resultData;
    }

    /**
     * 返回一个失败的标识
     * @return
     */
    public static <T> ResultData<T> createErrorResultData(String code, String msg){
        ResultData resultData = new ResultData();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setData(null);
        return resultData;
    }
}
