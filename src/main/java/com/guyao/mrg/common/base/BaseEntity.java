package com.guyao.mrg.common.base;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author guyao
 * @date 2020/4/16 8:47 上午
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaseEntity implements Serializable {
    /**
     * id
     */
    private String id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String creater;

    /**
     * 创建时间
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    private String modifier;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime modifyTime;

}
