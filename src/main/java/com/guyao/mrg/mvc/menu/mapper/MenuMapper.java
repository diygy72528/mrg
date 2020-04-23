package com.guyao.mrg.mvc.menu.mapper;

import com.guyao.mrg.common.base.MyBaseMapper;
import com.guyao.mrg.mvc.menu.entity.Menu;
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
public interface MenuMapper extends MyBaseMapper<Menu> {
    List<Menu> findByRoleId(@Param("roleId") String roleId);
    int selectCountByParentId(@Param("id") String id);

    int selectRoleCountById(@Param("id") String id);

}
