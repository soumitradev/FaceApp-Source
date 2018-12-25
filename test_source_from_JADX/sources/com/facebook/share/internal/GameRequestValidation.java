package com.facebook.share.internal;

import com.facebook.internal.Validate;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.GameRequestContent.ActionType;

public class GameRequestValidation {
    public static void validate(GameRequestContent content) {
        Validate.notNull(content.getMessage(), "message");
        int i = 0;
        int i2 = content.getObjectId() != null ? 1 : 0;
        if (content.getActionType() != ActionType.ASKFOR) {
            if (content.getActionType() != ActionType.SEND) {
                if ((i2 ^ i) == 0) {
                    throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
                }
                i2 = 0;
                if (content.getRecipients() != null) {
                    i2 = 0 + 1;
                }
                if (content.getSuggestions() != null) {
                    i2++;
                }
                if (content.getFilters() != null) {
                    i2++;
                }
                if (i2 > 1) {
                    throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
                }
                return;
            }
        }
        i = 1;
        if ((i2 ^ i) == 0) {
            i2 = 0;
            if (content.getRecipients() != null) {
                i2 = 0 + 1;
            }
            if (content.getSuggestions() != null) {
                i2++;
            }
            if (content.getFilters() != null) {
                i2++;
            }
            if (i2 > 1) {
                throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
            }
            return;
        }
        throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
    }
}
