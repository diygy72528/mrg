package com.guyao.mrg.mvc.dict.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.common.exception.BusinessException;
import com.guyao.mrg.common.utils.SecurityUtils;
import com.guyao.mrg.mvc.dict.entity.DictData;
import com.guyao.mrg.mvc.dict.mapper.DictDataMapper;
import com.guyao.mrg.mvc.dict.service.IDictDataService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataMapper, DictData> implements IDictDataService {

    @Override
    public Page page(Page page, DictData dictData) {
        return baseMapper.page(page, dictData);
    }

    @Override
    public IPage pageByTypeId(String id, Page page) {
        return baseMapper.pageByTypeId(page,id);
    }

    @Override
    public boolean checkUniqueValue(String typeId, String value, String id) {
        return baseMapper.checkUniqueValue(typeId, value, id) == 0;
    }

    @Override
    public boolean delete(String ids) {
        for (String id : ids.split(",")) {
            baseMapper.updateModifierAndModifyTime(id, SecurityUtils.getUserDetails().getUserId(), LocalDateTime.now(), "dict_data");
            baseMapper.deleteById(id);
        }
        return false;
    }

    @Override
    public boolean saveOrUpdate(DictData entity) {
        if(!checkUniqueValue(entity.getDictTypeId(), entity.getValue(), entity.getId())) {
            throw new BusinessException("字典值已存在");
        }
        return super.saveOrUpdate(entity);
    }
}
