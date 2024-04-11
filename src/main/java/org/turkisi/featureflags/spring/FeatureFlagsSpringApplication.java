package org.turkisi.featureflags.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class FeatureFlagsSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeatureFlagsSpringApplication.class, args);
    }

}
