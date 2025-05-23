package com.kolayik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kolayik.repository")
@EntityScan(basePackages = "com.kolayik.model")
public class KolayIkApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(KolayIkApplication.class, args);
    }
}
