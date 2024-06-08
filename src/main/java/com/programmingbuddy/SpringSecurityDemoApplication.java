package com.programmingbuddy;

import com.programmingbuddy.entity.UserInfo;
import com.programmingbuddy.repo.UserInfoRepo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class SpringSecurityDemoApplication implements CommandLineRunner {

	@Autowired
	UserInfoRepo userInfoRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		List<UserInfo> userInfoList = Arrays.asList(
				new UserInfo(1L,"user",passwordEncoder.encode("user"),"ROLE_USER"),
				new UserInfo(2L,"admin",passwordEncoder.encode("admin"),"ROLE_ADMIN")
		);

		userInfoRepo.saveAll(userInfoList);
	}
}
