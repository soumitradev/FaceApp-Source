package com.badlogic.gdx.backends.android;

import android.content.Context;
import com.badlogic.gdx.Gdx;

public class AndroidMultiTouchHandler implements AndroidTouchHandler {
    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onTouch(android.view.MotionEvent r26, com.badlogic.gdx.backends.android.AndroidInput r27) {
        /*
        r25 = this;
        r1 = r26;
        r11 = r27;
        r2 = r26.getAction();
        r12 = r2 & 255;
        r2 = r26.getAction();
        r3 = 65280; // 0xff00 float:9.1477E-41 double:3.22526E-319;
        r2 = r2 & r3;
        r13 = r2 >> 8;
        r14 = r1.getPointerId(r13);
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r15 = java.lang.System.nanoTime();
        monitor-enter(r27);
        r9 = 20;
        r10 = -1;
        r17 = 0;
        switch(r12) {
            case 0: goto L_0x0172;
            case 1: goto L_0x0106;
            case 2: goto L_0x002d;
            case 3: goto L_0x0106;
            case 4: goto L_0x0106;
            case 5: goto L_0x0172;
            case 6: goto L_0x0106;
            default: goto L_0x0029;
        };
    L_0x0029:
        r23 = r12;
        goto L_0x0226;
    L_0x002d:
        r6 = r26.getPointerCount();	 Catch:{ all -> 0x00fe }
        r8 = r6;
    L_0x0033:
        r7 = r17;
        if (r7 >= r8) goto L_0x0029;
    L_0x0037:
        r13 = r7;
        r6 = r1.getPointerId(r13);	 Catch:{ all -> 0x00fe }
        r14 = r6;
        r6 = r1.getX(r13);	 Catch:{ all -> 0x00fe }
        r6 = (int) r6;
        r2 = r1.getY(r13);	 Catch:{ all -> 0x00f6 }
        r3 = (int) r2;
        r2 = r11.lookUpPointerIndex(r14);	 Catch:{ all -> 0x00ec }
        r4 = r2;
        if (r4 != r10) goto L_0x005d;
    L_0x004e:
        r18 = r3;
        r19 = r4;
        r20 = r6;
        r21 = r7;
        r22 = r8;
        r23 = r12;
        r12 = -1;
        goto L_0x00cf;
    L_0x005d:
        if (r4 < r9) goto L_0x0064;
    L_0x005f:
        r2 = r6;
        r23 = r12;
        goto L_0x0226;
    L_0x0064:
        r2 = r11.button;	 Catch:{ all -> 0x00e0 }
        r2 = r2[r4];	 Catch:{ all -> 0x00e0 }
        r5 = r2;
        if (r5 == r10) goto L_0x0093;
    L_0x006b:
        r17 = 2;
        r2 = r25;
        r18 = r3;
        r3 = r11;
        r19 = r4;
        r4 = r17;
        r17 = r5;
        r5 = r6;
        r20 = r6;
        r6 = r18;
        r21 = r7;
        r7 = r19;
        r22 = r8;
        r8 = r17;
        r23 = r12;
        r12 = -1;
        r9 = r15;
        r2.postTouchEvent(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x008d }
        goto L_0x00b1;
    L_0x008d:
        r0 = move-exception;
        r2 = r0;
        r5 = r17;
        goto L_0x0143;
    L_0x0093:
        r18 = r3;
        r19 = r4;
        r17 = r5;
        r20 = r6;
        r21 = r7;
        r22 = r8;
        r23 = r12;
        r12 = -1;
        r4 = 4;
        r8 = 0;
        r2 = r25;
        r3 = r11;
        r5 = r20;
        r6 = r18;
        r7 = r19;
        r9 = r15;
        r2.postTouchEvent(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x008d }
    L_0x00b1:
        r2 = r11.deltaX;	 Catch:{ all -> 0x008d }
        r3 = r11.touchX;	 Catch:{ all -> 0x008d }
        r3 = r3[r19];	 Catch:{ all -> 0x008d }
        r6 = r20 - r3;
        r2[r19] = r6;	 Catch:{ all -> 0x008d }
        r2 = r11.deltaY;	 Catch:{ all -> 0x008d }
        r3 = r11.touchY;	 Catch:{ all -> 0x008d }
        r3 = r3[r19];	 Catch:{ all -> 0x008d }
        r3 = r18 - r3;
        r2[r19] = r3;	 Catch:{ all -> 0x008d }
        r2 = r11.touchX;	 Catch:{ all -> 0x008d }
        r2[r19] = r20;	 Catch:{ all -> 0x008d }
        r2 = r11.touchY;	 Catch:{ all -> 0x008d }
        r2[r19] = r18;	 Catch:{ all -> 0x008d }
        r5 = r17;
    L_0x00cf:
        r17 = r21 + 1;
        r3 = r18;
        r4 = r19;
        r2 = r20;
        r8 = r22;
        r12 = r23;
        r9 = 20;
        r10 = -1;
        goto L_0x0033;
    L_0x00e0:
        r0 = move-exception;
        r18 = r3;
        r19 = r4;
        r20 = r6;
        r23 = r12;
        r2 = r0;
        goto L_0x0235;
    L_0x00ec:
        r0 = move-exception;
        r18 = r3;
        r20 = r6;
        r23 = r12;
        r2 = r0;
        goto L_0x0235;
    L_0x00f6:
        r0 = move-exception;
        r20 = r6;
        r23 = r12;
        r2 = r0;
        goto L_0x0235;
    L_0x00fe:
        r0 = move-exception;
        r23 = r12;
        r20 = r2;
        r2 = r0;
        goto L_0x0235;
    L_0x0106:
        r23 = r12;
        r12 = -1;
        r6 = r11.lookUpPointerIndex(r14);	 Catch:{ all -> 0x0231 }
        r9 = r6;
        if (r9 != r12) goto L_0x0111;
    L_0x0110:
        goto L_0x0115;
    L_0x0111:
        r6 = 20;
        if (r9 < r6) goto L_0x0117;
    L_0x0115:
        goto L_0x017e;
    L_0x0117:
        r4 = r11.realId;	 Catch:{ all -> 0x021d }
        r4[r9] = r12;	 Catch:{ all -> 0x021d }
        r4 = r1.getX(r13);	 Catch:{ all -> 0x021d }
        r10 = (int) r4;
        r2 = r1.getY(r13);	 Catch:{ all -> 0x0214 }
        r8 = (int) r2;
        r2 = r11.button;	 Catch:{ all -> 0x0207 }
        r2 = r2[r9];	 Catch:{ all -> 0x0207 }
        r7 = r2;
        if (r7 == r12) goto L_0x0149;
    L_0x012c:
        r4 = 1;
        r2 = r25;
        r3 = r11;
        r5 = r10;
        r6 = r8;
        r12 = r7;
        r7 = r9;
        r18 = r8;
        r8 = r12;
        r19 = r9;
        r20 = r10;
        r9 = r15;
        r2.postTouchEvent(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x0140 }
        goto L_0x0150;
    L_0x0140:
        r0 = move-exception;
        r2 = r0;
        r5 = r12;
    L_0x0143:
        r3 = r18;
        r4 = r19;
        goto L_0x0235;
    L_0x0149:
        r12 = r7;
        r18 = r8;
        r19 = r9;
        r20 = r10;
    L_0x0150:
        r2 = r11.touchX;	 Catch:{ all -> 0x0140 }
        r2[r19] = r20;	 Catch:{ all -> 0x0140 }
        r2 = r11.touchY;	 Catch:{ all -> 0x0140 }
        r2[r19] = r18;	 Catch:{ all -> 0x0140 }
        r2 = r11.deltaX;	 Catch:{ all -> 0x0140 }
        r2[r19] = r17;	 Catch:{ all -> 0x0140 }
        r2 = r11.deltaY;	 Catch:{ all -> 0x0140 }
        r2[r19] = r17;	 Catch:{ all -> 0x0140 }
        r2 = r11.touched;	 Catch:{ all -> 0x0140 }
        r2[r19] = r17;	 Catch:{ all -> 0x0140 }
        r2 = r11.button;	 Catch:{ all -> 0x0140 }
        r2[r19] = r17;	 Catch:{ all -> 0x0140 }
        r5 = r12;
    L_0x016a:
        r3 = r18;
        r4 = r19;
        r2 = r20;
        goto L_0x0226;
    L_0x0172:
        r23 = r12;
        r6 = 20;
        r12 = -1;
        r7 = r27.getFreePointerIndex();	 Catch:{ all -> 0x0231 }
        r9 = r7;
        if (r9 < r6) goto L_0x0181;
    L_0x017e:
        r4 = r9;
        goto L_0x0226;
    L_0x0181:
        r4 = r11.realId;	 Catch:{ all -> 0x021d }
        r4[r9] = r14;	 Catch:{ all -> 0x021d }
        r4 = r1.getX(r13);	 Catch:{ all -> 0x021d }
        r10 = (int) r4;
        r2 = r1.getY(r13);	 Catch:{ all -> 0x0214 }
        r8 = (int) r2;
        r2 = android.os.Build.VERSION.SDK_INT;	 Catch:{ all -> 0x0207 }
        r3 = 14;
        if (r2 < r3) goto L_0x01ad;
    L_0x0195:
        r2 = r26.getButtonState();	 Catch:{ all -> 0x01a3 }
        r7 = r25;
        r2 = r7.toGdxButton(r2);	 Catch:{ all -> 0x01a1 }
        r6 = r2;
        goto L_0x01b0;
    L_0x01a1:
        r0 = move-exception;
        goto L_0x01a6;
    L_0x01a3:
        r0 = move-exception;
        r7 = r25;
    L_0x01a6:
        r2 = r0;
        r3 = r8;
        r4 = r9;
        r20 = r10;
        goto L_0x0235;
    L_0x01ad:
        r7 = r25;
        r6 = r5;
    L_0x01b0:
        if (r6 == r12) goto L_0x01d1;
    L_0x01b2:
        r4 = 0;
        r2 = r7;
        r3 = r11;
        r5 = r10;
        r24 = r6;
        r6 = r8;
        r7 = r9;
        r18 = r8;
        r8 = r24;
        r19 = r9;
        r20 = r10;
        r9 = r15;
        r2.postTouchEvent(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ all -> 0x01c7 }
        goto L_0x01d9;
    L_0x01c7:
        r0 = move-exception;
        r2 = r0;
        r3 = r18;
        r4 = r19;
        r5 = r24;
        goto L_0x0235;
    L_0x01d1:
        r24 = r6;
        r18 = r8;
        r19 = r9;
        r20 = r10;
    L_0x01d9:
        r2 = r11.touchX;	 Catch:{ all -> 0x01fe }
        r2[r19] = r20;	 Catch:{ all -> 0x01fe }
        r2 = r11.touchY;	 Catch:{ all -> 0x01fe }
        r2[r19] = r18;	 Catch:{ all -> 0x01fe }
        r2 = r11.deltaX;	 Catch:{ all -> 0x01fe }
        r2[r19] = r17;	 Catch:{ all -> 0x01fe }
        r2 = r11.deltaY;	 Catch:{ all -> 0x01fe }
        r2[r19] = r17;	 Catch:{ all -> 0x01fe }
        r2 = r11.touched;	 Catch:{ all -> 0x01fe }
        r5 = r24;
        if (r5 == r12) goto L_0x01f2;
    L_0x01ef:
        r17 = 1;
    L_0x01f2:
        r2[r19] = r17;	 Catch:{ all -> 0x01fa }
        r2 = r11.button;	 Catch:{ all -> 0x01fa }
        r2[r19] = r5;	 Catch:{ all -> 0x01fa }
        goto L_0x016a;
    L_0x01fa:
        r0 = move-exception;
        r2 = r0;
        goto L_0x0143;
    L_0x01fe:
        r0 = move-exception;
        r5 = r24;
        r2 = r0;
        r3 = r18;
        r4 = r19;
        goto L_0x0235;
    L_0x0207:
        r0 = move-exception;
        r18 = r8;
        r19 = r9;
        r20 = r10;
        r2 = r0;
        r3 = r18;
        r4 = r19;
        goto L_0x0235;
    L_0x0214:
        r0 = move-exception;
        r19 = r9;
        r20 = r10;
        r2 = r0;
        r4 = r19;
        goto L_0x0235;
    L_0x021d:
        r0 = move-exception;
        r19 = r9;
        r20 = r2;
        r4 = r19;
        r2 = r0;
        goto L_0x0235;
    L_0x0226:
        monitor-exit(r27);	 Catch:{ all -> 0x0231 }
        r6 = com.badlogic.gdx.Gdx.app;
        r6 = r6.getGraphics();
        r6.requestRendering();
        return;
    L_0x0231:
        r0 = move-exception;
        r20 = r2;
    L_0x0234:
        r2 = r0;
    L_0x0235:
        monitor-exit(r27);	 Catch:{ all -> 0x0237 }
        throw r2;
    L_0x0237:
        r0 = move-exception;
        goto L_0x0234;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.backends.android.AndroidMultiTouchHandler.onTouch(android.view.MotionEvent, com.badlogic.gdx.backends.android.AndroidInput):void");
    }

    private void logAction(int action, int pointer) {
        String actionStr = "";
        if (action == 0) {
            actionStr = "DOWN";
        } else if (action == 5) {
            actionStr = "POINTER DOWN";
        } else if (action == 1) {
            actionStr = "UP";
        } else if (action == 6) {
            actionStr = "POINTER UP";
        } else if (action == 4) {
            actionStr = "OUTSIDE";
        } else if (action == 3) {
            actionStr = "CANCEL";
        } else if (action == 2) {
            actionStr = "MOVE";
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
        stringBuilder2.append(", Android pointer id: ");
        stringBuilder2.append(pointer);
        Gdx.app.log("AndroidMultiTouchHandler", stringBuilder2.toString());
    }

    private int toGdxButton(int button) {
        if (button != 0) {
            if (button != 1) {
                if (button == 2) {
                    return 1;
                }
                if (button == 4) {
                    return 2;
                }
                if (button == 8) {
                    return 3;
                }
                if (button == 16) {
                    return 4;
                }
                return -1;
            }
        }
        return 0;
    }

    private void postTouchEvent(AndroidInput input, int type, int x, int y, int pointer, int button, long timeStamp) {
        TouchEvent event = (TouchEvent) input.usedTouchEvents.obtain();
        event.timeStamp = timeStamp;
        event.pointer = pointer;
        event.f59x = x;
        event.f60y = y;
        event.type = type;
        event.button = button;
        input.touchEvents.add(event);
    }

    public boolean supportsMultitouch(Context activity) {
        return activity.getPackageManager().hasSystemFeature("android.hardware.touchscreen.multitouch");
    }
}
