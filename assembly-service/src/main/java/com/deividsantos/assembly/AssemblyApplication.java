package com.deividsantos.assembly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AssemblyApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssemblyApplication.class, args);
    }

}
