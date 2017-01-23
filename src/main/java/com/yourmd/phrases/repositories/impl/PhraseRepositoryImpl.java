package com.yourmd.phrases.repositories.impl;

import com.yourmd.phrases.model.Phrase;
import com.yourmd.phrases.repositories.PhraseRepository;
import com.yourmd.phrases.util.TokenizerHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Phrases repository.
 *
 * Created by klemen on 16.1.2017.
 */
@Repository
public class PhraseRepositoryImpl implements PhraseRepository {
    private static final String PHRASES_RESOURCE = "/phrases.txt";
    private static final String NON_SIGNIFICANT_WOIRDS_RESOURCE = "/nonsignificant_words.txt";
    @Autowired
    private Logger log;
    private List<Phrase> phrases = new ArrayList<Phrase>();
    private Set<String> nonSignificantWords = new HashSet<>();

    public List<Phrase> getPhrases() {
        return Collections.unmodifiableList(phrases);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("Post constructing ...");
        loadPhrases();
        loadNonSignificantWords();
    }

    public Set<String> getNonSignificantWords() {
        return nonSignificantWords;
    }

    private void loadPhrases() {
        log.debug("Loading phrases from " + PHRASES_RESOURCE + "...");

        InputStream is = this.getClass().getResourceAsStream(PHRASES_RESOURCE);
        if (is == null) {
            throw new RuntimeException("No resource: " + PHRASES_RESOURCE);
        } else {
            try {
                BufferedReader br = null;
                try {
                    InputStreamReader isr = new InputStreamReader(is);
                    try {
                        br = new BufferedReader(isr);
                        String line = null;
                        try {
                            while ((line = br.readLine()) != null) {
                                Phrase phrase = new Phrase();
                                phrase.setPhrase(line);
                                List<String> words = TokenizerHelper.stringToLowercaseStringList(line, " -");
                                List<String> filteredWords = new ArrayList<>();
                                for (String word : words)
                                {
                                    if (!nonSignificantWords.contains(word))
                                    {
                                        filteredWords.add(word);
                                    }
                                }
                                phrase.setPhraseWords(filteredWords);
                                phrases.add(phrase);
                                log.trace("Loaded phrase " + line);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    } finally {
                        try {
                            isr.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }

                }
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        log.debug("Loaded " + phrases.size() + " phrases.");
    }

    private void loadNonSignificantWords() {
        log.debug("Loading non significant words from " + NON_SIGNIFICANT_WOIRDS_RESOURCE + "...");

        InputStream is = this.getClass().getResourceAsStream(NON_SIGNIFICANT_WOIRDS_RESOURCE);
        if (is == null) {
            throw new RuntimeException("No resource: " + NON_SIGNIFICANT_WOIRDS_RESOURCE);
        } else {
            try {
                BufferedReader br = null;
                try {
                    InputStreamReader isr = new InputStreamReader(is);
                    try {
                        br = new BufferedReader(isr);
                        String line = null;
                        try {
                            while ((line = br.readLine()) != null) {
                                nonSignificantWords.add(line);
                                log.trace("Loaded word " + line);
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    } finally {
                        try {
                            isr.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage(), e);
                        }
                    }
                } finally {
                    try {
                        br.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e.getMessage(), e);
                    }

                }
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
        log.debug("Loaded " + nonSignificantWords.size() + " nonsignificant words.");
    }
}
