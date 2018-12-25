package com.github.johnpersano.supertoasts.library;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.github.johnpersano.supertoasts.library.utils.BackgroundUtils;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Style implements Parcelable {
    public static final int ANIMATIONS_FADE = 1;
    public static final int ANIMATIONS_FLY = 2;
    public static final int ANIMATIONS_POP = 4;
    public static final int ANIMATIONS_SCALE = 3;
    public static final Creator CREATOR = new C00421();
    public static final int DURATION_LONG = 3500;
    public static final int DURATION_MEDIUM = 2750;
    public static final int DURATION_SHORT = 2000;
    public static final int DURATION_VERY_LONG = 4500;
    public static final int DURATION_VERY_SHORT = 1500;
    public static final int FRAME_KITKAT = 2;
    public static final int FRAME_LOLLIPOP = 3;
    public static final int FRAME_STANDARD = 1;
    public static final int ICONPOSITION_BOTTOM = 3;
    public static final int ICONPOSITION_LEFT = 1;
    public static final int ICONPOSITION_RIGHT = 2;
    public static final int ICONPOSITION_TOP = 4;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = 3;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int TEXTSIZE_LARGE = 18;
    public static final int TEXTSIZE_MEDIUM = 16;
    public static final int TEXTSIZE_SMALL = 14;
    public static final int TEXTSIZE_VERY_LARGE = 20;
    public static final int TEXTSIZE_VERY_SMALL = 12;
    public static final int TYPE_BUTTON = 2;
    public static final int TYPE_PROGRESS_BAR = 4;
    public static final int TYPE_PROGRESS_CIRCLE = 3;
    public static final int TYPE_STANDARD = 1;
    public int animations;
    public int buttonDividerColor;
    public int buttonIconResource;
    public String buttonTag;
    public String buttonText;
    public int buttonTextColor;
    public int buttonTextSize;
    public Parcelable buttonToken;
    public int buttonTypefaceStyle;
    public int color;
    public int container;
    public String dismissTag;
    public Parcelable dismissToken;
    public int duration;
    public int frame;
    public int gravity;
    public int height;
    public boolean isIndeterminate;
    protected boolean isSuperActivityToast;
    public String message;
    public int messageIconPosition;
    public int messageIconResource;
    public int messageTextColor;
    public int messageTextSize;
    public int messageTypefaceStyle;
    public int priorityColor;
    public int priorityLevel;
    public int progress;
    public int progressBarColor;
    public boolean progressIndeterminate;
    public int progressMax;
    protected long timestamp;
    public boolean touchToDismiss;
    public int type;
    public int width;
    public int xOffset;
    public int yOffset;

    /* renamed from: com.github.johnpersano.supertoasts.library.Style$1 */
    static class C00421 implements Creator {
        C00421() {
        }

        public Style createFromParcel(Parcel parcel) {
            return new Style(parcel);
        }

        public Style[] newArray(int size) {
            return new Style[size];
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Animations {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Duration {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Frame {
    }

    @SuppressLint({"RtlHardcoded"})
    @Retention(RetentionPolicy.SOURCE)
    public @interface GravityStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IconPosition {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface PriorityLevel {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TextSize {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface TypefaceStyle {
    }

    public Style() {
        this.duration = DURATION_MEDIUM;
        this.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREY);
        this.gravity = 81;
        this.yOffset = BackgroundUtils.convertToDIP(64);
        this.width = -2;
        this.height = -2;
        this.priorityLevel = 2;
        this.messageTypefaceStyle = 0;
        this.messageTextColor = PaletteUtils.getSolidColor(PaletteUtils.WHITE);
        this.messageTextSize = 14;
        this.messageIconPosition = 1;
        this.buttonTypefaceStyle = 1;
        this.buttonTextColor = PaletteUtils.getSolidColor(PaletteUtils.WHITE);
        this.buttonTextSize = 12;
        this.buttonDividerColor = PaletteUtils.getSolidColor(PaletteUtils.WHITE);
        this.progressBarColor = PaletteUtils.getSolidColor(PaletteUtils.WHITE);
        this.progressIndeterminate = true;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.message);
        parcel.writeInt(this.duration);
        parcel.writeInt(this.color);
        parcel.writeInt(this.priorityColor);
        parcel.writeInt(this.frame);
        parcel.writeInt(this.animations);
        parcel.writeInt(this.gravity);
        parcel.writeInt(this.xOffset);
        parcel.writeInt(this.yOffset);
        parcel.writeInt(this.width);
        parcel.writeInt(this.height);
        parcel.writeString(this.dismissTag);
        parcel.writeParcelable(this.dismissToken, 0);
        parcel.writeInt(this.priorityLevel);
        parcel.writeLong(this.timestamp);
        parcel.writeByte((byte) this.isSuperActivityToast);
        parcel.writeInt(this.messageTypefaceStyle);
        parcel.writeInt(this.messageTextColor);
        parcel.writeInt(this.messageTextSize);
        parcel.writeInt(this.messageIconPosition);
        parcel.writeInt(this.messageIconResource);
        parcel.writeInt(this.container);
        parcel.writeInt(this.type);
        parcel.writeByte((byte) this.isIndeterminate);
        parcel.writeByte((byte) this.touchToDismiss);
        parcel.writeString(this.buttonText);
        parcel.writeInt(this.buttonTypefaceStyle);
        parcel.writeInt(this.buttonTextColor);
        parcel.writeInt(this.buttonTextSize);
        parcel.writeInt(this.buttonDividerColor);
        parcel.writeInt(this.buttonIconResource);
        parcel.writeString(this.buttonTag);
        parcel.writeParcelable(this.buttonToken, 0);
        parcel.writeInt(this.progress);
        parcel.writeInt(this.progressMax);
        parcel.writeByte((byte) this.progressIndeterminate);
        parcel.writeInt(this.progressBarColor);
    }

    private Style(Parcel parcel) {
        this.message = parcel.readString();
        this.duration = parcel.readInt();
        this.color = parcel.readInt();
        this.priorityColor = parcel.readInt();
        this.frame = parcel.readInt();
        this.animations = parcel.readInt();
        this.gravity = parcel.readInt();
        this.xOffset = parcel.readInt();
        this.yOffset = parcel.readInt();
        this.width = parcel.readInt();
        this.height = parcel.readInt();
        this.dismissTag = parcel.readString();
        this.dismissToken = parcel.readParcelable(getClass().getClassLoader());
        this.priorityLevel = parcel.readInt();
        this.timestamp = parcel.readLong();
        boolean z = false;
        this.isSuperActivityToast = parcel.readByte() != (byte) 0;
        this.messageTypefaceStyle = parcel.readInt();
        this.messageTextColor = parcel.readInt();
        this.messageTextSize = parcel.readInt();
        this.messageIconPosition = parcel.readInt();
        this.messageIconResource = parcel.readInt();
        this.container = parcel.readInt();
        this.type = parcel.readInt();
        this.isIndeterminate = parcel.readByte() != (byte) 0;
        this.touchToDismiss = parcel.readByte() != (byte) 0;
        this.buttonText = parcel.readString();
        this.buttonTypefaceStyle = parcel.readInt();
        this.buttonTextColor = parcel.readInt();
        this.buttonTextSize = parcel.readInt();
        this.buttonDividerColor = parcel.readInt();
        this.buttonIconResource = parcel.readInt();
        this.buttonTag = parcel.readString();
        this.buttonToken = parcel.readParcelable(getClass().getClassLoader());
        this.progress = parcel.readInt();
        this.progressMax = parcel.readInt();
        if (parcel.readByte() != (byte) 0) {
            z = true;
        }
        this.progressIndeterminate = z;
        this.progressBarColor = parcel.readInt();
    }

    public static Style red() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_RED);
        return style;
    }

    public static Style pink() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PINK);
        return style;
    }

    public static Style purple() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE);
        return style;
    }

    public static Style deepPurple() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_PURPLE);
        return style;
    }

    public static Style indigo() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_INDIGO);
        return style;
    }

    public static Style blue() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE);
        return style;
    }

    public static Style lightBlue() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_LIGHT_BLUE);
        return style;
    }

    public static Style cyan() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_CYAN);
        return style;
    }

    public static Style teal() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_TEAL);
        return style;
    }

    public static Style green() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREEN);
        return style;
    }

    public static Style lightGreen() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_LIGHT_GREEN);
        return style;
    }

    public static Style lime() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_LIME);
        style.messageTextColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        style.buttonDividerColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        style.buttonTextColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        return style;
    }

    public static Style yellow() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_YELLOW);
        style.messageTextColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        style.buttonDividerColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        style.buttonTextColor = PaletteUtils.getSolidColor(PaletteUtils.DARK_GREY);
        return style;
    }

    public static Style amber() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_AMBER);
        return style;
    }

    public static Style orange() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_ORANGE);
        return style;
    }

    public static Style deepOrange() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_DEEP_ORANGE);
        return style;
    }

    public static Style brown() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BROWN);
        return style;
    }

    public static Style grey() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_GREY);
        return style;
    }

    public static Style blueGrey() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BLUE_GREY);
        return style;
    }

    public static Style rottenBanana() {
        Style style = new Style();
        style.color = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_YELLOW);
        style.frame = 3;
        style.messageTextColor = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BROWN);
        style.buttonDividerColor = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BROWN);
        style.buttonTextColor = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BROWN);
        style.priorityColor = PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_BROWN);
        return style;
    }
}
