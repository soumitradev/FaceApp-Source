package com.thoughtworks.xstream.io.path;

import com.thoughtworks.xstream.converters.ErrorWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.ReaderWrapper;
import org.catrobat.catroid.transfers.MediaDownloadService;

public class PathTrackingReader extends ReaderWrapper {
    private final PathTracker pathTracker;

    public PathTrackingReader(HierarchicalStreamReader reader, PathTracker pathTracker) {
        super(reader);
        this.pathTracker = pathTracker;
        pathTracker.pushElement(getNodeName());
    }

    public void moveDown() {
        super.moveDown();
        this.pathTracker.pushElement(getNodeName());
    }

    public void moveUp() {
        super.moveUp();
        this.pathTracker.popElement();
    }

    public void appendErrors(ErrorWriter errorWriter) {
        errorWriter.add(MediaDownloadService.MEDIA_FILE_PATH, this.pathTracker.getPath().toString());
        super.appendErrors(errorWriter);
    }
}
