package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.MenuItem;

class MenuItemCompat$MenuItemCompatBaseImpl implements MenuItemCompat$MenuVersionImpl {
    MenuItemCompat$MenuItemCompatBaseImpl() {
    }

    public void setContentDescription(MenuItem item, CharSequence contentDescription) {
    }

    public CharSequence getContentDescription(MenuItem item) {
        return null;
    }

    public void setTooltipText(MenuItem item, CharSequence tooltipText) {
    }

    public CharSequence getTooltipText(MenuItem item) {
        return null;
    }

    public void setShortcut(MenuItem item, char numericChar, char alphaChar, int numericModifiers, int alphaModifiers) {
    }

    public void setAlphabeticShortcut(MenuItem item, char alphaChar, int alphaModifiers) {
    }

    public int getAlphabeticModifiers(MenuItem item) {
        return 0;
    }

    public void setNumericShortcut(MenuItem item, char numericChar, int numericModifiers) {
    }

    public int getNumericModifiers(MenuItem item) {
        return 0;
    }

    public void setIconTintList(MenuItem item, ColorStateList tint) {
    }

    public ColorStateList getIconTintList(MenuItem item) {
        return null;
    }

    public void setIconTintMode(MenuItem item, Mode tintMode) {
    }

    public Mode getIconTintMode(MenuItem item) {
        return null;
    }
}
