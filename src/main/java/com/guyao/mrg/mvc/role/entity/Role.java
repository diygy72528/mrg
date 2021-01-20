package com.guyao.mrg.mvc.role.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.guyao.mrg.common.base.TreeEntity;
import com.guyao.mrg.mvc.menu.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

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
public class Role extends TreeEntity {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Menu> menuList;

    /**
     * 状态
     */
    @TableField("`status`")
    private Integer status;

    /**
     * 排序
     */
    private Integer orderNum;

    /**
     * 角色描述
     */
    private String roleDescript;


}
