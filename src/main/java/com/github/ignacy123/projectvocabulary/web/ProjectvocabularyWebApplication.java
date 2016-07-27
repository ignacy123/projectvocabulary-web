package com.github.ignacy123.projectvocabulary.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ProjectvocabularyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectvocabularyWebApplication.class, args);
    }
}
