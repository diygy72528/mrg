package com.guyao.mrg.mvc.user.service.impl;

import com.guyao.mrg.mvc.user.entity.User;
import com.guyao.mrg.mvc.user.mapper.UserMapper;
import com.guyao.mrg.mvc.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
