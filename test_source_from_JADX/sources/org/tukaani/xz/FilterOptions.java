package org.tukaani.xz;

import java.io.IOException;
import java.io.InputStream;

public abstract class FilterOptions implements Cloneable {
    FilterOptions() {
    }

    public static int getDecoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions decoderMemoryUsage : filterOptionsArr) {
            i += decoderMemoryUsage.getDecoderMemoryUsage();
        }
        return i;
    }

    public static int getEncoderMemoryUsage(FilterOptions[] filterOptionsArr) {
        int i = 0;
        for (FilterOptions encoderMemoryUsage : filterOptionsArr) {
            i += encoderMemoryUsage.getEncoderMemoryUsage();
        }
        return i;
    }

    public abstract int getDecoderMemoryUsage();

    public abstract int getEncoderMemoryUsage();

    abstract FilterEncoder getFilterEncoder();

    public abstract InputStream getInputStream(InputStream inputStream) throws IOException;

    public abstract FinishableOutputStream getOutputStream(FinishableOutputStream finishableOutputStream);
}
