package org.springboardLogin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
//@EnableMongoRepositories(basePackages = "org.springboardLogin.Repositories")
public class Main {

    // Main class to start the Spring Boot application
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
