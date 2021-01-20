package com.guyao.mrg.mvc.role.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guyao.mrg.common.utils.SecurityUtils;
import com.guyao.mrg.common.utils.UUIDUtils;
import com.guyao.mrg.mvc.role.entity.Role;
import com.guyao.mrg.mvc.role.mapper.RoleMapper;
import com.guyao.mrg.mvc.role.service.IRoleService;
import com.guyao.mrg.mvc.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private IUserService userService;

    @Override
    public boolean saveOrUpdate(Role role, String menuIds) {
        //新增时此处默认生成字uuid
         super.saveOrUpdate(role);
         //清除原始关联
        baseMapper.deleteRoleMenuRelationByRoleId(role.getId());
         for (String menuId : menuIds.split(",")) {
             baseMapper.saveRoleMenuRelation(role.getId(),menuId, UUIDUtils.uuid());
         }
        return true;
    }

    @Override
    public String selectMenuIdsByRoleId(String id) {
        List<String> menuIds = baseMapper.selectMenuIdsByRoleId(id);
        return menuIds.stream().collect(Collectors.joining(","));
    }

    @Override
    public void addUserRoleRela(String roleId, String userIds) {
        userService.addUsersRoleRela(roleId,userIds);
    }

    @Override
    public boolean deleteById(String ids) {
        for (String id : ids.split(",")) {
            baseMapper.updateModifierAndModifyTime(id, SecurityUtils.getUserDetails().getUserId(), LocalDateTime.now(), "role");
            baseMapper.deleteById(id);
        }
        return true;
    }
}
