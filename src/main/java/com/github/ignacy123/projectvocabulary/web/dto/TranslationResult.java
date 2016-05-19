package com.github.ignacy123.projectvocabulary.web.dto;

import java.util.List;
import java.util.Objects;

/**
 * Created by ignacy on 21.04.16.
 */
public class TranslationResult {
    private List<String> translations;

    public List<String> getTranslations() {
        return translations;
    }

    public void setTranslations(List<String> translations) {
        this.translations = translations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TranslationResult that = (TranslationResult) o;
        return Objects.equals(translations, that.translations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(translations);
    }

    @Override
    public String toString() {
        return "TranslationResult{" +
                "translations=" + translations +
                '}';
    }
}
