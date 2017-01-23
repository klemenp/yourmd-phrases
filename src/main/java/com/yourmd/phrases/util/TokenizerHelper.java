package com.yourmd.phrases.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by klemen on 18.1.2017.
 */
public class TokenizerHelper {
    public static List<String> stringToLowercaseStringList(String string, String delimiters) {
        string = string.trim();

        List<String> results = new ArrayList<String>();

        StringTokenizer st = new StringTokenizer(string, delimiters);
        while (st.hasMoreElements()) {
            String stringValue = st.nextToken();
            if (stringValue.length()>0) {
                results.add(stringValue.toLowerCase());
            }
        }
        return results;
    }
}
