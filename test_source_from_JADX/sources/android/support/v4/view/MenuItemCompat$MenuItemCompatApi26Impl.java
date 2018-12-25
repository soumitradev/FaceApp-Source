package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.support.annotation.RequiresApi;
import android.view.MenuItem;

@RequiresApi(26)
class MenuItemCompat$MenuItemCompatApi26Impl extends MenuItemCompat$MenuItemCompatBaseImpl {
    MenuItemCompat$MenuItemCompatApi26Impl() {
    }

    public void setContentDescription(MenuItem item, CharSequence contentDescription) {
        item.setContentDescription(contentDescription);
    }

    public CharSequence getContentDescription(MenuItem item) {
        return item.getContentDescription();
    }

    public void setTooltipText(MenuItem item, CharSequence tooltipText) {
        item.setTooltipText(tooltipText);
    }

    public CharSequence getTooltipText(MenuItem item) {
        return item.getTooltipText();
    }

    public void setShortcut(MenuItem item, char numericChar, char alphaChar, int numericModifiers, int alphaModifiers) {
        item.setShortcut(numericChar, alphaChar, numericModifiers, alphaModifiers);
    }

    public void setAlphabeticShortcut(MenuItem item, char alphaChar, int alphaModifiers) {
        item.setAlphabeticShortcut(alphaChar, alphaModifiers);
    }

    public int getAlphabeticModifiers(MenuItem item) {
        return item.getAlphabeticModifiers();
    }

    public void setNumericShortcut(MenuItem item, char numericChar, int numericModifiers) {
        item.setNumericShortcut(numericChar, numericModifiers);
    }

    public int getNumericModifiers(MenuItem item) {
        return item.getNumericModifiers();
    }

    public void setIconTintList(MenuItem item, ColorStateList tint) {
        item.setIconTintList(tint);
    }

    public ColorStateList getIconTintList(MenuItem item) {
        return item.getIconTintList();
    }

    public void setIconTintMode(MenuItem item, Mode tintMode) {
        item.setIconTintMode(tintMode);
    }

    public Mode getIconTintMode(MenuItem item) {
        return item.getIconTintMode();
    }
}
