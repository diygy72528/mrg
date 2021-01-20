package com.guyao.mrg.mvc.role.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.BaseController;
import com.guyao.mrg.common.base.ZTree;
import com.guyao.mrg.common.result.AjaxResult;
import com.guyao.mrg.common.result.PageResult;
import com.guyao.mrg.common.utils.TreeDataUtils;
import com.guyao.mrg.mvc.role.entity.Role;
import com.guyao.mrg.mvc.role.service.IRoleService;
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
 * @since 2019-07-18
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private static final String PREFIX = "modules/role/";

    @Autowired
    private IRoleService roleService;


    @GetMapping("")
    public String role() {
        return PREFIX + "role_list";
    }


    @PostMapping("page")
    @ResponseBody
    public PageResult page(Page page, Role role) {
        Page result = roleService.page(page, new QueryWrapper<>(role));
        return PageResult.builder().rows(result.getRecords()).total(result.getTotal()).build();
    }

    @GetMapping(value = {"form","form/{id}"})
    public String form(@PathVariable(value = "id",required = false) String id, Model model) {
        model.addAttribute("role",id == null?new Role():roleService.getById(id));
        model.addAttribute("menuIds",id == null? null:roleService.selectMenuIdsByRoleId(id));
        return PREFIX + "role_form";
    }

    @PostMapping("treeData")
    @ResponseBody
    public List<ZTree> treeData() {
        return TreeDataUtils.initTreeData(roleService.list());
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid Role role, @RequestParam("menuIds")String menuIds) {
        return AjaxResult.ok(roleService.saveOrUpdate(role,menuIds));
    }

    @GetMapping("roleUserList")
    public String roleUserList(@RequestParam(value = "id") String id, Model model) {
        model.addAttribute("id",id);
        return PREFIX + "role_user_list";
    }


    @PostMapping("addRoleUserRela")
    @ResponseBody
    public AjaxResult addUserRoleRela(@RequestParam("roleId")String roleId, @RequestParam(value = "userIds",required = false)String userIds) {
        roleService.addUserRoleRela(roleId, userIds);
        return AjaxResult.ok();
    }

    @PostMapping("delete")
    @ResponseBody
    public AjaxResult delete(@RequestParam("ids") String ids) {
        return AjaxResult.ok(roleService.deleteById(ids));
    }


}
