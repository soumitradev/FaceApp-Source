package org.catrobat.catroid.devices.mindstorms;

public interface MindstormsCommand {
    int getLength();

    byte[] getRawCommand();
}
