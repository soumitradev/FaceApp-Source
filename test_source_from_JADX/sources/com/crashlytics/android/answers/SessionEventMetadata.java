package com.crashlytics.android.answers;

final class SessionEventMetadata {
    public final String advertisingId;
    public final String androidId;
    public final String appBundleId;
    public final String appVersionCode;
    public final String appVersionName;
    public final String betaDeviceToken;
    public final String buildId;
    public final String deviceModel;
    public final String executionId;
    public final String installationId;
    public final Boolean limitAdTrackingEnabled;
    public final String osVersion;
    private String stringRepresentation;

    public SessionEventMetadata(String appBundleId, String executionId, String installationId, String androidId, String advertisingId, Boolean limitAdTrackingEnabled, String betaDeviceToken, String buildId, String osVersion, String deviceModel, String appVersionCode, String appVersionName) {
        this.appBundleId = appBundleId;
        this.executionId = executionId;
        this.installationId = installationId;
        this.androidId = androidId;
        this.advertisingId = advertisingId;
        this.limitAdTrackingEnabled = limitAdTrackingEnabled;
        this.betaDeviceToken = betaDeviceToken;
        this.buildId = buildId;
        this.osVersion = osVersion;
        this.deviceModel = deviceModel;
        this.appVersionCode = appVersionCode;
        this.appVersionName = appVersionName;
    }

    public String toString() {
        if (this.stringRepresentation == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("appBundleId=");
            sb.append(this.appBundleId);
            sb.append(", executionId=");
            sb.append(this.executionId);
            sb.append(", installationId=");
            sb.append(this.installationId);
            sb.append(", androidId=");
            sb.append(this.androidId);
            sb.append(", advertisingId=");
            sb.append(this.advertisingId);
            sb.append(", limitAdTrackingEnabled=");
            sb.append(this.limitAdTrackingEnabled);
            sb.append(", betaDeviceToken=");
            sb.append(this.betaDeviceToken);
            sb.append(", buildId=");
            sb.append(this.buildId);
            sb.append(", osVersion=");
            sb.append(this.osVersion);
            sb.append(", deviceModel=");
            sb.append(this.deviceModel);
            sb.append(", appVersionCode=");
            sb.append(this.appVersionCode);
            sb.append(", appVersionName=");
            this.stringRepresentation = sb.append(this.appVersionName).toString();
        }
        return this.stringRepresentation;
    }
}
