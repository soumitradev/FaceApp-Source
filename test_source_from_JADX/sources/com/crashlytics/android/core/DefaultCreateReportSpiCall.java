package com.crashlytics.android.core;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.Map.Entry;

class DefaultCreateReportSpiCall extends AbstractSpiCall implements CreateReportSpiCall {
    static final String FILE_CONTENT_TYPE = "application/octet-stream";
    static final String FILE_PARAM = "report[file]";
    static final String IDENTIFIER_PARAM = "report[identifier]";
    static final String MULTI_FILE_PARAM = "report[file";

    public DefaultCreateReportSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.POST);
    }

    DefaultCreateReportSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    public boolean invoke(CreateReportRequest requestData) {
        HttpRequest httpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), requestData), requestData.report);
        Logger logger = Fabric.getLogger();
        String str = CrashlyticsCore.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Sending report to: ");
        stringBuilder.append(getUrl());
        logger.d(str, stringBuilder.toString());
        int statusCode = httpRequest.code();
        Logger logger2 = Fabric.getLogger();
        String str2 = CrashlyticsCore.TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Create report request ID: ");
        stringBuilder2.append(httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
        logger2.d(str2, stringBuilder2.toString());
        logger2 = Fabric.getLogger();
        str2 = CrashlyticsCore.TAG;
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Result was: ");
        stringBuilder2.append(statusCode);
        logger2.d(str2, stringBuilder2.toString());
        return ResponseParser.parse(statusCode) == 0;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, CreateReportRequest requestData) {
        request = request.header(AbstractSpiCall.HEADER_API_KEY, requestData.apiKey).header(AbstractSpiCall.HEADER_CLIENT_TYPE, "android").header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        for (Entry<String, String> entry : requestData.report.getCustomHeaders().entrySet()) {
            request = request.header(entry);
        }
        return request;
    }

    private HttpRequest applyMultipartDataTo(HttpRequest request, Report report) {
        request.part(IDENTIFIER_PARAM, report.getIdentifier());
        if (report.getFiles().length == 1) {
            Logger logger = Fabric.getLogger();
            String str = CrashlyticsCore.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Adding single file ");
            stringBuilder.append(report.getFileName());
            stringBuilder.append(" to report ");
            stringBuilder.append(report.getIdentifier());
            logger.d(str, stringBuilder.toString());
            return request.part(FILE_PARAM, report.getFileName(), FILE_CONTENT_TYPE, report.getFile());
        }
        int i = 0;
        for (File file : report.getFiles()) {
            Logger logger2 = Fabric.getLogger();
            String str2 = CrashlyticsCore.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Adding file ");
            stringBuilder2.append(file.getName());
            stringBuilder2.append(" to report ");
            stringBuilder2.append(report.getIdentifier());
            logger2.d(str2, stringBuilder2.toString());
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(MULTI_FILE_PARAM);
            stringBuilder3.append(i);
            stringBuilder3.append("]");
            request.part(stringBuilder3.toString(), file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        return request;
    }
}
