package com.crashlytics.android.answers;

import kotlin.text.Typography;

public class CustomEvent extends AnswersEvent<CustomEvent> {
    private final String eventName;

    public CustomEvent(String eventName) {
        if (eventName == null) {
            throw new NullPointerException("eventName must not be null");
        }
        this.eventName = this.validator.limitStringLength(eventName);
    }

    String getCustomType() {
        return this.eventName;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{eventName:\"");
        stringBuilder.append(this.eventName);
        stringBuilder.append(Typography.quote);
        stringBuilder.append(", customAttributes:");
        stringBuilder.append(this.customAttributes);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
