package com.guyao.mrg.mvc.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.common.utils.UUIDUtils;
import com.guyao.mrg.mvc.user.entity.User;
import com.guyao.mrg.mvc.user.mapper.UserMapper;
import com.guyao.mrg.mvc.user.service.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {


    @Override
    public void updateUserInfo() {
        baseMapper.updateLoginInfo(new Date());
    }

    @Override
    public User findByUsername(String username) {
        return baseMapper.findByUsername(username);
    }

    @Override
    public IPage page(Page page, User user) {
        return baseMapper.page(page,user);
    }

    @Override
    public IPage listByRoleId(Page page,String roleId) {
        return baseMapper.listByRoleId(page, roleId);
    }

    private void addUserRoleRela(String userId, String roleId) {
        baseMapper.addUserRoleRela(UUIDUtils.uuid(), userId, roleId);
    }

    @Override
    public void deleteRelaByRoleId(String roleId) {
        baseMapper.deleteRelaByRoleId(roleId);
    }

    @Override
    public void deleteRelaByUserId(String userId) {
        baseMapper.deleteRelaByUserId(userId);
    }

    @Override
    public void addUserRolesRela(String userId, String roleIds) {
        this.deleteRelaByUserId(userId);
        if(StringUtils.isEmpty(roleIds)) {
            return;
        }
        for (String roleId : roleIds.split(",")) {
            this.addUserRoleRela(userId, roleId);
        }
    }

    @Override
    public void addUsersRoleRela(String roleId, String userIds) {
        this.deleteRelaByRoleId(roleId);
        if(StringUtils.isEmpty(userIds)) {
            return;
        }
        for (String userId : userIds.split(",")) {
            this.addUserRoleRela(userId, roleId);
        }
    }
}
