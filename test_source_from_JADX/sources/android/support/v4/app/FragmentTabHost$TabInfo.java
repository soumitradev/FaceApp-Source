package android.support.v4.app;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

final class FragmentTabHost$TabInfo {
    @Nullable
    final Bundle args;
    @NonNull
    final Class<?> clss;
    Fragment fragment;
    @NonNull
    final String tag;

    FragmentTabHost$TabInfo(@NonNull String _tag, @NonNull Class<?> _class, @Nullable Bundle _args) {
        this.tag = _tag;
        this.clss = _class;
        this.args = _args;
    }
}
