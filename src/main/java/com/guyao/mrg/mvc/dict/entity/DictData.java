package com.guyao.mrg.mvc.dict.entity;

import com.guyao.mrg.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
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
@ToString
public class DictData extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 类型id
     */
    private String dictTypeId;

    /**
     * 字典值状态
     */
    private Long status;

    /**
     * 是否删除
     */
    private String isDelete;


}
