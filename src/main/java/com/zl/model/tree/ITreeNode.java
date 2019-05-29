package com.zl.model.tree;

import java.util.List;

/**
 * @author tzxx
 */
public interface ITreeNode<T> {

    String id();

    String parentId();

	void addChild(T t);

	void removeChild(T t);

	List<T> children();
}