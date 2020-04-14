package com.guyao.mrg.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.guyao.mrg.common.base.MrG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.google.code.kaptcha.Constants.*;
import static com.google.code.kaptcha.Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES;

/**
 * @author guyao
 * @date 2019/10/18 2:57 下午
 */
@Configuration
public class KaptchaConfig {


    @Autowired
    private MrG mrG;

    @Bean
    public Producer producer() {
        MrG.KaptchaProperties kaptcha = mrG.getKaptcha();
        DefaultKaptcha captchaProducer = new DefaultKaptcha();
        java.util.Properties properties = new java.util.Properties();
        properties.setProperty(KAPTCHA_BORDER, kaptcha.getBorder());
        properties.setProperty(KAPTCHA_IMAGE_WIDTH, kaptcha.getImageWidth());
        properties.setProperty(KAPTCHA_IMAGE_HEIGHT, kaptcha.getImageHeight());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_COLOR, kaptcha.getTextProducerFontColor());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_SIZE, kaptcha.getTextProducerFontSize());
        properties.setProperty(KAPTCHA_OBSCURIFICATOR_IMPL, kaptcha.getTextProducerImpl());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, kaptcha.getTextProducerCharLength());
        properties.setProperty(KAPTCHA_TEXTPRODUCER_FONT_NAMES, kaptcha.getTextProducerFontNames());
        Config config = new Config(properties);
        captchaProducer.setConfig(config);
        return captchaProducer;
    }

}
