package com.yourmd.phrases.repositories;

import com.yourmd.phrases.model.Phrase;

import java.util.List;
import java.util.Set;

/**
 * Created by klemen on 16.1.2017.
 */
public interface PhraseRepository {
    List<Phrase> getPhrases();
    Set<String> getNonSignificantWords();
}
