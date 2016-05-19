package com.github.ignacy123.projectvocabulary.web.domain;

import java.util.*;

/**
 * Created by ignacy on 17.03.16.
 */
public class MultiDictionary {
    private Map<String, List<String>> translations = new HashMap<>();
    private List<String> words = new ArrayList<>();

    public void addTranslation(String word, List<String> wordTranslations) {
        words.add(word);
        translations.put(word, wordTranslations);
    }

    public List<String> getTranslations(String word) {
        List<String> wordTranslations = translations.get(word);
        if (wordTranslations == null) {
            return Collections.EMPTY_LIST;
        }
        return Collections.unmodifiableList(wordTranslations);

    }

    public int getSize() {
        return translations.size();
    }

    public String getWord(int index) {
        return words.get(index);
    }

    public List<String> getTranslations(int index) {
        String word = words.get(index);
        return getTranslations(word);
    }

}

