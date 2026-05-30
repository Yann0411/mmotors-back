package com.mmotors.mmotors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class MmotorsApplication {



    public static void main(String[] args) {
        System.out.println("SERVEUR QUI DEMARRE ===");
        SpringApplication.run(MmotorsApplication.class, args);
    }

}
