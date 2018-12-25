package com.github.johnpersano.supertoasts.library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;
import com.badlogic.gdx.Input.Keys;
import com.github.johnpersano.supertoasts.library.utils.AccessibilityUtils;
import com.github.johnpersano.supertoasts.library.utils.AnimationUtils;
import com.github.johnpersano.supertoasts.library.utils.BackgroundUtils;
import com.google.android.gms.cast.CastStatusCodes;

public class SuperToast {
    private final Context mContext;
    private OnDismissListener mOnDismissListener;
    private Style mStyle;
    private final TextView mTextView;
    private final View mView;

    public interface OnDismissListener {
        void onDismiss(View view, Parcelable parcelable);
    }

    public SuperToast(@NonNull Context context) {
        this.mContext = context;
        this.mStyle = new Style();
        this.mStyle.type = 1;
        this.mView = onCreateView(context, (LayoutInflater) context.getSystemService("layout_inflater"), 1);
        this.mTextView = (TextView) this.mView.findViewById(C0507R.id.message);
    }

    public SuperToast(@NonNull Context context, @NonNull Style style) {
        this.mContext = context;
        this.mStyle = style;
        this.mView = onCreateView(context, (LayoutInflater) context.getSystemService("layout_inflater"), this.mStyle.type);
        this.mTextView = (TextView) this.mView.findViewById(C0507R.id.message);
    }

    protected SuperToast(@NonNull Context context, int type) {
        this.mContext = context;
        this.mStyle = new Style();
        this.mStyle.type = type;
        this.mView = onCreateView(context, (LayoutInflater) context.getSystemService("layout_inflater"), type);
        this.mTextView = (TextView) this.mView.findViewById(C0507R.id.message);
    }

    protected SuperToast(@NonNull Context context, @NonNull Style style, int type) {
        this.mContext = context;
        this.mStyle = style;
        this.mStyle.type = type;
        this.mView = onCreateView(context, (LayoutInflater) context.getSystemService("layout_inflater"), type);
        this.mTextView = (TextView) this.mView.findViewById(C0507R.id.message);
    }

    protected SuperToast(@NonNull Context context, @NonNull Style style, int type, @IdRes int viewGroupID) {
        this.mContext = context;
        this.mStyle = style;
        this.mStyle.type = type;
        if (type == 2) {
            this.mStyle.yOffset = BackgroundUtils.convertToDIP(24);
            this.mStyle.width = -1;
        }
        this.mView = onCreateView(context, (LayoutInflater) context.getSystemService("layout_inflater"), type);
        this.mTextView = (TextView) this.mView.findViewById(C0507R.id.message);
    }

    @SuppressLint({"InflateParams"})
    protected View onCreateView(Context context, LayoutInflater layoutInflater, int type) {
        return layoutInflater.inflate(C0507R.layout.supertoast, null);
    }

    @SuppressLint({"NewApi"})
    protected void onPrepareShow() {
        int sdkVersion = VERSION.SDK_INT;
        this.mTextView.setText(this.mStyle.message);
        this.mTextView.setTypeface(this.mTextView.getTypeface(), this.mStyle.messageTypefaceStyle);
        this.mTextView.setTextColor(this.mStyle.messageTextColor);
        this.mTextView.setTextSize((float) this.mStyle.messageTextSize);
        if (this.mStyle.messageIconResource > 0) {
            if (this.mStyle.messageIconPosition == 1) {
                this.mTextView.setCompoundDrawablesWithIntrinsicBounds(this.mStyle.messageIconResource, 0, 0, 0);
            } else if (this.mStyle.messageIconPosition == 4) {
                this.mTextView.setCompoundDrawablesWithIntrinsicBounds(0, this.mStyle.messageIconResource, 0, 0);
            } else if (this.mStyle.messageIconPosition == 2) {
                this.mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, this.mStyle.messageIconResource, 0);
            } else if (this.mStyle.messageIconPosition == 3) {
                this.mTextView.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, this.mStyle.messageIconResource);
            }
        }
        if (sdkVersion >= 16) {
            this.mView.setBackground(BackgroundUtils.getBackground(this.mStyle, this.mStyle.color));
            if (sdkVersion >= 21) {
                this.mView.setElevation(3.0f);
            }
        } else {
            this.mView.setBackgroundDrawable(BackgroundUtils.getBackground(this.mStyle, this.mStyle.color));
        }
        if (this.mStyle.frame == 3) {
            this.mTextView.setGravity(8388611);
            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) >= 3) {
                this.mStyle.xOffset = BackgroundUtils.convertToDIP(12);
                this.mStyle.yOffset = BackgroundUtils.convertToDIP(12);
                this.mStyle.width = BackgroundUtils.convertToDIP(288);
                this.mStyle.gravity = 8388691;
                GradientDrawable gradientDrawable = new GradientDrawable();
                gradientDrawable.setCornerRadius((float) BackgroundUtils.convertToDIP(2));
                gradientDrawable.setColor(this.mStyle.color);
                if (sdkVersion >= 16) {
                    this.mView.setBackground(gradientDrawable);
                } else {
                    this.mView.setBackgroundDrawable(gradientDrawable);
                }
            } else {
                this.mStyle.yOffset = 0;
                this.mStyle.width = -1;
            }
            if (this.mStyle.priorityColor != 0) {
                this.mView.findViewById(C0507R.id.border).setVisibility(0);
                this.mView.findViewById(C0507R.id.border).setBackgroundColor(this.mStyle.priorityColor);
            }
        }
        getStyle().timestamp = System.currentTimeMillis();
    }

    public SuperToast setText(String message) {
        this.mStyle.message = message;
        return this;
    }

    public String getText() {
        return this.mStyle.message;
    }

    public SuperToast setDuration(int duration) {
        if (duration > Style.DURATION_VERY_LONG) {
            Log.e(getClass().getName(), "SuperToast duration cannot exceed 4500ms.");
            this.mStyle.duration = Style.DURATION_VERY_LONG;
            return this;
        }
        this.mStyle.duration = duration;
        return this;
    }

    public int getDuration() {
        return this.mStyle.duration;
    }

    public SuperToast setColor(@ColorInt int color) {
        this.mStyle.color = color;
        return this;
    }

    @ColorInt
    public int getColor() {
        return this.mStyle.color;
    }

    public SuperToast setPriorityLevel(int priorityLevel) {
        this.mStyle.priorityLevel = priorityLevel;
        return this;
    }

    public int getPriorityLevel() {
        return this.mStyle.priorityLevel;
    }

    public SuperToast setPriorityColor(@ColorInt int priorityColor) {
        this.mStyle.priorityColor = priorityColor;
        return this;
    }

    @ColorInt
    public int getPriorityColor() {
        return this.mStyle.priorityColor;
    }

    public SuperToast setTypefaceStyle(int typefaceStyle) {
        this.mStyle.messageTypefaceStyle = typefaceStyle;
        return this;
    }

    public int getTypefaceStyle() {
        return this.mStyle.messageTypefaceStyle;
    }

    public SuperToast setTextColor(@ColorInt int textColor) {
        this.mStyle.messageTextColor = textColor;
        return this;
    }

    @ColorInt
    public int getTextColor() {
        return this.mStyle.messageTextColor;
    }

    public SuperToast setTextSize(int textSize) {
        if (textSize < 12) {
            Log.e(getClass().getName(), "SuperToast text size cannot be below 12.");
            this.mStyle.messageTextSize = 12;
            return this;
        } else if (textSize > 20) {
            Log.e(getClass().getName(), "SuperToast text size cannot be above 20.");
            this.mStyle.messageTextSize = 20;
            return this;
        } else {
            this.mStyle.messageTextSize = textSize;
            return this;
        }
    }

    public int getTextSize() {
        return this.mStyle.messageTextSize;
    }

    public SuperToast setIconResource(int iconPosition, @DrawableRes int iconResource) {
        this.mStyle.messageIconPosition = iconPosition;
        this.mStyle.messageIconResource = iconResource;
        return this;
    }

    public SuperToast setIconPosition(int iconPosition) {
        this.mStyle.messageIconPosition = iconPosition;
        return this;
    }

    public SuperToast setIconResource(@DrawableRes int iconResource) {
        this.mStyle.messageIconResource = iconResource;
        return this;
    }

    @DrawableRes
    public int getIconResource() {
        return this.mStyle.messageIconResource;
    }

    public int getIconPosition() {
        return this.mStyle.messageIconPosition;
    }

    public SuperToast setFrame(int frame) {
        this.mStyle.frame = frame;
        return this;
    }

    public int getFrame() {
        return this.mStyle.frame;
    }

    public SuperToast setAnimations(int animations) {
        this.mStyle.animations = animations;
        return this;
    }

    public int getAnimations() {
        return this.mStyle.animations;
    }

    public SuperToast setGravity(int gravity, int xOffset, int yOffset) {
        this.mStyle.gravity = gravity;
        this.mStyle.xOffset = xOffset;
        this.mStyle.yOffset = yOffset;
        return this;
    }

    public SuperToast setGravity(int gravity) {
        this.mStyle.gravity = gravity;
        return this;
    }

    public int getGravity() {
        return this.mStyle.gravity;
    }

    public int getXOffset() {
        return this.mStyle.xOffset;
    }

    public int getYOffset() {
        return this.mStyle.yOffset;
    }

    public SuperToast setWidth(int width) {
        this.mStyle.width = width;
        return this;
    }

    public int getWidth() {
        return this.mStyle.width;
    }

    public SuperToast setHeight(int height) {
        this.mStyle.height = height;
        return this;
    }

    public int getHeight() {
        return this.mStyle.height;
    }

    protected SuperToast setOnDismissListener(String tag, Parcelable token, @NonNull OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        this.mStyle.dismissTag = tag;
        this.mStyle.dismissToken = token;
        return this;
    }

    protected SuperToast setOnDismissListener(String tag, @NonNull OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        this.mStyle.dismissTag = tag;
        this.mStyle.dismissToken = null;
        return this;
    }

    public SuperToast setOnDismissListener(@NonNull OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        this.mStyle.dismissTag = "";
        this.mStyle.dismissToken = null;
        return this;
    }

    protected String getDismissTag() {
        return this.mStyle.dismissTag;
    }

    protected Parcelable getDismissToken() {
        return this.mStyle.dismissToken;
    }

    public OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    protected SuperToast setStyle(Style style) {
        this.mStyle = style;
        return this;
    }

    public Style getStyle() {
        return this.mStyle;
    }

    public Context getContext() {
        return this.mContext;
    }

    public View getView() {
        return this.mView;
    }

    public boolean isShowing() {
        return this.mView != null && this.mView.isShown();
    }

    protected LayoutParams getWindowManagerParams() {
        LayoutParams layoutParams = new LayoutParams();
        layoutParams.height = this.mStyle.height;
        layoutParams.width = this.mStyle.width;
        layoutParams.flags = Keys.NUMPAD_8;
        layoutParams.format = -3;
        layoutParams.windowAnimations = AnimationUtils.getSystemAnimationsResource(this.mStyle.animations);
        layoutParams.type = CastStatusCodes.APPLICATION_NOT_RUNNING;
        layoutParams.gravity = this.mStyle.gravity;
        layoutParams.x = this.mStyle.xOffset;
        layoutParams.y = this.mStyle.yOffset;
        return layoutParams;
    }

    public void show() {
        onPrepareShow();
        Toaster.getInstance().add(this);
        AccessibilityUtils.sendAccessibilityEvent(this.mView);
    }

    public void dismiss() {
        Toaster.getInstance().removeSuperToast(this);
    }

    public static void cancelAllSuperToasts() {
        Toaster.getInstance().cancelAllSuperToasts();
    }

    public static int getQueueSize() {
        return Toaster.getInstance().getQueue().size();
    }

    public static SuperToast create(@NonNull Context context, @NonNull String text, int duration) {
        return new SuperToast(context).setText(text).setDuration(duration);
    }

    public static SuperToast create(@NonNull Context context, @NonNull String text, int duration, @NonNull Style style) {
        return new SuperToast(context, style).setText(text).setDuration(duration);
    }
}
