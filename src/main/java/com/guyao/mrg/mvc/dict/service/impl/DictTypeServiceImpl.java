package com.guyao.mrg.mvc.dict.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.common.exception.BusinessException;
import com.guyao.mrg.common.utils.SecurityUtils;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.guyao.mrg.mvc.dict.mapper.DictTypeMapper;
import com.guyao.mrg.mvc.dict.service.IDictTypeService;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Service
@Transactional
@Log
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {


    @Override
    public Page<DictType> page(Page page, DictType dictType) {
        return baseMapper.page(page,dictType);
    }

    @Override
    public boolean checkUniqueName(String dictTypeEnName, String id) {

        return baseMapper.selectCountByEnName(dictTypeEnName, id) == 0;
    }

    @Override
    public boolean delete(String ids) {
        for (String id: ids.split(",")) {
            baseMapper.updateModifierAndModifyTime(id, SecurityUtils.getUserDetails().getUserId(), LocalDateTime.now(),"dict_type");
            baseMapper.deleteById(id);
        }
        return true;
    }

    @Override
    public List<DictType> selectTypeDataCache() {
        return baseMapper.selectTypeDataCache();
    }

    @Override
    public DictType getTypeByName(String typeName) {
        return baseMapper.getTypeByName(typeName);
    }

    @Override
    public DictType getTypeById(String id) {
        return baseMapper.getTypeById(id);
    }


    @Override
    public boolean saveOrUpdate(DictType entity) {
        if(!this.checkUniqueName(entity.getDictTypeEnName(),entity.getId())) {
            throw new BusinessException("该字典类型已经存在");
        }
        return super.saveOrUpdate(entity);
    }
}
