package com.guyao.mrg.common.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;

/**
 * @author guyao
 * @date 2020/4/16 10:19 上午
 */
public interface MyBaseMapper<T> extends BaseMapper<T> {
    void updateModifierAndModifyTime(@Param("id") String id, @Param("userId") String userId, @Param("date") LocalDateTime now, @Param("tableName") String tableName);
}
