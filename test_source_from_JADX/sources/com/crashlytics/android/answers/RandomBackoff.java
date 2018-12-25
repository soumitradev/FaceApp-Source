package com.crashlytics.android.answers;

import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import java.util.Random;
import org.catrobat.catroid.common.BrickValues;

class RandomBackoff implements Backoff {
    final Backoff backoff;
    final double jitterPercent;
    final Random random;

    public RandomBackoff(Backoff backoff, double jitterPercent) {
        this(backoff, jitterPercent, new Random());
    }

    public RandomBackoff(Backoff backoff, double jitterPercent, Random random) {
        if (jitterPercent >= BrickValues.SET_COLOR_TO) {
            if (jitterPercent <= 1.0d) {
                if (backoff == null) {
                    throw new NullPointerException("backoff must not be null");
                } else if (random == null) {
                    throw new NullPointerException("random must not be null");
                } else {
                    this.backoff = backoff;
                    this.jitterPercent = jitterPercent;
                    this.random = random;
                    return;
                }
            }
        }
        throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
    }

    public long getDelayMillis(int retries) {
        return (long) (randomJitter() * ((double) this.backoff.getDelayMillis(retries)));
    }

    double randomJitter() {
        double minJitter = 1.0d - this.jitterPercent;
        return (((this.jitterPercent + 1.0d) - minJitter) * this.random.nextDouble()) + minJitter;
    }
}
