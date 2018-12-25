package org.catrobat.catroid.scratchconverter.protocol.command;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public abstract class Command {
    private final Map<ArgumentType, Object> arguments = new EnumMap(ArgumentType.class);
    private final Type type;

    public enum ArgumentType {
        CLIENT_ID("clientID"),
        JOB_ID("jobID"),
        FORCE("force"),
        VERBOSE("verbose");
        
        private final String rawValue;

        private ArgumentType(String rawValue) {
            this.rawValue = rawValue;
        }

        public String toString() {
            return this.rawValue;
        }
    }

    public enum Type {
        AUTHENTICATE(0),
        RETRIEVE_INFO(1),
        SCHEDULE_JOB(2),
        CANCEL_DOWNLOAD(3);
        
        private final int typeID;

        private Type(int typeID) {
            this.typeID = typeID;
        }

        public int getTypeID() {
            return this.typeID;
        }
    }

    public Command(Type type) {
        this.type = type;
    }

    public void addArgument(ArgumentType type, Object value) {
        this.arguments.put(type, value);
    }

    public JSONObject toJson() {
        Map<String, Object> args = new HashMap();
        for (Entry<ArgumentType, Object> entry : this.arguments.entrySet()) {
            args.put(((ArgumentType) entry.getKey()).toString(), entry.getValue());
        }
        Map<String, Object> payloadMap = new HashMap();
        payloadMap.put("cmd", Integer.valueOf(this.type.getTypeID()));
        payloadMap.put("args", args);
        return new JSONObject(payloadMap);
    }

    public Type getType() {
        return this.type;
    }
}
