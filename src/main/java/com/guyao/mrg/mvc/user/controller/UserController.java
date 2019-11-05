package com.guyao.mrg.mvc.user.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.guyao.mrg.result.PageResult;
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
    public PageResult list(Page page) {
        IPage pages = userService.page(page);
        return PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

}
