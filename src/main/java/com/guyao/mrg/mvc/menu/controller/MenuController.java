package com.guyao.mrg.mvc.menu.controller;


import com.guyao.mrg.common.base.BaseController;
import com.guyao.mrg.common.base.ZTree;
import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.guyao.mrg.common.result.AjaxResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return PREFIX + "menu_list";
    }


    @PostMapping("list")
    @ResponseBody
    public List<Menu> menuList(Menu menu) {
        return menuService.selectMenuList(menu);
    }

    @GetMapping(value = {"form/{id}","form"})
    public String form(@PathVariable(name = "id",required = false)String id, Model model) {
        model.addAttribute("menu", StringUtils.isNotEmpty(id)?menuService.getById(id):new Menu());
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


    @PostMapping("saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid Menu menu) {
        return AjaxResult.ok(menuService.saveOrUpdate(menu));
    }

    @PostMapping("delete")
    @ResponseBody
    public AjaxResult delete(@RequestParam("ids") String ids) {
        return AjaxResult.ok(menuService.delete(ids));
    }

}
