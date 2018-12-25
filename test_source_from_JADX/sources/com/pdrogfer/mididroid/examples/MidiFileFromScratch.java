package com.pdrogfer.mididroid.examples;

import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.MidiTrack;
import com.pdrogfer.mididroid.event.NoteOff;
import com.pdrogfer.mididroid.event.NoteOn;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MidiFileFromScratch {
    public static void main(String[] args) {
        MidiTrack tempoTrack = new MidiTrack();
        MidiTrack noteTrack = new MidiTrack();
        TimeSignature ts = new TimeSignature();
        ts.setTimeSignature(4, 4, 24, 8);
        Tempo t = new Tempo();
        t.setBpm(228.0f);
        tempoTrack.insertEvent(ts);
        tempoTrack.insertEvent(t);
        int i = 0;
        while (true) {
            int i2 = i;
            if (i2 < 80) {
                int pitch = i2 + 1;
                int i3 = 0;
                int i4 = pitch;
                NoteOn on = new NoteOn((long) (i2 * 480), i3, i4, 100);
                NoteOff off = new NoteOff((long) ((i2 * 480) + 120), i3, i4, 0);
                noteTrack.insertEvent(on);
                noteTrack.insertEvent(off);
                MidiTrack midiTrack = noteTrack;
                int i5 = 0;
                midiTrack.insertNote(i5, pitch + 2, 100, (long) (i2 * 480), 120);
                i = i2 + 1;
            } else {
                ArrayList<MidiTrack> tracks = new ArrayList();
                tracks.add(tempoTrack);
                tracks.add(noteTrack);
                try {
                    new MidiFile(480, tracks).writeToFile(new File("exampleout.mid"));
                    return;
                } catch (IOException e) {
                    System.err.println(e);
                    return;
                }
            }
        }
    }
}
