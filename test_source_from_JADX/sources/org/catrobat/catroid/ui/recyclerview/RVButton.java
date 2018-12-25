package org.catrobat.catroid.ui.recyclerview;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;

public class RVButton {
    public Drawable drawable;
    public int id;
    @Nullable
    public String subtitle;
    public String title;

    public RVButton(int id, Drawable drawable, String name) {
        this.id = id;
        this.drawable = drawable;
        this.title = name;
    }

    public RVButton(int id, Drawable drawable, String name, @Nullable String subtitle) {
        this.id = id;
        this.drawable = drawable;
        this.title = name;
        this.subtitle = subtitle;
    }
}
