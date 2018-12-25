package com.badlogic.gdx.utils;

import com.badlogic.gdx.math.Vector2;

public enum Scaling {
    fit,
    fill,
    fillX,
    fillY,
    stretch,
    stretchX,
    stretchY,
    none;
    
    private static final Vector2 temp = null;

    static {
        temp = new Vector2();
    }

    public Vector2 apply(float sourceWidth, float sourceHeight, float targetWidth, float targetHeight) {
        float scale;
        float scale2;
        switch (Scaling$1.$SwitchMap$com$badlogic$gdx$utils$Scaling[ordinal()]) {
            case 1:
                scale = targetHeight / targetWidth > sourceHeight / sourceWidth ? targetWidth / sourceWidth : targetHeight / sourceHeight;
                temp.f16x = sourceWidth * scale;
                temp.f17y = sourceHeight * scale;
                break;
            case 2:
                scale = targetHeight / targetWidth < sourceHeight / sourceWidth ? targetWidth / sourceWidth : targetHeight / sourceHeight;
                temp.f16x = sourceWidth * scale;
                temp.f17y = sourceHeight * scale;
                break;
            case 3:
                scale2 = targetWidth / sourceWidth;
                temp.f16x = sourceWidth * scale2;
                temp.f17y = sourceHeight * scale2;
                break;
            case 4:
                scale2 = targetHeight / sourceHeight;
                temp.f16x = sourceWidth * scale2;
                temp.f17y = sourceHeight * scale2;
                break;
            case 5:
                temp.f16x = targetWidth;
                temp.f17y = targetHeight;
                break;
            case 6:
                temp.f16x = targetWidth;
                temp.f17y = sourceHeight;
                break;
            case 7:
                temp.f16x = sourceWidth;
                temp.f17y = targetHeight;
                break;
            case 8:
                temp.f16x = sourceWidth;
                temp.f17y = sourceHeight;
                break;
            default:
                break;
        }
        return temp;
    }
}
