package com.guyao.mrg.mvc.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guyao.mrg.mvc.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
public interface UserMapper extends BaseMapper<User> {

    void updateLoginInfo(@Param("lastLoginTime") Date lastLoginTime);

    User findByUsername(@Param("username") String username);

    IPage<User> page(Page<User> page, User user);

}
