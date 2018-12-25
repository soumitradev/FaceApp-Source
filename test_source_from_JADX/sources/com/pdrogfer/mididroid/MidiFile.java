package com.pdrogfer.mididroid;

import com.pdrogfer.mididroid.util.MidiUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import org.billthefarmer.mididriver.GeneralMidiConstants;

public class MidiFile {
    public static final int DEFAULT_RESOLUTION = 480;
    public static final int HEADER_SIZE = 14;
    public static final byte[] IDENTIFIER = new byte[]{GeneralMidiConstants.SHAKUHACHI, GeneralMidiConstants.LEAD_4_CHARANG, GeneralMidiConstants.SIT_R, GeneralMidiConstants.FX_4_BRIGHTNESS};
    private int mResolution;
    private int mTrackCount;
    private ArrayList<MidiTrack> mTracks;
    private int mType;

    public MidiFile() {
        this(480);
    }

    public MidiFile(int resolution) {
        this(resolution, new ArrayList());
    }

    public MidiFile(int resolution, ArrayList<MidiTrack> tracks) {
        this.mResolution = resolution >= 0 ? resolution : 480;
        this.mTracks = tracks != null ? tracks : new ArrayList();
        this.mTrackCount = tracks.size();
        int i = 1;
        if (this.mTrackCount <= 1) {
            i = 0;
        }
        this.mType = i;
    }

    public MidiFile(File fileIn) throws FileNotFoundException, IOException {
        this(new FileInputStream(fileIn));
    }

    public MidiFile(InputStream rawIn) throws IOException {
        BufferedInputStream in = new BufferedInputStream(rawIn);
        byte[] buffer = new byte[14];
        in.read(buffer);
        initFromBuffer(buffer);
        this.mTracks = new ArrayList();
        for (int i = 0; i < this.mTrackCount; i++) {
            this.mTracks.add(new MidiTrack(in));
        }
    }

    public void setType(int type) {
        if (type < 0) {
            type = 0;
        } else if (type > 2) {
            type = 1;
        } else if (type == 0 && this.mTrackCount > 1) {
            type = 1;
        }
        this.mType = type;
    }

    public int getType() {
        return this.mType;
    }

    public int getTrackCount() {
        return this.mTrackCount;
    }

    public void setResolution(int res) {
        if (res >= 0) {
            this.mResolution = res;
        }
    }

    public int getResolution() {
        return this.mResolution;
    }

    public long getLengthInTicks() {
        long length = 0;
        Iterator it = this.mTracks.iterator();
        while (it.hasNext()) {
            long l = ((MidiTrack) it.next()).getLengthInTicks();
            if (l > length) {
                length = l;
            }
        }
        return length;
    }

    public ArrayList<MidiTrack> getTracks() {
        return this.mTracks;
    }

    public void addTrack(MidiTrack T) {
        addTrack(T, this.mTracks.size());
    }

    public void addTrack(MidiTrack T, int pos) {
        if (pos > this.mTracks.size()) {
            pos = this.mTracks.size();
        } else if (pos < 0) {
            pos = 0;
        }
        this.mTracks.add(pos, T);
        this.mTrackCount = this.mTracks.size();
        int i = 1;
        if (this.mTrackCount <= 1) {
            i = 0;
        }
        this.mType = i;
    }

    public void removeTrack(int pos) {
        if (pos >= 0) {
            if (pos < this.mTracks.size()) {
                this.mTracks.remove(pos);
                this.mTrackCount = this.mTracks.size();
                int i = 1;
                if (this.mTrackCount <= 1) {
                    i = 0;
                }
                this.mType = i;
            }
        }
    }

    public void writeToFile(File outFile) throws FileNotFoundException, IOException {
        FileOutputStream fout = new FileOutputStream(outFile);
        fout.write(IDENTIFIER);
        fout.write(MidiUtil.intToBytes(6, 4));
        fout.write(MidiUtil.intToBytes(this.mType, 2));
        fout.write(MidiUtil.intToBytes(this.mTrackCount, 2));
        fout.write(MidiUtil.intToBytes(this.mResolution, 2));
        Iterator it = this.mTracks.iterator();
        while (it.hasNext()) {
            ((MidiTrack) it.next()).writeToFile(fout);
        }
        fout.flush();
        fout.close();
    }

    private void initFromBuffer(byte[] buffer) {
        if (MidiUtil.bytesEqual(buffer, IDENTIFIER, 0, 4)) {
            this.mType = MidiUtil.bytesToInt(buffer, 8, 2);
            this.mTrackCount = MidiUtil.bytesToInt(buffer, 10, 2);
            this.mResolution = MidiUtil.bytesToInt(buffer, 12, 2);
            return;
        }
        System.out.println("File identifier not MThd. Exiting");
        this.mType = 0;
        this.mTrackCount = 0;
        this.mResolution = 480;
    }
}
