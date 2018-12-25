package org.catrobat.catroid.pocketmusic.note;

import android.util.LongSparseArray;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Track implements Serializable {
    private static final long serialVersionUID = 7483021689872527955L;
    private LongSparseArray<List<NoteEvent>> events = new LongSparseArray();
    private MusicalInstrument instrument;
    private MusicalKey key;
    private long lastTick;

    /* renamed from: org.catrobat.catroid.pocketmusic.note.Track$1 */
    class C18601 implements Comparator<NoteEvent> {
        C18601() {
        }

        public int compare(NoteEvent noteEvent1, NoteEvent noteEvent2) {
            if (noteEvent1.isNoteOn() == noteEvent2.isNoteOn()) {
                return 0;
            }
            if (noteEvent1.isNoteOn()) {
                return 1;
            }
            return -1;
        }
    }

    public Track(MusicalKey key, MusicalInstrument instrument) {
        this.instrument = instrument;
        this.key = key;
        this.lastTick = 0;
    }

    public Track(Track track) {
        this.instrument = track.getInstrument();
        this.key = track.getKey();
        this.lastTick = track.getLastTick();
        for (Long tick : track.getSortedTicks()) {
            long tick2 = tick.longValue();
            List<NoteEvent> noteEventList = new LinkedList();
            this.events.put(tick2, noteEventList);
            for (NoteEvent noteEvent : track.getNoteEventsForTick(tick2)) {
                noteEventList.add(new NoteEvent(noteEvent));
            }
        }
    }

    public MusicalInstrument getInstrument() {
        return this.instrument;
    }

    public MusicalKey getKey() {
        return this.key;
    }

    public void addNoteEvent(long tick, NoteEvent noteEvent) {
        List<NoteEvent> noteEventList;
        if (this.events.get(tick) != null) {
            noteEventList = (List) this.events.get(tick);
        } else {
            noteEventList = new LinkedList();
            this.events.put(tick, noteEventList);
        }
        if (!noteEvent.isNoteOn()) {
            this.lastTick = tick;
        }
        if (!eventListAlreadyContainsNoteEventWithTick(tick, noteEvent)) {
            noteEventList.add(noteEvent);
        }
    }

    private boolean eventListAlreadyContainsNoteEventWithTick(long tick, NoteEvent noteEvent) {
        for (int i = 0; i < this.events.size(); i++) {
            long key = this.events.keyAt(i);
            if (key == tick && ((List) this.events.get(key)).contains(noteEvent)) {
                return true;
            }
        }
        return false;
    }

    public List<NoteEvent> getNoteEventsForTick(long tick) {
        List<NoteEvent> noteEvents = (List) this.events.get(tick);
        Collections.sort(noteEvents, new C18601());
        return noteEvents;
    }

    public Set<Long> getSortedTicks() {
        Set<Long> treeSet = new TreeSet();
        for (int i = 0; i < this.events.size(); i++) {
            treeSet.add(Long.valueOf(this.events.keyAt(i)));
        }
        return treeSet;
    }

    public int size() {
        int size = 0;
        for (Long sortedTick : getSortedTicks()) {
            size += ((List) this.events.get(sortedTick.longValue())).size();
        }
        return size;
    }

    public long getLastTick() {
        return this.lastTick;
    }

    public long getTotalTimeInMilliseconds() {
        return NoteLength.tickToMilliseconds(this.lastTick);
    }

    public boolean empty() {
        return size() == 0;
    }

    public int hashCode() {
        return (31 * ((31 * ((31 * ((31 * 18) + this.instrument.hashCode())) + this.events.hashCode())) + this.key.hashCode())) + ((int) this.lastTick);
    }

    public boolean equals(Object obj) {
        if (obj != null) {
            if (obj instanceof Track) {
                Track track = (Track) obj;
                if (track.getInstrument() != getInstrument() || track.getKey() != getKey()) {
                    return false;
                }
                Set<Long> ownTrackTicks = getSortedTicks();
                if (!track.getSortedTicks().equals(ownTrackTicks)) {
                    return false;
                }
                for (Long tick : ownTrackTicks) {
                    long tick2 = tick.longValue();
                    if (!getNoteEventsForTick(tick2).equals(track.getNoteEventsForTick(tick2))) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[Track] instrument=");
        stringBuilder.append(this.instrument);
        stringBuilder.append(" key=");
        stringBuilder.append(this.key);
        stringBuilder.append(" size=");
        stringBuilder.append(size());
        return stringBuilder.toString();
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
