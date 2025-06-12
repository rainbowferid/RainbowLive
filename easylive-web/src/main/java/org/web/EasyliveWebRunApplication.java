package org.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"org"})
@ComponentScan({"org.common.**","org.web.**"})
@EnableTransactionManagement
@EnableScheduling
public class EasyliveWebRunApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyliveWebRunApplication.class, args);
    }
}