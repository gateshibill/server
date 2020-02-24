package com.ylb.service.impl;

import com.ylb.dao.AdminDao;
import com.ylb.dao.AdminRoleDao;
import com.ylb.entity.Admin;
import com.ylb.entity.AdminRole;
import com.ylb.service.AdminService;
import com.ylb.util.JsonUtil;
import com.ylb.util.MD5Util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminRoleDao adminRoleDao;

    /**
     * 新增管理员
     */
    @Transactional
    @Override
    public String addAdmin(HttpServletRequest request,Admin admin, Integer roleId) {
        int code = 1;
        String msg = "";
		Admin adminInfo = (Admin) request.getSession().getAttribute("stockManageAdmin");
		if(adminInfo == null) {
			msg = "请先登录";
		}else {
	        if (admin.getAdminAccount().equals("") || admin.getAdminAccount() == null) {
	            msg = "账号不能为空";
	        } else {
	            if (admin.getPassword().equals("") || admin.getPassword() == null) {
	                msg = "密码不能为空";
	            } else {
	                if (roleId == null) {
	                    msg = "请选择角色";
	                } else {
	                    int checkCount = adminDao.selectAdminCountByAccount(admin.getAdminAccount(), 1);
	                    if (checkCount > 0) {
	                        msg = "账号已存在,请换一个";
	                    } else {
                            Admin adminCoding = adminDao.selectAdminCoding(admin.getAdminCoding());
                            if(adminCoding != null){
                                msg = "代理商编号已存在,请换一个";
                            }else{
                                Admin admin1 = adminDao.selectAdminPhone(admin.getPhone());
                                if(admin1 != null){
                                    msg = "联系方式已存在,请换一个";
                                }else{
                                    try {
                                        Integer insertId = null;
                                        AdminRole adminrole = new AdminRole();
                                        admin.setParentId(adminInfo.getAdminId());//父id；先做成谁添加就是谁的
                                        admin.setPassword(MD5Util.MD5Encode(admin.getPassword(), "utf-8"));
                                        admin.setCreateTime(new Date());
                                        //根据当前登录代理商的ID获取代理商父级的ID
                                        Admin selectAdmin = adminDao.selectAdmin(adminInfo.getAdminId());
                                        if (adminInfo.getParentId() == 0) {
                                            admin.setAdminLevel(roleId - 1);
                                            admin.setParentId(roleId - 1);
                                            Integer adminId = adminDao.addAdmin(admin);
                                            if (adminId == null) {
                                                msg = "添加失败";
                                            } else {
                                                adminrole.setAdminId(admin.getAdminId());
                                                adminrole.setRoleId(roleId);
                                                insertId = adminRoleDao.addAdminRole(adminrole);
                                            }
                                        } else {
                                            if (selectAdmin.getAdminLevel() == 3) {
                                                msg = "该代理无法添加下级代理";
                                            } else {
                                                admin.setAdminLevel(selectAdmin.getAdminLevel() + 1);
                                                admin.setParentId(selectAdmin.getAdminLevel() + 1);
                                                Integer adminId = adminDao.addAdmin(admin);
                                                if (adminId == null) {
                                                    msg = "添加失败";
                                                } else {
                                                    adminrole.setAdminId(admin.getAdminId());
                                                    adminrole.setRoleId(roleId);
                                                    insertId = adminRoleDao.addAdminRole(adminrole);
                                                }
                                            }
                                        }

                                        if (insertId == null) {
                                            msg = "添加失败";
                                        } else {
                                            code = 0;
                                            msg = "添加成功";
                                        }
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                                        msg = "添加失败";
                                    }
                                 /*   try {
                                        admin.setParentId(adminInfo.getAdminId());//父id；先做成谁添加就是谁的
                                        admin.setPassword(MD5Util.MD5Encode(admin.getPassword(), "utf-8"));
                                        admin.setCreateTime(new Date());
                                        if(adminInfo.getParentId() == 0){
                                            //当登录者为管理员可以分配代理用户的级别
                                            admin.setAdminLevel(roleId - 1);
                                        }else{
                                            //根据当前登录代理商的ID获取代理商父级的ID
                                            Admin selectAdmin = adminDao.selectAdmin(adminInfo.getAdminId());
                                            if(selectAdmin.getAdminLevel() == 3){
                                                msg = "该代理无法添加下级代理";
                                            }else{
                                                System.out.println(selectAdmin.getAdminLevel());
                                                admin.setAdminLevel(selectAdmin.getAdminLevel() + 1);
                                            }
                                        }
                                        Integer adminId = adminDao.addAdmin(admin);
                                        if (adminId == null) {
                                            msg = "添加失败";
                                        } else {
                                            AdminRole adminrole = new AdminRole();
                                            adminrole.setAdminId(admin.getAdminId());
                                            adminrole.setRoleId(roleId);
                                            Integer insertId = adminRoleDao.addAdminRole(adminrole);
                                            if (insertId == null) {
                                                msg = "添加失败";
                                            } else {
                                                code = 0;
                                                msg = "添加成功";
                                            }
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
                                        msg = "添加失败";
                                    }*/
                                }
                            }
	                    }
	                }
	            }
	        }
		}
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    /**
     * 删除管理员
     */
    @Override
    public String delAdmin(Integer adminId) {
        int code = 1;
        String msg = "删除失败";
        if (adminId == null) {
            msg = "该账号不存在";
        } else {
            Integer delId = adminDao.delAdmin(adminId);
            if (delId != null) {
                code = 0;
                msg = "删除成功";
            }
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    /**
     * 获取管理员列表
     */
    @Override
    public String selectAdminList(Admin admin, Integer page, Integer limit) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        int count = adminDao.selectAdminCount(admin);
        List<Admin> lists = adminDao.selectAdminList(admin, (page - 1) * limit, limit);
        return JsonUtil.buildJsonByTotalCount(lists, count);
    }

    @Override
    public String getAdminList(Admin admin, Integer page, Integer limit, Integer adminIdSelf) {
        if (page == null) {
            page = 1;
        }
        if (limit == null) {
            limit = 10;
        }
        int count = adminDao.getAdminCount1(admin,adminIdSelf);
        List<Admin> lists = adminDao.getAdminList(admin, (page - 1) * limit, limit,adminIdSelf);
        return JsonUtil.buildJsonByTotalCount(lists, count);
    }

    @Override
    public List<Admin> findAdminList(Integer adminId,Integer parentId) {
        return adminDao.findAdminList(adminId,parentId);
    }

    @Override
    public List<Admin> findAdminListAll() {
        return adminDao.findAdminListAll();
    }

    @Override
    public Admin selectAdmin(Integer adminId) {
        return adminDao.selectAdmin(adminId);
    }

    @Override
    public Admin selectAdminCoding(String adminCoding) {
        return adminDao.selectAdminCoding(adminCoding);
    }

    @Override
    public Admin selectAdminPhone(String phone) {
        return adminDao.selectAdminPhone(phone);
    }


    /**
     * 编辑管理员
     */
    @Override
    public String updateAdmin(Admin admin) {
        int code = 1;
        String msg = "编辑失败";
        if (admin.getAdminId() == null) {
            msg = "非法管理员";
        } else {
            if (admin.getAdminAccount().equals("") || admin.getAdminAccount() == null) {
                msg = "账号不能为空";
            } else {
                int checkCount = adminDao.checkIsAlreadyCount(admin.getAdminId(), admin.getAdminAccount());
                if (checkCount > 0) {
                    msg = "账号已存在,请换一个";
                } else {
                    Admin adminCoding = adminDao.selectAdminCoding(admin.getAdminCoding());
                    Admin adminById = adminDao.selectAdmin(admin.getAdminId());
                    Admin admin1 = adminDao.selectAdminPhone(admin.getPhone());
                    if(adminCoding == null || admin1 == null){
                        try {
                            adminDao.updateAdmin(admin);
                            code = 0;
                            msg = "编辑成功";

                        } catch (Exception e) {
                            e.printStackTrace();
                         }
                    }else{
                        if(adminCoding.getAdminCoding().equals(adminById.getAdminCoding()) && admin1.getPhone().equals(adminById.getPhone())){
                            try {
                                adminDao.updateAdmin(admin);
                                code = 0;
                                msg = "编辑成功";

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else if(adminCoding != null && admin1 != null){
                            try{
                                msg = "用户已存在,请换一个";
                            }catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
//                    try {
//                        adminDao.updateAdmin(admin);
//                        code = 0;
//                        msg = "编辑成功";
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    /**
     * 获取管理员详情
     */
    @Override
    public String getAdminById(Integer adminId) {
        Admin admin = adminDao.getAdminById(adminId, null, null);
        int code = 1;
        String msg = "返回数据失败";
        if (admin != null) {
            code = 0;
            msg = "返回数据成功";
        }
        return JsonUtil.buildObjectJson(code, msg, admin);
    }

    /**
     * 管理员登录
     */
    @Override
    public String adminLogin(HttpServletRequest request, String adminAccount, String password) {
        int code = 1;
        String msg = "登录失败";
        Admin oldAdmin = (Admin) request.getSession().getAttribute("stockManageAdmin");
        if (oldAdmin == null) {
            if (adminAccount.equals("") || adminAccount == null) {
                msg = "账号不能为空";
            } else {
                if (password.equals("") || password == null) {
                    msg = "密码不能为空";
                } else {
                    String md5Password = MD5Util.MD5Encode(password, "utf-8");
                    Admin admin = adminDao.getAdminById(null, adminAccount, md5Password);
                    if (admin == null) {
                        msg = "账号或密码有误";
                    } else {
                        if (!admin.getIsEffect().equals(1)) {
                            msg = "该账户已被禁用";
                        } else {
                            //缓存管理员账号信息
                            request.getSession().setAttribute("stockManageAdmin", admin);
                            //更新登录时间
                            admin.setLastLoginTime(new Date());
                            adminDao.updateAdmin(admin);
                            msg = "登录成功";
                            code = 0;
                        }

                    }
                }
            }
        } else {
            code = 0;
            msg = "系统检测到你已登录,无需重复登录";
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    @Override
    public Admin findAdminByAccount(String adminAccount) {
        return adminDao.findAdminByAccount(adminAccount);
    }

    @Override
    public String shiroLogin(HttpSession session, String adminAccount, String password) {
        int code = 1;
        String msg = "登录失败";
        if (adminAccount.equals("") || adminAccount == null) {
            msg = "账号不能为空";
        } else {
            if (password.equals("") || password == null) {
                msg = "密码不能为空";
            } else {
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(adminAccount, password);
                Subject subject = SecurityUtils.getSubject();
                try {
                    subject.login(usernamePasswordToken);   //完成登录
                    Admin admin = (Admin) subject.getPrincipal();
                    //获取用户最新登录时间
                    admin.setLastLoginTime(new Date());
                    adminDao.updateAdmin(admin);
                    if (!admin.getIsEffect().equals(1)) {
                        msg = "该账户已被禁用";
                    } else {
                        session.setAttribute("stockManageAdmin", admin);
                        code = 0;
                        msg = "登录成功";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    msg = "该账号无权限登录或登录失败";
                }
            }
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    /**
     * 设置角色
     */
    @Override
    public String setRole(Admin admin) {
        int code = 1;
        String msg = "设置角色失败";
        if (admin.getAdminId() == null) {
            msg = "参数非法";
        } else {
            Integer resultId = adminDao.updateAdmin(admin);
            if (resultId != null) {
                code = 0;
                msg = "设置角色成功";
            }
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    /**
     * 修改密码
     */
    @Override
    public String updatePwd(Integer adminId, String oldPwd, String newPwd) {
        int code = 1;
        String msg = "修改密码失败";
        if (adminId == null) {
            msg = "非法代理";
        } else {
            if (oldPwd.equals("") || oldPwd == null) {
                msg = "原始密码不能为空";
            } else {
                if (newPwd.equals("") || newPwd == null) {
                    msg = "新密码不能为空";
                } else {
                    Admin admin = adminDao.getAdminById(adminId, null, null);
                    String password = MD5Util.MD5Encode(newPwd, "utf-8");
                    if (!admin.getPassword().equals(MD5Util.MD5Encode(oldPwd, "utf-8"))) {
                        msg = "原始密码不正确";
                    } else {
                        admin.setPassword(password);
                        Integer updateId = adminDao.updateAdmin(admin);
                        if (updateId == null) {
                            msg = "修改密码失败";
                        } else {
                            msg = "修改密码成功";
                            code = 0;
                        }
                    }
                }
            }
        }
        return JsonUtil.buildObjectJson(code, msg, "");
    }

    @Override
    public Admin getInfoById(Integer adminId) {
        return adminDao.getAdminById(adminId, null, null);
    }

    //获取admin总数
    @Override
    public int getAdminCount() {
        return adminDao.getAdminCount();
    }


}
