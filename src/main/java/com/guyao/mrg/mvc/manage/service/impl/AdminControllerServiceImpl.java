package com.guyao.mrg.mvc.manage.service.impl;

import com.google.code.kaptcha.Producer;
import com.guyao.mrg.base.MrGConstant;
import com.guyao.mrg.mvc.manage.entity.Kaptcha;
import com.guyao.mrg.mvc.manage.service.AdminControllerService;
import com.guyao.mrg.mvc.utils.ImageUtils;
import com.guyao.mrg.mvc.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.security.KeyPair;
import java.time.LocalDateTime;

/**
 * @author guyao
 * @date 2019/10/18 2:42 下午
 */
@Service
@Transactional
public class AdminControllerServiceImpl implements AdminControllerService {

    @Autowired
    private HttpSessionSessionStrategy sessionSessionStrategy;

    @Autowired
    private Producer producer;

    /**
     * 生成验证码
     */
    @Override
    public void generateKaptcha(HttpServletRequest request, HttpServletResponse response) {
        //生成方式1：工具生成
        /*String text = producer.createText();
        BufferedImage image = producer.createImage(text);
        Kaptcha kaptcha = new Kaptcha(text, image, LocalDateTime.now().plusSeconds(60));*/
        //生成方式2：手动生成
        Kaptcha kaptcha = ImageUtils.createKaptcha();
        sessionSessionStrategy.setAttribute(new ServletWebRequest(request), MrGConstant.SESSIONKEY,kaptcha);
        try{
            ImageIO.write(kaptcha.getImage(),"JPEG",response.getOutputStream());
        }catch (Exception e) {

        }
    }


    /**
     * 生成钥匙对
     */
    @Override
    public String generateKeys() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        KeyPair keyPair = RSAUtils.getKeyPair();
        sessionSessionStrategy.setAttribute(new ServletWebRequest(request),MrGConstant.PRIVATEKEY,RSAUtils.getPrivateKey(keyPair));
        return RSAUtils.getPublicKey(keyPair);
    }
}
