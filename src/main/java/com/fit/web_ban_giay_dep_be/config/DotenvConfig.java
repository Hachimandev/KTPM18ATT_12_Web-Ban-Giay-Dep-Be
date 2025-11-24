package com.fit.web_ban_giay_dep_be.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DotenvConfig {

    public static final Dotenv dotenv = Dotenv.configure()
            .filename(".env")
            .load();

}