//package com.ylb.configurer;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import com.ylb.interceptor.HandlerInterceptorAdapter;
//@Configuration
//public class ConfigAdapter implements WebMvcConfigurer{
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new HandlerInterceptorAdapter())
//		.addPathPatterns("/**")                      //设置拦截
//	//	.excludePathPatterns("/login/login","/login/dologin","/login/shirologin")
//		//.excludePathPatterns("/api/*","/api/**")  //api接口
//		//.excludePathPatterns("/pc/*","/pc/**")  //pc端页面
//		//.excludePathPatterns("/websocket")  //websocket
//       // .excludePathPatterns("/error")
//		//.excludePathPatterns("/error/*")
//		.excludePathPatterns("/static/admin/**")  //忽略拦截
//		.excludePathPatterns("/static/pc/**")  //忽略拦截
//	//	.excludePathPatterns("/swagger-resources/**", "/webjars/**", "/v2/**", "/swagger-ui.html/**")
//		.excludePathPatterns("/group/*","/group/**")
//		.excludePathPatterns("/upload/*","/upload/**")
//		.excludePathPatterns("/officialAccount/*","/officialAccount/**")
//		.excludePathPatterns("/user/*","/user/**")
//		.excludePathPatterns("/client/*","/client/**")
//		.excludePathPatterns("/apk/*","/apk/**")
//		.excludePathPatterns("/*","/**");
//		
//		
//	}
//	@Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//    }
//}
