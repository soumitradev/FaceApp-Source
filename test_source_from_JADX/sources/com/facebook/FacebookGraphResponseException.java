package com.facebook;

import name.antonsmirnov.firmata.FormatHelper;

public class FacebookGraphResponseException extends FacebookException {
    private final GraphResponse graphResponse;

    public FacebookGraphResponseException(GraphResponse graphResponse, String errorMessage) {
        super(errorMessage);
        this.graphResponse = graphResponse;
    }

    public final GraphResponse getGraphResponse() {
        return this.graphResponse;
    }

    public final String toString() {
        FacebookRequestError requestError = this.graphResponse != null ? this.graphResponse.getError() : null;
        StringBuilder errorStringBuilder = new StringBuilder().append("{FacebookGraphResponseException: ");
        String message = getMessage();
        if (message != null) {
            errorStringBuilder.append(message);
            errorStringBuilder.append(FormatHelper.SPACE);
        }
        if (requestError != null) {
            errorStringBuilder.append("httpResponseCode: ");
            errorStringBuilder.append(requestError.getRequestStatusCode());
            errorStringBuilder.append(", facebookErrorCode: ");
            errorStringBuilder.append(requestError.getErrorCode());
            errorStringBuilder.append(", facebookErrorType: ");
            errorStringBuilder.append(requestError.getErrorType());
            errorStringBuilder.append(", message: ");
            errorStringBuilder.append(requestError.getErrorMessage());
            errorStringBuilder.append("}");
        }
        return errorStringBuilder.toString();
    }
}
