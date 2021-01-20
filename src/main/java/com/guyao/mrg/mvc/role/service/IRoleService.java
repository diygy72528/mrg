package com.guyao.mrg.mvc.role.service;

import com.guyao.mrg.mvc.role.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface IRoleService extends IService<Role> {

    boolean saveOrUpdate(Role role, String menuIds);

    String selectMenuIdsByRoleId(String id);

    void addUserRoleRela(String roleId, String userIds);

    boolean deleteById(String id);
}
