package com.ylb.shiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ylb.entity.Power;
import com.ylb.entity.Role;
import com.ylb.entity.User;
import com.ylb.service.AdminService;
import com.ylb.service.RoleService;
import com.ylb.service.UserService;
import com.ylb.util.TokenUtils;

/**
 * 自定义权限登录器
 * 
 * @author Administrator
 *
 */
public class AuthRealm extends AuthorizingRealm {
	Logger logger = LoggerFactory.getLogger(AuthRealm.class);
	@Autowired
	private UserService UserService;

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		// Admin admin = (Admin)
		// principals.fromRealm(this.getClass().getName()).iterator().next();//
		// 获取session中的用户
		User user = (User) principals.fromRealm(this.getClass().getName()).iterator().next();// 获取session中的用户
		List<String> permissions = new ArrayList<>();
//		int roleId = user.getRoleId();
//		Role role = roleService.getRoleById(roleId);
//
//		Set<Power> powers = role.getPowers();
//		if (powers.size() > 0) {
//			for (Power power : powers) {
//				permissions.add(power.getPowerName());
//			}
//		}

		logger.info("doGetAuthorizationInfo()");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addStringPermissions(permissions);// 将权限放入shiro中.
		return info;
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		logger.info("doGetAuthenticationInfo() :" + token.toString());
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;// 获取用户输入的token
		String username = utoken.getUsername();
		//char[] password = utoken.getPassword();
		String password = String.copyValueOf(utoken.getPassword());
		logger.info("doGetAuthenticationInfo() :" + username + "/" + password);
		User user = UserService.getUserByUsername(username);
		String tokenEx = username + password + new Date().getTime();
		tokenEx = TokenUtils.getToken(tokenEx);
		logger.info("doGetAuthenticationInfo() tokenEx:" +tokenEx);
		user.setToken(tokenEx);
		return new SimpleAuthenticationInfo(user, password, this.getClass().getName());// 放入shiro.调用CredentialsMatcher检验密码
	}

}
