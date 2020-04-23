package com.guyao.mrg.mvc.role.mapper;

import com.guyao.mrg.common.base.MyBaseMapper;
import com.guyao.mrg.mvc.role.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface RoleMapper extends MyBaseMapper<Role> {
    List<Role> findByUserId(@Param("userId") String userid);
    int saveRoleMenuRelation(@Param("roleId") String roleId,@Param("menuId") String menuId,@Param("id") String id);

    void deleteRoleMenuRelationByRoleId(@Param("roleId") String id);

    List<String> selectMenuIdsByRoleId(@Param("roleId") String id);
}
