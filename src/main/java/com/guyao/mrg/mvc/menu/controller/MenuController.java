package com.guyao.mrg.mvc.menu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.base.ZTree;
import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.guyao.mrg.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final String PREFIX = "modules/menu/";

    @GetMapping("")
    public String menu() {
        return PREFIX + "/menu_list";
    }


    @PostMapping("list")
    @ResponseBody
    public PageResult menuList(Page page) {
        IPage pages = menuService.page(page);
        return PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

    @GetMapping("form/{id}")
    public String form(@PathVariable("id") String id, Model model) {
        Menu menu = null;
        if(StringUtils.isNotEmpty(id)) {
            menu = menuService.getById(id);
        }
        if(menu == null) {
            menu = new Menu();
            menu.setId("0");
        }
        model.addAttribute("menu",menu);
        return PREFIX + "menu_form";
    }

    @GetMapping("menuTree")
    public String menuTree() {
        return PREFIX + "menu_tree";
    }


    @PostMapping("treeData")
    @ResponseBody
    public List<ZTree> treeData() {
        return menuService.getTreeMenu();
    }

}
