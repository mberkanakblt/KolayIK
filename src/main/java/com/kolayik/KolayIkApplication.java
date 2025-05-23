package com.kolayik;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableJpaRepositories("com.kolayik.repository")
@EntityScan("com.kolayik.entity")
public class KolayIkApplication {

    public static void main(String[] args) {
        SpringApplication.run(KolayIkApplication.class, args);
    }

}
