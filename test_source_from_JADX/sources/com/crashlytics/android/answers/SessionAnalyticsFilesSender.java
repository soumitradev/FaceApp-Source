package com.crashlytics.android.answers;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.List;

class SessionAnalyticsFilesSender extends AbstractSpiCall implements FilesSender {
    static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
    static final String FILE_PARAM_NAME = "session_analytics_file_";
    private final String apiKey;

    public SessionAnalyticsFilesSender(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, String apiKey) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.POST);
        this.apiKey = apiKey;
    }

    public boolean send(List<File> files) {
        StringBuilder stringBuilder;
        HttpRequest httpRequest = getHttpRequest().header(AbstractSpiCall.HEADER_CLIENT_TYPE, "android").header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion()).header(AbstractSpiCall.HEADER_API_KEY, this.apiKey);
        int i = 0;
        for (File file : files) {
            stringBuilder = new StringBuilder();
            stringBuilder.append(FILE_PARAM_NAME);
            stringBuilder.append(i);
            httpRequest.part(stringBuilder.toString(), file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        Logger logger = Fabric.getLogger();
        String str = Answers.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Sending ");
        stringBuilder.append(files.size());
        stringBuilder.append(" analytics files to ");
        stringBuilder.append(getUrl());
        logger.d(str, stringBuilder.toString());
        int statusCode = httpRequest.code();
        Logger logger2 = Fabric.getLogger();
        String str2 = Answers.TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Response code for analytics file send is ");
        stringBuilder2.append(statusCode);
        logger2.d(str2, stringBuilder2.toString());
        return ResponseParser.parse(statusCode) == 0;
    }
}
