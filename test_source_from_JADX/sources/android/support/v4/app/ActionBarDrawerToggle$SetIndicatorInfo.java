package android.support.v4.app;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.lang.reflect.Method;

class ActionBarDrawerToggle$SetIndicatorInfo {
    Method mSetHomeActionContentDescription;
    Method mSetHomeAsUpIndicator;
    ImageView mUpIndicatorView;

    ActionBarDrawerToggle$SetIndicatorInfo(Activity activity) {
        try {
            this.mSetHomeAsUpIndicator = ActionBar.class.getDeclaredMethod("setHomeAsUpIndicator", new Class[]{Drawable.class});
            this.mSetHomeActionContentDescription = ActionBar.class.getDeclaredMethod("setHomeActionContentDescription", new Class[]{Integer.TYPE});
        } catch (NoSuchMethodException e) {
            View home = activity.findViewById(16908332);
            if (home != null) {
                ViewGroup parent = (ViewGroup) home.getParent();
                if (parent.getChildCount() == 2) {
                    View first = parent.getChildAt(0);
                    View up = first.getId() == 16908332 ? parent.getChildAt(1) : first;
                    if (up instanceof ImageView) {
                        this.mUpIndicatorView = (ImageView) up;
                    }
                }
            }
        }
    }
}
