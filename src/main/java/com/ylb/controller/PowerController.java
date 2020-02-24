package com.ylb.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ylb.entity.Power;
import com.ylb.entity.RolePower;
import com.ylb.service.PowerService;
import com.ylb.service.RolePowerService;
import com.ylb.util.BaseUtil;
import com.ylb.util.JsonUtil;
import com.ylb.util.TreeUtil;

/**
 * 获取权限
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/power")
public class PowerController extends BaseUtil {
	
	private final static Logger log = LoggerFactory.getLogger(PowerController.class);
	
	@Autowired
	private PowerService powerService;
	@Autowired
	private RolePowerService rolePowerService;

	/**
	 * 获取权限
	 */
	@RequestMapping(value = "getpowerlist", method = RequestMethod.GET)
	public void getPowerList(HttpServletResponse response) {
		List<Power> lists = powerService.getPowerList();
		List<Object> newlists = TreeUtil.getPowerTree(lists);
		output(response, JsonUtil.buildJsonByTotalCount(newlists, 1));
	}

	/**
	 * 组装我的权限和所有权限
	 */
	@RequestMapping(value = "/getUserPowersByRoleId", method = RequestMethod.GET)
	public void getUserPowersByRoleId(HttpServletResponse response, Integer roleId) {
		log.info("getUserPowersByRoleId() "+roleId);
		if(null==roleId) {
			output(response, JsonUtil.buildObjectJson("2", "roleId不能为空"));
			return;
		}
		try {
			List<RolePower> rplists = rolePowerService.getPowerListByRoleId(roleId);
			List<Power> plists = powerService.getPowerList();
			List<Power> lists = TreeUtil.getMyOwnPower(plists, rplists);
			List<Object> newlists = TreeUtil.getPowerTree(lists);
			output(response, JsonUtil.buildObjectJson(0, "success", newlists));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}
}
