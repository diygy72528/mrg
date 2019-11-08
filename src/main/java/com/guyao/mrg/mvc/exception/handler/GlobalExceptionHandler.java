package com.guyao.mrg.mvc.exception.handler;

import com.guyao.mrg.base.MrG;
import com.guyao.mrg.result.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 全局异常处理器
 * @author guyao
 * @date 2019/11/7 2:12 下午
 */
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(Exception.class)
    public Object defaultExceptionHandler(HttpServletRequest request, Exception e) {
        if(isAjaxRequest(request)) {
            return AjaxResult.builder()
                    .msg(StringUtils.isEmpty(e.getMessage())? Arrays.toString(e.getStackTrace()):e.getMessage())
                    .status(AjaxResult.FAILED_STATUS)
                    .build();

        }else {
            //todo:完成异常
            new ModelAndView("");
        }
        return null;
    }


    private boolean isAjaxRequest(HttpServletRequest request) {

        return false;
    }


}
