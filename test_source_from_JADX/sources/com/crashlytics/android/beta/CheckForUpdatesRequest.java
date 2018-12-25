package com.crashlytics.android.beta;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest extends AbstractSpiCall {
    static final String BETA_SOURCE = "3";
    static final String BUILD_VERSION = "build_version";
    static final String DISPLAY_VERSION = "display_version";
    static final String HEADER_BETA_TOKEN = "X-CRASHLYTICS-BETA-TOKEN";
    static final String INSTANCE = "instance";
    static final String SDK_ANDROID_DIR_TOKEN_TYPE = "3";
    static final String SOURCE = "source";
    private final CheckForUpdatesResponseTransform responseTransform;

    static String createBetaTokenHeaderValueFor(String betaDeviceToken) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("3:");
        stringBuilder.append(betaDeviceToken);
        return stringBuilder.toString();
    }

    public CheckForUpdatesRequest(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, CheckForUpdatesResponseTransform responseTransform) {
        super(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
        this.responseTransform = responseTransform;
    }

    public CheckForUpdatesResponse invoke(String apiKey, String betaDeviceToken, BuildProperties buildProps) {
        String requestId;
        HttpRequest httpRequest = null;
        Logger logger;
        String str;
        StringBuilder stringBuilder;
        try {
            Map<String, String> queryParams = getQueryParamsFor(buildProps);
            httpRequest = applyHeadersTo(getHttpRequest(queryParams), apiKey, betaDeviceToken);
            logger = Fabric.getLogger();
            str = Beta.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Checking for updates from ");
            stringBuilder.append(getUrl());
            logger.d(str, stringBuilder.toString());
            logger = Fabric.getLogger();
            str = Beta.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Checking for updates query params are: ");
            stringBuilder.append(queryParams);
            logger.d(str, stringBuilder.toString());
            if (httpRequest.ok()) {
                Fabric.getLogger().d(Beta.TAG, "Checking for updates was successful");
                CheckForUpdatesResponse fromJson = this.responseTransform.fromJson(new JSONObject(httpRequest.body()));
                if (httpRequest != null) {
                    String requestId2 = httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                    Logger logger2 = Fabric.getLogger();
                    String str2 = Fabric.TAG;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Checking for updates request ID: ");
                    stringBuilder2.append(requestId2);
                    logger2.d(str2, stringBuilder2.toString());
                }
                return fromJson;
            }
            logger = Fabric.getLogger();
            str = Beta.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Checking for updates failed. Response code: ");
            stringBuilder.append(httpRequest.code());
            logger.e(str, stringBuilder.toString());
            if (httpRequest != null) {
                requestId = httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                logger = Fabric.getLogger();
                str = Fabric.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Checking for updates request ID: ");
                stringBuilder.append(requestId);
                logger.d(str, stringBuilder.toString());
            }
            return null;
        } catch (Exception e) {
            logger = Fabric.getLogger();
            str = Beta.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Error while checking for updates from ");
            stringBuilder.append(getUrl());
            logger.e(str, stringBuilder.toString(), e);
            if (httpRequest != null) {
                requestId = httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                logger = Fabric.getLogger();
                str = Fabric.TAG;
                stringBuilder = new StringBuilder();
            }
        } catch (Throwable th) {
            if (httpRequest != null) {
                requestId = httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID);
                logger = Fabric.getLogger();
                str = Fabric.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Checking for updates request ID: ");
                stringBuilder.append(requestId);
                logger.d(str, stringBuilder.toString());
            }
        }
    }

    private HttpRequest applyHeadersTo(HttpRequest request, String apiKey, String betaDeviceToken) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(AbstractSpiCall.CRASHLYTICS_USER_AGENT);
        stringBuilder.append(this.kit.getVersion());
        return request.header("Accept", "application/json").header("User-Agent", stringBuilder.toString()).header(AbstractSpiCall.HEADER_DEVELOPER_TOKEN, "470fa2b4ae81cd56ecbcda9735803434cec591fa").header(AbstractSpiCall.HEADER_CLIENT_TYPE, "android").header(AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion()).header(AbstractSpiCall.HEADER_API_KEY, apiKey).header(HEADER_BETA_TOKEN, createBetaTokenHeaderValueFor(betaDeviceToken));
    }

    private Map<String, String> getQueryParamsFor(BuildProperties buildProps) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put(BUILD_VERSION, buildProps.versionCode);
        queryParams.put(DISPLAY_VERSION, buildProps.versionName);
        queryParams.put(INSTANCE, buildProps.buildId);
        queryParams.put("source", "3");
        return queryParams;
    }
}
