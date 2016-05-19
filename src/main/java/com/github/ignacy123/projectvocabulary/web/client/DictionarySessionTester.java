package com.github.ignacy123.projectvocabulary.web.client;

import com.github.ignacy123.projectvocabulary.web.dto.SessionRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Created by ignacy on 21.04.16.
 */
public class DictionarySessionTester {
    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();
        SessionRequest sessionRequest = new SessionRequest();
        sessionRequest.setSize(20);

        ResponseEntity<List> resultResponseEntity = restTemplate.postForEntity(
                "http://localhost:8080/dictionary/session",
                sessionRequest,
                List.class);
        System.out.println(resultResponseEntity.getStatusCode());
        System.out.println(resultResponseEntity.getBody());

    }
}
