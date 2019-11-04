package com.guyao.mrg.mvc.user.entity;

import java.io.File;
import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.guyao.mrg.mvc.role.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author guyao
 * @since 2019-07-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Role> roleList;

    /**
     * 主键
     */
    private String id;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 注册时间
     */
    private LocalDateTime registerTime;

    /**
     * 上次登陆时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 登陆次数
     */
    private Long loginCount;

    /**
     * 是否离职（删除）
     */
    private String isDelete;

    /**
     * 备注
     */
    private String remark;


    /**
     * 用户状态
     */
    private Long userStatus;

    /**
     * 头像
     */
    private byte[] image;

}
