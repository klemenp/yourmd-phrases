package com.yourmd.phrases.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests PhrasesService
 *
 * Created by klemen on 16.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhrasesServiceTest {
    @Autowired
    private Logger log;

    @Autowired
    protected PhrasesService phrasesService;

    @Test
    public void noMatchedPhrasesTest() throws Exception {
        assertEquals(0, phrasesService.matchPhrases("non existant phrases text").size());
    }

    @Test
    public void matchedPhrasesTest() throws Exception {
        List<String> matchedPhrases = phrasesService.matchPhrases("I have a sore throat and headache.");
        assertEquals(2, matchedPhrases.size());
        assertTrue(matchedPhrases.contains("sore throat"));
        assertTrue(matchedPhrases.contains("headache"));
    }
}
