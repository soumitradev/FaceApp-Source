package com.thoughtworks.xstream.security;

import kotlin.text.Typography;

public class WildcardTypePermission extends RegExpTypePermission {
    public WildcardTypePermission(String[] patterns) {
        super(getRegExpPatterns(patterns));
    }

    private static String[] getRegExpPatterns(String[] wildcards) {
        if (wildcards == null) {
            return null;
        }
        String[] regexps = new String[wildcards.length];
        for (int i = 0; i < wildcards.length; i++) {
            String wildcardExpression = wildcards[i];
            StringBuffer result = new StringBuffer(wildcardExpression.length() * 2);
            result.append("(?u)");
            int length = wildcardExpression.length();
            int j = 0;
            while (j < length) {
                char ch = wildcardExpression.charAt(j);
                if (!(ch == Typography.dollar || ch == '.')) {
                    if (ch != '?') {
                        if (ch != '|') {
                            switch (ch) {
                                case '(':
                                case ')':
                                case '+':
                                    break;
                                case '*':
                                    if (j + 1 < length && wildcardExpression.charAt(j + 1) == '*') {
                                        result.append("[\\P{C}]*");
                                        j++;
                                        break;
                                    }
                                    result.append("[\\P{C}&&[^");
                                    result.append('.');
                                    result.append("]]*");
                                    break;
                                default:
                                    switch (ch) {
                                        case '[':
                                        case '\\':
                                        case ']':
                                        case '^':
                                            break;
                                        default:
                                            result.append(ch);
                                            break;
                                    }
                            }
                        }
                    }
                    result.append('.');
                    j++;
                }
                result.append('\\');
                result.append(ch);
                j++;
            }
            regexps[i] = result.toString();
        }
        return regexps;
    }
}
