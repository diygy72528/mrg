package com.guyao.mrg.mvc.menu.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.guyao.mrg.common.base.TreeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
public class Menu extends TreeEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单类型
     */
    @NotNull
    private Integer type;

    /**
     * 删除标记
     */
    private String isDelete = "0";

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


    /**
     * 显示排序
     */
    private Integer orderNum;


}
