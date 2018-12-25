package com.badlogic.gdx.backends.android;

import android.view.MotionEvent;
import com.badlogic.gdx.Gdx;

public class AndroidMouseHandler {
    private int deltaX = 0;
    private int deltaY = 0;

    public boolean onGenericMotion(MotionEvent event, AndroidInput input) {
        AndroidMouseHandler androidMouseHandler = this;
        if ((event.getSource() & 2) == 0) {
            return false;
        }
        int action = event.getAction() & 255;
        long timeStamp = System.nanoTime();
        synchronized (input) {
            switch (action) {
                case 7:
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if (!(x == androidMouseHandler.deltaX && y == androidMouseHandler.deltaY)) {
                        postTouchEvent(input, 4, x, y, 0, timeStamp);
                        androidMouseHandler.deltaX = x;
                        androidMouseHandler.deltaY = y;
                        break;
                    }
                case 8:
                    try {
                        postTouchEvent(input, 3, 0, 0, (int) (-Math.signum(event.getAxisValue(9))), timeStamp);
                        break;
                    } catch (Throwable th) {
                        while (true) {
                            Throwable th2 = th;
                            break;
                        }
                    }
                default:
                    break;
            }
        }
        Gdx.app.getGraphics().requestRendering();
        return true;
    }

    private void logAction(int action) {
        String actionStr = "";
        if (action == 9) {
            actionStr = "HOVER_ENTER";
        } else if (action == 7) {
            actionStr = "HOVER_MOVE";
        } else if (action == 10) {
            actionStr = "HOVER_EXIT";
        } else if (action == 8) {
            actionStr = "SCROLL";
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("UNKNOWN (");
            stringBuilder.append(action);
            stringBuilder.append(")");
            actionStr = stringBuilder.toString();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("action ");
        stringBuilder2.append(actionStr);
        Gdx.app.log("AndroidMouseHandler", stringBuilder2.toString());
    }

    private void postTouchEvent(AndroidInput input, int type, int x, int y, int scrollAmount, long timeStamp) {
        TouchEvent event = (TouchEvent) input.usedTouchEvents.obtain();
        event.timeStamp = timeStamp;
        event.f59x = x;
        event.f60y = y;
        event.type = type;
        event.scrollAmount = scrollAmount;
        input.touchEvents.add(event);
    }
}
