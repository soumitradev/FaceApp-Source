package org.catrobat.catroid.scratchconverter.protocol.message.job;

import android.support.annotation.NonNull;
import java.util.Arrays;

public class JobOutputMessage extends JobMessage {
    private final String[] lines;

    public JobOutputMessage(long jobID, @NonNull String[] lines) {
        super(jobID);
        this.lines = lines != null ? (String[]) Arrays.copyOf(lines, lines.length) : new String[0];
    }

    public String[] getLines() {
        return (String[]) Arrays.copyOf(this.lines, this.lines.length);
    }
}
