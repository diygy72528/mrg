package com.guyao.mrg.mvc.user.controller;


import com.guyao.mrg.base.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/list")
    public String list() {
        return  "modules/user/user_list";
    }

}
