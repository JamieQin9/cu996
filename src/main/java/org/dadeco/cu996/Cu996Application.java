package org.dadeco.cu996;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication(scanBasePackages = "org.dadeco")
public class Cu996Application {

    public static void main(String[] args) {
        SpringApplication.run(Cu996Application.class, args);
    }

}
