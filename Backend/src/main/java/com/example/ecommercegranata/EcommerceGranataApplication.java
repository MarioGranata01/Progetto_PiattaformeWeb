package com.example.ecommercegranata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.EcommerceGranata.entities")
public class EcommerceGranataApplication {

    public static void main(String[] args) {
        SpringApplication.run(EcommerceGranataApplication.class, args);
    }

}
