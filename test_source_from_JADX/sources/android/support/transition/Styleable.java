package android.support.transition;

import android.annotation.SuppressLint;
import android.support.annotation.StyleableRes;

@SuppressLint({"InlinedApi"})
class Styleable {
    @StyleableRes
    static final int[] ARC_MOTION = new int[]{16843901, 16843902, 16843903};
    @StyleableRes
    static final int[] CHANGE_BOUNDS = new int[]{16843983};
    @StyleableRes
    static final int[] CHANGE_TRANSFORM = new int[]{16843964, 16843965};
    @StyleableRes
    static final int[] FADE = new int[]{16843745};
    @StyleableRes
    static final int[] PATTERN_PATH_MOTION = new int[]{16843978};
    @StyleableRes
    static final int[] SLIDE = new int[]{16843824};
    @StyleableRes
    static final int[] TRANSITION = new int[]{16843073, 16843160, 16843746, 16843855};
    @StyleableRes
    static final int[] TRANSITION_MANAGER = new int[]{16843741, 16843742, 16843743};
    @StyleableRes
    static final int[] TRANSITION_SET = new int[]{16843744};
    @StyleableRes
    static final int[] TRANSITION_TARGET = new int[]{16842799, 16843740, 16843841, 16843842, 16843853, 16843854};
    @StyleableRes
    static final int[] VISIBILITY_TRANSITION = new int[]{16843900};

    interface Slide {
        @StyleableRes
        public static final int SLIDE_EDGE = 0;
    }

    interface Transition {
        @StyleableRes
        public static final int DURATION = 1;
        @StyleableRes
        public static final int INTERPOLATOR = 0;
        @StyleableRes
        public static final int MATCH_ORDER = 3;
        @StyleableRes
        public static final int START_DELAY = 2;
    }

    interface VisibilityTransition {
        @StyleableRes
        public static final int TRANSITION_VISIBILITY_MODE = 0;
    }

    Styleable() {
    }
}
