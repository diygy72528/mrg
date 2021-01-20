package com.guyao.mrg.mvc.dict.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guyao.mrg.mvc.dict.entity.DictType;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface IDictTypeService extends IService<DictType> {

    Page<DictType> page(Page page, DictType dictType);

    boolean checkUniqueName(String dictTypeEnName, String id);

    boolean delete(String ids);

    List<DictType> selectTypeDataCache();

    DictType getTypeByName(String typeName);

    DictType getTypeById(String id);
}
