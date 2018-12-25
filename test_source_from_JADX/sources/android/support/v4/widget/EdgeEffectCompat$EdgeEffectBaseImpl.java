package android.support.v4.widget;

import android.widget.EdgeEffect;

class EdgeEffectCompat$EdgeEffectBaseImpl {
    EdgeEffectCompat$EdgeEffectBaseImpl() {
    }

    public void onPull(EdgeEffect edgeEffect, float deltaDistance, float displacement) {
        edgeEffect.onPull(deltaDistance);
    }
}
