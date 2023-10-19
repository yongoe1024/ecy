package com.yongoe.ecy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.yongoe.ecy"})
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        System.out.println(("================================================启动完毕================================================"));
        System.out.println(("===============================  接口文档: http://localhost:8081/ecy/doc.html  ============================="));
    }

}
