package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;

public class FPSLogger {
    long startTime = TimeUtils.nanoTime();

    public void log() {
        if (TimeUtils.nanoTime() - this.startTime > 1000000000) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("fps: ");
            stringBuilder.append(Gdx.graphics.getFramesPerSecond());
            Gdx.app.log("FPSLogger", stringBuilder.toString());
            this.startTime = TimeUtils.nanoTime();
        }
    }
}
