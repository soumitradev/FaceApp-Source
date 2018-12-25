package org.catrobat.catroid.ui;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public final class UiUtils {
    private UiUtils() {
        throw new AssertionError("No.");
    }

    public static AppCompatActivity getActivityFromContextWrapper(Context context) {
        while (context instanceof ContextWrapper) {
            if (context instanceof AppCompatActivity) {
                break;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        if (context instanceof AppCompatActivity) {
            return (AppCompatActivity) context;
        }
        return null;
    }

    public static AppCompatActivity getActivityFromView(View view) {
        return getActivityFromContextWrapper(view.getContext());
    }
}
