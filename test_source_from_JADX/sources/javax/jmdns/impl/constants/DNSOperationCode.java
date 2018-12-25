package javax.jmdns.impl.constants;

import com.badlogic.gdx.net.HttpResponseHeader;

public enum DNSOperationCode {
    Query("Query", 0),
    IQuery("Inverse Query", 1),
    Status(HttpResponseHeader.Status, 2),
    Unassigned("Unassigned", 3),
    Notify("Notify", 4),
    Update("Update", 5);
    
    static final int OpCode_MASK = 30720;
    private final String _externalName;
    private final int _index;

    private DNSOperationCode(String name, int index) {
        this._externalName = name;
        this._index = index;
    }

    public String externalName() {
        return this._externalName;
    }

    public int indexValue() {
        return this._index;
    }

    public static DNSOperationCode operationCodeForFlags(int flags) {
        int maskedIndex = (flags & OpCode_MASK) >> 11;
        for (DNSOperationCode aCode : values()) {
            if (aCode._index == maskedIndex) {
                return aCode;
            }
        }
        return Unassigned;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name());
        stringBuilder.append(" index ");
        stringBuilder.append(indexValue());
        return stringBuilder.toString();
    }
}
