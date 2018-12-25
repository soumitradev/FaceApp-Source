package org.billthefarmer.mididriver;

public class MidiConstants {
    public static final byte ACTIVE_SENSING = (byte) -2;
    public static final byte CHANNEL_PRESSURE = (byte) -48;
    public static final byte CONTINUE = (byte) -5;
    public static final byte CONTROL_CHANGE = (byte) -80;
    public static final byte END_OF_EXCLUSIVE = (byte) -9;
    public static final byte MIDI_TIME_CODE = (byte) -15;
    public static final byte NOTE_OFF = Byte.MIN_VALUE;
    public static final byte NOTE_ON = (byte) -112;
    public static final byte PITCH_BEND = (byte) -32;
    public static final byte POLY_PRESSURE = (byte) -96;
    public static final byte PROGRAM_CHANGE = (byte) -64;
    public static final byte SONG_POSITION_POBYTEER = (byte) -14;
    public static final byte SONG_SELECT = (byte) -13;
    public static final byte START = (byte) -6;
    public static final byte STOP = (byte) -4;
    public static final byte SYSTEM_RESET = (byte) -1;
    public static final byte TIMING_CLOCK = (byte) -8;
    public static final byte TUNE_REQUEST = (byte) -10;
}
