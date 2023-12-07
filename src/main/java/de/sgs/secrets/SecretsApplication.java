package de.sgs.secrets;

import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import java.security.Principal;

@SpringBootApplication
public class SecretsApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(SecretsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SecretsApplication.class, args);
    }

}
