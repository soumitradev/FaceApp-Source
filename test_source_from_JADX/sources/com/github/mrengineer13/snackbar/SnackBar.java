package com.github.mrengineer13.snackbar;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.common.primitives.Ints;

public class SnackBar {
    public static final short LONG_SNACK = (short) 5000;
    public static final short MED_SNACK = (short) 3500;
    public static final short PERMANENT_SNACK = (short) 0;
    public static final short SHORT_SNACK = (short) 2000;
    private final OnClickListener mButtonListener = new C05131();
    private OnMessageClickListener mClickListener;
    private View mParentView;
    private SnackContainer mSnackContainer;
    private OnVisibilityChangeListener mVisibilityChangeListener;

    /* renamed from: com.github.mrengineer13.snackbar.SnackBar$1 */
    class C05131 implements OnClickListener {
        C05131() {
        }

        public void onClick(View v) {
            if (SnackBar.this.mClickListener != null && SnackBar.this.mSnackContainer.isShowing()) {
                SnackBar.this.mClickListener.onMessageClick(SnackBar.this.mSnackContainer.peek().mToken);
            }
            SnackBar.this.mSnackContainer.hide();
        }
    }

    public static class Builder {
        private int mActionIcon = 0;
        private String mActionMessage;
        private boolean mAnimateClear;
        private ColorStateList mBackgroundColor;
        private boolean mClear;
        private Context mContext;
        private short mDuration = SnackBar.MED_SNACK;
        private int mHeight;
        private String mMessage;
        private SnackBar mSnackBar;
        private ColorStateList mTextColor;
        private Parcelable mToken;
        private Typeface mTypeFace;

        public Builder(Activity activity) {
            this.mContext = activity.getApplicationContext();
            this.mSnackBar = new SnackBar(activity);
        }

        public Builder(Context context, View v) {
            this.mContext = context;
            this.mSnackBar = new SnackBar(context, v);
        }

        public Builder withMessage(String message) {
            this.mMessage = message;
            return this;
        }

        public Builder withMessageId(int messageId) {
            this.mMessage = this.mContext.getString(messageId);
            return this;
        }

        public Builder withActionMessage(String actionMessage) {
            this.mActionMessage = actionMessage;
            return this;
        }

        public Builder withActionMessageId(int actionMessageResId) {
            if (actionMessageResId > 0) {
                this.mActionMessage = this.mContext.getString(actionMessageResId);
            }
            return this;
        }

        public Builder withActionIconId(int id) {
            this.mActionIcon = id;
            return this;
        }

        public Builder withStyle(Style style) {
            this.mTextColor = getActionTextColor(style);
            return this;
        }

        public Builder withToken(Parcelable token) {
            this.mToken = token;
            return this;
        }

        public Builder withDuration(Short duration) {
            this.mDuration = duration.shortValue();
            return this;
        }

        public Builder withTextColorId(int colorId) {
            this.mTextColor = this.mContext.getResources().getColorStateList(colorId);
            return this;
        }

        public Builder withBackgroundColorId(int colorId) {
            this.mBackgroundColor = this.mContext.getResources().getColorStateList(colorId);
            return this;
        }

        public Builder withSnackBarHeight(int height) {
            this.mHeight = height;
            return this;
        }

        public Builder withOnClickListener(OnMessageClickListener onClickListener) {
            this.mSnackBar.setOnClickListener(onClickListener);
            return this;
        }

        public Builder withVisibilityChangeListener(OnVisibilityChangeListener visibilityChangeListener) {
            this.mSnackBar.setOnVisibilityChangeListener(visibilityChangeListener);
            return this;
        }

        public Builder withClearQueued() {
            return withClearQueued(true);
        }

        public Builder withClearQueued(boolean animate) {
            this.mAnimateClear = animate;
            this.mClear = true;
            return this;
        }

        public Builder withTypeFace(Typeface typeFace) {
            this.mTypeFace = typeFace;
            return this;
        }

        public SnackBar show() {
            Snack message = new Snack(this.mMessage, this.mActionMessage != null ? this.mActionMessage.toUpperCase() : null, this.mActionIcon, this.mToken, this.mDuration, this.mTextColor != null ? this.mTextColor : getActionTextColor(Style.DEFAULT), this.mBackgroundColor != null ? this.mBackgroundColor : this.mContext.getResources().getColorStateList(C0511R.color.sb__snack_bkgnd), this.mHeight != 0 ? this.mHeight : 0, this.mTypeFace);
            if (this.mClear) {
                this.mSnackBar.clear(this.mAnimateClear);
            }
            this.mSnackBar.showMessage(message);
            return this.mSnackBar;
        }

        private ColorStateList getActionTextColor(Style style) {
            switch (style) {
                case ALERT:
                    return this.mContext.getResources().getColorStateList(C0511R.color.sb__button_text_color_red);
                case INFO:
                    return this.mContext.getResources().getColorStateList(C0511R.color.sb__button_text_color_yellow);
                case CONFIRM:
                    return this.mContext.getResources().getColorStateList(C0511R.color.sb__button_text_color_green);
                case DEFAULT:
                    return this.mContext.getResources().getColorStateList(C0511R.color.sb__default_button_text_color);
                default:
                    return this.mContext.getResources().getColorStateList(C0511R.color.sb__default_button_text_color);
            }
        }
    }

    public interface OnMessageClickListener {
        void onMessageClick(Parcelable parcelable);
    }

    public interface OnVisibilityChangeListener {
        void onHide(int i);

        void onShow(int i);
    }

    public enum Style {
        DEFAULT,
        ALERT,
        CONFIRM,
        INFO
    }

    public SnackBar(Activity activity) {
        ViewGroup container = (ViewGroup) activity.findViewById(16908290);
        init(container, activity.getLayoutInflater().inflate(C0511R.layout.sb__snack, container, false));
    }

    public SnackBar(Context context, View v) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService("layout_inflater");
        inflater.inflate(C0511R.layout.sb__snack_container, (ViewGroup) v);
        init((ViewGroup) v, inflater.inflate(C0511R.layout.sb__snack, (ViewGroup) v, false));
    }

    private void init(ViewGroup container, View v) {
        this.mSnackContainer = (SnackContainer) container.findViewById(C0511R.id.snackContainer);
        if (this.mSnackContainer == null) {
            this.mSnackContainer = new SnackContainer(container);
        }
        this.mParentView = v;
        ((TextView) v.findViewById(C0511R.id.snackButton)).setOnClickListener(this.mButtonListener);
    }

    private void showMessage(Snack message) {
        this.mSnackContainer.showSnack(message, this.mParentView, this.mVisibilityChangeListener);
    }

    public int getHeight() {
        this.mParentView.measure(MeasureSpec.makeMeasureSpec(this.mParentView.getWidth(), Ints.MAX_POWER_OF_TWO), MeasureSpec.makeMeasureSpec(this.mParentView.getHeight(), Integer.MIN_VALUE));
        return this.mParentView.getMeasuredHeight();
    }

    public View getContainerView() {
        return this.mParentView;
    }

    private SnackBar setOnClickListener(OnMessageClickListener listener) {
        this.mClickListener = listener;
        return this;
    }

    private SnackBar setOnVisibilityChangeListener(OnVisibilityChangeListener listener) {
        this.mVisibilityChangeListener = listener;
        return this;
    }

    public void clear(boolean animate) {
        this.mSnackContainer.clearSnacks(animate);
    }

    public void clear() {
        clear(true);
    }

    public void hide() {
        this.mSnackContainer.hide();
        clear();
    }

    public void onRestoreInstanceState(Bundle state) {
        this.mSnackContainer.restoreState(state, this.mParentView);
    }

    public Bundle onSaveInstanceState() {
        return this.mSnackContainer.saveState();
    }
}
