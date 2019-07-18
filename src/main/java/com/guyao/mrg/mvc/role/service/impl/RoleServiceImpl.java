package com.guyao.mrg.mvc.role.service.impl;

import com.guyao.mrg.mvc.role.entity.Role;
import com.guyao.mrg.mvc.role.mapper.RoleMapper;
import com.guyao.mrg.mvc.role.service.IRoleService;
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
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
