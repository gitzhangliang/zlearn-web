package com.zl.model.tree;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author zl
 */
public class TreeBuilder<T extends ITreeNode<T>> {
    private LinkedHashMap<Object, T> treeNodesMap = new LinkedHashMap<>();
    private List<T> rootNodesList = new ArrayList<>();

    /**
     * @param list 平行关系的数据集合
     * @param predicate 判断根节点
     * @param r 和parentId比较的值，以此判断是否为根节点
     * @param <R> 和parentId比较的值的类型
     */
    public <R> TreeBuilder(List<T> list, TreePredicate<T,R> predicate, R r){
        for(T t : list){
            treeNodesMap.put(t.id(), t);
        }
        treeNodesMap.values().forEach(v->{
            if(!predicate.test(v,r)){
                T p = treeNodesMap.get(v.parentId());
                if(p != null){
                    p.addChild(v);
                }
            }else {
                rootNodesList.add(v);
            }
        });
    }

    public <R> TreeBuilder(List<T> list, R r){
        this(list,TreePredicate.rootNodePredicate(),r);
    }

    protected T getTreeNode(Object id) {
        return treeNodesMap.get(id);
    }

    public List<T> getRoot() {
        return rootNodesList;
    }

    public String treeJsonData(){
        return JSONObject.toJSONString(rootNodesList);
    }

    /**获取某一节点所有子孙节点
     * @param id  当前节点id
     * @return List<TreeNode>
     */
    public List<T> getAllChildren(Object id) {
        List<T> allChildren = new ArrayList<>(16);
        T treeNode = getTreeNode(id);
        for (T t : treeNode.children()) {
            allChildren.add(t);
            allChildren.addAll(getAllChildren(t.id()));
        }
        return allChildren;
    }

    /**获取某一节点所有祖父节点
     */
    public List<T> getAllParent(Object id) {
        List<T> allParent = new ArrayList<>(16);
        T treeNode = getTreeNode(id);
        T parent = treeNodesMap.get(treeNode.parentId());
        if(parent != null){
            allParent.add(parent);
            allParent.addAll(getAllParent((parent.id())));
        }
        return allParent;
    }
}