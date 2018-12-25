package org.catrobat.catroid.pocketmusic.mididriver;

import org.billthefarmer.mididriver.MidiConstants;

public enum MidiSignals {
    MIDI_TIME_CODE(MidiConstants.MIDI_TIME_CODE),
    SONG_POSITION_POBYTEER(MidiConstants.SONG_POSITION_POBYTEER),
    SONG_SELECT(MidiConstants.SONG_SELECT),
    TUNE_REQUEST((byte) -10),
    END_OF_EXCLUSIVE((byte) -9),
    TIMING_CLOCK((byte) -8),
    START((byte) -6),
    CONTINUE((byte) -5),
    STOP((byte) -4),
    ACTIVE_SENSING((byte) -2),
    SYSTEM_RESET((byte) -1),
    NOTE_OFF(Byte.MIN_VALUE),
    NOTE_ON(MidiConstants.NOTE_ON),
    POLY_PRESSURE(MidiConstants.POLY_PRESSURE),
    CONTROL_CHANGE(MidiConstants.CONTROL_CHANGE),
    PROGRAM_CHANGE(MidiConstants.PROGRAM_CHANGE),
    CHANNEL_PRESSURE(MidiConstants.CHANNEL_PRESSURE),
    PITCH_BEND(MidiConstants.PITCH_BEND);
    
    private byte signalByte;

    private MidiSignals(byte signalByte) {
        this.signalByte = signalByte;
    }

    public byte getSignalByte() {
        return this.signalByte;
    }
}
