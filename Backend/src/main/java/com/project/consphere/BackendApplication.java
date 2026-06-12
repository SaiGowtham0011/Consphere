package com.project.consphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
        System.out.println("Backend Is running on PORT: 8080, Open URL: http://localhost:8080\n"
                + "");
    }

}
