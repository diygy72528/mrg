package com.guyao.mrg.mvc.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.mvc.user.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.lang.NonNull;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface IUserService extends IService<User> {
    void updateUserInfo();

    User findByUsername(String username);

    IPage page(Page page, User user);

    IPage listByRoleId(Page page, String roleId);

    void deleteRelaByRoleId(String roleId);

    void deleteRelaByUserId(String userId);

    void addUserRolesRela(String userId, String roleIds);

    void addUsersRoleRela(@NonNull String roleId, String userIds);
}
