package org.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"org.common.**","org.web.**"})
public class EasyliveWebRunApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyliveWebRunApplication.class, args);
    }
}