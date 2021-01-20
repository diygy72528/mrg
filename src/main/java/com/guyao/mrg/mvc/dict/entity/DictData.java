package com.guyao.mrg.mvc.dict.entity;

import com.guyao.mrg.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

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
    @NotNull
    private String dictName;

    /**
     * 类型id
     */
    @NotNull
    private String dictTypeId;

    /**
     * 值
     */
    @NotNull
    private String value;

    /**
     * 字典值状态
     */
    private Long status;


}
