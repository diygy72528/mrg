package com.guyao.mrg.mvc.dict.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.guyao.mrg.mvc.dict.mapper.DictTypeMapper;
import com.guyao.mrg.mvc.dict.service.IDictTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class DictTypeServiceImpl extends ServiceImpl<DictTypeMapper, DictType> implements IDictTypeService {



    @Override
    public IPage<DictType> page(Page page, DictType dictType) {
        baseMapper.page(page,dictType);
        return null;
    }
}
