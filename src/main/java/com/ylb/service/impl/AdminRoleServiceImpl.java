package com.ylb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ylb.dao.AdminRoleDao;
import com.ylb.entity.AdminRole;
import com.ylb.service.AdminRoleService;
import com.ylb.util.JsonUtil;

@Service
public class AdminRoleServiceImpl implements AdminRoleService{
	@Autowired
	private AdminRoleDao adminRoleDao;
	/**
	 * 获取角色信息
	 */
	@Override
	public AdminRole getAdminRoleByAdminId(Integer adminId) {
		return adminRoleDao.getAdminRoleByAdminId(adminId);
	}
	/**
	 * 编辑角色
	 */
	@Transactional
	@Override
	public String doSetRole(AdminRole ar) {
		int code = 1;
		String msg = "更新角色失败";
		if(ar.getAdminId() == null) {
			msg = "参数非法";
		}else {
			if(ar.getRoleId() == null) {
				msg = "参数非法";
			}else {
				try {
					Integer deleteId = adminRoleDao.delAdminRole(ar.getAdminId());
					if(deleteId == null) {
						msg = "更新角色失败";
					}else {
						Integer insertId = adminRoleDao.addAdminRole(ar);
						if(insertId == null) {
							msg = "更新角色失败";
						}else {
							
							code = 0;
							msg = "更新角色成功";
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚事务
				}
			}
		}
		return JsonUtil.buildObjectJson(code, msg, "");
	}
	
}
