package com.guyao.mrg.mvc.dict.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guyao.mrg.mvc.dict.entity.DictData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface IDictDataService extends IService<DictData> {

    Page page(Page page, DictData dictData);

    IPage pageByTypeId(String id, Page page);

    boolean checkUniqueValue(String typeId, String value, String id);

    boolean delete(String ids);
}
