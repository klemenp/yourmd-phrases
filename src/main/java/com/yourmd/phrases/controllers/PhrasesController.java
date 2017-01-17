package com.yourmd.phrases.controllers;

import com.yourmd.phrases.services.PhrasesService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controller with REST API endpoints.
 * <p>
 * Created by klemen on 15.1.2017.
 */
@RestController
public class PhrasesController {
    public static final String PHRASES_ENDPOINT_PATH = "/matched_phrases";
    public static final String INPUT_TEXT_PARAM_NAME = "input_text";

    @Autowired
    private Logger log;

    @Autowired(required = true)
    private PhrasesService phrasesService;

    @RequestMapping(value = PHRASES_ENDPOINT_PATH, method = RequestMethod.GET)
    public List<String> phrases(@RequestParam(value = INPUT_TEXT_PARAM_NAME, required = true) String inputText) {
        log.info("Request to " + PHRASES_ENDPOINT_PATH + " with param " + INPUT_TEXT_PARAM_NAME + " value " + inputText);
        return phrasesService.matchPhrases(inputText);
    }
}
