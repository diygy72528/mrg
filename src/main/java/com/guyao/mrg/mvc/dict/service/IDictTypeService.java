package com.guyao.mrg.mvc.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface IDictTypeService extends IService<DictType> {

    IPage<DictType> page(Page page, DictType dictType);

}
