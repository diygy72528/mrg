package com.guyao.mrg.mvc.menu.service;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
