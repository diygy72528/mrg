package com.guyao.mrg.mvc.role.controller;


import com.guyao.mrg.base.BaseController;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@RestController
@RequestMapping("/role")
public class RoleController extends BaseController {
    @RequestMapping("test")
    @ResponseBody
    public String test() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(name);
        return name;
    }

}
