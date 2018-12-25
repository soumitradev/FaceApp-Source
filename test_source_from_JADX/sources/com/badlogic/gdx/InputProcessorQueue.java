package com.badlogic.gdx;

import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.TimeUtils;

public class InputProcessorQueue implements InputProcessor {
    private static final int KEY_DOWN = 0;
    private static final int KEY_TYPED = 2;
    private static final int KEY_UP = 1;
    private static final int MOUSE_MOVED = 6;
    private static final int SCROLLED = 7;
    private static final int TOUCH_DOWN = 3;
    private static final int TOUCH_DRAGGED = 5;
    private static final int TOUCH_UP = 4;
    private long currentEventTime;
    private final IntArray processingQueue = new IntArray();
    private InputProcessor processor;
    private final IntArray queue = new IntArray();

    public InputProcessorQueue(InputProcessor processor) {
        this.processor = processor;
    }

    public void setProcessor(InputProcessor processor) {
        this.processor = processor;
    }

    public InputProcessor getProcessor() {
        return this.processor;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void drain() {
        /*
        r12 = this;
        r0 = r12.processingQueue;
        monitor-enter(r12);
        r1 = r12.processor;	 Catch:{ all -> 0x00ec }
        if (r1 != 0) goto L_0x000e;
    L_0x0007:
        r1 = r12.queue;	 Catch:{ all -> 0x00ec }
        r1.clear();	 Catch:{ all -> 0x00ec }
        monitor-exit(r12);	 Catch:{ all -> 0x00ec }
        return;
    L_0x000e:
        r1 = r12.queue;	 Catch:{ all -> 0x00ec }
        r0.addAll(r1);	 Catch:{ all -> 0x00ec }
        r1 = r12.queue;	 Catch:{ all -> 0x00ec }
        r1.clear();	 Catch:{ all -> 0x00ec }
        monitor-exit(r12);	 Catch:{ all -> 0x00ec }
        r1 = 0;
        r2 = r0.size;
    L_0x001c:
        if (r1 >= r2) goto L_0x00e8;
    L_0x001e:
        r3 = r1 + 1;
        r1 = r0.get(r1);
        r4 = (long) r1;
        r1 = 32;
        r4 = r4 << r1;
        r1 = r3 + 1;
        r3 = r0.get(r3);
        r6 = (long) r3;
        r8 = 4294967295; // 0xffffffff float:NaN double:2.1219957905E-314;
        r10 = r6 & r8;
        r6 = r4 | r10;
        r12.currentEventTime = r6;
        r3 = r1 + 1;
        r1 = r0.get(r1);
        switch(r1) {
            case 0: goto L_0x00d9;
            case 1: goto L_0x00cd;
            case 2: goto L_0x00c0;
            case 3: goto L_0x00a0;
            case 4: goto L_0x0082;
            case 5: goto L_0x0068;
            case 6: goto L_0x0053;
            case 7: goto L_0x0046;
            default: goto L_0x0043;
        };
    L_0x0043:
        r1 = r3;
        goto L_0x00e6;
    L_0x0046:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r1.scrolled(r3);
        goto L_0x00e5;
    L_0x0053:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r5 = r4 + 1;
        r4 = r0.get(r4);
        r1.mouseMoved(r3, r4);
        r1 = r5;
        goto L_0x00e6;
    L_0x0068:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r5 = r4 + 1;
        r4 = r0.get(r4);
        r6 = r5 + 1;
        r5 = r0.get(r5);
        r1.touchDragged(r3, r4, r5);
        r1 = r6;
        goto L_0x00e6;
    L_0x0082:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r5 = r4 + 1;
        r4 = r0.get(r4);
        r6 = r5 + 1;
        r5 = r0.get(r5);
        r7 = r6 + 1;
        r6 = r0.get(r6);
        r1.touchUp(r3, r4, r5, r6);
        goto L_0x00be;
    L_0x00a0:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r5 = r4 + 1;
        r4 = r0.get(r4);
        r6 = r5 + 1;
        r5 = r0.get(r5);
        r7 = r6 + 1;
        r6 = r0.get(r6);
        r1.touchDown(r3, r4, r5, r6);
    L_0x00be:
        r1 = r7;
        goto L_0x00e6;
    L_0x00c0:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r3 = (char) r3;
        r1.keyTyped(r3);
        goto L_0x00e5;
    L_0x00cd:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r1.keyUp(r3);
        goto L_0x00e5;
    L_0x00d9:
        r1 = r12.processor;
        r4 = r3 + 1;
        r3 = r0.get(r3);
        r1.keyDown(r3);
    L_0x00e5:
        r1 = r4;
    L_0x00e6:
        goto L_0x001c;
    L_0x00e8:
        r0.clear();
        return;
    L_0x00ec:
        r1 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x00ec }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.InputProcessorQueue.drain():void");
    }

    private void queueTime() {
        long time = TimeUtils.nanoTime();
        this.queue.add((int) (time >> 32));
        this.queue.add((int) time);
    }

    public synchronized boolean keyDown(int keycode) {
        queueTime();
        this.queue.add(0);
        this.queue.add(keycode);
        return false;
    }

    public synchronized boolean keyUp(int keycode) {
        queueTime();
        this.queue.add(1);
        this.queue.add(keycode);
        return false;
    }

    public synchronized boolean keyTyped(char character) {
        queueTime();
        this.queue.add(2);
        this.queue.add(character);
        return false;
    }

    public synchronized boolean touchDown(int screenX, int screenY, int pointer, int button) {
        queueTime();
        this.queue.add(3);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        this.queue.add(button);
        return false;
    }

    public synchronized boolean touchUp(int screenX, int screenY, int pointer, int button) {
        queueTime();
        this.queue.add(4);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        this.queue.add(button);
        return false;
    }

    public synchronized boolean touchDragged(int screenX, int screenY, int pointer) {
        queueTime();
        this.queue.add(5);
        this.queue.add(screenX);
        this.queue.add(screenY);
        this.queue.add(pointer);
        return false;
    }

    public synchronized boolean mouseMoved(int screenX, int screenY) {
        queueTime();
        this.queue.add(6);
        this.queue.add(screenX);
        this.queue.add(screenY);
        return false;
    }

    public synchronized boolean scrolled(int amount) {
        queueTime();
        this.queue.add(7);
        this.queue.add(amount);
        return false;
    }

    public long getCurrentEventTime() {
        return this.currentEventTime;
    }
}
