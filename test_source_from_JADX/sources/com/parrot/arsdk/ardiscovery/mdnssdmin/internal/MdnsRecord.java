package com.parrot.arsdk.ardiscovery.mdnssdmin.internal;

import org.billthefarmer.mididriver.GeneralMidiConstants;

public class MdnsRecord {
    protected final String name;
    protected final Type type;

    enum Type {
        A((byte) 1),
        PTR((byte) 12),
        TXT((byte) 16),
        SRV(GeneralMidiConstants.ELECTRIC_BASS_FINGER);
        
        private final byte val;

        private Type(byte val) {
            this.val = val;
        }

        static Type get(int val) {
            for (Type type : values()) {
                if (type.val == val) {
                    return type;
                }
            }
            return null;
        }
    }

    public MdnsRecord(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MdnsRecord)) {
            return false;
        }
        MdnsRecord mdnsEntry = (MdnsRecord) o;
        if (this.name.equals(mdnsEntry.name) && this.type == mdnsEntry.type) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.name.hashCode() * 31) + this.type.hashCode();
    }
}
