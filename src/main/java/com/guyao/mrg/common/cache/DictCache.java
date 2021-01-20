package com.guyao.mrg.common.cache;

import com.guyao.mrg.common.base.MrGConstant;
import com.guyao.mrg.common.utils.RedisUtils;
import com.guyao.mrg.mvc.dict.entity.DictData;
import com.guyao.mrg.mvc.dict.entity.DictType;
import com.guyao.mrg.mvc.dict.service.IDictTypeService;
import lombok.extern.java.Log;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guyao
 * @date 2020/5/11 9:59 上午
 */
@Component
@Log
public class DictCache {

    private RedisUtils redisUtils;

    private IDictTypeService typeService;

    public DictCache(IDictTypeService typeService, RedisUtils redisUtils) {
        this.redisUtils = redisUtils;
        this.typeService = typeService;
        List<DictType> dictTypes = typeService.selectTypeDataCache();
        dictTypes.forEach(type -> {
            addData(type);
        });
    }


    public String getName(String typeName, String value) {
        Map<String, String> datas = (Map)redisUtils.gethashValue(MrGConstant.DICT_CACHE, typeName);
        if(datas == null || datas.size() == 0) {
            DictType dictType = typeService.getTypeByName(typeName);
            if(dictType != null) {
                addData(dictType);
                return dictType.getDataList().stream().filter(m -> m.getValue().equals(value)).findFirst().get().getDictName();
            }
            return null;

        }
        return datas.get(value);
    }

    private void addData(DictType type) {
        List<DictData> dataList = type.getDataList();
        if (dataList == null || dataList.size() == 0)
            return;
        HashMap<String, String> datas = new HashMap<>();
        dataList.forEach(data -> {
            datas.put(data.getValue(),data.getDictName());
        });
        redisUtils.pushHash(MrGConstant.DICT_CACHE,type.getDictTypeEnName(), datas);
    }

    private void removeData(DictType type) {
        redisUtils.deleteHash(MrGConstant.DICT_CACHE,type.getDictTypeEnName());
    }



    public void typeChanged(String ids) {
        if(StringUtils.isEmpty(ids)) {
            log.warning("更新缓存时字典id不存在");
            return;
        }
        for (String id : ids.split(",")) {
            DictType type = typeService.getTypeById(id);
            addData(type);
        }


    }

    public void typeDeleted(String ids) {
        if(StringUtils.isEmpty(ids)) {
            log.warning("更新缓存时字典id不存在");
            return;
        }
        for (String id : ids.split(",")) {
            DictType type = typeService.getTypeById(id);
            removeData(type);
        }

    }

}
