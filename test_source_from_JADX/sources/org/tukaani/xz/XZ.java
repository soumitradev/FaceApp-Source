package org.tukaani.xz;

import org.billthefarmer.mididriver.GeneralMidiConstants;

public class XZ {
    public static final int CHECK_CRC32 = 1;
    public static final int CHECK_CRC64 = 4;
    public static final int CHECK_NONE = 0;
    public static final int CHECK_SHA256 = 10;
    public static final byte[] FOOTER_MAGIC = new byte[]{GeneralMidiConstants.PAD_1_WARM, GeneralMidiConstants.PAD_2_POLYSYNTH};
    public static final byte[] HEADER_MAGIC = new byte[]{(byte) -3, (byte) 55, GeneralMidiConstants.SEASHORE, (byte) 88, GeneralMidiConstants.PAD_2_POLYSYNTH, (byte) 0};

    private XZ() {
    }
}
