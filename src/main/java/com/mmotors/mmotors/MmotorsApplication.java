package com.mmotors.mmotors;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class MmotorsApplication {



    public static void main(String[] args) {
        System.out.println("SERVEUR QUI DEMARRE ===");
        SpringApplication.run(MmotorsApplication.class, args);
    }

}
