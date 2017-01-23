package com.yourmd.phrases.model;

import java.util.List;

/**
 * Created by klemen on 18.1.2017.
 */
public class Phrase {
    private String phrase;
    private List<String> phraseWords;

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public List<String> getPhraseWords() {
        return phraseWords;
    }

    public void setPhraseWords(List<String> phraseWords) {
        this.phraseWords = phraseWords;
    }
}
