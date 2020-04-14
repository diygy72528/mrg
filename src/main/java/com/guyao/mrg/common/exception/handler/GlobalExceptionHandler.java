package com.guyao.mrg.common.exception.handler;

import com.guyao.mrg.common.exception.WarnException;
import com.guyao.mrg.common.result.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.Locale;

/**
 * 全局异常处理器
 * @author guyao
 * @date 2019/11/7 2:12 下午
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;


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

    private static final String INTERNALEXCEPTION_PAGE = "templates/home/error/500";

    /**
     * 通用异常处理
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Object defaultExceptionHandler(HttpServletRequest request, Exception e) {
        if(isAjaxRequest(request)) {
            return AjaxResult.exception(StringUtils.isEmpty(e.getMessage())? Arrays.toString(e.getStackTrace()):e.getMessage());
        }else {
            return getModel(e,request);
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Object accessDenied(HttpServletRequest request,Exception accessDenied) {
        if(isAjaxRequest(request)) {
            return AjaxResult.exception(StringUtils.isEmpty(accessDenied.getMessage())? Arrays.toString(accessDenied.getStackTrace()):accessDenied.getMessage());
        }else {
            return getModel(accessDenied,request);
        }

    }

    /**
     * 参数绑定异常
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    public Object bindException(BindException e) {
        StringBuilder sb = new StringBuilder();
        Locale locale = LocaleContextHolder.getLocale();
        BindingResult result = e.getBindingResult();
        for (FieldError error : result.getFieldErrors()) {
            sb.append(error.getField()).append(":").append(messageSource.getMessage(error,locale)).append(";");
        }
        return AjaxResult.exception(sb.toString());
    }

    /**
     * 非法操作
     * @param e
     * @return
     */
    @ExceptionHandler(WarnException.class)
    public AjaxResult warnException(WarnException e) {
        return AjaxResult.warn(e.getMessage());
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
