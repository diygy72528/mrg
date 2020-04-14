package com.guyao.mrg.common.result;

import lombok.Data;

/**
 * @author guyao
 * @date 2019/10/22 10:26 上午
 */
@Data
public class AjaxResult {

    public static final String REDIRECT = "redirect";
    public static final String SUCCESS_STATUS = "0";
    public static final String LOGIN_SUCCESS_MESSGAGE = "登陆成功";
    public static final String FAILED_STATUS = "1";
    public static final String FAILED_MESSAGE = "登陆失败";
    public static final String EXCEPTION_STATUS = "2";
    public static final String ACCESS_DENIED = "3";
    public static final String LOGIN_SUCCESS_STATUS = "4";
    public static final String LOGIN_FAILED_STATUS = "5";
    public static final String WRAN_STATUS = "6";
    public static final String ACTION_SUCCESSED_MSG = "操作成功！";
    public static final String ACTION_FAILED_MSG = "操作失败！";

    private String status;
    private String msg;
    private Object result;

    private AjaxResult(String status, String msg, Object result) {
        this.status = status;
        this.msg = msg;
        this.result = result;
    }


    public static AjaxResult ok(Object result) {
        return new AjaxResult(SUCCESS_STATUS,ACTION_SUCCESSED_MSG,result);
    }

    public static AjaxResult ok() {
        return new AjaxResult(SUCCESS_STATUS,ACTION_SUCCESSED_MSG,"");
    }

    public static AjaxResult failed(String msg) {
        return new AjaxResult(FAILED_STATUS,msg,"");
    }

    public static AjaxResult exception(String msg) {
        return new AjaxResult(EXCEPTION_STATUS,msg,"");
    }

    public static AjaxResult warn(String msg) {
        return new AjaxResult(WRAN_STATUS,msg,"");
    };

}
