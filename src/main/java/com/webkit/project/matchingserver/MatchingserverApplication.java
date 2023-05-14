package com.webkit.project.matchingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class MatchingserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(MatchingserverApplication.class, args);
    }

}
