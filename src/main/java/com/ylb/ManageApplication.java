package com.ylb;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.ylb.util.openfire.SmackClient;

@SpringBootApplication
@EnableScheduling //开启定时任务
public class ManageApplication {

	public static void main(String[] args) {
		new Thread1().start();
		SpringApplication.run(ManageApplication.class, args);
	}

}

//@SpringBootApplication
//public class ManageApplication extends SpringBootServletInitializer {
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		//new Thread1().start();
//		return application.sources(ManageApplication.class);
//	}
//
//	public static void main(String[] args) {
//		SpringApplication.run(ManageApplication.class, args);
//	}
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		//new Thread1().start();
//	}
//}

class Thread1 extends Thread {
	@Override
	public void run() {
		System.out.println("onStartup()");
		new SmackClient().connect();
	}
}
