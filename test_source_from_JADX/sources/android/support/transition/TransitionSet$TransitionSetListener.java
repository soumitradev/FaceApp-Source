package android.support.transition;

import android.support.annotation.NonNull;

class TransitionSet$TransitionSetListener extends TransitionListenerAdapter {
    TransitionSet mTransitionSet;

    TransitionSet$TransitionSetListener(TransitionSet transitionSet) {
        this.mTransitionSet = transitionSet;
    }

    public void onTransitionStart(@NonNull Transition transition) {
        if (!TransitionSet.access$000(this.mTransitionSet)) {
            this.mTransitionSet.start();
            TransitionSet.access$002(this.mTransitionSet, true);
        }
    }

    public void onTransitionEnd(@NonNull Transition transition) {
        TransitionSet.access$106(this.mTransitionSet);
        if (TransitionSet.access$100(this.mTransitionSet) == 0) {
            TransitionSet.access$002(this.mTransitionSet, false);
            this.mTransitionSet.end();
        }
        transition.removeListener(this);
    }
}
