package com.guyao.mrg.mvc.security;

import com.guyao.mrg.base.MrGConstant;
import com.guyao.mrg.mvc.utils.RSAUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.security.PrivateKey;
import java.util.Base64;

/**
 * @author guyao
 * @date 2019/10/21 10:45 上午
 */
public class LoginAuthenticationProvider extends DaoAuthenticationProvider {

    private HttpSessionSessionStrategy sessionSessionStrategy = new HttpSessionSessionStrategy();

    public LoginAuthenticationProvider(UserDetailsService userDetailsService) {
        this.setUserDetailsService(userDetailsService);
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            logger.debug("验证失败：未提供认证信息");

            throw new BadCredentialsException(messages.getMessage(
                    "自定义认证1",
                    "身份认证失败：未输入用户名或密码"));
        }

        String presentedPassword = authentication.getCredentials().toString();
        //密码解密
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        String privateKeyStr = (String)sessionSessionStrategy.getAttribute(new ServletWebRequest(request), MrGConstant.PRIVATEKEY);
        //清空密钥
        sessionSessionStrategy.removeAttribute(new ServletWebRequest(request), MrGConstant.PRIVATEKEY);
        //获取输入的原始密码
        PrivateKey privateKey = RSAUtils.string2Privatekey(privateKeyStr);
        byte[] passwrodBytes = RSAUtils.privateDecrypt(javax.xml.bind.DatatypeConverter.parseBase64Binary(presentedPassword), privateKey);
        String originalPWD = new String(passwrodBytes);
        if (!new BCryptPasswordEncoder().matches(originalPWD, userDetails.getPassword())) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(messages.getMessage(
                    "自定义信息2",
                    "身份认证失败：密码不匹配"));
        }
    }
}
