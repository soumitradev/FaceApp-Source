package org.catrobat.catroid.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFinder {
    private boolean matcherRun;
    private String result;

    public static String encodeSpecialChars(String string) {
        return Pattern.quote(string);
    }

    public boolean findBetween(String string, String start, String end) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(start);
        stringBuilder.append("(.*?)");
        stringBuilder.append(end);
        Matcher matcher = Pattern.compile(stringBuilder.toString(), 32).matcher(string);
        this.matcherRun = true;
        if (matcher.find()) {
            this.result = matcher.group(1);
            return true;
        }
        this.result = null;
        return false;
    }

    public String getResult() {
        if (this.matcherRun) {
            this.matcherRun = false;
            return this.result;
        }
        throw new IllegalStateException("You must call findBetween(String string, String start, String end) first.");
    }
}
