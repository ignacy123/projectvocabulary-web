package com.github.ignacy123.projectvocabulary.web.domain;

import java.util.List;

/**
 * Created by ignacy on 17.03.16.
 */
public class SessionWord {
    private final String word;
    private final List<String> translations;
    private Boolean guessResult;

    public SessionWord(String word, List<String> translations) {
        this.word = word;
        this.translations = translations;
    }

    public String getWord() {
        return word;
    }

    public List<String> getTranslations() {
        return translations;
    }

    public Boolean getGuessResult() {
        return guessResult;
    }

    public void setGuessResult(Boolean guessResult) {
        this.guessResult = guessResult;
    }

    public boolean matches(String translation) {
        for (String currentTranslation : translations) {
            if (translation.equals(currentTranslation)) {
                return true;
            }
        }
        return false;
    }
}
