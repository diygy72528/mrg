package com.guyao.mrg;

import com.guyao.mrg.base.MrG;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan(value = "com.guyao.mrg.**.**.mapper")
@EnableConfigurationProperties(MrG.class)
public class MrgApplication {

    public static void main(String[] args) {
        SpringApplication.run(MrgApplication.class, args);
    }

}
