package com.badlogic.gdx.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Peripheral;
import com.badlogic.gdx.InputProcessor;
import java.io.DataOutputStream;
import java.net.Socket;

public class RemoteSender implements InputProcessor {
    public static final int ACCEL = 6;
    public static final int COMPASS = 7;
    public static final int KEY_DOWN = 0;
    public static final int KEY_TYPED = 2;
    public static final int KEY_UP = 1;
    public static final int SIZE = 8;
    public static final int TOUCH_DOWN = 3;
    public static final int TOUCH_DRAGGED = 5;
    public static final int TOUCH_UP = 4;
    private boolean connected = false;
    private DataOutputStream out;

    public RemoteSender(String ip, int port) {
        try {
            Socket socket = new Socket(ip, port);
            socket.setTcpNoDelay(true);
            socket.setSoTimeout(3000);
            this.out = new DataOutputStream(socket.getOutputStream());
            this.out.writeBoolean(Gdx.input.isPeripheralAvailable(Peripheral.MultitouchScreen));
            this.connected = true;
            Gdx.input.setInputProcessor(this);
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("couldn't connect to ");
            stringBuilder.append(ip);
            stringBuilder.append(":");
            stringBuilder.append(port);
            Gdx.app.log("RemoteSender", stringBuilder.toString());
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void sendUpdate() {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.connected;	 Catch:{ all -> 0x007e }
        if (r0 != 0) goto L_0x0007;
    L_0x0005:
        monitor-exit(r2);	 Catch:{ all -> 0x007e }
        return;
    L_0x0007:
        monitor-exit(r2);	 Catch:{ all -> 0x007e }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = 6;
        r0.writeInt(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getAccelerometerX();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getAccelerometerY();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getAccelerometerZ();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = 7;
        r0.writeInt(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getAzimuth();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getPitch();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.input;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getRoll();	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = 8;
        r0.writeInt(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.graphics;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getWidth();	 Catch:{ Throwable -> 0x0076 }
        r1 = (float) r1;	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0076 }
        r1 = com.badlogic.gdx.Gdx.graphics;	 Catch:{ Throwable -> 0x0076 }
        r1 = r1.getHeight();	 Catch:{ Throwable -> 0x0076 }
        r1 = (float) r1;	 Catch:{ Throwable -> 0x0076 }
        r0.writeFloat(r1);	 Catch:{ Throwable -> 0x0076 }
        goto L_0x007d;
    L_0x0076:
        r0 = move-exception;
        r1 = 0;
        r2.out = r1;
        r1 = 0;
        r2.connected = r1;
    L_0x007d:
        return;
    L_0x007e:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x007e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.sendUpdate():void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean keyDown(int r3) {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.connected;	 Catch:{ all -> 0x001d }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r2);	 Catch:{ all -> 0x001d }
        return r1;
    L_0x0008:
        monitor-exit(r2);	 Catch:{ all -> 0x001d }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0014 }
        r0.writeInt(r1);	 Catch:{ Throwable -> 0x0014 }
        r0 = r2.out;	 Catch:{ Throwable -> 0x0014 }
        r0.writeInt(r3);	 Catch:{ Throwable -> 0x0014 }
        goto L_0x0019;
    L_0x0014:
        r0 = move-exception;
        monitor-enter(r2);
        r2.connected = r1;	 Catch:{ all -> 0x001a }
        monitor-exit(r2);	 Catch:{ all -> 0x001a }
    L_0x0019:
        return r1;
    L_0x001a:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001a }
        throw r1;
    L_0x001d:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x001d }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.keyDown(int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean keyUp(int r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.connected;	 Catch:{ all -> 0x001e }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        r0 = r3.out;	 Catch:{ Throwable -> 0x0015 }
        r2 = 1;
        r0.writeInt(r2);	 Catch:{ Throwable -> 0x0015 }
        r0 = r3.out;	 Catch:{ Throwable -> 0x0015 }
        r0.writeInt(r4);	 Catch:{ Throwable -> 0x0015 }
        goto L_0x001a;
    L_0x0015:
        r0 = move-exception;
        monitor-enter(r3);
        r3.connected = r1;	 Catch:{ all -> 0x001b }
        monitor-exit(r3);	 Catch:{ all -> 0x001b }
    L_0x001a:
        return r1;
    L_0x001b:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001b }
        throw r1;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.keyUp(int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean keyTyped(char r4) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.connected;	 Catch:{ all -> 0x001e }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        r0 = r3.out;	 Catch:{ Throwable -> 0x0015 }
        r2 = 2;
        r0.writeInt(r2);	 Catch:{ Throwable -> 0x0015 }
        r0 = r3.out;	 Catch:{ Throwable -> 0x0015 }
        r0.writeChar(r4);	 Catch:{ Throwable -> 0x0015 }
        goto L_0x001a;
    L_0x0015:
        r0 = move-exception;
        monitor-enter(r3);
        r3.connected = r1;	 Catch:{ all -> 0x001b }
        monitor-exit(r3);	 Catch:{ all -> 0x001b }
    L_0x001a:
        return r1;
    L_0x001b:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001b }
        throw r1;
    L_0x001e:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x001e }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.keyTyped(char):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean touchDown(int r4, int r5, int r6, int r7) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.connected;	 Catch:{ all -> 0x0028 }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r2 = 3;
        r0.writeInt(r2);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r4);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r5);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r6);	 Catch:{ Throwable -> 0x001f }
        goto L_0x0024;
    L_0x001f:
        r0 = move-exception;
        monitor-enter(r3);
        r3.connected = r1;	 Catch:{ all -> 0x0025 }
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
    L_0x0024:
        return r1;
    L_0x0025:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        throw r1;
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.touchDown(int, int, int, int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean touchUp(int r4, int r5, int r6, int r7) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.connected;	 Catch:{ all -> 0x0028 }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r2 = 4;
        r0.writeInt(r2);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r4);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r5);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r6);	 Catch:{ Throwable -> 0x001f }
        goto L_0x0024;
    L_0x001f:
        r0 = move-exception;
        monitor-enter(r3);
        r3.connected = r1;	 Catch:{ all -> 0x0025 }
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
    L_0x0024:
        return r1;
    L_0x0025:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        throw r1;
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.touchUp(int, int, int, int):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean touchDragged(int r4, int r5, int r6) {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.connected;	 Catch:{ all -> 0x0028 }
        r1 = 0;
        if (r0 != 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        return r1;
    L_0x0008:
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r2 = 5;
        r0.writeInt(r2);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r4);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r5);	 Catch:{ Throwable -> 0x001f }
        r0 = r3.out;	 Catch:{ Throwable -> 0x001f }
        r0.writeInt(r6);	 Catch:{ Throwable -> 0x001f }
        goto L_0x0024;
    L_0x001f:
        r0 = move-exception;
        monitor-enter(r3);
        r3.connected = r1;	 Catch:{ all -> 0x0025 }
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
    L_0x0024:
        return r1;
    L_0x0025:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0025 }
        throw r1;
    L_0x0028:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0028 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.input.RemoteSender.touchDragged(int, int, int):boolean");
    }

    public boolean mouseMoved(int x, int y) {
        return false;
    }

    public boolean scrolled(int amount) {
        return false;
    }

    public boolean isConnected() {
        boolean z;
        synchronized (this) {
            z = this.connected;
        }
        return z;
    }
}
