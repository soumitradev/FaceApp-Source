package org.catrobat.catroid.pocketmusic.note.midi;

import android.os.Environment;
import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.MidiTrack;
import com.pdrogfer.mididroid.event.ProgramChange;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.event.meta.Text;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import com.pdrogfer.mididroid.event.meta.TrackName;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.catrobat.catroid.pocketmusic.note.MusicalBeat;
import org.catrobat.catroid.pocketmusic.note.NoteEvent;
import org.catrobat.catroid.pocketmusic.note.Project;
import org.catrobat.catroid.pocketmusic.note.Track;

public class ProjectToMidiConverter {
    private static final int MAX_CHANNEL = 16;
    public static final String MIDI_FILE_EXTENSION = ".midi";
    public static final String MIDI_FILE_IDENTIFIER = "Musicdroid Midi File";
    public static final File MIDI_FOLDER = new File(Environment.getExternalStorageDirectory().toString(), "musicdroid");
    private NoteEventToMidiEventConverter eventConverter = new NoteEventToMidiEventConverter();
    private int nextChannel = 0;

    public void writeProjectAsMidi(Project project) throws IOException, MidiException {
        MidiFile midiFile = convertProject(project);
        checkMidiFolder();
        midiFile.writeToFile(getMidiFileFromProjectName(project.getName()));
    }

    private static void checkMidiFolder() throws IOException {
        if (!MIDI_FOLDER.exists() && !MIDI_FOLDER.mkdir()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not create folder: ");
            stringBuilder.append(MIDI_FOLDER);
            throw new IOException(stringBuilder.toString());
        }
    }

    public static File getMidiFileFromProjectName(String name) throws IOException {
        checkMidiFolder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MIDI_FOLDER);
        stringBuilder.append(File.separator);
        stringBuilder.append(name);
        stringBuilder.append(MIDI_FILE_EXTENSION);
        return new File(stringBuilder.toString());
    }

    public static String removeMidiExtensionFromString(String input) {
        return input.split(MIDI_FILE_EXTENSION)[0];
    }

    public void writeProjectAsMidi(Project project, File file) throws IOException, MidiException {
        convertProject(project).writeToFile(file);
    }

    private MidiFile convertProject(Project project) throws MidiException {
        for (String trackName : project.getTrackNames()) {
            if (project.getTrack(trackName).size() == 0) {
                throw new MidiException("Cannot save a project with an empty track!");
            }
        }
        ArrayList<MidiTrack> tracks = new ArrayList();
        tracks.add(createTempoTrackWithMetaInfo(project.getBeat(), project.getBeatsPerMinute()));
        for (String trackName2 : project.getTrackNames()) {
            tracks.add(createNoteTrack(trackName2, project.getTrack(trackName2), getNextChannel()));
        }
        return new MidiFile(480, tracks);
    }

    private int getNextChannel() throws MidiException {
        if (this.nextChannel >= 16) {
            throw new MidiException("You cannot have more than 16 channels!");
        }
        int i = this.nextChannel;
        this.nextChannel = i + 1;
        return i;
    }

    private MidiTrack createTempoTrackWithMetaInfo(MusicalBeat beat, int beatsPerMinute) {
        MidiTrack tempoTrack = new MidiTrack();
        tempoTrack.insertEvent(new Text(0, 0, MIDI_FILE_IDENTIFIER));
        Tempo tempo = new Tempo();
        tempo.setBpm((float) beatsPerMinute);
        tempoTrack.insertEvent(tempo);
        TimeSignature timeSignature = new TimeSignature();
        timeSignature.setTimeSignature(beat.getTopNumber(), beat.getBottomNumber(), 24, 8);
        tempoTrack.insertEvent(timeSignature);
        return tempoTrack;
    }

    private MidiTrack createNoteTrack(String trackName, Track track, int channel) throws MidiException {
        MidiTrack noteTrack = new MidiTrack();
        noteTrack.insertEvent(new TrackName(0, (long) channel, trackName));
        noteTrack.insertEvent(new ProgramChange(0, channel, track.getInstrument().getProgram()));
        for (Long tick : track.getSortedTicks()) {
            long tick2 = tick.longValue();
            for (NoteEvent noteEvent : track.getNoteEventsForTick(tick2)) {
                noteTrack.insertEvent(this.eventConverter.convertNoteEvent(tick2, noteEvent, channel));
            }
        }
        return noteTrack;
    }
}
