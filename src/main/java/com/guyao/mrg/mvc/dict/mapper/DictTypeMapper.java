package com.guyao.mrg.mvc.dict.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.MyBaseMapper;
import com.guyao.mrg.mvc.dict.entity.DictType;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface DictTypeMapper extends MyBaseMapper<DictType> {

    IPage<DictType> page(Page page, DictType dictType);

}
