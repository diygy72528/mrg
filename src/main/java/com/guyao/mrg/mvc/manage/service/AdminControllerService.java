package com.guyao.mrg.mvc.manage.service;

import com.guyao.mrg.mvc.manage.entity.Kaptcha;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * @author guyao
 * @date 2019/10/18 2:42 下午
 */
public interface AdminControllerService {

    void generateKaptcha(HttpServletRequest request, HttpServletResponse response);

    String generateKeys();
}
