package com.guyao.mrg.mvc.dict.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.common.base.MyBaseMapper;
import com.guyao.mrg.mvc.dict.entity.DictType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface DictTypeMapper extends MyBaseMapper<DictType> {

    Page<DictType> page(Page page, DictType dictType);

    int selectCountByEnName(@Param("enName") String dictTypeEnName, @Param("id") String id);

    List<DictType> selectTypeDataCache();

    DictType getTypeByName(String typeName);

    DictType getTypeById(String id);
}
