package com.guyao.mrg.mvc.menu.service.impl;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.mapper.MenuMapper;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.mvc.security.LoginUserDetails;
import com.guyao.mrg.mvc.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**N
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guyao
 * @since 2019-10-08
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    private static final int BUTTONMENU = 1;

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
        //获取顶层
        List<Menu> result = menus.parallelStream().filter(m->StringUtils.isEmpty(m.getParentId())).collect(Collectors.toList());
        reLoadMenus(result,menus);
        return result;
    }

    private void reLoadMenus(List<Menu> parents,List<Menu> menus) {
        for (Menu parent: parents ) {
            //为底层菜单
            if(parent.getType() == BUTTONMENU) {
                parent.setChildren(new ArrayList<>());
                continue;
            }
            List<Menu> children = menus.parallelStream().filter(m -> parent.getId().equals(m.getParentId())).collect(Collectors.toList());
            parent.setChildren(children);
            reLoadMenus(children,menus);
        }
    }
}
