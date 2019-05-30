package com.zl.model.tree;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author tzxx
 */
public class TreeBuilder<T extends ITreeNode<T>> {
    private HashMap<String, T> treeNodesMap = new HashMap<>();
    private List<T> rootNodesList = new ArrayList<>();

    public TreeBuilder(List<T> list){
        for(T t : list){
            treeNodesMap.put(t.id(), t);
        }
        treeNodesMap.values().forEach(v->{
            if(StringUtils.isNotBlank(v.parentId())){
                T p = treeNodesMap.get(v.parentId());
                if(p != null){
                    p.addChild(v);
                }
            }
        });
        treeNodesMap.values().stream().filter(v->v.parentId() == null || "".equals(v.parentId())).forEach(e->rootNodesList.add(e));
    }

    protected T getTreeNode(String id) {
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
    public List<T> getAllChildren(String id) {
        List<T> allChildren = new ArrayList<>(16);
        T treeNode = getTreeNode(id);
        for (T t : treeNode.children()) {
            allChildren.add(t);
            allChildren.addAll(getAllChildren(t.id()));
        }
        return allChildren;
    }

    /**获取某一节点所有祖父节点
     * @param id key
     * @return List<TreeNode>
     */
    public List<T> getAllParent(String id) {
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