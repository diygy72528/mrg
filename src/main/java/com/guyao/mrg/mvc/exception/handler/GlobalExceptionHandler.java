package com.guyao.mrg.mvc.exception.handler;

import com.guyao.mrg.result.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    /**
     * URL
     */
    private static final String URL = "url";
    /**
     * 错误原因
     */
    private static final String EXCEPTION = "exception";
    /**
     * 消息信息
     */
    private static final String MESSAGE = "message";
    /**
     * 堆栈信息
     */
    private static final String STACK_TRACE = "stackTrace";

    private static final String INTERNALEXCEPTION_PAGE = "error/500";

    @ExceptionHandler(Exception.class)
    public Object defaultExceptionHandler(HttpServletRequest request, Exception e) {
        if(isAjaxRequest(request)) {
            return AjaxResult.builder()
                    .msg(StringUtils.isEmpty(e.getMessage())? Arrays.toString(e.getStackTrace()):e.getMessage())
                    .status(AjaxResult.EXCEPTION_STATUS)
                    .build();
        }else {
            return getModel(e,request);
        }
    }


    private boolean isAjaxRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        String header = request.getHeader("X-Requested-With");
        String acceptHeader = request.getHeader("Accept");
        return ((header != null && header.equals("XMLHttpRequest")) || (header != null && contentType.contains("application/json"))
        || (acceptHeader != null && acceptHeader.contains("applicaiton/json")));
    }

    private ModelAndView getModel(Exception e,HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(EXCEPTION, e);
        modelAndView.addObject(URL, request.getRequestURL());
        modelAndView.addObject(MESSAGE, e.getMessage());
        modelAndView.addObject(STACK_TRACE, e.getStackTrace());
        modelAndView.setViewName(INTERNALEXCEPTION_PAGE);
        return modelAndView;
    }


}
