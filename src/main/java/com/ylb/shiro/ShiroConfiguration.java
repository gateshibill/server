package com.ylb.shiro;

import java.util.LinkedHashMap;

import javax.servlet.Filter;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.mgt.SecurityManager;

@Configuration
public class ShiroConfiguration {
	Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);
	// 用于thymeleaf模板使用shiro标签
	@Bean
	public ShiroDialect ShiroDialect() {
		return new ShiroDialect();
	}

	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(manager);
		// 配置登录的url和登录成功的url
		bean.setUnauthorizedUrl("/user/unauthorized");
		bean.setLoginUrl("/user/login");
		//bean.setSuccessUrl("/home");
		// bean.setUnauthorizedUrl("/page403");
		// ?：匹配一个字符
		// *：匹配零个或多个字符
		// **：匹配零个或多个路径
		// 配置访问权限
		LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

		filterChainDefinitionMap.put("/static/admin/**", "anon"); // 表示可以匿名访问
		filterChainDefinitionMap.put("/static/pc/**", "anon"); // 表示可以匿名访问
		filterChainDefinitionMap.put("/login/shirologin", "anon");
		//filterChainDefinitionMap.put("/login/login", "anon");
		//filterChainDefinitionMap.put("/login/loginout", "anon");
		//filterChainDefinitionMap.put("/login/dologin", "anon");
		//filterChainDefinitionMap.put("/websocket/*", "anon");
		//filterChainDefinitionMap.put("/websocket/**", "anon");
		filterChainDefinitionMap.put("/swagger-resources/**", "anon");
		//filterChainDefinitionMap.put("/webjars/**", "anon");
		//filterChainDefinitionMap.put("/v2/**", "anon");
		//filterChainDefinitionMap.put("/swagger-ui.html/**", "anon");
		//filterChainDefinitionMap.put("/api/*", "anon"); // api接口
		//filterChainDefinitionMap.put("/api/**", "anon");// api接口
		//filterChainDefinitionMap.put("/pc/*", "anon"); // pc端页面
		//filterChainDefinitionMap.put("/pc/**", "anon");// pc端页面
		filterChainDefinitionMap.put("/*", "authc");// 表示需要认证才可以访问
		filterChainDefinitionMap.put("/**", "authc");// 表示需要认证才可以访问
		filterChainDefinitionMap.put("/*.*", "authc");
		filterChainDefinitionMap.put("/group/*", "anon");
		filterChainDefinitionMap.put("/group/**", "anon");
		filterChainDefinitionMap.put("/user/*", "authc");
		filterChainDefinitionMap.put("/user/**", "authc");
		filterChainDefinitionMap.put("/client/*", "anon");
		filterChainDefinitionMap.put("/client/**", "anon");

		LinkedHashMap<String, Filter> filtsMap = new LinkedHashMap<String, Filter>();
		filtsMap.put("authc", new ShiroFormAuthenticationFilter());
		bean.setFilters(filtsMap);
		bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		return bean;
	}

	// 配置核心安全事务管理器
	@Bean(name = "securityManager")
	public SecurityManager securityManager(@Qualifier("authRealm") AuthRealm authRealm) {
		System.err.println("--------------shiro已经加载----------------");
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		return manager;
	}

	// 配置自定义的权限登录器
	@Bean(name = "authRealm")
	public AuthRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		AuthRealm authRealm = new AuthRealm();
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}

	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher() {
		return new CredentialsMatcher();
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}
//	   /**
//     * shiro session的管理
//     */
//    @Bean("sessionManager")
//    public MySessionManager sessionManager() {
//        MySessionManager sessionManager = new MySessionManager();
//       // sessionManager.setGlobalSessionTimeout(18000 * 2);
//        return sessionManager;
//    }
}
