package org.catrobat.catroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint({"AppCompatCustomView"})
public class BorderTextView extends TextView {
    private static final int SHADOW_RADIUS = 3;
    private static final int SHADOW_REPEAT = 3;

    public BorderTextView(Context context) {
        super(context);
    }

    public BorderTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BorderTextView(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    protected void onDraw(Canvas canvas) {
        getPaint().setShadowLayer(3.0f, 0.0f, 0.0f, ViewCompat.MEASURED_STATE_MASK);
        getPaint().setShader(null);
        for (int i = 0; i < 3; i++) {
            super.onDraw(canvas);
        }
    }
}
