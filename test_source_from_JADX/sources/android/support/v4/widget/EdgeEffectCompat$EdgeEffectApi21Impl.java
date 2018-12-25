package android.support.v4.widget;

import android.support.annotation.RequiresApi;
import android.widget.EdgeEffect;

@RequiresApi(21)
class EdgeEffectCompat$EdgeEffectApi21Impl extends EdgeEffectCompat$EdgeEffectBaseImpl {
    EdgeEffectCompat$EdgeEffectApi21Impl() {
    }

    public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
        edgeEffect.onPull(deltaDistance, displacement);
    }
}
