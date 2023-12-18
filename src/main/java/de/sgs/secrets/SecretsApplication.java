package de.sgs.secrets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class SecretsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecretsApplication.class, args);
    }

}
