package com.pdrogfer.mididroid.util;

import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import org.catrobat.catroid.physics.PhysicsCollision;

public class MetronomeTick extends MidiEvent {
    private int mCurrentBeat;
    private int mCurrentMeasure = 1;
    private int mMetronomeFrequency;
    private double mMetronomeProgress;
    private int mResolution;
    private TimeSignature mSignature;

    public MetronomeTick(TimeSignature sig, int resolution) {
        super(0, 0);
        this.mResolution = resolution;
        setTimeSignature(sig);
    }

    public void setTimeSignature(TimeSignature sig) {
        this.mSignature = sig;
        this.mCurrentBeat = 0;
        setMetronomeFrequency(sig.getMeter());
    }

    public boolean update(double ticksElapsed) {
        this.mMetronomeProgress += ticksElapsed;
        if (this.mMetronomeProgress < ((double) this.mMetronomeFrequency)) {
            return false;
        }
        this.mMetronomeProgress %= (double) this.mMetronomeFrequency;
        this.mCurrentBeat = (this.mCurrentBeat + 1) % this.mSignature.getNumerator();
        if (this.mCurrentBeat == 0) {
            this.mCurrentMeasure++;
        }
        return true;
    }

    public void setMetronomeFrequency(int meter) {
        if (meter == 12) {
            this.mMetronomeFrequency = this.mResolution / 2;
        } else if (meter == 24) {
            this.mMetronomeFrequency = this.mResolution;
        } else if (meter == 48) {
            this.mMetronomeFrequency = this.mResolution * 2;
        } else if (meter == 96) {
            this.mMetronomeFrequency = this.mResolution * 4;
        }
    }

    public int getBeatNumber() {
        return this.mCurrentBeat + 1;
    }

    public int getMeasure() {
        return this.mCurrentMeasure;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Metronome: ");
        stringBuilder.append(this.mCurrentMeasure);
        stringBuilder.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
        stringBuilder.append(getBeatNumber());
        return stringBuilder.toString();
    }

    public int compareTo(MidiEvent o) {
        return 0;
    }

    protected int getEventSize() {
        return 0;
    }

    public int getSize() {
        return 0;
    }
}
