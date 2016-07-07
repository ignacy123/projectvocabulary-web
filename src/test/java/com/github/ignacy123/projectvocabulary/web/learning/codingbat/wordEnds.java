package com.github.ignacy123.projectvocabulary.web.learning.codingbat;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by ignacy on 06.07.16.
 */
public class wordEnds {
    public String wordEnds(String str, String word) {
        String result = "";
        int a = 0;
        for(; a<str.length(); a++){
            if(str.length()>=a+word.length() && str.substring(a, a+word.length()).equals(word)){
                if(a != 0){
                    result += str.charAt(a-1);
                }
                if(a + word.length() < str.length()){

                    result += str.charAt(a+word.length());
                }
                a++;
            }

        }
        return result;
    }

    @Test
    public void wordEnds() throws Exception {
        assertThat(wordEnds("abc1xyz11", "1"), is("cxz11"));

    }
}
