package com.guyao.mrg.mvc.security;

import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.common.exception.ValidKaptchaException;
import com.guyao.mrg.mvc.manage.entity.Kaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author guyao
 * @date 2019/10/18 3:58 下午
 */
public class KaptcherFilter extends AbstractAuthenticationProcessingFilter {


    public KaptcherFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        if ("/login".equalsIgnoreCase(request.getRequestURI()) && "post".equalsIgnoreCase(request.getMethod())) {
            try{
                validateKaptcha(request);
            }catch (ValidKaptchaException e) {
                res.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = res.getWriter();
                writer.write("{\"status\":\"kaptcha\",\"msg\":\""+e.getMessage()+"\"}");
                return;
            }
        }
        chain.doFilter(req, res);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        return null;
    }

    private void validateKaptcha(HttpServletRequest request) {
        String key = request.getParameter(MrGConstant.SESSIONKEY);
        try {
            key = ServletRequestUtils.getStringParameter(request, MrGConstant.SESSIONKEY);
        } catch (ServletRequestBindingException e) {
            e.printStackTrace();
        }
        HttpSessionSessionStrategy sessionSessionStrategy = new HttpSessionSessionStrategy();
        Object attribute = sessionSessionStrategy.getAttribute(new ServletWebRequest(request), MrGConstant.SESSIONKEY);
        //清空缓存
        sessionSessionStrategy.removeAttribute(new ServletWebRequest(request), MrGConstant.SESSIONKEY);
        if (!(attribute instanceof Kaptcha)) {
            throw new ValidKaptchaException("验证码缓存异常");
        }
        Kaptcha kaptcha = (Kaptcha) attribute;
        if (StringUtils.isEmpty(key)) {
            throw new ValidKaptchaException("验证码不存在");
        }
        if (kaptcha.isExpired()) {
            throw new ValidKaptchaException("验证码已过期");
        }
        if (!kaptcha.getCode().equalsIgnoreCase(key)) {
            throw new ValidKaptchaException("验证码输入错误");
        }
    }
}
