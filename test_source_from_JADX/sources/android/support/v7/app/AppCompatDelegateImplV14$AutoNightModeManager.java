package android.support.v7.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

@VisibleForTesting
final class AppCompatDelegateImplV14$AutoNightModeManager {
    private BroadcastReceiver mAutoTimeChangeReceiver;
    private IntentFilter mAutoTimeChangeReceiverFilter;
    private boolean mIsNight;
    private TwilightManager mTwilightManager;
    final /* synthetic */ AppCompatDelegateImplV14 this$0;

    /* renamed from: android.support.v7.app.AppCompatDelegateImplV14$AutoNightModeManager$1 */
    class C02291 extends BroadcastReceiver {
        C02291() {
        }

        public void onReceive(Context context, Intent intent) {
            AppCompatDelegateImplV14$AutoNightModeManager.this.dispatchTimeChanged();
        }
    }

    AppCompatDelegateImplV14$AutoNightModeManager(@NonNull AppCompatDelegateImplV14 this$0, TwilightManager twilightManager) {
        this.this$0 = this$0;
        this.mTwilightManager = twilightManager;
        this.mIsNight = twilightManager.isNight();
    }

    final int getApplyableNightMode() {
        this.mIsNight = this.mTwilightManager.isNight();
        return this.mIsNight ? 2 : 1;
    }

    final void dispatchTimeChanged() {
        boolean isNight = this.mTwilightManager.isNight();
        if (isNight != this.mIsNight) {
            this.mIsNight = isNight;
            this.this$0.applyDayNight();
        }
    }

    final void setup() {
        cleanup();
        if (this.mAutoTimeChangeReceiver == null) {
            this.mAutoTimeChangeReceiver = new C02291();
        }
        if (this.mAutoTimeChangeReceiverFilter == null) {
            this.mAutoTimeChangeReceiverFilter = new IntentFilter();
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET");
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
            this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
        }
        this.this$0.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
    }

    final void cleanup() {
        if (this.mAutoTimeChangeReceiver != null) {
            this.this$0.mContext.unregisterReceiver(this.mAutoTimeChangeReceiver);
            this.mAutoTimeChangeReceiver = null;
        }
    }
}
