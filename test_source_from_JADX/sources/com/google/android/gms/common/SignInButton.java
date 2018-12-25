package com.google.android.gms.common;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import com.google.android.gms.C0043R;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbx;
import com.google.android.gms.common.internal.zzby;
import com.google.android.gms.dynamic.zzq;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class SignInButton extends FrameLayout implements OnClickListener {
    public static final int COLOR_AUTO = 2;
    public static final int COLOR_DARK = 0;
    public static final int COLOR_LIGHT = 1;
    public static final int SIZE_ICON_ONLY = 2;
    public static final int SIZE_STANDARD = 0;
    public static final int SIZE_WIDE = 1;
    private int zza;
    private int zzb;
    private View zzc;
    private OnClickListener zzd;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ButtonSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorScheme {
    }

    public SignInButton(Context context) {
        this(context, null);
    }

    public SignInButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignInButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzd = null;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C0043R.styleable.SignInButton, 0, 0);
        try {
            this.zza = obtainStyledAttributes.getInt(C0043R.styleable.SignInButton_buttonSize, 0);
            this.zzb = obtainStyledAttributes.getInt(C0043R.styleable.SignInButton_colorScheme, 2);
            setStyle(this.zza, this.zzb);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    public final void onClick(View view) {
        if (this.zzd != null && view == this.zzc) {
            this.zzd.onClick(this);
        }
    }

    public final void setColorScheme(int i) {
        setStyle(this.zza, i);
    }

    public final void setEnabled(boolean z) {
        super.setEnabled(z);
        this.zzc.setEnabled(z);
    }

    public final void setOnClickListener(OnClickListener onClickListener) {
        this.zzd = onClickListener;
        if (this.zzc != null) {
            this.zzc.setOnClickListener(this);
        }
    }

    @Deprecated
    public final void setScopes(Scope[] scopeArr) {
        setStyle(this.zza, this.zzb);
    }

    public final void setSize(int i) {
        setStyle(i, this.zzb);
    }

    public final void setStyle(int i, int i2) {
        this.zza = i;
        this.zzb = i2;
        Context context = getContext();
        if (this.zzc != null) {
            removeView(this.zzc);
        }
        try {
            this.zzc = zzbx.zza(context, this.zza, this.zzb);
        } catch (zzq e) {
            Log.w("SignInButton", "Sign in button not found, using placeholder instead");
            i2 = this.zza;
            int i3 = this.zzb;
            View zzby = new zzby(context);
            zzby.zza(context.getResources(), i2, i3);
            this.zzc = zzby;
        }
        addView(this.zzc);
        this.zzc.setEnabled(isEnabled());
        this.zzc.setOnClickListener(this);
    }

    @Deprecated
    public final void setStyle(int i, int i2, Scope[] scopeArr) {
        setStyle(i, i2);
    }
}
