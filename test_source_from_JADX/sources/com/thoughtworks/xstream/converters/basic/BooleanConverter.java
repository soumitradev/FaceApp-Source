package com.thoughtworks.xstream.converters.basic;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;

public class BooleanConverter extends AbstractSingleValueConverter {
    public static final BooleanConverter BINARY = new BooleanConverter(AppEventsConstants.EVENT_PARAM_VALUE_YES, AppEventsConstants.EVENT_PARAM_VALUE_NO, true);
    public static final BooleanConverter TRUE_FALSE = new BooleanConverter(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE, "false", false);
    public static final BooleanConverter YES_NO = new BooleanConverter("yes", "no", false);
    private final boolean caseSensitive;
    private final String negative;
    private final String positive;

    public BooleanConverter(String positive, String negative, boolean caseSensitive) {
        this.positive = positive;
        this.negative = negative;
        this.caseSensitive = caseSensitive;
    }

    public BooleanConverter() {
        this(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE, "false", false);
    }

    public boolean shouldConvert(Class type, Object value) {
        return true;
    }

    public boolean canConvert(Class type) {
        if (!type.equals(Boolean.TYPE)) {
            if (!type.equals(Boolean.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        if (this.caseSensitive) {
            return this.positive.equals(str) ? Boolean.TRUE : Boolean.FALSE;
        }
        return this.positive.equalsIgnoreCase(str) ? Boolean.TRUE : Boolean.FALSE;
    }

    public String toString(Object obj) {
        Boolean value = (Boolean) obj;
        if (obj == null) {
            return null;
        }
        return value.booleanValue() ? this.positive : this.negative;
    }
}
