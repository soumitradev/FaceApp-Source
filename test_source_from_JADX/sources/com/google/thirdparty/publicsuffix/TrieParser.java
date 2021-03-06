package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap$Builder;
import com.google.common.collect.Lists;
import java.util.List;
import kotlin.text.Typography;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
class TrieParser {
    private static final Joiner PREFIX_JOINER = Joiner.on("");

    TrieParser() {
    }

    static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence encoded) {
        ImmutableMap$Builder<String, PublicSuffixType> builder = ImmutableMap.builder();
        int encodedLen = encoded.length();
        int idx = 0;
        while (idx < encodedLen) {
            idx += doParseTrieToBuilder(Lists.newLinkedList(), encoded.subSequence(idx, encodedLen), builder);
        }
        return builder.build();
    }

    private static int doParseTrieToBuilder(List<CharSequence> stack, CharSequence encoded, ImmutableMap$Builder<String, PublicSuffixType> builder) {
        int encodedLen = encoded.length();
        int idx = 0;
        char c = '\u0000';
        while (idx < encodedLen) {
            c = encoded.charAt(idx);
            if (c == Typography.amp || c == '?' || c == '!' || c == ':') {
                break;
            } else if (c == Constants.REMIX_URL_SEPARATOR) {
                break;
            } else {
                idx++;
            }
        }
        stack.add(0, reverse(encoded.subSequence(0, idx)));
        if (c == '!' || c == '?' || c == ':' || c == Constants.REMIX_URL_SEPARATOR) {
            String domain = PREFIX_JOINER.join(stack);
            if (domain.length() > 0) {
                builder.put(domain, PublicSuffixType.fromCode(c));
            }
        }
        idx++;
        if (c != '?' && c != Constants.REMIX_URL_SEPARATOR) {
            while (idx < encodedLen) {
                idx += doParseTrieToBuilder(stack, encoded.subSequence(idx, encodedLen), builder);
                if (encoded.charAt(idx) != '?') {
                    if (encoded.charAt(idx) == Constants.REMIX_URL_SEPARATOR) {
                    }
                }
                idx++;
                break;
            }
        }
        stack.remove(0);
        return idx;
    }

    private static CharSequence reverse(CharSequence s) {
        int length = s.length();
        int i = 1;
        if (length <= 1) {
            return s;
        }
        char[] buffer = new char[length];
        buffer[0] = s.charAt(length - 1);
        while (i < length) {
            buffer[i] = s.charAt((length - 1) - i);
            if (Character.isSurrogatePair(buffer[i], buffer[i - 1])) {
                swap(buffer, i - 1, i);
            }
            i++;
        }
        return new String(buffer);
    }

    private static void swap(char[] buffer, int f, int s) {
        char tmp = buffer[f];
        buffer[f] = buffer[s];
        buffer[s] = tmp;
    }
}
