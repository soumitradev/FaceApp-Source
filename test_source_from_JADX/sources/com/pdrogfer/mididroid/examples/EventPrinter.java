package com.pdrogfer.mididroid.examples;

import com.pdrogfer.mididroid.MidiFile;
import com.pdrogfer.mididroid.event.MidiEvent;
import com.pdrogfer.mididroid.event.NoteOn;
import com.pdrogfer.mididroid.event.meta.Tempo;
import com.pdrogfer.mididroid.util.MidiEventListener;
import com.pdrogfer.mididroid.util.MidiProcessor;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class EventPrinter implements MidiEventListener {
    private String mLabel;

    public EventPrinter(String label) {
        this.mLabel = label;
    }

    public void onStart(boolean fromBeginning) {
        if (fromBeginning) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mLabel);
            stringBuilder.append(" Started!");
            printStream.println(stringBuilder.toString());
            return;
        }
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLabel);
        stringBuilder.append(" resumed");
        printStream.println(stringBuilder.toString());
    }

    public void onEvent(MidiEvent event, long ms) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLabel);
        stringBuilder.append(" received event: ");
        stringBuilder.append(event);
        printStream.println(stringBuilder.toString());
    }

    public void onStop(boolean finished) {
        if (finished) {
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.mLabel);
            stringBuilder.append(" Finished!");
            printStream.println(stringBuilder.toString());
            return;
        }
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.mLabel);
        stringBuilder.append(" paused");
        printStream.println(stringBuilder.toString());
    }

    public static void main(String[] args) {
        try {
            MidiProcessor processor = new MidiProcessor(new MidiFile(new File("inputmid.mid")));
            EventPrinter ep = new EventPrinter("Individual Listener");
            processor.registerEventListener(ep, Tempo.class);
            processor.registerEventListener(ep, NoteOn.class);
            processor.registerEventListener(new EventPrinter("Listener For All"), MidiEvent.class);
            processor.start();
            try {
                Thread.sleep(10000);
                processor.stop();
                Thread.sleep(10000);
                processor.start();
            } catch (Exception e) {
            }
        } catch (IOException e2) {
            System.err.println(e2);
        }
    }
}
