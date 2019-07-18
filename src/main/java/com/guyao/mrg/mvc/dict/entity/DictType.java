package com.guyao.mrg.mvc.dict.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
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
public class DictType implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

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
