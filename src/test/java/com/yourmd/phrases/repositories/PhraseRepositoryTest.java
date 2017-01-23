package com.yourmd.phrases.repositories;

import com.yourmd.phrases.model.Phrase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests PhraseRepository.
 *
 * Created by klemen on 16.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhraseRepositoryTest {
    @Autowired
    private Logger log;

    @Autowired
    protected PhraseRepository phraseRepository;

    /**
     * Iterators must be independent of each other.
     *
     * @throws Exception
     */
    @Test
    public void concurrentIterationTest() throws Exception {

        Iterator<Phrase> iterator1 = phraseRepository.getPhrases().iterator();
        Iterator<Phrase> iterator2 = phraseRepository.getPhrases().iterator();

        assertNotEquals(iterator1, iterator2);

        iterator1.next();
        assertNotEquals(iterator1.next(), iterator2.next());
        assertNotEquals(iterator1.next(), iterator2.next());
        iterator2.next();
        assertEquals(iterator1.next(), iterator2.next());
        assertEquals(iterator1.next(), iterator2.next());
    }

}
