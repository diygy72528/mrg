package com.guyao.mrg.mvc.menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.guyao.mrg.common.base.ZTree;
import com.guyao.mrg.mvc.menu.entity.Menu;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-10-08
 */
public interface IMenuService extends IService<Menu> {
    List<Menu> findByRoleId(String roleId);

    List<Menu> getMenus();

    List<ZTree> getTreeMenu();

    List<Menu> selectMenuList(Menu menu);

    boolean delete(String id);

    int selectCountByParentId(String id);

    int selectRoleCountById(String id);
}
