package com.ylb.controller;

import com.ylb.entity.Admin;
import com.ylb.entity.AdminRole;
import com.ylb.entity.Role;
import com.ylb.service.AdminRoleService;
import com.ylb.service.AdminService;
import com.ylb.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("/admin")
public class AdminController{
	@Autowired
	private AdminService adminService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AdminRoleService adminRoleService;
	
	/**
	 * 进入代理商列表页面
	 * @param model
	 * @param admin
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, Admin admin) {
		model.addAttribute("admin", admin);
		return "admin/index";
	}
	
	/**
	 * 获取代理商列表数据
	 * @param response
	 * @param admin
	 * @param page
	 * @param limit
	 */
	@ResponseBody
	@RequestMapping(value="/getadminlist",method=RequestMethod.POST)
	public String getAdminList(HttpServletRequest request, HttpServletResponse response, Admin admin, Integer page, Integer limit) {
		//判断是否为超级管理员
		String result;
		//获取当前登录admin对象
		Admin selfAdmin = (Admin) request.getSession().getAttribute("stockManageAdmin");
		Integer adminIdSelf = selfAdmin.getAdminId();
		if(selfAdmin.getAdminLevel() == 0){
			result = adminService.selectAdminList(admin, page, limit);
		}else{
			result = adminService.getAdminList(admin, page, limit,adminIdSelf);
		}
		return result;
	}
	/**
	 * 添加代理页面
	 * @return
	 */
	@RequestMapping(value="/addadmin",method=RequestMethod.GET)
	public String addAdmin(Model model) {
		List<Role> roles = roleService.getAllRole();
		model.addAttribute("roles", roles);
		return "admin/addadmin";
	}
	/**
	 * 执行添加代理
	 * @param response
	 * @param admin
	 */
	@ResponseBody
	@RequestMapping(value="/doaddadmin",method=RequestMethod.POST)
	public String doAddAdmin(HttpServletResponse response,HttpServletRequest request,Admin admin,Integer roleId) {
		String result = adminService.addAdmin(request,admin,roleId);
		return result;
	}
	/**
	 * 编辑代理页面
	 * @return
	 */
	@RequestMapping(value="/updateadmin",method=RequestMethod.GET)
	public String updateAdmin() {
		return "admin/updateadmin";
	}
	/**
	 * 执行编辑代理
	 * @param response
	 * @param admin
	 */
	@ResponseBody
	@RequestMapping(value="/doupdateadmin",method=RequestMethod.POST)
	public String doUpdateAdmin(HttpServletResponse response,Admin admin) {
		String result = adminService.updateAdmin(admin);
		return result;
	}
	/**
	 * 删除代理
	 * @param response
	 * @param adminId
	 */
	@ResponseBody
	@RequestMapping(value="/deladmin",method=RequestMethod.POST)
	public String delAdmin(HttpServletResponse response,Integer adminId) {
		String result = adminService.delAdmin(adminId);
		return result;
	}
	/**
	 * 进入设置角色页面
	 * @param model
	 * @param adminId
	 * @return
	 */
	@RequestMapping(value="/setrole",method=RequestMethod.GET)
	public String setRole(Model model,Integer adminId) {
		List<Role> roles = roleService.getAllRole();
		AdminRole adminrole = adminRoleService.getAdminRoleByAdminId(adminId);
		if(adminrole == null) {
			model.addAttribute("roleId", 0);
		}else {
			model.addAttribute("roleId", adminrole.getRoleId());
		}
		model.addAttribute("roles",roles);
		model.addAttribute("adminId", adminId);
		return "admin/setrole";
	}
	/**
	 * 实现分配角色
	 * @param ar
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/dosetrole",method=RequestMethod.POST)
	public String doSetRole(AdminRole ar) {
		return adminRoleService.doSetRole(ar);
	}
	/**
	 * 进入编辑密码页面
	 * @return
	 */
	@RequestMapping(value="/updatepwd",method=RequestMethod.GET)
	public String updatePwd() {
		return "common/changepwd";
	}
	/**
	 * 进入代理详情页面
	 * @return
	 */
	@RequestMapping(value="/userinfo")
	public String userInfo(HttpSession session,Model model) {
		Admin oldadmin = (Admin) session.getAttribute("stockManageAdmin");
		Admin admin = adminService.getInfoById(oldadmin.getAdminId());
		model.addAttribute("admin", admin);
		return "common/userinfo";
	}
	/**
	 * 修改密码
	 * @param adminId
	 * @param oldPwd
	 * @param newPwd
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/doupdatepwd")
	public String doUpdatePwd(Integer adminId,String oldPwd,String newPwd) {
		return adminService.updatePwd(adminId, oldPwd, newPwd);
	}
}
