package com.guyao.mrg.mvc.config;

import com.guyao.mrg.base.MrG;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author guyao
 * @date 2019/12/25 2:17 下午
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(MrG.class)
public class MrGConfig {
    private final MrG mrg;

    public MrGConfig(MrG mrg) {
        this.mrg = mrg;
    }
}
