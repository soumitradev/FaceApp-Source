package org.catrobat.catroid.pocketmusic.note.midi;

import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.MidiTrack;
import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.NoteOff;
import com.pdrogfer.mididroid.event.NoteOn;
import com.pdrogfer.mididroid.event.ProgramChange;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.event.meta.Text;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import com.pdrogfer.mididroid.event.meta.TrackName;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.MusicalInstrument;
import org.catrobat.catroid.pocketmusic.note.MusicalKey;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.NoteName;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;

public class MidiToProjectConverter {
    private MusicalBeat beat = Project.DEFAULT_BEAT;
    private int beatsPerMinute = 60;
    private List<String> trackNames = new ArrayList();
    private List<Track> tracks = new ArrayList();

    public Project convertMidiFileToProject(File file) throws MidiException, IOException {
        MidiFile midi = new MidiFile(file);
        validateMidiFile(midi);
        return convertMidi(file.getName().split(ProjectToMidiConverter.MIDI_FILE_EXTENSION)[0], midi);
    }

    private void validateMidiFile(MidiFile midiFile) throws MidiException {
        if (midiFile.getTrackCount() > 0) {
            Iterator<MidiEvent> it = ((MidiTrack) midiFile.getTracks().get(0)).getEvents().iterator();
            if (it.hasNext()) {
                MidiEvent event = (MidiEvent) it.next();
                if ((event instanceof Text) && ((Text) event).getText().equals(ProjectToMidiConverter.MIDI_FILE_IDENTIFIER)) {
                    return;
                }
            }
        }
        throw new MidiException("Unsupported MIDI!");
    }

    private Project convertMidi(String name, MidiFile midi) {
        Iterator it = midi.getTracks().iterator();
        while (it.hasNext()) {
            createTrack((MidiTrack) it.next());
        }
        Project project = new Project(name, this.beat, this.beatsPerMinute);
        int i = 0;
        for (Track track : this.tracks) {
            if (track.size() > 0) {
                i++;
                project.putTrack((String) this.trackNames.get(i), track);
            }
        }
        return project;
    }

    private void createTrack(MidiTrack midiTrack) {
        Track track = new Track(MusicalKey.VIOLIN, getInstrumentFromMidiTrack(midiTrack));
        Iterator<MidiEvent> it = midiTrack.getEvents().iterator();
        while (it.hasNext()) {
            MidiEvent midiEvent = (MidiEvent) it.next();
            if (midiEvent instanceof TrackName) {
                this.trackNames.add(((TrackName) midiEvent).getTrackName());
            }
            if (midiEvent instanceof NoteOn) {
                NoteOn noteOn = (NoteOn) midiEvent;
                track.addNoteEvent(noteOn.getTick(), new NoteEvent(NoteName.getNoteNameFromMidiValue(noteOn.getNoteValue()), true));
            } else if (midiEvent instanceof NoteOff) {
                NoteOff noteOff = (NoteOff) midiEvent;
                track.addNoteEvent(noteOff.getTick(), new NoteEvent(NoteName.getNoteNameFromMidiValue(noteOff.getNoteValue()), false));
            } else if (midiEvent instanceof Tempo) {
                this.beatsPerMinute = (int) ((Tempo) midiEvent).getBpm();
            } else if (midiEvent instanceof TimeSignature) {
                TimeSignature timeSignature = (TimeSignature) midiEvent;
                this.beat = MusicalBeat.convertToMusicalBeat(timeSignature.getNumerator(), timeSignature.getRealDenominator());
            }
        }
        this.tracks.add(track);
    }

    private MusicalInstrument getInstrumentFromMidiTrack(MidiTrack midiTrack) {
        Iterator<MidiEvent> it = midiTrack.getEvents().iterator();
        MusicalInstrument instrument = Project.DEFAULT_INSTRUMENT;
        while (it.hasNext()) {
            MidiEvent midiEvent = (MidiEvent) it.next();
            if (midiEvent instanceof ProgramChange) {
                return MusicalInstrument.getInstrumentFromProgram(((ProgramChange) midiEvent).getProgramNumber());
            }
        }
        return instrument;
    }
}
