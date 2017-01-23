package com.yourmd.phrases.controllers;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests PhraseController.
 *
 * Created by klemen on 16.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PhrasesControllerTest {
    protected ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private Logger log;
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void noMatchedPhrasesTest() throws Exception {
        assertEquals(0, getMatchedPhrases("nonexistant_phrases_text", HttpMethod.GET, 200).size());
    }

    @Test
    public void matchedPhrasesTest() throws Exception {
        List<String> matchedPhrases = getMatchedPhrases("I have a sore throat and headache.", HttpMethod.GET, 200);
        assertTrue(matchedPhrases.contains("sore throat"));
        assertTrue(matchedPhrases.contains("headache"));
    }

    @Test
    public void noParameterTest() throws Exception {
        assertNull(getMatchedPhrases(null, HttpMethod.GET, 400));
    }

    @Test
    public void invalidMethodTest() throws Exception {
        assertNull(getMatchedPhrases("Some input text", HttpMethod.PUT, 405));
        assertNull(getMatchedPhrases("Some input text", HttpMethod.POST, 405));
        assertNull(getMatchedPhrases("Some input text", HttpMethod.DELETE, 405));
    }

    protected List<String> getMatchedPhrases(String inputText, HttpMethod method, Integer codeCheck) throws Exception {

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath(PhrasesController.PHRASES_ENDPOINT_PATH);
        if (inputText != null) {
            uriBuilder = uriBuilder.queryParam(PhrasesController.INPUT_TEXT_PARAM_NAME, inputText);
        }
        String url = uriBuilder.build().toUriString();
        ResponseEntity<String> response = this.restTemplate.exchange(url, method, null, String.class);
        if (codeCheck != null) {
            assertEquals(codeCheck.intValue(), response.getStatusCode().value());
        }
        if (response.getStatusCode().value() != 200) {
            return null;
        }

        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(List.class, String.class);
        List<String> resultList = objectMapper.readValue(response.getBody(), javaType);
        return resultList;
    }
}
