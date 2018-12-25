package org.catrobat.catroid.pocketmusic.mididriver;

import org.billthefarmer.mididriver.MidiDriver;
import org.billthefarmer.mididriver.MidiDriver.OnMidiStartListener;
import org.catrobat.catroid.pocketmusic.note.Project;

public final class MidiNotePlayer implements OnMidiStartListener {
    private final MidiDriver midiDriver = new MidiDriver();

    public MidiNotePlayer() {
        this.midiDriver.setOnMidiStartListener(this);
    }

    public void onMidiStart() {
        sendMidi(MidiSignals.PROGRAM_CHANGE.getSignalByte(), (byte) Project.DEFAULT_INSTRUMENT.getProgram());
        this.midiDriver.config();
    }

    public void start() {
        this.midiDriver.start();
    }

    public void stop() {
        this.midiDriver.stop();
    }

    private void sendMidi(int midiSignal, int instrument) {
        this.midiDriver.write(new byte[]{(byte) midiSignal, (byte) instrument});
    }

    void sendMidi(int midiSignal, int note, int velocity) {
        this.midiDriver.write(new byte[]{(byte) midiSignal, (byte) note, (byte) velocity});
    }
}
