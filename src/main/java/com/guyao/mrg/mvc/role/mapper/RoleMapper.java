package com.guyao.mrg.mvc.role.mapper;

import com.guyao.mrg.mvc.role.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface RoleMapper extends BaseMapper<Role> {
    List<Role> findByUserId(@Param("userId") String userid);
}
