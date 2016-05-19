package com.github.ignacy123.projectvocabulary.web.service;

import com.github.ignacy123.projectvocabulary.web.domain.MultiDictionary;
import com.github.ignacy123.projectvocabulary.web.domain.SessionWord;
import com.github.ignacy123.projectvocabulary.web.dto.TranslationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by ignacy on 21.04.16.
 */
@Service
public class DictionaryServiceImpl implements DictionaryService {

    private final MultiDictionary multiDictionary;
    private Random random;

    @Autowired
    public DictionaryServiceImpl(MultiDictionary multiDictionary) {
        random = new Random(System.currentTimeMillis());
        this.multiDictionary = multiDictionary;
    }

    @Override
    public TranslationResult translate(String word) {
        TranslationResult translationResult = new TranslationResult();
        translationResult.setTranslations(multiDictionary.getTranslations(word));
        return translationResult;
    }

    @Override
    public List<SessionWord> createSession(int size) {
        int multiDictionarySize = multiDictionary.getSize();
        List<SessionWord> sessionWords = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int keyIndex = random.nextInt(multiDictionarySize);
            String word = multiDictionary.getWord(keyIndex);
            List<String> translations = multiDictionary.getTranslations(keyIndex);
            SessionWord sessionWord = new SessionWord(word, translations);
            sessionWords.add(sessionWord);
        }
        return sessionWords;
    }
}
