package com.pdrogfer.mididroid.examples;

import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.MidiTrack;
import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.NoteOff;
import com.pdrogfer.mididroid.event.NoteOn;
import com.pdrogfer.mididroid.event.meta.Tempo;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class MidiManipulation {
    public static void main(String[] args) {
        File input = new File("example.mid");
        try {
            MidiEvent E;
            MidiFile mf = new MidiFile(input);
            MidiTrack T = (MidiTrack) mf.getTracks().get(1);
            Iterator<MidiEvent> it = T.getEvents().iterator();
            ArrayList<MidiEvent> eventsToRemove = new ArrayList();
            while (it.hasNext()) {
                E = (MidiEvent) it.next();
                if (!(E.getClass().equals(NoteOn.class) || E.getClass().equals(NoteOff.class))) {
                    eventsToRemove.add(E);
                }
            }
            Iterator it2 = eventsToRemove.iterator();
            while (it2.hasNext()) {
                T.removeEvent((MidiEvent) it2.next());
            }
            mf.removeTrack(2);
            it = ((MidiTrack) mf.getTracks().get(0)).getEvents().iterator();
            while (it.hasNext()) {
                E = (MidiEvent) it.next();
                if (E.getClass().equals(Tempo.class)) {
                    Tempo tempo = (Tempo) E;
                    tempo.setBpm(tempo.getBpm() / 2.0f);
                }
            }
            try {
                mf.writeToFile(input);
            } catch (IOException e) {
                System.err.println("Error writing MIDI file:");
                e.printStackTrace();
            }
        } catch (IOException e2) {
            System.err.println("Error parsing MIDI file:");
            e2.printStackTrace();
        }
    }
}
