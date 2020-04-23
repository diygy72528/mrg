package com.guyao.mrg.common.utils;

import java.util.UUID;

/**
 * @author guyao
 * @date 2020/4/17 4:42 下午
 */
public class UUIDUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
