package com.guyao.mrg.mvc.menu.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.base.ZTree;
import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.guyao.mrg.result.AjaxResult;
import com.guyao.mrg.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

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
    private MessageSource messageSource;

    @Autowired
    private IMenuService menuService;

    private final String PREFIX = "modules/menu/";

    @GetMapping("")
    public String menu() {
        return PREFIX + "menu_list";
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


    @PostMapping("saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid Menu menu, BindingResult result) {
        if(result.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            Locale locale = LocaleContextHolder.getLocale();
            for (FieldError error : result.getFieldErrors()) {
                sb.append(error.getField()).append(":").append(messageSource.getMessage(error,locale)).append(";");
            }
            return AjaxResult.builder().msg(AjaxResult.ACTION_FAILED_MSG+sb.toString()).status(AjaxResult.FAILED_STATUS).build();
        }
        menuService.saveOrUpdate(menu);
        return AjaxResult.builder().msg(AjaxResult.ACTION_SUCCESSED_MSG).status(AjaxResult.SUCCESS_STATUS).build();
    }

}
