package com.springrestjosh.demo;

import com.springrestjosh.demo.configuration.TwitterConfiguration;
import com.springrestjosh.demo.repository.TwitterRepository;
import com.springrestjosh.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Import(TwitterConfiguration.class)
@EnableJpaRepositories
@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
