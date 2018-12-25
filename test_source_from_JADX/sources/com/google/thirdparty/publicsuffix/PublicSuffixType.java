package com.google.thirdparty.publicsuffix;

import com.google.common.annotations.GwtCompatible;
import org.catrobat.catroid.common.Constants;

@GwtCompatible
enum PublicSuffixType {
    PRIVATE(':', Constants.REMIX_URL_SEPARATOR),
    ICANN('!', '?');
    
    private final char innerNodeCode;
    private final char leafNodeCode;

    private PublicSuffixType(char innerNodeCode, char leafNodeCode) {
        this.innerNodeCode = innerNodeCode;
        this.leafNodeCode = leafNodeCode;
    }

    char getLeafNodeCode() {
        return this.leafNodeCode;
    }

    char getInnerNodeCode() {
        return this.innerNodeCode;
    }

    static PublicSuffixType fromCode(char code) {
        PublicSuffixType[] arr$ = values();
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            PublicSuffixType value = arr$[i$];
            if (value.getInnerNodeCode() != code) {
                if (value.getLeafNodeCode() != code) {
                    i$++;
                }
            }
            return value;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("No enum corresponding to given code: ");
        stringBuilder.append(code);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static PublicSuffixType fromIsPrivate(boolean isPrivate) {
        return isPrivate ? PRIVATE : ICANN;
    }
}
