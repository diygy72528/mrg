package com.guyao.mrg.mvc.menu.service.impl;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.mapper.MenuMapper;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.mvc.security.LoginUserDetails;
import com.guyao.mrg.mvc.utils.SecurityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
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

    /**
     * 生成菜单列表
     * @return
     */
    @Override
    public List<Menu> getMenus() {
        List<Menu> menus = SecurityUtils.getMenus();
        return reLoadMenus(menus);
    }

    private List<Menu> reLoadMenus(List<Menu> menus) {
        //todo 重组menu
        return null;
    }
}
