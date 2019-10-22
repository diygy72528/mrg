package com.guyao.mrg.result;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author guyao
 * @date 2019/10/22 10:26 上午
 */
@Data
public class AjaxResult {
    //0为成功，1为失败。
    private String status;
    private String msg;
    private Object result;

    private AjaxResult(String status, String msg, Object result) {
        this.status = status;
        this.msg = msg;
        this.result = result;
    }

    public static AjaxResultBuilder builder() {
        return new AjaxResultBuilder();
    }

    public static AjaxResult ok(Object result) {
        return new AjaxResult("0","success",result);
    }

    public static AjaxResult ok() {
        return new AjaxResult("0","success","");
    }

    public static AjaxResult failed() {
        return new AjaxResult("1","failed","");
    }

    public static class AjaxResultBuilder {
        private String status;
        private String msg;
        private Object result;

        public AjaxResultBuilder status(String status) {
            this.status = status;
            return this;
        }

        public AjaxResultBuilder msg(String msg) {
            this.msg = msg;
            return this;
        }

        public AjaxResultBuilder result(Object result) {
            this.result = result;
            return this;
        }



        public AjaxResult build() {
            if(StringUtils.isEmpty(status)){
                this.status = "0";
            }
            if(StringUtils.isEmpty(msg)) {
                this.msg = "success";
            }
            return new AjaxResult(status,msg,result);
        }
    }
}
