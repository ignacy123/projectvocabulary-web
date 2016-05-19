package com.github.ignacy123.projectvocabulary.web.controller;

import com.github.ignacy123.projectvocabulary.web.domain.SessionWord;
import com.github.ignacy123.projectvocabulary.web.dto.SessionRequest;
import com.github.ignacy123.projectvocabulary.web.dto.TranslationRequest;
import com.github.ignacy123.projectvocabulary.web.dto.TranslationResult;
import com.github.ignacy123.projectvocabulary.web.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ignacy on 21.04.16.
 */
@Controller
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dictionary/translate")
    @ResponseBody
    public TranslationResult translate(@RequestBody TranslationRequest translationRequest) {
        return dictionaryService.translate(translationRequest.getWord());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/dictionary/session")
    @ResponseBody
    public List<SessionWord> createSession(@RequestBody SessionRequest sessionRequest) {
        return dictionaryService.createSession(sessionRequest.getSize());
    }
}
