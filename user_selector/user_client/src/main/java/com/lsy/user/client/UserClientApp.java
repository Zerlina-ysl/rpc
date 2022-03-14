package com.lsy.user.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/14
 */
@SpringBootApplication
public class UserClientApp {
    public static void main(String[] args) {
        SpringApplication.run(UserClientApp.class,args);
        //问题 无法初始化控制器方法userService
    }
}
