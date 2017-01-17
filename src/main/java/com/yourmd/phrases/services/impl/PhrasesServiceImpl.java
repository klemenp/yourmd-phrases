package com.yourmd.phrases.services.impl;

import com.yourmd.phrases.repositories.PhraseRepository;
import com.yourmd.phrases.services.PhrasesService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

/**
 * Service containes business logic for phrase matching.
 * <p>
 * Created by klemen on 16.1.2017.
 */
@Service
public class PhrasesServiceImpl implements PhrasesService {
    @Autowired
    private Logger log;

    @Autowired(required = true)
    private PhraseRepository phraseRepository;

    /**
     * Returns all phrases contained by the inputText.
     *
     * @param inputText
     * @return
     */
    public List<String> matchPhrases(String inputText) {
        log.debug("Matching phrases for input text " + inputText + " ...");
        List<String> matchedPhrases = new LinkedList<String>();
        List<String> phrases = phraseRepository.getAll();

        for (String phrase : phrases) {
            if (inputText.contains(phrase)) {
                matchedPhrases.add(phrase);
            }
        }

        return matchedPhrases;
    }
}
