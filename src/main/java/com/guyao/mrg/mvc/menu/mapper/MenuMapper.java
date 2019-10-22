package com.guyao.mrg.mvc.menu.mapper;

import com.guyao.mrg.mvc.menu.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-10-08
 */
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findByRoleId(@Param("roleId") String roleId);
}
