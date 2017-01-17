package com.yourmd.phrases.repositories.impl;

import com.yourmd.phrases.repositories.PhraseRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Phrases repository.
 *
 * Created by klemen on 16.1.2017.
 */
@Repository
public class PhraseRepositoryImpl implements PhraseRepository {
    private static final String PHRASES_RESOURCE = "/phrases.txt";
    @Autowired
    private Logger log;
    private List<String> phrases = new ArrayList<String>();

    public List<String> getAll() {
        return Collections.unmodifiableList(phrases);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("Post constructing ...");
        loadPhrases();
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
                                phrases.add(line);
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
}
