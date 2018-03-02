package com.xzc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@MapperScan(basePackages = { "com.xzc.mapper" })
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
