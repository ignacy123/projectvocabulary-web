package com.github.ignacy123.projectvocabulary.web.config;

import com.github.ignacy123.projectvocabulary.web.domain.DictionaryFactory;
import com.github.ignacy123.projectvocabulary.web.domain.MultiDictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;

/**
 * Created by ignacy on 28.04.16.
 */
@Configuration
public class DictionaryConfig {
    @Bean
    public MultiDictionary multiDictionary() {
        InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream("dictionary.c5");
        MultiDictionary dictionary = DictionaryFactory.createDictionaryFromC5InputStream(inputStream);
        return dictionary;
    }
}
