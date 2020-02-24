package com.ylb.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ylb.entity.Role;
import com.ylb.entity.RolePower;
import com.ylb.entity.User;
import com.ylb.service.RolePowerService;
import com.ylb.service.RoleService;
import com.ylb.util.BaseUtil;
import com.ylb.util.JsonUtil;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseUtil {
	private final static Logger logger = LoggerFactory.getLogger(RoleController.class);
	@Autowired
	private RoleService roleService;
	@Autowired
	private RolePowerService rolePowerService;

	/**
	 * 进去角色列表页面
	 * 
	 * @param role
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Role role, Model model) {
		return "role/index";
	}

	/**
	 * 进入添加角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/addrole", method = RequestMethod.GET)
	public String addRole() {
		return "role/addrole";
	}

	/**
	 * 编辑角色页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/updaterole", method = RequestMethod.GET)
	public String updateRole(Model model, Integer roleId) {
		logger.info("updateRole() " + roleId);
		model.addAttribute("roleId", roleId);
		return "role/updaterole";
	}

	/**
	 * 获取角色列表数据
	 * 
	 * @param role
	 * @param page
	 * @param limit
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getrolelist", method = RequestMethod.GET)
	public void getRoleList(HttpServletRequest request, HttpServletResponse response, Role role, Integer page,
			Integer limit) {
		logger.info("getRoleList() " + role.detail());
		page = (null == page || page < 0) ? 0 : page;
		limit = (null == limit || limit < 0) ? 10 : limit;
		try {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("is_effect", role.getIsEffect());
			param.put("roleName", role.getRoleName());
			param.put("page", page);
			param.put("limit", limit);
			System.out.println("searchManageUsers():" + limit);
			PageHelper.startPage(page, limit);
			Page<Role> data = roleService.getRoleList(param);
			int pageSize = data.getPageSize();
			long total = data.getTotal();
			List<Role> list = data.getResult();
			System.out.println("getrolelist():" + list.size());
			output(response, JsonUtil.buildJsonForPage(list, total, pageSize));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "搜索失败"));
		}
	}

	/**
	 * 添加角色,并赋值权限
	 * 
	 * @param role
	 * @param rolePower
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public void addRole(HttpServletRequest request, HttpServletResponse response, String roleName, String rolePower) {
		logger.info("addRole() " + roleName + "/" + rolePower);
		if (null == roleName || roleName.isEmpty() || null == rolePower || rolePower.isEmpty()) {
			output(response, JsonUtil.buildObjectJson("2", "参数错误"));
			return;
		}
		try {
			Role role = new Role();
			role.setRoleName(roleName);
			role.setIsEffect(1);
			role.setCreateTime(new Date());
			roleService.addRole(role, rolePower);
			output(response, JsonUtil.buildObjectJson("0", "success"));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	/**
	 * 删除角色
	 * 
	 * @param role
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delRole", method = RequestMethod.GET)
	public String delRole(Integer roleId) {
		logger.info("addRole() " + roleId);
		return roleService.delRole(roleId);
	}

	/**
	 * 更新角色
	 * 
	 * @param role
	 * @param rolePower ： 权限集合
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateRole", method = RequestMethod.GET)
	public void updateRole(HttpServletRequest request, HttpServletResponse response, Integer roleId, String roleName,
			String rolePower, Integer isEffect) {
		logger.info("updateRole()0000000000 " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
		if (null == roleId) {
			output(response, JsonUtil.buildObjectJson("2", "roleId不能为空"));
			return;
		}
		logger.info("updateRole()99999999 " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
		if (1 == roleId) {
			output(response, JsonUtil.buildObjectJson("3", "创建者角色不能修改"));
			return;
		}
		logger.info("updateRole()88888888888 " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
		try {

			Role role = roleService.getRoleById(roleId);
			if (null == role) {
				output(response, JsonUtil.buildObjectJson("4", "roleId不存在改"));
				return;
			}
			logger.info("updateRole()777777 " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
			role.setRoleId(roleId);
			if (null != roleName) {
				role.setRoleName(roleName);
			}
			if (null != isEffect) {
				role.setIsEffect(isEffect);
			}
			logger.info("updateRole()6666666 " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
			// 需要保留
			if (null == rolePower) {
				List<RolePower> rplists = rolePowerService.getPowerListByRoleId(roleId);
				if (null == rplists) {
					logger.info("updateRole()5555 rplists is null");
					rolePower = "";
				} else {
					logger.info("updateRole()111111111 rplists " + rplists.size());
					StringBuffer sb = new StringBuffer();
					for (RolePower rp : rplists) {
						sb.append(rp.getPowerId() + ",");
					}

					rolePower = sb.toString();
				}
			}
			logger.info("updateRole() " + roleId + "/" + roleName + "/" + rolePower + "/" + isEffect);
			roleService.updateRole(role, rolePower);
			output(response, JsonUtil.buildObjectJson(0, "success", role));
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getRoleByOfficailAcountAndUsername", method = RequestMethod.GET)
	public void getRoleByOfficailAcountAndUsername(HttpServletRequest request, HttpServletResponse response,
			String officialAccountId, String username) {
		logger.info("getRoleByOfficailAcountAndUsername() " + officialAccountId + "/" + username);
		if (null == officialAccountId || officialAccountId.isEmpty() || null == username || username.isEmpty()) {
			output(response, JsonUtil.buildObjectJson("2", "officialAccountId和username不能为空"));
			return;
		}
		try {
			Role role = roleService.getRoleByOfficailAcountAndUsername(officialAccountId, username);
			if (null != role) {
				output(response, JsonUtil.buildObjectJson(0, "success", role));
			} else {
				output(response, JsonUtil.buildObjectJson("2", "没有管理角色"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			output(response, JsonUtil.buildObjectJson("1", "系统异常"));
		}
	}

}
