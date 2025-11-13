package com.hsjh.nowdo;

import org.springframework.boot.SpringApplication;

public class TestNowdoApplication {

	public static void main(String[] args) {
		SpringApplication.from(NowdoApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
