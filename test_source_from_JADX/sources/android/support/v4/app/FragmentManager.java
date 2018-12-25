package android.support.v4.app;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public abstract class FragmentManager {
    public static final int POP_BACK_STACK_INCLUSIVE = 1;

    public abstract void addOnBackStackChangedListener(FragmentManager$OnBackStackChangedListener fragmentManager$OnBackStackChangedListener);

    public abstract FragmentTransaction beginTransaction();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    public abstract boolean executePendingTransactions();

    public abstract Fragment findFragmentById(@IdRes int i);

    public abstract Fragment findFragmentByTag(String str);

    public abstract FragmentManager$BackStackEntry getBackStackEntryAt(int i);

    public abstract int getBackStackEntryCount();

    public abstract Fragment getFragment(Bundle bundle, String str);

    public abstract List<Fragment> getFragments();

    public abstract Fragment getPrimaryNavigationFragment();

    public abstract boolean isDestroyed();

    public abstract boolean isStateSaved();

    public abstract void popBackStack();

    public abstract void popBackStack(int i, int i2);

    public abstract void popBackStack(String str, int i);

    public abstract boolean popBackStackImmediate();

    public abstract boolean popBackStackImmediate(int i, int i2);

    public abstract boolean popBackStackImmediate(String str, int i);

    public abstract void putFragment(Bundle bundle, String str, Fragment fragment);

    public abstract void registerFragmentLifecycleCallbacks(FragmentManager$FragmentLifecycleCallbacks fragmentManager$FragmentLifecycleCallbacks, boolean z);

    public abstract void removeOnBackStackChangedListener(FragmentManager$OnBackStackChangedListener fragmentManager$OnBackStackChangedListener);

    public abstract Fragment$SavedState saveFragmentInstanceState(Fragment fragment);

    public abstract void unregisterFragmentLifecycleCallbacks(FragmentManager$FragmentLifecycleCallbacks fragmentManager$FragmentLifecycleCallbacks);

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Deprecated
    public FragmentTransaction openTransaction() {
        return beginTransaction();
    }

    public static void enableDebugLogging(boolean enabled) {
        FragmentManagerImpl.DEBUG = enabled;
    }
}
