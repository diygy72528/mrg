package com.guyao.mrg;

import com.guyao.mrg.common.utils.DefaultPropertyUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.guyao.mrg.**.**.mapper")
public class MrgApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MrgApplication.class);
        DefaultPropertyUtil.addDefaultProperties(app);
        app.run(args);
    }

}
