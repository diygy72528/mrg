package com.guyao.mrg.result;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author guyao
 * @date 2019/11/5 1:30 下午
 */
@Data
@Builder
public class PageResult {
    private long total;

    private List rows;
}
