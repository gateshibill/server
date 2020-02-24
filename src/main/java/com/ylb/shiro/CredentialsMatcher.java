package com.ylb.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ylb.controller.UserController;
import com.ylb.dao.OfPropertyDao;
import com.ylb.entity.User;
import com.ylb.service.UserService;
import com.ylb.util.openfire.client.auth.AuthFactory;

/**
 * 自定义密码比较器
 * 
 * @author 46678
 *
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {
	Logger logger = LoggerFactory.getLogger(CredentialsMatcher.class);
	@Autowired
	private UserService UserService;
	@Autowired
	private OfPropertyDao ofPropertyDao;

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		String username = new String(utoken.getUsername());
		String password = new String(utoken.getPassword());
		logger.info("doCredentialsMatch():" + username + "/" + password);
		return openfireCredent(username, password);
	}

	public Boolean openfireCredent(String username, String password) {
		try {
			User user = UserService.getUserByUsername(username);
			logger.info("openfireCredent():" + user.getUsername() + "/" + user.getPassword());
			// 服务端配置加密key
			String propValue = ofPropertyDao.getOfProperty(UserController.passwordKey).getPropValue();
			String encryptedPassword = user.getEncrypted();
			String storedKey = user.getStoredKey();
			String salt = user.getSalt();
			AuthFactory.setPasswordKey(propValue);
			Boolean isAuthenticated = AuthFactory.checkPassword(password, encryptedPassword, salt, storedKey);
			logger.info("propValue:" + propValue);
			logger.info("encryptedPassword:" + encryptedPassword);
			logger.info("storedKey:" + storedKey);
			logger.info("salt:" + salt);
			//logger.info("salt:" + salt);
			logger.info("openfireCredent():" + username + "/" + password+"/"+encryptedPassword+"/"+isAuthenticated);
			return isAuthenticated;
		} catch (Exception e) {
			e.printStackTrace();
		    	
			return false;
		}
	}

}
