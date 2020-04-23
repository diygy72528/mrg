package com.guyao.mrg.mvc.dict.entity;

import com.guyao.mrg.common.base.BaseEntity;
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
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 中文名
     */
    private String dictTypeChName;

    /**
     * 英文名
     */
    private String dictTypeEnName;

    /**
     * 类型描述
     */
    private String dictTypeDescript;

    /**
     * 字典状态
     */
    private Long status;

    /**
     * 是否删除
     */
    private String isDelete;


}
