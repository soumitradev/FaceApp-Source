package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

class SharedPreferencesCompat$EditorCompat$Helper {
    SharedPreferencesCompat$EditorCompat$Helper() {
    }

    public void apply(@NonNull Editor editor) {
        try {
            editor.apply();
        } catch (AbstractMethodError e) {
            editor.commit();
        }
    }
}
