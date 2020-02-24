package com.ylb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class StartupLoading implements CommandLineRunner{
	@Override
	public void run(String... var1) throws Exception {
		System.out.println("启动");	
	}
}
