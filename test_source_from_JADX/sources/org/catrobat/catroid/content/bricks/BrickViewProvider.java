package org.catrobat.catroid.content.bricks;

import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

public final class BrickViewProvider {
    public static final int ALPHA_FULL = 255;
    public static final int ALPHA_GREYED = 100;

    private BrickViewProvider() {
    }

    public static void setAlphaForBrick(Brick brick, int alphaValue) {
        brick.setAlpha(alphaValue);
        setAlphaOnView(((BrickBaseType) brick).view, alphaValue);
    }

    public static View setAlphaOnView(View view, int alphaValue) {
        if (view != null) {
            getBrickLayout(view).setAlpha(convertAlphaValueToFloat(alphaValue));
        }
        return view;
    }

    public static void setSaturationOnBrick(Brick brick, boolean greyScale) {
        setSaturationOnView(((BrickBaseType) brick).view, greyScale);
    }

    public static void setSaturationOnView(View view, boolean greyScale) {
        if (view != null) {
            Drawable background = getBrickLayout(view).getBackground();
            if (greyScale) {
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0.0f);
                background.setColorFilter(new ColorMatrixColorFilter(matrix));
            } else {
                background.clearColorFilter();
            }
        }
    }

    public static void setCheckboxVisibility(Brick brick, int visibility) {
        if (brick.getCheckBox() != null) {
            brick.getCheckBox().setVisibility(visibility);
        }
    }

    public static void setCheckboxClickability(Brick brick, boolean clickable) {
        if (brick.getCheckBox() != null) {
            brick.getCheckBox().setEnabled(clickable);
        }
    }

    public static void setSpinnerClickability(View view, boolean clickable) {
        if (view != null) {
            int pos = 0;
            if (view instanceof Spinner) {
                view.setClickable(clickable);
                view.setEnabled(clickable);
                view.setFocusable(false);
            }
            if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                while (pos < viewGroup.getChildCount()) {
                    setSpinnerClickability(viewGroup.getChildAt(pos), clickable);
                    pos++;
                }
            }
        }
    }

    private static View getBrickLayout(View view) {
        return ((ViewGroup) view).getChildAt(1);
    }

    private static float convertAlphaValueToFloat(int alphaValue) {
        return ((float) alphaValue) / 255.0f;
    }
}
