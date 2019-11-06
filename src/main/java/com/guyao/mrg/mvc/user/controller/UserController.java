package com.guyao.mrg.mvc.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.guyao.mrg.result.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    @GetMapping("")
    public String list() {
        return  "modules/user/user_list";
    }

    @PostMapping("list")
    @ResponseBody
    public PageResult list(Page page, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String key = params.nextElement();
            String[] values = request.getParameterValues(key);
            if (values.length > 0 && StringUtils.isNotEmpty(values[0])) {
                if (values.length == 1) {
                    String val = values[0].trim();
                    map.put(key, val);
                } else {
                    map.put(key, values);
                }
            } else {
                map.put(key, values[0]);
            }
        }
        System.out.println(map);
        String current = request.getParameter("current");
        Map<String, String[]> parameterMap =
                request.getParameterMap();
        System.out.println(current);
        System.out.println(parameterMap.values());
        System.out.println("size:"+page.getSize()+";current:"+page.getCurrent());
        IPage pages = userService.page(page);
        return PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

}
