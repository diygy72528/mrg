package com.guyao.mrg.mvc.dict.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.BaseController;
import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.common.cache.DictCache;
import com.guyao.mrg.common.result.AjaxResult;
import com.guyao.mrg.common.result.PageResult;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.guyao.mrg.mvc.dict.service.IDictTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * <p>
 *  字典
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Controller
@RequestMapping("/dictType")
public class DictTypeController extends BaseController {

    private final String PREFIX = "modules/dict/";

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private IDictTypeService dictTypeService;

    @Autowired
    private DictCache cache;


    @GetMapping
    public String dictType() {
        return PREFIX + "dict_type";
    }

    @PostMapping("list")
    @ResponseBody
    public PageResult page(Page page,DictType dictType) {
        Page pages = dictTypeService.page(page,dictType);
        return pages == null?null:PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

    @GetMapping(value = {"form/{id}","form"})
    public String form(@PathVariable(value = "id",required = false) String id, Model model) {
        model.addAttribute("dictType", StringUtils.isNotEmpty(id) ? dictTypeService.getById(id) : new DictType());
        return PREFIX + "dict_type_form";
    }

    @GetMapping("checkUniqueName")
    @ResponseBody
    public String checkUniqueName(@RequestParam("dictTypeEnName") String dictTypeEnName, String id) {
        return "{\"valid\":"+dictTypeService.checkUniqueName(dictTypeEnName, id)+"}";
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid DictType dictType) {
        boolean result = dictTypeService.saveOrUpdate(dictType);
        template.convertAndSend(MrGConstant.RABBIT_DICT_EXCHANGE, MrGConstant.RABBIT_DICT_UPDATE,dictType.getId());
        return AjaxResult.ok(result);
    }

    @PostMapping("delete")
    @ResponseBody
    public AjaxResult delete(@RequestParam String ids) {
        boolean result = dictTypeService.delete(ids);
        template.convertAndSend(MrGConstant.RABBIT_DICT_EXCHANGE, MrGConstant.RABBIT_DICT_DELETE,ids);
        return AjaxResult.ok(result);
    }

    @GetMapping("typeDataList")
    public String typeDataList(@RequestParam("id") String id ,Model model) {
        model.addAttribute("id",id);
        return PREFIX + "type_data_list";
    }

    @GetMapping("getValue")
    @ResponseBody
    public Object getValue(@RequestParam("name")String name, @RequestParam("value")String value) {
        return JSON.toJSONString(cache.getName(name, value));
    }

}
