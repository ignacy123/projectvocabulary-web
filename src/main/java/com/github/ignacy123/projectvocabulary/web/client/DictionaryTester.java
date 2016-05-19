package com.github.ignacy123.projectvocabulary.web.client;

import com.github.ignacy123.projectvocabulary.web.dto.TranslationRequest;
import com.github.ignacy123.projectvocabulary.web.dto.TranslationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ignacy on 21.04.16.
 */
public class DictionaryTester {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> request = new HashMap<>();
        request.put("wordp", "apple");
        TranslationRequest request2 = new TranslationRequest();
        request2.setWord("apple");
        ResponseEntity<TranslationResult> resultResponseEntity = restTemplate.postForEntity("http://localhost:8080/dictionary/translate", request, TranslationResult.class);
        System.out.println(resultResponseEntity.getStatusCode());
        System.out.println(resultResponseEntity.getBody());
    }
}
