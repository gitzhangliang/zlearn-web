package com.zl.model.tree.orgtree;


import com.alibaba.fastjson.annotation.JSONField;
import com.zl.model.tree.ITreeNode;
import com.zl.model.tree.TreeBuilder;
import com.zl.utils.CollectionUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author tzxx
 */
@Getter@Setter
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
	public String id() {
		return this.getId();
	}


	@Override
	public String parentId() {
		return this.getParentId();
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
		List<OrgDTO> dtos = new ArrayList<>();
		OrgDTO dto1 = new OrgDTO("1","","1");
		OrgDTO dto2 = new OrgDTO("2","1","2");
		OrgDTO dto3 = new OrgDTO("3","2","3");
		OrgDTO dto4 = new OrgDTO("4","3","4");
		OrgDTO dto5 = new OrgDTO("5","4","5");

		dtos.add(dto1);
		dtos.add(dto2);
		dtos.add(dto3);
		dtos.add(dto4);
		dtos.add(dto5);

		TreeBuilder<OrgDTO> tree = new TreeBuilder<>(dtos);
		System.out.println(tree.treeJsonData());

		CollectionUtil.listToMap(dtos,"id").forEach((k,v)->{
			System.out.println(k+"---"+v.getParentId());
		});
		dtos.stream()
				 .collect(Collectors.toMap(OrgDTO::getId, Function.identity())).forEach((k,v)->{
			System.out.println(k+"---"+v.getParentId());
		});
	}

}
