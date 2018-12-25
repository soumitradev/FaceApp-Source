package android.support.v7.app;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.annotation.ArrayRes;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertController.AlertParams;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;

public class AlertDialog$Builder {
    /* renamed from: P */
    private final AlertParams f0P;
    private final int mTheme;

    public AlertDialog$Builder(@NonNull Context context) {
        this(context, AlertDialog.resolveDialogTheme(context, 0));
    }

    public AlertDialog$Builder(@NonNull Context context, @StyleRes int themeResId) {
        this.f0P = new AlertParams(new ContextThemeWrapper(context, AlertDialog.resolveDialogTheme(context, themeResId)));
        this.mTheme = themeResId;
    }

    @NonNull
    public Context getContext() {
        return this.f0P.mContext;
    }

    public AlertDialog$Builder setTitle(@StringRes int titleId) {
        this.f0P.mTitle = this.f0P.mContext.getText(titleId);
        return this;
    }

    public AlertDialog$Builder setTitle(@Nullable CharSequence title) {
        this.f0P.mTitle = title;
        return this;
    }

    public AlertDialog$Builder setCustomTitle(@Nullable View customTitleView) {
        this.f0P.mCustomTitleView = customTitleView;
        return this;
    }

    public AlertDialog$Builder setMessage(@StringRes int messageId) {
        this.f0P.mMessage = this.f0P.mContext.getText(messageId);
        return this;
    }

    public AlertDialog$Builder setMessage(@Nullable CharSequence message) {
        this.f0P.mMessage = message;
        return this;
    }

    public AlertDialog$Builder setIcon(@DrawableRes int iconId) {
        this.f0P.mIconId = iconId;
        return this;
    }

    public AlertDialog$Builder setIcon(@Nullable Drawable icon) {
        this.f0P.mIcon = icon;
        return this;
    }

    public AlertDialog$Builder setIconAttribute(@AttrRes int attrId) {
        TypedValue out = new TypedValue();
        this.f0P.mContext.getTheme().resolveAttribute(attrId, out, true);
        this.f0P.mIconId = out.resourceId;
        return this;
    }

    public AlertDialog$Builder setPositiveButton(@StringRes int textId, OnClickListener listener) {
        this.f0P.mPositiveButtonText = this.f0P.mContext.getText(textId);
        this.f0P.mPositiveButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setPositiveButton(CharSequence text, OnClickListener listener) {
        this.f0P.mPositiveButtonText = text;
        this.f0P.mPositiveButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setPositiveButtonIcon(Drawable icon) {
        this.f0P.mPositiveButtonIcon = icon;
        return this;
    }

    public AlertDialog$Builder setNegativeButton(@StringRes int textId, OnClickListener listener) {
        this.f0P.mNegativeButtonText = this.f0P.mContext.getText(textId);
        this.f0P.mNegativeButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setNegativeButton(CharSequence text, OnClickListener listener) {
        this.f0P.mNegativeButtonText = text;
        this.f0P.mNegativeButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setNegativeButtonIcon(Drawable icon) {
        this.f0P.mNegativeButtonIcon = icon;
        return this;
    }

    public AlertDialog$Builder setNeutralButton(@StringRes int textId, OnClickListener listener) {
        this.f0P.mNeutralButtonText = this.f0P.mContext.getText(textId);
        this.f0P.mNeutralButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setNeutralButton(CharSequence text, OnClickListener listener) {
        this.f0P.mNeutralButtonText = text;
        this.f0P.mNeutralButtonListener = listener;
        return this;
    }

    public AlertDialog$Builder setNeutralButtonIcon(Drawable icon) {
        this.f0P.mNeutralButtonIcon = icon;
        return this;
    }

    public AlertDialog$Builder setCancelable(boolean cancelable) {
        this.f0P.mCancelable = cancelable;
        return this;
    }

    public AlertDialog$Builder setOnCancelListener(OnCancelListener onCancelListener) {
        this.f0P.mOnCancelListener = onCancelListener;
        return this;
    }

    public AlertDialog$Builder setOnDismissListener(OnDismissListener onDismissListener) {
        this.f0P.mOnDismissListener = onDismissListener;
        return this;
    }

    public AlertDialog$Builder setOnKeyListener(OnKeyListener onKeyListener) {
        this.f0P.mOnKeyListener = onKeyListener;
        return this;
    }

    public AlertDialog$Builder setItems(@ArrayRes int itemsId, OnClickListener listener) {
        this.f0P.mItems = this.f0P.mContext.getResources().getTextArray(itemsId);
        this.f0P.mOnClickListener = listener;
        return this;
    }

    public AlertDialog$Builder setItems(CharSequence[] items, OnClickListener listener) {
        this.f0P.mItems = items;
        this.f0P.mOnClickListener = listener;
        return this;
    }

    public AlertDialog$Builder setAdapter(ListAdapter adapter, OnClickListener listener) {
        this.f0P.mAdapter = adapter;
        this.f0P.mOnClickListener = listener;
        return this;
    }

    public AlertDialog$Builder setCursor(Cursor cursor, OnClickListener listener, String labelColumn) {
        this.f0P.mCursor = cursor;
        this.f0P.mLabelColumn = labelColumn;
        this.f0P.mOnClickListener = listener;
        return this;
    }

    public AlertDialog$Builder setMultiChoiceItems(@ArrayRes int itemsId, boolean[] checkedItems, OnMultiChoiceClickListener listener) {
        this.f0P.mItems = this.f0P.mContext.getResources().getTextArray(itemsId);
        this.f0P.mOnCheckboxClickListener = listener;
        this.f0P.mCheckedItems = checkedItems;
        this.f0P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog$Builder setMultiChoiceItems(CharSequence[] items, boolean[] checkedItems, OnMultiChoiceClickListener listener) {
        this.f0P.mItems = items;
        this.f0P.mOnCheckboxClickListener = listener;
        this.f0P.mCheckedItems = checkedItems;
        this.f0P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog$Builder setMultiChoiceItems(Cursor cursor, String isCheckedColumn, String labelColumn, OnMultiChoiceClickListener listener) {
        this.f0P.mCursor = cursor;
        this.f0P.mOnCheckboxClickListener = listener;
        this.f0P.mIsCheckedColumn = isCheckedColumn;
        this.f0P.mLabelColumn = labelColumn;
        this.f0P.mIsMultiChoice = true;
        return this;
    }

    public AlertDialog$Builder setSingleChoiceItems(@ArrayRes int itemsId, int checkedItem, OnClickListener listener) {
        this.f0P.mItems = this.f0P.mContext.getResources().getTextArray(itemsId);
        this.f0P.mOnClickListener = listener;
        this.f0P.mCheckedItem = checkedItem;
        this.f0P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog$Builder setSingleChoiceItems(Cursor cursor, int checkedItem, String labelColumn, OnClickListener listener) {
        this.f0P.mCursor = cursor;
        this.f0P.mOnClickListener = listener;
        this.f0P.mCheckedItem = checkedItem;
        this.f0P.mLabelColumn = labelColumn;
        this.f0P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog$Builder setSingleChoiceItems(CharSequence[] items, int checkedItem, OnClickListener listener) {
        this.f0P.mItems = items;
        this.f0P.mOnClickListener = listener;
        this.f0P.mCheckedItem = checkedItem;
        this.f0P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog$Builder setSingleChoiceItems(ListAdapter adapter, int checkedItem, OnClickListener listener) {
        this.f0P.mAdapter = adapter;
        this.f0P.mOnClickListener = listener;
        this.f0P.mCheckedItem = checkedItem;
        this.f0P.mIsSingleChoice = true;
        return this;
    }

    public AlertDialog$Builder setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.f0P.mOnItemSelectedListener = listener;
        return this;
    }

    public AlertDialog$Builder setView(int layoutResId) {
        this.f0P.mView = null;
        this.f0P.mViewLayoutResId = layoutResId;
        this.f0P.mViewSpacingSpecified = false;
        return this;
    }

    public AlertDialog$Builder setView(View view) {
        this.f0P.mView = view;
        this.f0P.mViewLayoutResId = 0;
        this.f0P.mViewSpacingSpecified = false;
        return this;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    @Deprecated
    public AlertDialog$Builder setView(View view, int viewSpacingLeft, int viewSpacingTop, int viewSpacingRight, int viewSpacingBottom) {
        this.f0P.mView = view;
        this.f0P.mViewLayoutResId = 0;
        this.f0P.mViewSpacingSpecified = true;
        this.f0P.mViewSpacingLeft = viewSpacingLeft;
        this.f0P.mViewSpacingTop = viewSpacingTop;
        this.f0P.mViewSpacingRight = viewSpacingRight;
        this.f0P.mViewSpacingBottom = viewSpacingBottom;
        return this;
    }

    @Deprecated
    public AlertDialog$Builder setInverseBackgroundForced(boolean useInverseBackground) {
        this.f0P.mForceInverseBackground = useInverseBackground;
        return this;
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public AlertDialog$Builder setRecycleOnMeasureEnabled(boolean enabled) {
        this.f0P.mRecycleOnMeasure = enabled;
        return this;
    }

    public AlertDialog create() {
        AlertDialog dialog = new AlertDialog(this.f0P.mContext, this.mTheme);
        this.f0P.apply(dialog.mAlert);
        dialog.setCancelable(this.f0P.mCancelable);
        if (this.f0P.mCancelable) {
            dialog.setCanceledOnTouchOutside(true);
        }
        dialog.setOnCancelListener(this.f0P.mOnCancelListener);
        dialog.setOnDismissListener(this.f0P.mOnDismissListener);
        if (this.f0P.mOnKeyListener != null) {
            dialog.setOnKeyListener(this.f0P.mOnKeyListener);
        }
        return dialog;
    }

    public AlertDialog show() {
        AlertDialog dialog = create();
        dialog.show();
        return dialog;
    }
}
