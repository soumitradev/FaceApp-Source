package com.github.johnpersano.supertoasts.library;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ProgressBar;
import com.github.johnpersano.supertoasts.library.SuperToast.OnDismissListener;
import com.github.johnpersano.supertoasts.library.utils.BackgroundUtils;
import com.github.johnpersano.supertoasts.library.utils.ListenerUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class SuperActivityToast extends SuperToast {
    private static final String BUNDLE_KEY = "0x532e412e542e";
    private Context mContext;
    private boolean mFromOrientationChange;
    private OnButtonClickListener mOnButtonClickListener;
    private ProgressBar mProgressBar;
    private Style mStyle;
    private View mView;
    private ViewGroup mViewGroup;

    /* renamed from: com.github.johnpersano.supertoasts.library.SuperActivityToast$1 */
    class C05081 implements OnClickListener {
        short clicked = (short) 0;

        C05081() {
        }

        public void onClick(View view) {
            if (this.clicked <= (short) 0) {
                this.clicked = (short) (this.clicked + 1);
                SuperActivityToast.this.mOnButtonClickListener.onClick(view, SuperActivityToast.this.getButtonToken());
                SuperActivityToast.this.dismiss();
            }
        }
    }

    /* renamed from: com.github.johnpersano.supertoasts.library.SuperActivityToast$2 */
    class C05092 implements OnTouchListener {
        int timesTouched;

        C05092() {
        }

        public boolean onTouch(View v, MotionEvent motionEvent) {
            if (this.timesTouched == 0 && motionEvent.getAction() == 0) {
                SuperActivityToast.this.dismiss();
            }
            this.timesTouched++;
            return false;
        }
    }

    public interface OnButtonClickListener {
        void onClick(View view, Parcelable parcelable);
    }

    public SuperActivityToast(@NonNull Context context) {
        super(context);
        if (context instanceof Activity) {
            this.mContext = context;
            this.mStyle = getStyle();
            this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(16908290);
            return;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    public SuperActivityToast(@NonNull Context context, @NonNull Style style) {
        super(context, style);
        if (context instanceof Activity) {
            this.mContext = context;
            this.mStyle = style;
            this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(16908290);
            return;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    public SuperActivityToast(@NonNull Context context, int type) {
        super(context, type);
        if (context instanceof Activity) {
            this.mContext = context;
            this.mStyle = getStyle();
            this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(16908290);
            return;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    public SuperActivityToast(@NonNull Context context, @NonNull Style style, int type) {
        super(context, style, type);
        if (context instanceof Activity) {
            this.mContext = context;
            this.mStyle = getStyle();
            this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(16908290);
            return;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    public SuperActivityToast(@NonNull Context context, @NonNull Style style, int type, @IdRes int viewGroupId) {
        super(context, style, type, viewGroupId);
        if (context instanceof Activity) {
            this.mContext = context;
            this.mStyle = getStyle();
            this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(viewGroupId);
            if (this.mViewGroup == null) {
                String name = getClass().getName();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not find a ViewGroup with id ");
                stringBuilder.append(String.valueOf(viewGroupId));
                Log.e(name, stringBuilder.toString());
                this.mViewGroup = (ViewGroup) ((Activity) context).findViewById(16908290);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    protected View onCreateView(@NonNull Context context, LayoutInflater layoutInflater, int type) {
        if (context instanceof Activity) {
            switch (type) {
                case 1:
                    this.mView = layoutInflater.inflate(C0507R.layout.supertoast, (ViewGroup) ((Activity) context).findViewById(16908290), false);
                    break;
                case 2:
                    this.mView = layoutInflater.inflate(C0507R.layout.supertoast_button, (ViewGroup) ((Activity) context).findViewById(16908290), false);
                    break;
                case 3:
                    this.mView = layoutInflater.inflate(C0507R.layout.supertoast_progress_circle, (ViewGroup) ((Activity) context).findViewById(16908290), false);
                    this.mProgressBar = (ProgressBar) this.mView.findViewById(C0507R.id.progress_bar);
                    break;
                case 4:
                    this.mView = layoutInflater.inflate(C0507R.layout.supertoast_progress_bar, (ViewGroup) ((Activity) context).findViewById(16908290), false);
                    this.mProgressBar = (ProgressBar) this.mView.findViewById(C0507R.id.progress_bar);
                    break;
                default:
                    this.mView = layoutInflater.inflate(C0507R.layout.supertoast, (ViewGroup) ((Activity) context).findViewById(16908290), false);
                    break;
            }
            return this.mView;
        }
        throw new IllegalArgumentException("SuperActivityToast Context must be an Activity.");
    }

    public SuperToast setOnDismissListener(String tag, Parcelable token, @NonNull OnDismissListener onDismissListener) {
        return super.setOnDismissListener(tag, token, onDismissListener);
    }

    public SuperToast setOnDismissListener(String tag, @NonNull OnDismissListener onDismissListener) {
        return super.setOnDismissListener(tag, onDismissListener);
    }

    public String getDismissTag() {
        return super.getDismissTag();
    }

    public Parcelable getDismissToken() {
        return super.getDismissToken();
    }

    protected SuperActivityToast fromOrientationChange() {
        this.mFromOrientationChange = true;
        return this;
    }

    protected boolean isFromOrientationChange() {
        return this.mFromOrientationChange;
    }

    public SuperActivityToast setIndeterminate(boolean indeterminate) {
        this.mStyle.isIndeterminate = indeterminate;
        this.mStyle.touchToDismiss = true;
        return this;
    }

    public boolean isIndeterminate() {
        return this.mStyle.isIndeterminate;
    }

    public SuperActivityToast setTouchToDismiss(boolean touchToDismiss) {
        this.mStyle.touchToDismiss = touchToDismiss;
        return this;
    }

    public boolean isTouchDismissible() {
        return this.mStyle.touchToDismiss;
    }

    public SuperActivityToast setButtonText(String buttonText) {
        this.mStyle.buttonText = buttonText;
        return this;
    }

    public String getButtonText() {
        return this.mStyle.buttonText;
    }

    public SuperActivityToast setButtonTypefaceStyle(int buttonTypefaceStyle) {
        this.mStyle.buttonTypefaceStyle = buttonTypefaceStyle;
        return this;
    }

    public int getButtonTypefaceStyle() {
        return this.mStyle.buttonTypefaceStyle;
    }

    public SuperActivityToast setButtonTextColor(@ColorInt int buttonTextColor) {
        this.mStyle.buttonTextColor = buttonTextColor;
        return this;
    }

    @ColorInt
    public int getButtonTextColor() {
        return this.mStyle.buttonTextColor;
    }

    public SuperActivityToast setButtonTextSize(int buttonTextSize) {
        this.mStyle.buttonTextSize = buttonTextSize;
        return this;
    }

    public int getButtonTextSize() {
        return this.mStyle.buttonTextSize;
    }

    public SuperActivityToast setButtonDividerColor(@ColorInt int buttonDividerColor) {
        this.mStyle.buttonDividerColor = buttonDividerColor;
        return this;
    }

    @ColorInt
    public int getButtonDividerColor() {
        return this.mStyle.buttonDividerColor;
    }

    public SuperActivityToast setButtonIconResource(@DrawableRes int buttonIconResource) {
        this.mStyle.buttonIconResource = buttonIconResource;
        return this;
    }

    public int getButtonIconResource() {
        return this.mStyle.buttonIconResource;
    }

    public SuperActivityToast setOnButtonClickListener(@NonNull String tag, Parcelable token, @NonNull OnButtonClickListener onButtonClickListener) {
        this.mOnButtonClickListener = onButtonClickListener;
        this.mStyle.buttonTag = tag;
        this.mStyle.buttonToken = token;
        return this;
    }

    public String getButtonTag() {
        return this.mStyle.buttonTag;
    }

    public Parcelable getButtonToken() {
        return this.mStyle.buttonToken;
    }

    public OnButtonClickListener getOnButtonClickListener() {
        return this.mOnButtonClickListener;
    }

    public SuperActivityToast setProgress(int progress) {
        if (this.mProgressBar == null) {
            Log.e(getClass().getName(), "Could not set SuperActivityToast progress, are you sure you set the type to TYPE_PROGRESS_CIRCLE or TYPE_PROGRESS_BAR?");
            return this;
        }
        this.mStyle.progress = progress;
        this.mProgressBar.setProgress(progress);
        return this;
    }

    public int getProgress() {
        return this.mStyle.progress;
    }

    public SuperActivityToast setProgressBarColor(@ColorInt int progressBarColor) {
        if (VERSION.SDK_INT < 21) {
            Log.w(getClass().getName(), "SuperActivityToast.setProgressBarColor() requires API 21 or newer.");
            return this;
        }
        this.mStyle.progressBarColor = progressBarColor;
        return this;
    }

    @ColorInt
    public int getProgressBarColor() {
        if (VERSION.SDK_INT >= 21) {
            return this.mStyle.progressBarColor;
        }
        Log.w(getClass().getName(), "SuperActivityToast.getProgressBarColor() requires API 21 or newer.");
        return 0;
    }

    public SuperActivityToast setProgressMax(int progressMax) {
        this.mStyle.progressMax = progressMax;
        return this;
    }

    public int getProgressMax() {
        return this.mStyle.progressMax;
    }

    public SuperActivityToast setProgressIndeterminate(boolean progressIndeterminate) {
        this.mStyle.progressIndeterminate = progressIndeterminate;
        return this;
    }

    public boolean getProgressIndeterminate() {
        return this.mStyle.progressIndeterminate;
    }

    public ViewGroup getViewGroup() {
        return this.mViewGroup;
    }

    public int getType() {
        return this.mStyle.type;
    }

    protected void onPrepareShow() {
        super.onPrepareShow();
        LayoutParams layoutParams = new LayoutParams(this.mStyle.width, this.mStyle.height);
        switch (this.mStyle.type) {
            case 1:
                break;
            case 2:
                if (this.mStyle.frame != 3) {
                    this.mStyle.width = -1;
                    this.mStyle.xOffset = BackgroundUtils.convertToDIP(24);
                    this.mStyle.yOffset = BackgroundUtils.convertToDIP(24);
                }
                if ((this.mContext.getResources().getConfiguration().screenLayout & 15) >= 3) {
                    this.mStyle.width = BackgroundUtils.convertToDIP(568);
                    this.mStyle.gravity = 8388691;
                }
                Button button = (Button) this.mView.findViewById(C0507R.id.button);
                button.setBackgroundResource(BackgroundUtils.getButtonBackgroundResource(this.mStyle.frame));
                button.setText(this.mStyle.buttonText != null ? this.mStyle.buttonText.toUpperCase() : "");
                button.setTypeface(button.getTypeface(), this.mStyle.buttonTypefaceStyle);
                button.setTextColor(this.mStyle.buttonTextColor);
                button.setTextSize((float) this.mStyle.buttonTextSize);
                if (this.mStyle.frame != 3) {
                    this.mView.findViewById(C0507R.id.divider).setBackgroundColor(this.mStyle.buttonDividerColor);
                    if (this.mStyle.buttonIconResource > 0) {
                        button.setCompoundDrawablesWithIntrinsicBounds(ResourcesCompat.getDrawable(this.mContext.getResources(), this.mStyle.buttonIconResource, this.mContext.getTheme()), null, null, null);
                    }
                }
                if (this.mOnButtonClickListener != null) {
                    button.setOnClickListener(new C05081());
                    break;
                }
                break;
            case 3:
                if (VERSION.SDK_INT >= 21) {
                    this.mProgressBar.setIndeterminateTintMode(Mode.SRC_IN);
                    this.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(this.mStyle.progressBarColor));
                    break;
                }
                break;
            case 4:
                if (VERSION.SDK_INT >= 21) {
                    this.mProgressBar.setIndeterminateTintMode(Mode.SRC_IN);
                    this.mProgressBar.setIndeterminateTintList(ColorStateList.valueOf(this.mStyle.progressBarColor));
                    this.mProgressBar.setProgressTintMode(Mode.SRC_IN);
                    this.mProgressBar.setProgressTintList(ColorStateList.valueOf(this.mStyle.progressBarColor));
                }
                this.mProgressBar.setProgress(this.mStyle.progress);
                this.mProgressBar.setMax(this.mStyle.progressMax);
                this.mProgressBar.setIndeterminate(this.mStyle.progressIndeterminate);
                break;
            default:
                break;
        }
        layoutParams.width = this.mStyle.width;
        layoutParams.height = this.mStyle.height;
        layoutParams.gravity = this.mStyle.gravity;
        layoutParams.bottomMargin = this.mStyle.yOffset;
        layoutParams.topMargin = this.mStyle.yOffset;
        layoutParams.leftMargin = this.mStyle.xOffset;
        layoutParams.rightMargin = this.mStyle.xOffset;
        this.mView.setLayoutParams(layoutParams);
        if (this.mStyle.touchToDismiss) {
            this.mView.setOnTouchListener(new C05092());
        } else {
            this.mView.setOnTouchListener(null);
        }
    }

    public static void onSaveState(Bundle bundle) {
        ArrayList<Style> styleList = new ArrayList();
        Iterator it = Toaster.getInstance().getQueue().iterator();
        while (it.hasNext()) {
            SuperToast superToast = (SuperToast) it.next();
            if (superToast instanceof SuperActivityToast) {
                superToast.getStyle().isSuperActivityToast = true;
            }
            styleList.add(superToast.getStyle());
        }
        bundle.putParcelableArrayList(BUNDLE_KEY, styleList);
        Toaster.getInstance().cancelAllSuperToasts();
    }

    public static void onRestoreState(Context context, Bundle bundle) {
        if (bundle != null) {
            ArrayList<Style> styleList = bundle.getParcelableArrayList(BUNDLE_KEY);
            if (styleList == null) {
                Log.e(SuperActivityToast.class.getName(), "Cannot recreate SuperActivityToasts onRestoreState(). Was onSaveState() called?");
                return;
            }
            boolean firstInList = true;
            Iterator it = styleList.iterator();
            while (it.hasNext()) {
                Style style = (Style) it.next();
                if (!style.isSuperActivityToast) {
                    new SuperToast(context, style).show();
                } else if (firstInList) {
                    new SuperActivityToast(context, style).fromOrientationChange().show();
                } else {
                    new SuperActivityToast(context, style).show();
                }
                firstInList = false;
            }
        }
    }

    public static void onRestoreState(Context context, Bundle bundle, ListenerUtils listenerUtils) {
        if (bundle != null) {
            ArrayList<Style> styleList = bundle.getParcelableArrayList(BUNDLE_KEY);
            if (styleList == null) {
                Log.e(SuperActivityToast.class.getName(), "Cannot recreate SuperActivityToasts onRestoreState(). Was onSaveState() called?");
                return;
            }
            boolean firstInList = true;
            Iterator it = styleList.iterator();
            while (it.hasNext()) {
                Style style = (Style) it.next();
                if (style.isSuperActivityToast) {
                    SuperActivityToast superActivityToast = new SuperActivityToast(context, style);
                    if (firstInList) {
                        superActivityToast.fromOrientationChange();
                    }
                    OnDismissListener onDismissListener = (OnDismissListener) listenerUtils.getOnDismissListenerHashMap().get(style.dismissTag);
                    OnButtonClickListener onButtonClickListener = (OnButtonClickListener) listenerUtils.getOnButtonClickListenerHashMap().get(style.buttonTag);
                    if (onDismissListener != null) {
                        superActivityToast.setOnDismissListener(style.dismissTag, style.dismissToken, onDismissListener);
                    }
                    if (onButtonClickListener != null) {
                        superActivityToast.setOnButtonClickListener(style.buttonTag, style.buttonToken, onButtonClickListener);
                    }
                    superActivityToast.show();
                } else {
                    new SuperToast(context, style).show();
                }
                firstInList = false;
            }
        }
    }

    public static SuperActivityToast create(@NonNull Context context) {
        return new SuperActivityToast(context);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull Style style) {
        return new SuperActivityToast(context, style);
    }

    public static SuperActivityToast create(@NonNull Context context, int type) {
        return new SuperActivityToast(context, type);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull Style style, int type) {
        return new SuperActivityToast(context, style, type);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull Style style, int type, @IdRes int viewGroup) {
        return new SuperActivityToast(context, style, type, viewGroup);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull String text, int duration) {
        return (SuperActivityToast) new SuperActivityToast(context).setText(text).setDuration(duration);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull String text, int duration, @NonNull Style style) {
        return (SuperActivityToast) new SuperActivityToast(context, style).setText(text).setDuration(duration);
    }

    public static SuperActivityToast create(@NonNull Context context, @NonNull String text, int duration, @NonNull Style style, @IdRes int viewGroup) {
        return (SuperActivityToast) new SuperActivityToast(context, style, 1, viewGroup).setText(text).setDuration(duration);
    }
}
