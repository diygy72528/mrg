package com.guyao.mrg.mvc.role.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.List;

import com.guyao.mrg.mvc.menu.entity.Menu;
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
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Menu> menuList;

    /**
     * id
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色描述
     */
    private String roleDescript;

    /**
     * 创建人
     */
    private String createrId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifierId;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 是否删除
     */
    private String isDelete;


}
