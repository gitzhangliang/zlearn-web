package com.zl.model.tree;

/**
 * @author zhangliang
 * @date 2019/10/29.
 */
@FunctionalInterface
public interface TreePredicate<T,R> {
    boolean test(T t, R r);

    static <T extends ITreeNode,R> TreePredicate<T ,R> rootNodePredicate(){
        return (T t,R r)->t.parentId() == null || t.parentId().equals(r);
    }
}

