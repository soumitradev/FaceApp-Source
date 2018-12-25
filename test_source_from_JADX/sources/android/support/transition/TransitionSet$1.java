package android.support.transition;

import android.support.annotation.NonNull;

class TransitionSet$1 extends TransitionListenerAdapter {
    final /* synthetic */ TransitionSet this$0;
    final /* synthetic */ Transition val$nextTransition;

    TransitionSet$1(TransitionSet this$0, Transition transition) {
        this.this$0 = this$0;
        this.val$nextTransition = transition;
    }

    public void onTransitionEnd(@NonNull Transition transition) {
        this.val$nextTransition.runAnimators();
        transition.removeListener(this);
    }
}
