package com.guyao.mrg.mvc.menu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.mvc.menu.service.IMenuService;
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
 * @since 2019-10-08
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("")
    public String menu() {
        return "modules/menu/menu_list";
    }

    @PostMapping("list")
    @ResponseBody
    public PageResult menuList(Page page) {
        IPage pages = menuService.page(page);
        return PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

}
