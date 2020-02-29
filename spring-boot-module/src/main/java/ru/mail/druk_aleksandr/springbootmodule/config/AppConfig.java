package ru.mail.druk_aleksandr.springbootmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"ru.mail.druk_aleksandr.config",
        "ru.mail.druk_aleksandr",
        "ru.mail.druk_aleksandr"})
public class AppConfig {
}
