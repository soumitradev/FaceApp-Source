package android.support.design.widget;

import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.C0002R;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar.BaseCallback;
import android.support.design.widget.BaseTransientBottomBar.ContentViewCallback;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.TextView;

public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    @Nullable
    private BaseCallback<Snackbar> mCallback;

    private Snackbar(ViewGroup parent, View content, ContentViewCallback contentViewCallback) {
        super(parent, content, contentViewCallback);
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        ViewGroup parent = findSuitableParent(view);
        if (parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        }
        SnackbarContentLayout content = (SnackbarContentLayout) LayoutInflater.from(parent.getContext()).inflate(C0002R.layout.design_layout_snackbar_include, parent, false);
        Snackbar snackbar = new Snackbar(parent, content, content);
        snackbar.setText(text);
        snackbar.setDuration(duration);
        return snackbar;
    }

    @NonNull
    public static Snackbar make(@NonNull View view, @StringRes int resId, int duration) {
        return make(view, view.getResources().getText(resId), duration);
    }

    private static ViewGroup findSuitableParent(View view) {
        View view2 = view;
        ViewGroup fallback = null;
        while (!(view2 instanceof CoordinatorLayout)) {
            if (view2 instanceof FrameLayout) {
                if (view2.getId() == 16908290) {
                    return (ViewGroup) view2;
                }
                fallback = (ViewGroup) view2;
            }
            if (view2 != null) {
                ViewParent parent = view2.getParent();
                view2 = parent instanceof View ? (View) parent : null;
                continue;
            }
            if (view2 == null) {
                return fallback;
            }
        }
        return (ViewGroup) view2;
    }

    @NonNull
    public Snackbar setText(@NonNull CharSequence message) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getMessageView().setText(message);
        return this;
    }

    @NonNull
    public Snackbar setText(@StringRes int resId) {
        return setText(getContext().getText(resId));
    }

    @NonNull
    public Snackbar setAction(@StringRes int resId, OnClickListener listener) {
        return setAction(getContext().getText(resId), listener);
    }

    @NonNull
    public Snackbar setAction(CharSequence text, OnClickListener listener) {
        TextView tv = ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView();
        if (!TextUtils.isEmpty(text)) {
            if (listener != null) {
                tv.setVisibility(0);
                tv.setText(text);
                tv.setOnClickListener(new Snackbar$1(this, listener));
                return this;
            }
        }
        tv.setVisibility(8);
        tv.setOnClickListener(null);
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(ColorStateList colors) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(colors);
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(@ColorInt int color) {
        ((SnackbarContentLayout) this.mView.getChildAt(0)).getActionView().setTextColor(color);
        return this;
    }

    @Deprecated
    @NonNull
    public Snackbar setCallback(Snackbar$Callback callback) {
        if (this.mCallback != null) {
            removeCallback(this.mCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        this.mCallback = callback;
        return this;
    }
}
