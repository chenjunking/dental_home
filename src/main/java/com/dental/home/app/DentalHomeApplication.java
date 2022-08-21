package com.dental.home.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@SpringBootApplication(scanBasePackages = "com.dental.home.app")
@EnableTransactionManagement //开启事务支持
public class DentalHomeApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DentalHomeApplication.class);
        // 设置Banner开启状态
        app.setBannerMode(Banner.Mode.LOG);
        app.run(args);
    }

    @PostConstruct
    public void init(){
        System.out.println("应用初始化！");
    }

    @PreDestroy
    public void destroy() {
        System.out.println("应用关闭！");
    }
}
