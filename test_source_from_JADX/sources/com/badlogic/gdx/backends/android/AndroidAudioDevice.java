package com.badlogic.gdx.backends.android;

import android.media.AudioTrack;
import com.badlogic.gdx.audio.AudioDevice;

class AndroidAudioDevice implements AudioDevice {
    private short[] buffer = new short[1024];
    private final boolean isMono;
    private final int latency;
    private final AudioTrack track;

    AndroidAudioDevice(int samplingRate, boolean isMono) {
        this.isMono = isMono;
        int i = 2;
        int minSize = AudioTrack.getMinBufferSize(samplingRate, isMono ? 4 : 12, 2);
        this.track = new AudioTrack(3, samplingRate, isMono ? 4 : 12, 2, minSize, 1);
        this.track.play();
        if (isMono) {
            i = 1;
        }
        this.latency = minSize / i;
    }

    public void dispose() {
        this.track.stop();
        this.track.release();
    }

    public boolean isMono() {
        return this.isMono;
    }

    public void writeSamples(short[] samples, int offset, int numSamples) {
        int writtenSamples = this.track.write(samples, offset, numSamples);
        while (writtenSamples != numSamples) {
            writtenSamples += this.track.write(samples, offset + writtenSamples, numSamples - writtenSamples);
        }
    }

    public void writeSamples(float[] samples, int offset, int numSamples) {
        if (this.buffer.length < samples.length) {
            this.buffer = new short[samples.length];
        }
        int bound = offset + numSamples;
        int i = offset;
        int j = 0;
        while (i < bound) {
            float fValue = samples[i];
            if (fValue > 1.0f) {
                fValue = 1.0f;
            }
            if (fValue < -1.0f) {
                fValue = -1.0f;
            }
            this.buffer[j] = (short) ((int) (32767.0f * fValue));
            i++;
            j++;
        }
        j = this.track.write(this.buffer, 0, numSamples);
        while (j != numSamples) {
            j += this.track.write(this.buffer, j, numSamples - j);
        }
    }

    public int getLatency() {
        return this.latency;
    }

    public void setVolume(float volume) {
        this.track.setStereoVolume(volume, volume);
    }
}
