package com.guyao.mrg.mvc.manage.entity;

import lombok.Data;
import org.apache.tomcat.jni.Local;

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

/**
 * @author guyao
 * @date 2019/10/18 2:44 下午
 */
@Data
public class Kaptcha {
    private String code;
    private BufferedImage image;
    private LocalDateTime expireTime;

    public Kaptcha(String code, BufferedImage image, LocalDateTime expireTime) {
        this.code = code;
        this.image = image;
        this.expireTime = expireTime;
    }

    public boolean isExpired() {
        return expireTime.isBefore(LocalDateTime.now());
    }
}
