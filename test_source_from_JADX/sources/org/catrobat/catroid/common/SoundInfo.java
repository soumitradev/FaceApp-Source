package org.catrobat.catroid.common;

import android.support.annotation.NonNull;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import org.catrobat.catroid.io.StorageOperations;

public class SoundInfo implements Cloneable, Nameable, Serializable {
    private static final String TAG = SoundInfo.class.getSimpleName();
    private static final long serialVersionUID = 1;
    private transient File file;
    @XStreamAsAttribute
    private String fileName;
    @XStreamAsAttribute
    private String name;

    public SoundInfo(String name, @NonNull File file) {
        this.name = name;
        this.file = file;
        this.fileName = file.getName();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXstreamFileName() {
        if (this.file == null) {
            return this.fileName;
        }
        throw new IllegalStateException("This should be used only to deserialize the Object. You should use @getFile() instead.");
    }

    public File getFile() {
        return this.file;
    }

    public void setFile(File file) {
        this.file = file;
        this.fileName = file.getName();
    }

    public SoundInfo clone() {
        try {
            return new SoundInfo(this.name, StorageOperations.duplicateFile(this.file));
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(TAG);
            stringBuilder.append(": Could not copy file: ");
            stringBuilder.append(this.file.getAbsolutePath());
            throw new RuntimeException(stringBuilder.toString());
        }
    }
}
