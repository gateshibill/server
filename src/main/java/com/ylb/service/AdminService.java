package com.ylb.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.ylb.entity.Admin;

import java.util.List;

public interface AdminService {
	public String addAdmin(HttpServletRequest request,Admin admin,Integer roleId);
	public String delAdmin(Integer adminId);
	public String selectAdminList(Admin admin,Integer page,Integer limit);
	public String updateAdmin(Admin admin);
	public String setRole(Admin admin);
	public String getAdminById(Integer adminId);
	public String adminLogin(HttpServletRequest request,String adminAccount,String password);
	public String shiroLogin(HttpSession session,String adminAccount,String password);
	public String updatePwd(Integer adminId,String oldPwd,String newPwd);
	public Admin findAdminByAccount(String adminAccount);
	public Admin getInfoById(Integer adminId);
	//获取admin总数
    int getAdminCount();

	String getAdminList(Admin admin, Integer page, Integer limit, Integer adminIdSelf);

	List<Admin> findAdminList(Integer adminId,Integer parentId);

	List<Admin> findAdminListAll();

	Admin selectAdmin(Integer adminId);

	Admin selectAdminCoding(String adminCoding);

	Admin selectAdminPhone(String phone);
}
