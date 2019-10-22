package com.guyao.mrg.mvc.user.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.guyao.mrg.mvc.user.entity.User;
import com.guyao.mrg.mvc.user.mapper.UserMapper;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
}
