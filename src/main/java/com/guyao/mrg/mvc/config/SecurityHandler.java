package com.guyao.mrg.mvc.config;

import com.guyao.mrg.base.MrGConstant;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.guyao.mrg.mvc.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

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


    /**
     * 记录登陆时间和次数
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response , authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write("{\"status\":\"0\",\"msg\":\"登录成功\"}");
            writer.flush();
            writer.close();
            userService.updateUserInfo();
        };
    }


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
