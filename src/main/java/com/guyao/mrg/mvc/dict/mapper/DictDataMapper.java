package com.guyao.mrg.mvc.dict.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.MyBaseMapper;
import com.guyao.mrg.mvc.dict.entity.DictData;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface DictDataMapper extends MyBaseMapper<DictData> {

    Page page(Page page, DictData dictData);

    IPage pageByTypeId(Page page, @RequestParam("id") String id);

    int checkUniqueValue(@RequestParam("typeId")String typeId, @RequestParam("value")String value, @RequestParam("id")String id);
}
