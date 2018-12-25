package com.pdrogfer.mididroid.util;

import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.MidiTrack;
import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.event.meta.TimeSignature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.catrobat.catroid.common.BrickValues;

public class MidiProcessor {
    private static final int PROCESS_RATE_MS = 8;
    private MidiTrackEventQueue[] mEventQueues;
    private HashMap<Class<? extends MidiEvent>, ArrayList<MidiEventListener>> mEventsToListeners = new HashMap();
    private HashMap<MidiEventListener, ArrayList<Class<? extends MidiEvent>>> mListenersToEvents = new HashMap();
    private int mMPQN = Tempo.DEFAULT_MPQN;
    private MetronomeTick mMetronome = new MetronomeTick(new TimeSignature(), this.mPPQ);
    private MidiFile mMidiFile;
    private long mMsElapsed;
    private int mPPQ = this.mMidiFile.getResolution();
    private boolean mRunning;
    private double mTicksElapsed;

    /* renamed from: com.pdrogfer.mididroid.util.MidiProcessor$1 */
    class C16381 implements Runnable {
        C16381() {
        }

        public void run() {
            MidiProcessor.this.process();
        }
    }

    private class MidiTrackEventQueue {
        private ArrayList<MidiEvent> mEventsToDispatch = new ArrayList();
        private Iterator<MidiEvent> mIterator = this.mTrack.getEvents().iterator();
        private MidiEvent mNext;
        private MidiTrack mTrack;

        public MidiTrackEventQueue(MidiTrack track) {
            this.mTrack = track;
            if (this.mIterator.hasNext() != null) {
                this.mNext = (MidiEvent) this.mIterator.next();
            }
        }

        public ArrayList<MidiEvent> getNextEventsUpToTick(double tick) {
            this.mEventsToDispatch.clear();
            while (this.mNext != null && ((double) this.mNext.getTick()) <= tick) {
                this.mEventsToDispatch.add(this.mNext);
                if (this.mIterator.hasNext()) {
                    this.mNext = (MidiEvent) this.mIterator.next();
                } else {
                    this.mNext = null;
                }
            }
            return this.mEventsToDispatch;
        }

        public boolean hasMoreEvents() {
            return this.mNext != null;
        }
    }

    public MidiProcessor(MidiFile input) {
        this.mMidiFile = input;
        reset();
    }

    public synchronized void start() {
        if (!this.mRunning) {
            this.mRunning = true;
            new Thread(new C16381()).start();
        }
    }

    public void stop() {
        this.mRunning = false;
    }

    public void reset() {
        int i = 0;
        this.mRunning = false;
        this.mTicksElapsed = BrickValues.SET_COLOR_TO;
        this.mMsElapsed = 0;
        this.mMetronome.setTimeSignature(new TimeSignature());
        ArrayList<MidiTrack> tracks = this.mMidiFile.getTracks();
        if (this.mEventQueues == null) {
            this.mEventQueues = new MidiTrackEventQueue[tracks.size()];
        }
        while (i < tracks.size()) {
            this.mEventQueues[i] = new MidiTrackEventQueue((MidiTrack) tracks.get(i));
            i++;
        }
    }

    public boolean isStarted() {
        return this.mTicksElapsed > BrickValues.SET_COLOR_TO;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    protected void onStart(boolean fromBeginning) {
        for (MidiEventListener mel : this.mListenersToEvents.keySet()) {
            mel.onStart(fromBeginning);
        }
    }

    protected void onStop(boolean finished) {
        for (MidiEventListener mel : this.mListenersToEvents.keySet()) {
            mel.onStop(finished);
        }
    }

    public void registerEventListener(MidiEventListener mel, Class<? extends MidiEvent> event) {
        ArrayList<MidiEventListener> listeners = (ArrayList) this.mEventsToListeners.get(event);
        if (listeners == null) {
            listeners = new ArrayList();
            listeners.add(mel);
            this.mEventsToListeners.put(event, listeners);
        } else {
            listeners.add(mel);
        }
        ArrayList<Class<? extends MidiEvent>> events = (ArrayList) this.mListenersToEvents.get(mel);
        if (events == null) {
            events = new ArrayList();
            events.add(event);
            this.mListenersToEvents.put(mel, events);
            return;
        }
        events.add(event);
    }

    public void unregisterEventListener(MidiEventListener mel) {
        ArrayList<Class<? extends MidiEvent>> events = (ArrayList) this.mListenersToEvents.get(mel);
        if (events != null) {
            Iterator it = events.iterator();
            while (it.hasNext()) {
                ((ArrayList) this.mEventsToListeners.get((Class) it.next())).remove(mel);
            }
            this.mListenersToEvents.remove(mel);
        }
    }

    public void unregisterEventListener(MidiEventListener mel, Class<? extends MidiEvent> event) {
        ArrayList<MidiEventListener> listeners = (ArrayList) this.mEventsToListeners.get(event);
        if (listeners != null) {
            listeners.remove(mel);
        }
        ArrayList<Class<? extends MidiEvent>> events = (ArrayList) this.mListenersToEvents.get(mel);
        if (events != null) {
            events.remove(event);
        }
    }

    public void unregisterAllEventListeners() {
        this.mEventsToListeners.clear();
        this.mListenersToEvents.clear();
    }

    protected void dispatch(MidiEvent event) {
        if (event.getClass().equals(Tempo.class)) {
            this.mMPQN = ((Tempo) event).getMpqn();
        } else if (event.getClass().equals(TimeSignature.class)) {
            boolean z = true;
            if (this.mMetronome.getBeatNumber() == 1) {
                z = false;
            }
            boolean shouldDispatch = z;
            this.mMetronome.setTimeSignature((TimeSignature) event);
            if (shouldDispatch) {
                dispatch(this.mMetronome);
            }
        }
        sendOnEventForClass(event, event.getClass());
        sendOnEventForClass(event, MidiEvent.class);
    }

    private void sendOnEventForClass(MidiEvent event, Class<? extends MidiEvent> eventClass) {
        ArrayList<MidiEventListener> listeners = (ArrayList) this.mEventsToListeners.get(eventClass);
        if (listeners != null) {
            Iterator it = listeners.iterator();
            while (it.hasNext()) {
                ((MidiEventListener) it.next()).onEvent(event, this.mMsElapsed);
            }
        }
    }

    private void process() {
        double d = 1.0d;
        onStart(this.mTicksElapsed < 1.0d);
        long lastMs = System.currentTimeMillis();
        boolean finished = false;
        while (r1.mRunning) {
            long now = System.currentTimeMillis();
            long msElapsed = now - lastMs;
            if (msElapsed < 8) {
                try {
                    Thread.sleep(8 - msElapsed);
                } catch (Exception e) {
                }
            } else {
                double ticksElapsed = MidiUtil.msToTicks(msElapsed, r1.mMPQN, r1.mPPQ);
                if (ticksElapsed >= d) {
                    boolean finished2;
                    if (r1.mMetronome.update(ticksElapsed)) {
                        dispatch(r1.mMetronome);
                    }
                    lastMs = now;
                    r1.mMsElapsed += msElapsed;
                    r1.mTicksElapsed += ticksElapsed;
                    boolean more = false;
                    int i = 0;
                    while (i < r1.mEventQueues.length) {
                        MidiTrackEventQueue queue = r1.mEventQueues[i];
                        if (queue.hasMoreEvents()) {
                            finished2 = finished;
                            Iterator it = queue.getNextEventsUpToTick(r1.mTicksElapsed).iterator();
                            while (it.hasNext()) {
                                dispatch((MidiEvent) it.next());
                            }
                            if (queue.hasMoreEvents()) {
                                more = true;
                            }
                        } else {
                            finished2 = finished;
                        }
                        i++;
                        finished = finished2;
                    }
                    finished2 = finished;
                    if (!more) {
                        finished = true;
                        break;
                    } else {
                        finished = finished2;
                        d = 1.0d;
                    }
                }
            }
        }
        r1.mRunning = false;
        onStop(finished);
    }
}
