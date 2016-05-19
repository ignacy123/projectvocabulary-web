package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.SessionWord;
import com.github.ignacy123.projectvocabulary.web.dto.TranslationResult;

import java.util.List;

/**
 * Created by ignacy on 21.04.16.
 */
public interface DictionaryService {

    TranslationResult translate(String word);

    List<SessionWord> createSession(int size);
}
