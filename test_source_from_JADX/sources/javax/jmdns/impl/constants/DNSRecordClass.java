package javax.jmdns.impl.constants;

import com.badlogic.gdx.Input.Keys;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum DNSRecordClass {
    CLASS_UNKNOWN("?", 0),
    CLASS_IN("in", 1),
    CLASS_CS("cs", 2),
    CLASS_CH("ch", 3),
    CLASS_HS("hs", 4),
    CLASS_NONE("none", Keys.F11),
    CLASS_ANY("any", 255);
    
    public static final int CLASS_MASK = 32767;
    public static final int CLASS_UNIQUE = 32768;
    public static final boolean NOT_UNIQUE = false;
    public static final boolean UNIQUE = true;
    private static Logger logger;
    private final String _externalName;
    private final int _index;

    static {
        logger = Logger.getLogger(DNSRecordClass.class.getName());
    }

    private DNSRecordClass(String name, int index) {
        this._externalName = name;
        this._index = index;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public boolean isUnique(int index) {
        return (this == CLASS_UNKNOWN || (32768 & index) == 0) ? false : true;
    }

    public static DNSRecordClass classForName(String name) {
        if (name != null) {
            String aName = name.toLowerCase();
            for (DNSRecordClass aClass : values()) {
                if (aClass._externalName.equals(aName)) {
                    return aClass;
                }
            }
        }
        Logger logger = logger;
        Level level = Level.WARNING;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find record class for name: ");
        stringBuilder.append(name);
        logger.log(level, stringBuilder.toString());
        return CLASS_UNKNOWN;
    }

    public static DNSRecordClass classForIndex(int index) {
        int maskedIndex = index & CLASS_MASK;
        for (DNSRecordClass aClass : values()) {
            if (aClass._index == maskedIndex) {
                return aClass;
            }
        }
        Logger logger = logger;
        Level level = Level.WARNING;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not find record class for index: ");
        stringBuilder.append(index);
        logger.log(level, stringBuilder.toString());
        return CLASS_UNKNOWN;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name());
        stringBuilder.append(" index ");
        stringBuilder.append(indexValue());
        return stringBuilder.toString();
    }
}
