package javax.jmdns.impl.constants;

import android.support.v4.internal.view.SupportMenu;
import com.facebook.internal.AnalyticsEvents;

public enum DNSOptionCode {
    Unknown(AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN, SupportMenu.USER_MASK),
    LLQ("LLQ", 1),
    UL("UL", 2),
    NSID("NSID", 3),
    Owner("Owner", 4);
    
    private final String _externalName;
    private final int _index;

    private DNSOptionCode(String name, int index) {
        this._externalName = name;
        this._index = index;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public static DNSOptionCode resultCodeForFlags(int optioncode) {
        int maskedIndex = optioncode;
        for (DNSOptionCode aCode : values()) {
            if (aCode._index == maskedIndex) {
                return aCode;
            }
        }
        return Unknown;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name());
        stringBuilder.append(" index ");
        stringBuilder.append(indexValue());
        return stringBuilder.toString();
    }
}
