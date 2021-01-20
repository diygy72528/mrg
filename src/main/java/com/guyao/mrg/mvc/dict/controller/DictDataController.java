package com.guyao.mrg.mvc.dict.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.BaseController;
import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.common.result.AjaxResult;
import com.guyao.mrg.common.result.PageResult;
import com.guyao.mrg.mvc.dict.entity.DictData;
import com.guyao.mrg.mvc.dict.service.IDictDataService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Controller
@RequestMapping("/dictData")
public class DictDataController extends BaseController {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private IDictDataService dictDataService;

    private final String PREFIX = "modules/dict/";

    @GetMapping
    public String dictType() {
        return PREFIX + "dict_data";
    }

    @PostMapping("list")
    @ResponseBody
    public PageResult page(Page page, DictData dictData) {
        Page pages = dictDataService.page(page,dictData);
        return pages == null?null:PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }

    @GetMapping(value = {"form","form/{id}"})
    public String form(@PathVariable(value = "id",required = false) String id, @RequestParam("typeId")String typeId, Model model) {
        DictData data = StringUtils.isNotEmpty(id) ? dictDataService.getById(id) : new DictData();
        data.setDictTypeId(typeId);
        model.addAttribute("dictData", data);
        return PREFIX + "dict_data_form";
    }

    @PostMapping("listByTypeId")
    @ResponseBody
    public PageResult listByTypeId(@RequestParam("typeId") String id, Page page) {
        return PageResult.newResult(dictDataService.pageByTypeId(id,page));
    }

    @PostMapping("saveOrUpdate")
    @ResponseBody
    public AjaxResult saveOrUpdate(@Valid DictData dictData) {
        boolean result = dictDataService.saveOrUpdate(dictData);
        template.convertAndSend(MrGConstant.RABBIT_DICT_EXCHANGE,MrGConstant.RABBIT_DICT_UPDATE,dictData.getDictTypeId());
        return AjaxResult.ok(result);
    }

    @GetMapping("checkValueUnique")
    @ResponseBody
    public String checkValueUnique(@RequestParam("typeId")String typeId, @RequestParam("value") String value, String id) {
        return "{\"valid\":"+dictDataService.checkUniqueValue(typeId, value, id)+"}";
    }

    @PostMapping("delete")
    @ResponseBody
    public AjaxResult delete(@RequestParam("ids")String ids, @RequestParam("typeId") String typeId) {
        boolean result = dictDataService.delete(ids);
        template.convertAndSend(MrGConstant.RABBIT_DICT_EXCHANGE, MrGConstant.RABBIT_DICT_UPDATE, typeId);
        return AjaxResult.ok(result);
    }


}
