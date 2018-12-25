package org.catrobat.catroid.ui;

import android.app.Activity;
import org.catrobat.catroid.generated70026.R;

public final class BottomBar {
    private BottomBar() {
        throw new AssertionError();
    }

    public static void showBottomBar(Activity activity) {
        activity.findViewById(R.id.bottom_bar).setVisibility(0);
    }

    public static void hideBottomBar(Activity activity) {
        activity.findViewById(R.id.bottom_bar).setVisibility(8);
    }

    public static void showAddButton(Activity activity) {
        activity.findViewById(R.id.button_add).setVisibility(0);
    }

    public static void hideAddButton(Activity activity) {
        activity.findViewById(R.id.button_add).setVisibility(8);
    }

    public static void showPlayButton(Activity activity) {
        activity.findViewById(R.id.button_play).setVisibility(0);
    }

    public static void hidePlayButton(Activity activity) {
        activity.findViewById(R.id.button_play).setVisibility(8);
    }
}
