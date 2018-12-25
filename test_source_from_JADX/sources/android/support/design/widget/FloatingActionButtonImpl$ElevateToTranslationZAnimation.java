package android.support.design.widget;

class FloatingActionButtonImpl$ElevateToTranslationZAnimation extends FloatingActionButtonImpl$ShadowAnimatorImpl {
    final /* synthetic */ FloatingActionButtonImpl this$0;

    FloatingActionButtonImpl$ElevateToTranslationZAnimation(FloatingActionButtonImpl floatingActionButtonImpl) {
        this.this$0 = floatingActionButtonImpl;
        super(floatingActionButtonImpl);
    }

    protected float getTargetShadowSize() {
        return this.this$0.mElevation + this.this$0.mPressedTranslationZ;
    }
}
