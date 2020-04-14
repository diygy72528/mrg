package com.guyao.mrg.common.base;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import static com.guyao.mrg.common.base.MrG.LOGO;

/**
 * @author guyao
 * @date 2019/10/15 4:54 下午
 */
@Data
@ConfigurationProperties(value = LOGO)
public class MrG {
    public static final String LOGO = "mrg";

    private final Project project = new Project();

    private final KaptchaProperties kaptcha = new KaptchaProperties();
    public static class Project {
        /**
         * 名称
         */
        private String name = MrGDefault.Project.name;
        /**
         * 版本
         */
        private String version = MrGDefault.Project.version;
        /**
         * 技术支持
         */
        private String poweredBy = MrGDefault.Project.poweredBy;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getPoweredBy() {
            return poweredBy;
        }

        public void setPoweredBy(String poweredBy) {
            this.poweredBy = poweredBy;
        }
    }

    @Data
    public class KaptchaProperties {
        /**
         * 是否有边框
         */
        public String border = "no";
        /**
         * 图像宽度
         */
        public String imageWidth = "240";
        /**
         * 图像高度
         */
        public String imageHeight = "80";
        /**
         * 文本生产者字体颜色
         */
        public String textProducerFontColor = "black";
        /**
         * 文字制作者字体大小
         */
        public String textProducerFontSize = "60";
        /**
         * 文字制片人实现
         */
        public String textProducerImpl = "com.google.code.kaptcha.impl.ShadowGimpy";
        /**
         * 文本生产者字符长度
         */
        public String textProducerCharLength = "5";
        /**
         * 文本生产者字体名称
         */
        public String textProducerFontNames = "宋体,楷体,微软雅黑";

    }

}
