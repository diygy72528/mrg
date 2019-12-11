package com.guyao.mrg.mvc.dict.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.base.BaseController;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.guyao.mrg.mvc.dict.service.IDictTypeService;
import com.guyao.mrg.result.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


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
    private IDictTypeService dictTypeService;


    @GetMapping
    public String dictType() {
        return PREFIX + "dict_type";
    }

    @PostMapping("list")
    @ResponseBody
    public PageResult page(Page page,DictType dictType) {
        IPage pages = dictTypeService.page(page,dictType);
        return pages == null?null:PageResult.builder().total(pages.getTotal()).rows(pages.getRecords()).build();
    }


}
