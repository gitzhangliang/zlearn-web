package com.zl.model.tree;

import java.util.List;

/**
 * @author zl
 */
public interface ITreeNode<T> {

	Object id();

	Object parentId();

	void addChild(T t);

	void removeChild(T t);

	List<T> children();
}