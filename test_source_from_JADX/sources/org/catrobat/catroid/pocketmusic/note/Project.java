package org.catrobat.catroid.pocketmusic.note;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Project implements Serializable {
    public static final MusicalBeat DEFAULT_BEAT = MusicalBeat.BEAT_4_4;
    public static final int DEFAULT_BEATS_PER_MINUTE = 60;
    public static final MusicalInstrument DEFAULT_INSTRUMENT = MusicalInstrument.ACOUSTIC_GRAND_PIANO;
    private static final long serialVersionUID = 7396763540934053008L;
    private MusicalBeat beat;
    private int beatsPerMinute;
    private File file;
    private String name;
    private Map<String, Track> tracks;

    public Project(String name, MusicalBeat beat, int beatsPerMinute) {
        this.name = name;
        this.beatsPerMinute = beatsPerMinute;
        this.beat = beat;
        this.tracks = new HashMap();
    }

    public Project(Project project) {
        this.name = project.getName();
        this.beatsPerMinute = project.getBeatsPerMinute();
        this.beat = project.getBeat();
        this.tracks = new HashMap();
        for (String name : project.tracks.keySet()) {
            this.tracks.put(name, new Track((Track) project.tracks.get(name)));
        }
    }

    public Project(Project project, String name) {
        this(project);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBeatsPerMinute() {
        return this.beatsPerMinute;
    }

    public MusicalBeat getBeat() {
        return this.beat;
    }

    public void putTrack(String trackName, Track track) {
        this.tracks.put(trackName, track);
    }

    public Set<String> getTrackNames() {
        return this.tracks.keySet();
    }

    public Track getTrack(String trackName) {
        return (Track) this.tracks.get(trackName);
    }

    public long getTotalTimeInMilliseconds() {
        long totalTime = 0;
        for (Track track : this.tracks.values()) {
            long trackTime = track.getTotalTimeInMilliseconds();
            if (trackTime > totalTime) {
                totalTime = trackTime;
            }
        }
        return totalTime;
    }

    public int size() {
        return this.tracks.size();
    }

    public int hashCode() {
        return (31 * ((31 * ((31 * ((31 * 16) + this.name.hashCode())) + this.beatsPerMinute)) + this.beat.hashCode())) + this.tracks.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Project) {
                Project project = (Project) obj;
                if (getName().equals(project.getName()) && getBeatsPerMinute() == project.getBeatsPerMinute() && getBeat() == project.getBeat() && this.tracks.equals(project.tracks)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Project] name=");
        stringBuilder.append(this.name);
        stringBuilder.append(" beatsPerMinute=");
        stringBuilder.append(this.beatsPerMinute);
        stringBuilder.append(" trackCount=");
        stringBuilder.append(size());
        return stringBuilder.toString();
    }
}
