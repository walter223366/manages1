package com.ral.manages;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.ral.manages.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class ManagesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagesApplication.class, args);
    }

}
