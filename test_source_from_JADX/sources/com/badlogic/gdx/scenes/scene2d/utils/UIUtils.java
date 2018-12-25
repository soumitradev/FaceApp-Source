package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;

public class UIUtils {
    public static boolean isLinux = System.getProperty("os.name").contains("Linux");
    public static boolean isMac = System.getProperty("os.name").contains("OS X");
    public static boolean isWindows = System.getProperty("os.name").contains("Windows");

    public static boolean left() {
        return Gdx.input.isButtonPressed(0);
    }

    public static boolean left(int button) {
        return button == 0;
    }

    public static boolean right() {
        return Gdx.input.isButtonPressed(1);
    }

    public static boolean right(int button) {
        return button == 1;
    }

    public static boolean middle() {
        return Gdx.input.isButtonPressed(2);
    }

    public static boolean middle(int button) {
        return button == 2;
    }

    public static boolean shift() {
        if (!Gdx.input.isKeyPressed(59)) {
            if (!Gdx.input.isKeyPressed(60)) {
                return false;
            }
        }
        return true;
    }

    public static boolean shift(int keycode) {
        if (keycode != 59) {
            if (keycode != 60) {
                return false;
            }
        }
        return true;
    }

    public static boolean ctrl() {
        if (isMac) {
            return Gdx.input.isKeyPressed(63);
        }
        boolean z;
        if (!Gdx.input.isKeyPressed(129)) {
            if (!Gdx.input.isKeyPressed(130)) {
                z = false;
                return z;
            }
        }
        z = true;
        return z;
    }

    public static boolean ctrl(int keycode) {
        boolean z = false;
        if (isMac) {
            if (keycode == 63) {
                z = true;
            }
            return z;
        }
        if (keycode != 129) {
            if (keycode != 130) {
                return z;
            }
        }
        z = true;
        return z;
    }

    public static boolean alt() {
        if (!Gdx.input.isKeyPressed(57)) {
            if (!Gdx.input.isKeyPressed(58)) {
                return false;
            }
        }
        return true;
    }

    public static boolean alt(int keycode) {
        if (keycode != 57) {
            if (keycode != 58) {
                return false;
            }
        }
        return true;
    }
}
