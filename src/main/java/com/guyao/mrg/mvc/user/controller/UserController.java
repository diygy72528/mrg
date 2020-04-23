package com.guyao.mrg.mvc.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.BaseController;
import com.guyao.mrg.common.result.PageResult;
import com.guyao.mrg.mvc.user.entity.User;
import com.guyao.mrg.mvc.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public PageResult list(Page page, User user) {
        return PageResult.newResult(userService.page(page,user));
    }

    @PostMapping("listByRoleId")
    @ResponseBody
    public PageResult listByRoleId(@RequestParam(value = "roleId",required = true)String roleId, Page page) {
        return PageResult.newResult(userService.listByRoleId(page,roleId));
    }

}
