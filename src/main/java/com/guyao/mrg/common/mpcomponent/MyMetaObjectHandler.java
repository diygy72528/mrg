package com.guyao.mrg.common.mpcomponent;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.guyao.mrg.common.utils.SecurityUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author guyao
 * @date 2020/4/16 10:44 上午
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //创建时间
        this.strictInsertFill(metaObject,"createTime", LocalDateTime.class, LocalDateTime.now());
        //创建人
        this.strictInsertFill(metaObject,"creater", String.class, SecurityUtils.getUserDetails().getUserId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        //修改时间
        this.strictUpdateFill(metaObject,"modifyTime",LocalDateTime.class, LocalDateTime.now());
        //修改人
        this.strictUpdateFill(metaObject,"modifier",String.class,SecurityUtils.getUserDetails().getUserId());
    }

}
