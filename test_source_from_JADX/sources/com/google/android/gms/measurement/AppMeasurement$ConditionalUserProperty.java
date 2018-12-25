package com.google.android.gms.measurement;

import android.os.Bundle;
import android.support.annotation.Keep;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzcno;

@Hide
public class AppMeasurement$ConditionalUserProperty {
    @Keep
    @Hide
    public boolean mActive;
    @Keep
    @Hide
    public String mAppId;
    @Keep
    @Hide
    public long mCreationTimestamp;
    @Keep
    @Hide
    public String mExpiredEventName;
    @Keep
    @Hide
    public Bundle mExpiredEventParams;
    @Keep
    @Hide
    public String mName;
    @Keep
    @Hide
    public String mOrigin;
    @Keep
    @Hide
    public long mTimeToLive;
    @Keep
    @Hide
    public String mTimedOutEventName;
    @Keep
    @Hide
    public Bundle mTimedOutEventParams;
    @Keep
    @Hide
    public String mTriggerEventName;
    @Keep
    @Hide
    public long mTriggerTimeout;
    @Keep
    @Hide
    public String mTriggeredEventName;
    @Keep
    @Hide
    public Bundle mTriggeredEventParams;
    @Keep
    @Hide
    public long mTriggeredTimestamp;
    @Keep
    @Hide
    public Object mValue;

    public AppMeasurement$ConditionalUserProperty(AppMeasurement$ConditionalUserProperty appMeasurement$ConditionalUserProperty) {
        zzbq.zza(appMeasurement$ConditionalUserProperty);
        this.mAppId = appMeasurement$ConditionalUserProperty.mAppId;
        this.mOrigin = appMeasurement$ConditionalUserProperty.mOrigin;
        this.mCreationTimestamp = appMeasurement$ConditionalUserProperty.mCreationTimestamp;
        this.mName = appMeasurement$ConditionalUserProperty.mName;
        if (appMeasurement$ConditionalUserProperty.mValue != null) {
            this.mValue = zzcno.zzb(appMeasurement$ConditionalUserProperty.mValue);
            if (this.mValue == null) {
                this.mValue = appMeasurement$ConditionalUserProperty.mValue;
            }
        }
        this.mValue = appMeasurement$ConditionalUserProperty.mValue;
        this.mActive = appMeasurement$ConditionalUserProperty.mActive;
        this.mTriggerEventName = appMeasurement$ConditionalUserProperty.mTriggerEventName;
        this.mTriggerTimeout = appMeasurement$ConditionalUserProperty.mTriggerTimeout;
        this.mTimedOutEventName = appMeasurement$ConditionalUserProperty.mTimedOutEventName;
        if (appMeasurement$ConditionalUserProperty.mTimedOutEventParams != null) {
            this.mTimedOutEventParams = new Bundle(appMeasurement$ConditionalUserProperty.mTimedOutEventParams);
        }
        this.mTriggeredEventName = appMeasurement$ConditionalUserProperty.mTriggeredEventName;
        if (appMeasurement$ConditionalUserProperty.mTriggeredEventParams != null) {
            this.mTriggeredEventParams = new Bundle(appMeasurement$ConditionalUserProperty.mTriggeredEventParams);
        }
        this.mTriggeredTimestamp = appMeasurement$ConditionalUserProperty.mTriggeredTimestamp;
        this.mTimeToLive = appMeasurement$ConditionalUserProperty.mTimeToLive;
        this.mExpiredEventName = appMeasurement$ConditionalUserProperty.mExpiredEventName;
        if (appMeasurement$ConditionalUserProperty.mExpiredEventParams != null) {
            this.mExpiredEventParams = new Bundle(appMeasurement$ConditionalUserProperty.mExpiredEventParams);
        }
    }
}
