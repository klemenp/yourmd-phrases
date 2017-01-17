package com.yourmd.phrases.services;

import java.util.List;

/**
 * Created by klemen on 16.1.2017.
 */
public interface PhrasesService {

    List<String> matchPhrases(String inputText);
}
