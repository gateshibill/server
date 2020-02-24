package com.ylb.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ylb.entity.Power;
import com.ylb.entity.RolePower;
/**
 * 无限极递归
 * @author Administrator
 *
 */
public class TreeUtil {
	public static List<Object> getPowerTree(List<Power> lists){
		List<Object> trees = new ArrayList<Object>();
		for (Power power : lists) {
			Map<String,Object> mapArr = new LinkedHashMap<String, Object>();
			if(power.getParentId() == 0) {
				mapArr.put("name", power.getPowerName());
				mapArr.put("value",power.getPowerId());
				if(power.getIsHasPower().equals(1)) {
					mapArr.put("checked", true);
				}else {
					mapArr.put("checked", false);
				}
				mapArr.put("disabled",false);
				mapArr.put("list", powerChild(power.getPowerId(),lists));
				trees.add(mapArr);
			}

		}
		return trees;
	}
	public static List<?> powerChild(Integer id,List<Power> powerlist){
		List<Object> lists = new ArrayList<Object>();
		for (Power power : powerlist) {
			Map<String,Object> childArray = new LinkedHashMap<String, Object>(); 
			if(power.getParentId() == id) {
				childArray.put("name", power.getPowerName());
				childArray.put("value",power.getPowerId());
				if(power.getIsHasPower().equals(1)) {
					childArray.put("checked", true);
				}else {
					childArray.put("checked", false);
				}
				childArray.put("disabled",false);
				childArray.put("list", powerChild(power.getPowerId(),powerlist));
				lists.add(childArray);
			}
		}
		return lists;
	}
	/**
	 * 查询我具有的权限
	 * @param powerlist
	 * @param rplist
	 * @return
	 */
	public static List<Power> getMyOwnPower(List<Power> powerlist,List<RolePower> rplist){
		for (RolePower rp : rplist) {
			for (Power p : powerlist) {
				if(rp.getPowerId().equals(p.getPowerId()) || rp.getPowerId() == p.getPowerId()) {
					p.setIsHasPower(1);
				}
			}	
		}
		return powerlist;
	}
}
