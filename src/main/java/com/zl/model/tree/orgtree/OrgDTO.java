package com.zl.model.tree.orgtree;


import com.alibaba.fastjson.annotation.JSONField;
import com.zl.model.tree.ITreeNode;
import com.zl.model.tree.TreeBuilder;
import com.zl.utils.JsonUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zl
 */
@Data
public class OrgDTO implements ITreeNode<OrgDTO> {

	@JSONField(ordinal=1)
	private String id;

	@JSONField(ordinal=2)
	private String name;

	@JSONField(ordinal=3)
	private String parentId;


	@JSONField(ordinal=4)
	private List<OrgDTO> children = new ArrayList<>();

	@Override
	public Object id() {
		return getId();
	}

	@Override
	public Object parentId() {
		return getParentId();
	}

	@Override
	public void addChild(OrgDTO orgDTO) {
		children.add(orgDTO);
	}

	@Override
	public List<OrgDTO> children() {
		return children;
	}

	@Override
	public void removeChild(OrgDTO orgDTO) {
		children.remove(orgDTO);
	}


	public OrgDTO(String id, String parentId, String name){
		this.id = id;
		this.parentId = parentId;
		this.name = name;
	}
	public static void main(String[] args) {
		List<OrgDTO> list = new ArrayList<>();
		list.add(new OrgDTO("1","","1"));
		list.add(new OrgDTO("2","1","2"));
		list.add(new OrgDTO("3","2","3"));
		list.add(new OrgDTO("4","2","4"));
		list.add(new OrgDTO("5","4","5"));
		TreeBuilder<OrgDTO> tree = new TreeBuilder<>(list,"");
		System.out.println(tree.treeJsonData());
		System.out.println(		JsonUtil.objToStr(tree.getAllChildren("2"))
		);

	}

}
