package io.fabric.sdk.android.services.settings;

import com.badlogic.gdx.net.HttpStatus;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequest.HttpRequestException;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall extends AbstractSpiCall implements SettingsSpiCall {
    static final String BUILD_VERSION_PARAM = "build_version";
    static final String DISPLAY_VERSION_PARAM = "display_version";
    static final String HEADER_ADVERTISING_TOKEN = "X-CRASHLYTICS-ADVERTISING-TOKEN";
    static final String HEADER_ANDROID_ID = "X-CRASHLYTICS-ANDROID-ID";
    static final String HEADER_DEVICE_MODEL = "X-CRASHLYTICS-DEVICE-MODEL";
    static final String HEADER_INSTALLATION_ID = "X-CRASHLYTICS-INSTALLATION-ID";
    static final String HEADER_OS_BUILD_VERSION = "X-CRASHLYTICS-OS-BUILD-VERSION";
    static final String HEADER_OS_DISPLAY_VERSION = "X-CRASHLYTICS-OS-DISPLAY-VERSION";
    static final String ICON_HASH = "icon_hash";
    static final String INSTANCE_PARAM = "instance";
    static final String SOURCE_PARAM = "source";

    public DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory) {
        this(kit, protocolAndHostOverride, url, requestFactory, HttpMethod.GET);
    }

    DefaultSettingsSpiCall(Kit kit, String protocolAndHostOverride, String url, HttpRequestFactory requestFactory, HttpMethod method) {
        super(kit, protocolAndHostOverride, url, requestFactory, method);
    }

    public JSONObject invoke(SettingsRequest requestData) {
        JSONObject toReturn;
        HttpRequest httpRequest = null;
        Logger logger;
        String str;
        StringBuilder stringBuilder;
        Logger logger2;
        String str2;
        StringBuilder stringBuilder2;
        try {
            Map<String, String> queryParams = getQueryParamsFor(requestData);
            httpRequest = applyHeadersTo(getHttpRequest(queryParams), requestData);
            logger = Fabric.getLogger();
            str = Fabric.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Requesting settings from ");
            stringBuilder.append(getUrl());
            logger.mo4809d(str, stringBuilder.toString());
            logger = Fabric.getLogger();
            str = Fabric.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Settings query params were: ");
            stringBuilder.append(queryParams);
            logger.mo4809d(str, stringBuilder.toString());
            toReturn = handleResponse(httpRequest);
            if (httpRequest != null) {
                logger2 = Fabric.getLogger();
                str2 = Fabric.TAG;
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Settings request ID: ");
                stringBuilder2.append(httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                logger2.mo4809d(str2, stringBuilder2.toString());
            }
        } catch (HttpRequestException e) {
            Fabric.getLogger().mo4812e(Fabric.TAG, "Settings request failed.", e);
            toReturn = null;
            if (httpRequest != null) {
                logger2 = Fabric.getLogger();
                str2 = Fabric.TAG;
                stringBuilder2 = new StringBuilder();
            }
        } catch (Throwable th) {
            if (httpRequest != null) {
                logger = Fabric.getLogger();
                str = Fabric.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Settings request ID: ");
                stringBuilder.append(httpRequest.header(AbstractSpiCall.HEADER_REQUEST_ID));
                logger.mo4809d(str, stringBuilder.toString());
            }
        }
        return toReturn;
    }

    JSONObject handleResponse(HttpRequest httpRequest) {
        int statusCode = httpRequest.code();
        Logger logger = Fabric.getLogger();
        String str = Fabric.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Settings result was: ");
        stringBuilder.append(statusCode);
        logger.mo4809d(str, stringBuilder.toString());
        if (requestWasSuccessful(statusCode)) {
            return getJsonObjectFrom(httpRequest.body());
        }
        logger = Fabric.getLogger();
        str = Fabric.TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Failed to retrieve settings from ");
        stringBuilder.append(getUrl());
        logger.mo4811e(str, stringBuilder.toString());
        return null;
    }

    boolean requestWasSuccessful(int httpStatusCode) {
        if (!(httpStatusCode == 200 || httpStatusCode == HttpStatus.SC_CREATED || httpStatusCode == HttpStatus.SC_ACCEPTED)) {
            if (httpStatusCode != HttpStatus.SC_NON_AUTHORITATIVE_INFORMATION) {
                return false;
            }
        }
        return true;
    }

    private JSONObject getJsonObjectFrom(String httpRequestBody) {
        try {
            return new JSONObject(httpRequestBody);
        } catch (Exception e) {
            Logger logger = Fabric.getLogger();
            String str = Fabric.TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed to parse settings JSON from ");
            stringBuilder.append(getUrl());
            logger.mo4810d(str, stringBuilder.toString(), e);
            logger = Fabric.getLogger();
            str = Fabric.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("Settings response ");
            stringBuilder.append(httpRequestBody);
            logger.mo4809d(str, stringBuilder.toString());
            return null;
        }
    }

    private Map<String, String> getQueryParamsFor(SettingsRequest requestData) {
        Map<String, String> queryParams = new HashMap();
        queryParams.put(BUILD_VERSION_PARAM, requestData.buildVersion);
        queryParams.put(DISPLAY_VERSION_PARAM, requestData.displayVersion);
        queryParams.put("source", Integer.toString(requestData.source));
        if (requestData.iconHash != null) {
            queryParams.put(ICON_HASH, requestData.iconHash);
        }
        String instanceId = requestData.instanceId;
        if (!CommonUtils.isNullOrEmpty(instanceId)) {
            queryParams.put(INSTANCE_PARAM, instanceId);
        }
        return queryParams;
    }

    private HttpRequest applyHeadersTo(HttpRequest request, SettingsRequest requestData) {
        applyNonNullHeader(request, AbstractSpiCall.HEADER_API_KEY, requestData.apiKey);
        applyNonNullHeader(request, AbstractSpiCall.HEADER_CLIENT_TYPE, "android");
        applyNonNullHeader(request, AbstractSpiCall.HEADER_CLIENT_VERSION, this.kit.getVersion());
        applyNonNullHeader(request, "Accept", "application/json");
        applyNonNullHeader(request, HEADER_DEVICE_MODEL, requestData.deviceModel);
        applyNonNullHeader(request, HEADER_OS_BUILD_VERSION, requestData.osBuildVersion);
        applyNonNullHeader(request, HEADER_OS_DISPLAY_VERSION, requestData.osDisplayVersion);
        applyNonNullHeader(request, HEADER_ADVERTISING_TOKEN, requestData.advertisingId);
        applyNonNullHeader(request, HEADER_INSTALLATION_ID, requestData.installationId);
        applyNonNullHeader(request, HEADER_ANDROID_ID, requestData.androidId);
        return request;
    }

    private void applyNonNullHeader(HttpRequest request, String key, String value) {
        if (value != null) {
            request.header(key, value);
        }
    }
}
