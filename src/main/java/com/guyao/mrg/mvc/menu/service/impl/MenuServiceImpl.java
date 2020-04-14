package com.guyao.mrg.mvc.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.common.base.ZTree;
import com.guyao.mrg.common.exception.WarnException;
import com.guyao.mrg.common.utils.TreeDataUtils;
import com.guyao.mrg.mvc.menu.entity.Menu;
import com.guyao.mrg.mvc.menu.mapper.MenuMapper;
import com.guyao.mrg.mvc.menu.service.IMenuService;
import com.guyao.mrg.common.utils.SecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
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
@Transactional
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

    @Override
    public List<ZTree> getTreeMenu() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        HashMap<String, Object> eqs = new HashMap<>();
        eqs.put("is_delete","0");
        wrapper.allEq(eqs);
        List<Menu> menus = baseMapper.selectList(wrapper);
        return TreeDataUtils.initTreeData(menus);
    }

    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return baseMapper.selectList(new QueryWrapper<>(menu));
    }

    @Override
    public int delete(String id) {
        if(selectCountByParentId(id) > 0) {
            throw new WarnException("存在子菜单，不允许删除！");
        }
        if(selectRoleCountById(id) > 0){
            throw new WarnException("菜单已经分配，不允许删除！");
        }
        return baseMapper.deleteById(id);
    }

    @Override
    public int selectCountByParentId(String id) {
        return baseMapper.selectCountByParentId(id);
    }


    @Override
    public int selectRoleCountById(String id) {
        return baseMapper.selectRoleCountById(id);
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


    @Override
    public boolean saveOrUpdate(Menu entity) {
        return super.saveOrUpdate(entity);
    }
}
