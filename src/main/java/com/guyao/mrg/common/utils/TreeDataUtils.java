package com.guyao.mrg.common.utils;

import com.guyao.mrg.common.base.TreeEntity;
import com.guyao.mrg.common.base.ZTree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author guyao
 * @date 2020/4/14 2:44 下午
 */
public class TreeDataUtils {

    public static List<ZTree> initTreeData(List<? extends TreeEntity> trees) {
        ArrayList<ZTree> zTrees = new ArrayList<>();
        for (TreeEntity tree : trees) {
            ZTree zTree = new ZTree();
            zTree.setId(tree.getId());
            zTree.setName(tree.getName());
            zTree.setTitle(tree.getName());
            zTree.setPId(tree.getParentId());
            zTrees.add(zTree);
        }
        return zTrees;
    }

}
