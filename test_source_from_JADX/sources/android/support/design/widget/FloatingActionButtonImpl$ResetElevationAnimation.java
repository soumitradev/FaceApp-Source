package android.support.design.widget;

class FloatingActionButtonImpl$ResetElevationAnimation extends FloatingActionButtonImpl$ShadowAnimatorImpl {
    final /* synthetic */ FloatingActionButtonImpl this$0;

    FloatingActionButtonImpl$ResetElevationAnimation(FloatingActionButtonImpl floatingActionButtonImpl) {
        this.this$0 = floatingActionButtonImpl;
        super(floatingActionButtonImpl);
    }

    protected float getTargetShadowSize() {
        return this.this$0.mElevation;
    }
}
