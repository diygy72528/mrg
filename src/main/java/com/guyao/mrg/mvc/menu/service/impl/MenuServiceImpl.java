package com.guyao.mrg.mvc.menu.service.impl;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.mapper.MenuMapper;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guyao
 * @since 2019-10-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> findByRoleId(String roleId) {
        return baseMapper.findByRoleId(roleId);
    }
}
