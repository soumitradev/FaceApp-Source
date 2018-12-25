package com.github.mrengineer13.snackbar;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

class Snack implements Parcelable {
    public static final Creator<Snack> CREATOR = new C05121();
    final int mActionIcon;
    final String mActionMessage;
    final ColorStateList mBackgroundColor;
    final ColorStateList mBtnTextColor;
    final short mDuration;
    final int mHeight;
    final String mMessage;
    final Parcelable mToken;
    Typeface mTypeface;

    /* renamed from: com.github.mrengineer13.snackbar.Snack$1 */
    static class C05121 implements Creator<Snack> {
        C05121() {
        }

        public Snack createFromParcel(Parcel in) {
            return new Snack(in);
        }

        public Snack[] newArray(int size) {
            return new Snack[size];
        }
    }

    Snack(String message, String actionMessage, int actionIcon, Parcelable token, short duration, ColorStateList textColor, ColorStateList backgroundColor, int height, Typeface typeFace) {
        this.mMessage = message;
        this.mActionMessage = actionMessage;
        this.mActionIcon = actionIcon;
        this.mToken = token;
        this.mDuration = duration;
        this.mBtnTextColor = textColor;
        this.mBackgroundColor = backgroundColor;
        this.mHeight = height;
        this.mTypeface = typeFace;
    }

    Snack(Parcel p) {
        this.mMessage = p.readString();
        this.mActionMessage = p.readString();
        this.mActionIcon = p.readInt();
        this.mToken = p.readParcelable(p.getClass().getClassLoader());
        this.mDuration = (short) p.readInt();
        this.mBtnTextColor = (ColorStateList) p.readParcelable(p.getClass().getClassLoader());
        this.mBackgroundColor = (ColorStateList) p.readParcelable(p.getClass().getClassLoader());
        this.mHeight = p.readInt();
        this.mTypeface = (Typeface) p.readValue(p.getClass().getClassLoader());
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.mMessage);
        out.writeString(this.mActionMessage);
        out.writeInt(this.mActionIcon);
        out.writeParcelable(this.mToken, 0);
        out.writeInt(this.mDuration);
        out.writeParcelable(this.mBtnTextColor, 0);
        out.writeParcelable(this.mBackgroundColor, 0);
        out.writeInt(this.mHeight);
        out.writeValue(this.mTypeface);
    }

    public int describeContents() {
        return 0;
    }
}
