package com.ylb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.github.pagehelper.Page;
import com.ylb.dao.RoleDao;
import com.ylb.dao.RolePowerDao;
import com.ylb.entity.Role;
import com.ylb.entity.RolePower;
import com.ylb.service.RoleService;
import com.ylb.util.JsonUtil;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private RolePowerDao rolePowerDao;
	/**
	 * 添加角色
	 */
	@Override
	public String addRole(Role role,String rolePower) {
		int code = 1;
		String msg = "";
		if(role.getRoleName().equals("") || role.getRoleName() == null) {
			msg = "角色名称不能为空";
		}else {
			int count = roleDao.checkRoleIsAlready(role.getRoleName(),null);
			if(count > 0) {
				msg = "角色名称已存在";
			}else {
				role.setCreateTime(new Date());
				Integer roleId = roleDao.addRole(role);
				if(roleId == null) {
					msg = "添加角色失败";
				}else {
					//批量插入权限
					if(!rolePower.equals("")) {
						List<RolePower> lists = new ArrayList<RolePower>();
						String newArr[] = rolePower.split(",");
						for (int i = 0; i < newArr.length; i++) {
							RolePower rp = new RolePower();
							rp.setPowerId(Integer.valueOf(newArr[i]));
							rp.setRoleId(role.getRoleId());
							lists.add(rp);
						}
						rolePowerDao.addRolePower(lists);
					}
					code = 0;
					msg = "添加角色成功";
				}
			}
		}
		return JsonUtil.buildObjectJson(code, msg, "");
	}
	/**
	 * 删除角色
	 */
	@Override
	public String delRole(Integer roleId) {
		int code = 1;
		String msg = "";
		if(roleId == null) {
			msg = "参数非法";
		}else {
			Integer resultId = roleDao.delRole(roleId);
			if(resultId == null) {
				msg = "删除角色失败";
			}else {
				code = 0;
				msg = "删除角色成功";
			}
		}
		return JsonUtil.buildObjectJson(code, msg, "");
	}
	/**
	 * 角色列表
	 */
	@Override
	public Page<Role> getRoleList(Map<String,Object> param) {
		return roleDao.getRoleList(param);

	}
	/**
	 * 编辑角色
	 */
	@Transactional //事务
	@Override
	public String updateRole(Role role,String rolePower) {
		int code = 1;
		String msg = "";
		if(role.getRoleId() == null) {
			msg = "参数非法";
		}else {
			if(role.getRoleName().equals("") || role.getRoleName() == null) {
				msg = "角色名称不能为空";
			}else {
				int count = roleDao.checkRoleIsAlready(role.getRoleName(), role.getRoleId());
				if(count > 0) {
					msg = "角色名称已存在";
				}else {
					 Integer resultId = roleDao.updateRole(role);
					 if(resultId == null) {
						 msg = "编辑角色失败";
					 }else {
							try {
								Integer delResultId = rolePowerDao.delAllPower(role.getRoleId());//删除权限
								if(delResultId != null) {
									if(!rolePower.equals("")) {
										List<RolePower> lists = new ArrayList<RolePower>();
										String newArr[] = rolePower.split(",");
										for (int i = 0; i < newArr.length; i++) {
											RolePower rp = new RolePower();
											rp.setPowerId(Integer.valueOf(newArr[i]));
											rp.setRoleId(role.getRoleId());
											lists.add(rp);
										}
										rolePowerDao.addRolePower(lists);
									}
									msg = "编辑成功";
									code = 0;
								}else {
									msg = "编辑失败";
								}
							} catch (Exception e) {
								e.printStackTrace();
								//事务回滚
								TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
								msg = "编辑失败，事务回滚中";
							}
					 }
				}
			}
		}
		return JsonUtil.buildObjectJson(code, msg, "");
	}
	/**
	 * 获取详情
	 */
	@Override
	public Role getRoleById(Integer roleId) {
		int code = 1;
		String msg = "";
		Role role = new Role();
		if(roleId == null) {
			msg = "参数非法";
		}else {
			role = roleDao.getRoleById(roleId);
			if(role == null) {
				msg = "角色不存在";
			}else {
				code = 0;
				msg = "返回角色详情成功";
			}
		}
		return role;
		//return JsonUtil.buildObjectJson(code, msg, role);
	}
	@Override
	public List<Role> getAllRole() {
		return roleDao.getAllRole();
	}
	
	public Role getRoleByOfficailAcountAndUsername(String officialAccountId,String username) {
		return roleDao.getRoleByOfficailAcountAndUsername(officialAccountId, username);
	};
	
}
