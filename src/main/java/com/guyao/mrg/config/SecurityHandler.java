package com.guyao.mrg.config;

import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.guyao.mrg.common.result.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author guyao
 * @date 2019/9/18 11:05 AM
 */
@Configuration
@Slf4j
public class SecurityHandler {



    @Autowired
    private IUserService userService;


    /**
     * 未登录,返回登录页面
     *
     * @return {@link AuthenticationEntryPoint}
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            log.info("----------------------------------------------------------");
            log.info("未登录,或登录过期");
            log.info("----------------------------------------------------------");
            response.sendRedirect("/login");
        };
    }

    //未配置
    public SessionInformationExpiredStrategy sessionInformationExpiredStrategy() {
        return new SessionInformationExpiredStrategy() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
                event.getRequest().getSession();
                redirectStrategy.sendRedirect(event.getRequest(),event.getResponse(),"/login?msg=expired");
            }
        };
    }


    /**
     * 不拦截logout和login?msg=expired
     * @return
     */
    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new InvalidSessionStrategy() {
            private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            @Override
            public void onInvalidSessionDetected(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
                req.getSession();
                String queryString = req.getQueryString();
                String requestURI = req.getRequestURI();
                if(SecurityConfig.LOGINURL.equals(requestURI)) {
                    if("msg=expired".equals(queryString)) {
                        redirectStrategy.sendRedirect(req, res, SecurityConfig.EXPIREDURL);
                    }else {
                        redirectStrategy.sendRedirect(req, res, SecurityConfig.LOGINURL);
                    }
                    return;
                }
                redirectStrategy.sendRedirect(req, res, SecurityConfig.INVALIDURL);
            }
        };

    }


    /**
     * 记录登陆时间和次数
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response , authentication) -> {
            String status = AjaxResult.LOGIN_SUCCESS_STATUS;
            String msg = AjaxResult.LOGIN_SUCCESS_MESSGAGE;
            RequestCache requestCache = new HttpSessionRequestCache();
            SavedRequest savedRequest = requestCache.getRequest(request, response);
            if(savedRequest != null && StringUtils.isNotEmpty(savedRequest.getRedirectUrl())) {
                status = AjaxResult.REDIRECT;
                msg = savedRequest.getRedirectUrl();
            }
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"status\":\""+status+"\",\"msg\":\""+msg+"\"}");
            writer.flush();
            writer.close();
            userService.updateUserInfo();
        };
    }


    /**
     * 登陆失败返回
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request,response,exception) -> {
            log.info("=======================================================");
            log.info("{}:{}",request.getParameter(MrGConstant.USERNAME),exception.getMessage());
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write("{\"status\":\"1\",\"msg\":\"" + exception.getMessage() + "\"}");
            out.flush();
            out.close();
            log.info("=======================================================");
        };
    }

}
