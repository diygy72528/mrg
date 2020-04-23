package com.guyao.mrg.common.base;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * @author guyao
 * @date 2020/4/14 3:08 下午
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeEntity extends BaseEntity {

    /**
     * 菜单名
     */
    @NotEmpty
    private String name;

    /**
     * 父id
     */
    private String parentId;
}
