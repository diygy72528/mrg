package com.guyao.mrg.mvc.utils;

import com.guyao.mrg.base.MrG;
import com.guyao.mrg.base.MrGDefault;
import javafx.application.Application;
import org.springframework.boot.SpringApplication;

import java.util.HashMap;

/**
 * @author guyao
 * @date 2019/12/25 3:31 下午
 */
public class DefaultPropertyUtil {
    public static void addDefaultProperties(SpringApplication app) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("mrg.project.name", MrGDefault.Project.name);
        properties.put("mrg.project.version", MrGDefault.Project.version);
        properties.put("mrg.project.poweredBy", MrGDefault.Project.poweredBy);
        app.setDefaultProperties(properties);
    }
}
