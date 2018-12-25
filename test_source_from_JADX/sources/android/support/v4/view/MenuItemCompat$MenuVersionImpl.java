package android.support.v4.view;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.view.MenuItem;

interface MenuItemCompat$MenuVersionImpl {
    int getAlphabeticModifiers(MenuItem menuItem);

    CharSequence getContentDescription(MenuItem menuItem);

    ColorStateList getIconTintList(MenuItem menuItem);

    Mode getIconTintMode(MenuItem menuItem);

    int getNumericModifiers(MenuItem menuItem);

    CharSequence getTooltipText(MenuItem menuItem);

    void setAlphabeticShortcut(MenuItem menuItem, char c, int i);

    void setContentDescription(MenuItem menuItem, CharSequence charSequence);

    void setIconTintList(MenuItem menuItem, ColorStateList colorStateList);

    void setIconTintMode(MenuItem menuItem, Mode mode);

    void setNumericShortcut(MenuItem menuItem, char c, int i);

    void setShortcut(MenuItem menuItem, char c, char c2, int i, int i2);

    void setTooltipText(MenuItem menuItem, CharSequence charSequence);
}
