package com.guyao.mrg.mvc.menu.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author guyao
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private String id;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 父id
     */
    private String parentId;

    /**
     * 删除标记
     */
    private String isDelete;

    /**
     * 图标
     */
    private String icon;

    /**
     * 路径
     */
    private String url;

    /**
     * 权限
     */
    private String permission;

    /**
     * 创建人
     */
    private String creater;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;


}
