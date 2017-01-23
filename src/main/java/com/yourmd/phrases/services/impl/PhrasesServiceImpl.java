package com.yourmd.phrases.services.impl;

import com.yourmd.phrases.model.Phrase;
import com.yourmd.phrases.repositories.PhraseRepository;
import com.yourmd.phrases.services.PhrasesService;
import com.yourmd.phrases.util.LevenshteinHelper;
import com.yourmd.phrases.util.TokenizerHelper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Service containes business logic for phrase matching.
 * <p>
 * Created by klemen on 16.1.2017.
 */
@Service
public class PhrasesServiceImpl implements PhrasesService {
    private static final double WORD_SIMILARITY_TRESHOLD = 0.8;
    private static final double PHRASE_MATCHED_WORDS_RATIO_TRESHOLD = 0.5;

    private static final String WORD_DELIMITERS = " -.,!:;";

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
        inputText = inputText.toLowerCase();
        List<String> matchedPhrases = new LinkedList<String>();
        List<Phrase> phrases = phraseRepository.getPhrases();

        List<String> inputWords = filterOutNonSignificantWords(TokenizerHelper.stringToLowercaseStringList(inputText, WORD_DELIMITERS));

        phrases.parallelStream().forEach(phrase->{

            double similarity = LevenshteinHelper.similarityOfPhrases(inputWords,phrase.getPhraseWords(), WORD_SIMILARITY_TRESHOLD);

            if (similarity> PHRASE_MATCHED_WORDS_RATIO_TRESHOLD) {
                synchronized (matchedPhrases) {
                    System.out.println("Found phrase " + phrase.getPhrase());
                    matchedPhrases.add(phrase.getPhrase());
                }
            }
        });

        return matchedPhrases;
    }

    /**
     * Filters out non-significant words
     *
     * @param words
     * @return
     */
    public List<String> filterOutNonSignificantWords(List<String> words)
    {
        List<String> resultWords = new ArrayList<>();
        for (String word : words)
        {
            if (!phraseRepository.getNonSignificantWords().contains(word))
            {
                resultWords.add(word);
            }
        }
        return resultWords;
    }
}
