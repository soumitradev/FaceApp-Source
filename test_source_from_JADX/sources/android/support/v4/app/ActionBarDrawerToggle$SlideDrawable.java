package android.support.v4.app;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.InsetDrawable;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;

class ActionBarDrawerToggle$SlideDrawable extends InsetDrawable implements Callback {
    private final boolean mHasMirroring;
    private float mOffset;
    private float mPosition;
    private final Rect mTmpRect;
    final /* synthetic */ ActionBarDrawerToggle this$0;

    ActionBarDrawerToggle$SlideDrawable(ActionBarDrawerToggle actionBarDrawerToggle, Drawable wrapped) {
        this.this$0 = actionBarDrawerToggle;
        actionBarDrawerToggle = null;
        super(wrapped, 0);
        if (VERSION.SDK_INT > 18) {
            actionBarDrawerToggle = true;
        }
        this.mHasMirroring = actionBarDrawerToggle;
        this.mTmpRect = new Rect();
    }

    public void setPosition(float position) {
        this.mPosition = position;
        invalidateSelf();
    }

    public float getPosition() {
        return this.mPosition;
    }

    public void setOffset(float offset) {
        this.mOffset = offset;
        invalidateSelf();
    }

    public void draw(@NonNull Canvas canvas) {
        copyBounds(this.mTmpRect);
        canvas.save();
        int flipRtl = 1;
        boolean isLayoutRTL = ViewCompat.getLayoutDirection(this.this$0.mActivity.getWindow().getDecorView()) == 1;
        if (isLayoutRTL) {
            flipRtl = -1;
        }
        int width = this.mTmpRect.width();
        canvas.translate((((-this.mOffset) * ((float) width)) * this.mPosition) * ((float) flipRtl), 0.0f);
        if (isLayoutRTL && !this.mHasMirroring) {
            canvas.translate((float) width, 0.0f);
            canvas.scale(-1.0f, 1.0f);
        }
        super.draw(canvas);
        canvas.restore();
    }
}
