package com.ylb.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ylb.entity.Admin;

import java.util.Date;
import java.util.List;

@Mapper
public interface AdminDao {
	/**
	 * 新增代理
	 * @param admin
	 * @return
	 */
	Integer addAdmin(Admin admin);
	/**
	 * 删除代理
	 * @param adminId
	 * @return
	 */
	Integer delAdmin(@Param("adminId")Integer adminId);
	/**
	 * 代理列表
	 * @param admin
	 * @param page
	 * @param limit
	 * @return
	 */
	List<Admin> selectAdminList(@Param("admin")Admin admin,@Param("page")Integer page,@Param("limit")Integer limit);
	/**
	 * 查询代理数量
	 * @param admin
	 * @return
	 */
	int selectAdminCount(@Param("admin")Admin admin);
	/**
	 * 更新代理信息
	 * @param admin
	 * @return
	 */
	Integer updateAdmin(Admin admin);
	/**
	 * 获取代理详情
	 * @param adminId
	 * @param adminAccount
	 * @param password
	 * @return
	 */
	Admin getAdminById(@Param("adminId")Integer adminId,@Param("adminAccount")String adminAccount,@Param("password")String password);
	/**
	 * 根据账号查询代理信息
	 * @param adminAccount
	 * @param isEffect
	 * @return
	 */
	int selectAdminCountByAccount(@Param("adminAccount")String adminAccount,@Param("isEffect")Integer isEffect);
	/**
	 * 检测代理是否存在
	 * @param adminId
	 * @param adminAccount
	 * @return
	 */
	int checkIsAlreadyCount(@Param("adminId")Integer adminId,@Param("adminAccount")String adminAccount);
	/**
	 * 根据代理账号查询
	 * @param adminAccount
	 * @return
	 */
	Admin findAdminByAccount(@Param("adminAccount")String adminAccount);
	
	/**
	 * 查询自己和自己子级代理的条数
	 */
	int getByAndSonCount(@Param("adminIds")List<Integer> adminIds,@Param("admin")Admin admin);
	/**
	 * 查询自己和自己子级的代理商
	 */
	List<Admin> getByMyAndSonList(@Param("adminIds")List<Integer> adminIds,@Param("admin") Admin admin,
			@Param("page")Integer page,@Param("limit")Integer limit);
	//获取admin总数
    int getAdminCount();
	//获取上次登录时间
	Date getLastLoginTime(@Param("admin") Admin admin);

	List<Admin> getAdminList(@Param("admin")Admin admin,@Param("page")Integer page,@Param("limit")Integer limit,@Param("adminIdSelf")Integer adminIdSelf );

	int getAdminCount1(@Param("admin") Admin admin, @Param("adminIdSelf") Integer adminIdSelf);

	List<Admin> findAdminList(@Param("adminId")Integer adminId,@Param("parentId")Integer parentId);

	List<Admin> findAdminListAll();

	Admin selectAdmin(@Param("adminId")Integer adminId);

	Admin selectAdminCoding(@Param("adminCoding")String adminCoding);

	Admin selectAdminPhone(@Param("phone")String phone);

}
