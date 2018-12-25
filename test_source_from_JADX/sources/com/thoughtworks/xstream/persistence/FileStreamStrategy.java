package com.thoughtworks.xstream.persistence;

import com.thoughtworks.xstream.XStream;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.File;
import org.catrobat.catroid.physics.content.bricks.CollisionReceiverBrick;

public class FileStreamStrategy extends AbstractFilePersistenceStrategy implements StreamStrategy {
    public FileStreamStrategy(File baseDirectory) {
        this(baseDirectory, new XStream());
    }

    public FileStreamStrategy(File baseDirectory, XStream xstream) {
        super(baseDirectory, xstream, null);
    }

    protected Object extractKey(String name) {
        String key = unescape(name.substring(0, name.length() - 4));
        return key.equals(CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR) ? null : key;
    }

    protected String unescape(String name) {
        StringBuffer buffer = new StringBuffer();
        char lastC = '￿';
        int currentValue = -1;
        char[] array = name.toCharArray();
        for (char c : array) {
            if (c == '_' && currentValue != -1) {
                if (lastC == '_') {
                    buffer.append('_');
                } else {
                    buffer.append((char) currentValue);
                }
                currentValue = -1;
            } else if (c == '_') {
                currentValue = 0;
            } else if (currentValue != -1) {
                currentValue = (currentValue * 16) + Integer.parseInt(String.valueOf(c), 16);
            } else {
                buffer.append(c);
            }
            lastC = c;
        }
        return buffer.toString();
    }

    protected String getName(Object key) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(escape(key == null ? CollisionReceiverBrick.ANYTHING_ESCAPE_CHAR : key.toString()));
        stringBuilder.append(".xml");
        return stringBuilder.toString();
    }

    protected String escape(String key) {
        StringBuffer buffer = new StringBuffer();
        char[] array = key.toCharArray();
        for (char c : array) {
            if (!Character.isDigit(c) && (c < 'A' || c > 'Z')) {
                if (c < 'a' || c > 'z') {
                    if (c == '_') {
                        buffer.append("__");
                    } else {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                        stringBuilder.append(Integer.toHexString(c));
                        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                        buffer.append(stringBuilder.toString());
                    }
                }
            }
            buffer.append(c);
        }
        return buffer.toString();
    }
}
