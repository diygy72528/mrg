package com.guyao.mrg.mvc.dict.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.guyao.mrg.common.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
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
public class DictType extends BaseEntity {

    private static final long serialVersionUID = 1L;


    /**
     * 中文名
     */
    private String dictTypeChName;

    /**
     * 英文名
     */
    @NotNull
    private String dictTypeEnName;

    /**
     * 类型描述
     */
    private String dictTypeDescript;

    /**
     * 字典状态
     */
    @NotNull
    private Long status;

    @TableField(exist = false)
    private List<DictData> dataList;

}
