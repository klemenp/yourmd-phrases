package com.yourmd.phrases.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Phrase match test helper methods.
 *
 * Created by klemen on 18.1.2017.
 */
public class LevenshteinHelper {

    /**
     * Word match based on Levenshtein distance between two words.
     *
     * @param word1
     * @param word2
     * @return
     */
    private static double similarityOfWords(String word1, String word2) {
        String longer = word1, shorter = word2;
        if (word1.length() < word2.length()) {
            longer = word2; shorter = word1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; }
        return (longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength;
    }

    public static double similarityOfPhrases(List<String> inpustTextWords, List<String> phraseWords, double wordMatchTreshold) {
        int matchCount = maxPhraseWordMatchCount(inpustTextWords, phraseWords, wordMatchTreshold);
        return ((double)matchCount) / (double) phraseWords.size();
    }

    /**
     * Returns the maximum number of consecutive matched words.
     *
     * @param inputTextWords
     * @param phraseWords
     * @param wordMatchTreshold
     * @return
     */
    private static int maxPhraseWordMatchCount(List<String> inputTextWords, List<String> phraseWords, double wordMatchTreshold) {

        int maxPhraseMatchSize = 0;

        for (int inputTextWordsCnt = 0; inputTextWordsCnt < inputTextWords.size(); inputTextWordsCnt++) {

            // Find the first matching word.
            int startI = 0;
            int startJ = 0;
            boolean startFound = false;
            for (int i = inputTextWordsCnt; i < inputTextWords.size(); i++) {
                for (int j = 0; j < phraseWords.size(); j++) {
                    if (isWordMatch(inputTextWords.get(i), phraseWords.get(j), wordMatchTreshold)) {
                        startI = i;
                        startJ = j;
                        startFound = true;
                        break;
                    }
                }
                if (startFound) {
                    break;
                }
            }

            // Count the number of matched words
            if (!startFound)
            {
                continue;
            }
            int i = 1;
            boolean phraseMatchContinue = false;
            do {

                int testI = startI + i;
                int testJ = startJ + i;
                if (testI >= inputTextWords.size() || testJ >= phraseWords.size())
                {
                    break;
                }
                phraseMatchContinue = isWordMatch(inputTextWords.get(startI+i), phraseWords.get(startJ+i), wordMatchTreshold);
                if (phraseMatchContinue)
                {
                    i++;
                }
            }
            while (phraseMatchContinue);
            int phraseMatchSize = i;
            if (maxPhraseMatchSize<phraseMatchSize)
            {
                maxPhraseMatchSize = phraseMatchSize;
            }
        }

        return maxPhraseMatchSize;
    }

    private static boolean isWordMatch(String word1, String word2, double wordMatchTreshold)
    {
        return similarityOfWords(word1, word2) >= wordMatchTreshold;
    }


}
