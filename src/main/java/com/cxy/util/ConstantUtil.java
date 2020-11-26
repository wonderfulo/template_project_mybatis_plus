package com.cxy.util;

import java.util.HashMap;
import java.util.Map;

public class ConstantUtil{
	private Map change_qtype_map;
	public final static int ORG_ENTERPERISE = 1; //企业
	public final static int ORG_COMPANY = 2;    //分公司
	public final static int ORG_DEP = 3;       //部门
	public final static int ORG_AREA = 4;      //区域
	public final static int ORG_SHOP = 5;      //店铺
	public final static int ORG_AGENT = 6;      //代理
	
	public ConstantUtil() {
		super();
		change_qtype_map = new HashMap();
		change_qtype_map.put("to_single", 2);
		change_qtype_map.put("to_multiple", 3);
		change_qtype_map.put("to_vote_single", 9);
		change_qtype_map.put("to_matrix_single", 4);
		change_qtype_map.put("to_vote_multiple", 10);
		change_qtype_map.put("to_weight_single", 11);
		change_qtype_map.put("to_matrix_multiple", 5);
		change_qtype_map.put("to_weight_multiple", 12);
	}

	
	public Map getChange_qtype_map() {
		return change_qtype_map;
	}

	public void setChange_qtype_map(Map changeQtypeMap) {
		change_qtype_map = changeQtypeMap;
	}
	
}