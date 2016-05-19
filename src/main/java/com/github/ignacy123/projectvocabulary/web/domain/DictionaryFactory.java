package com.github.ignacy123.projectvocabulary.web.domain;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by ignacy on 17.03.16.
 */
public class DictionaryFactory {
    private final Scanner sc;
    private final MultiDictionary multiDictionary = new MultiDictionary();

    private DictionaryFactory(InputStream inputStream) {
        sc = new Scanner(inputStream);
        sc.useDelimiter("");
    }

    public static MultiDictionary createDictionaryFromC5InputStream(InputStream inputStream) {
        DictionaryFactory dictionaryFactory = new DictionaryFactory(inputStream);
        return dictionaryFactory.scan();
    }

    private MultiDictionary scan() {
        try {
            scanC5InputStream();
            return multiDictionary;
        } finally {
            sc.close();
        }
    }

    private void scanC5InputStream() {
        outer:
        while (true) {
            while (true) {
                if (!sc.hasNextLine()) {
                    break outer;
                }
                if (sc.nextLine().equals("_____")) {
                    break;
                }
            }
            readTranslationUnit();
        }
    }

    private void readTranslationUnit() {
        sc.nextLine();
        String word = sc.nextLine();
        sc.nextLine();
        List<String> translations = new ArrayList<>();
        while (scannerHasNextTranslation()) {
            String translation = sc.nextLine();
            translation = removeOrdinal(translation);
            if (isTranslationValid(translation)) {
                translations.add(makeValid(translation));
            }
        }
        if (!translations.isEmpty()) {
            multiDictionary.addTranslation(word, translations);
        }
    }

    private boolean isTranslationValid(String translation) {
        return !translation.trim().startsWith("idiom:") && !translation.contains("=");
    }

    private boolean scannerHasNextTranslation() {
        return sc.hasNextLine() && !sc.hasNext("\\s*\n");
    }

    private String makeValid(String translation) {
        translation = translation.replaceAll("<.+>", "");
        translation = translation.replaceAll("\\(.+\\)", "");
        translation = translation.trim();
        return translation;
    }

    private String removeOrdinal(String translation) {
        translation = translation.replaceAll("\\d+\\. ", "");
        return translation;
    }


}

