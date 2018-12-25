package javax.jmdns.impl;

import io.fabric.sdk.android.services.events.EventsFilesManager;
import io.fabric.sdk.android.services.network.UrlUtils;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import javax.jmdns.ServiceInfo$Fields;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;

public abstract class DNSEntry {
    private final DNSRecordClass _dnsClass;
    private final String _key;
    private final String _name;
    final Map<ServiceInfo$Fields, String> _qualifiedNameMap = ServiceInfoImpl.decodeQualifiedNameMapForType(getName());
    private final DNSRecordType _recordType;
    private final String _type;
    private final boolean _unique;

    public abstract boolean isExpired(long j);

    public abstract boolean isStale(long j);

    DNSEntry(String name, DNSRecordType type, DNSRecordClass recordClass, boolean unique) {
        String stringBuilder;
        this._name = name;
        this._recordType = type;
        this._dnsClass = recordClass;
        this._unique = unique;
        String domain = (String) this._qualifiedNameMap.get(ServiceInfo$Fields.Domain);
        String protocol = (String) this._qualifiedNameMap.get(ServiceInfo$Fields.Protocol);
        String application = (String) this._qualifiedNameMap.get(ServiceInfo$Fields.Application);
        String instance = ((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Instance)).toLowerCase();
        StringBuilder stringBuilder2 = new StringBuilder();
        if (application.length() > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder3.append(application);
            stringBuilder3.append(".");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        if (protocol.length() > 0) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder3.append(protocol);
            stringBuilder3.append(".");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(domain);
        stringBuilder2.append(".");
        this._type = stringBuilder2.toString();
        stringBuilder2 = new StringBuilder();
        if (instance.length() > 0) {
            stringBuilder3 = new StringBuilder();
            stringBuilder3.append(instance);
            stringBuilder3.append(".");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(this._type);
        this._key = stringBuilder2.toString().toLowerCase();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DNSEntry)) {
            return false;
        }
        DNSEntry other = (DNSEntry) obj;
        boolean z = getKey().equals(other.getKey()) && getRecordType().equals(other.getRecordType()) && getRecordClass() == other.getRecordClass();
        return z;
    }

    public boolean isSameEntry(DNSEntry entry) {
        return getKey().equals(entry.getKey()) && getRecordType().equals(entry.getRecordType()) && (DNSRecordClass.CLASS_ANY == entry.getRecordClass() || getRecordClass().equals(entry.getRecordClass()));
    }

    public boolean sameSubtype(DNSEntry other) {
        return getSubtype().equals(other.getSubtype());
    }

    public String getSubtype() {
        String subtype = (String) getQualifiedNameMap().get(ServiceInfo$Fields.Subtype);
        return subtype != null ? subtype : "";
    }

    public String getName() {
        return this._name != null ? this._name : "";
    }

    public String getType() {
        return this._type != null ? this._type : "";
    }

    public String getKey() {
        return this._key != null ? this._key : "";
    }

    public DNSRecordType getRecordType() {
        return this._recordType != null ? this._recordType : DNSRecordType.TYPE_IGNORE;
    }

    public DNSRecordClass getRecordClass() {
        return this._dnsClass != null ? this._dnsClass : DNSRecordClass.CLASS_UNKNOWN;
    }

    public boolean isUnique() {
        return this._unique;
    }

    public Map<ServiceInfo$Fields, String> getQualifiedNameMap() {
        return Collections.unmodifiableMap(this._qualifiedNameMap);
    }

    public boolean isServicesDiscoveryMetaQuery() {
        return ((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Application)).equals("dns-sd") && ((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Instance)).equals("_services");
    }

    public boolean isDomainDiscoveryQuery() {
        boolean z = false;
        if (!((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Application)).equals("dns-sd")) {
            return false;
        }
        String name = (String) this._qualifiedNameMap.get(ServiceInfo$Fields.Instance);
        if (!("b".equals(name) || "db".equals(name) || "r".equals(name) || "dr".equals(name))) {
            if (!"lb".equals(name)) {
                return z;
            }
        }
        z = true;
        return z;
    }

    public boolean isReverseLookup() {
        if (!isV4ReverseLookup()) {
            if (!isV6ReverseLookup()) {
                return false;
            }
        }
        return true;
    }

    public boolean isV4ReverseLookup() {
        return ((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Domain)).endsWith("in-addr.arpa");
    }

    public boolean isV6ReverseLookup() {
        return ((String) this._qualifiedNameMap.get(ServiceInfo$Fields.Domain)).endsWith("ip6.arpa");
    }

    public boolean isSameRecordClass(DNSEntry entry) {
        return entry != null && entry.getRecordClass() == getRecordClass();
    }

    public boolean isSameType(DNSEntry entry) {
        return entry != null && entry.getRecordType() == getRecordType();
    }

    protected void toByteArray(DataOutputStream dout) throws IOException {
        dout.write(getName().getBytes(UrlUtils.UTF8));
        dout.writeShort(getRecordType().indexValue());
        dout.writeShort(getRecordClass().indexValue());
    }

    protected byte[] toByteArray() {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            DataOutputStream dout = new DataOutputStream(bout);
            toByteArray(dout);
            dout.close();
            return bout.toByteArray();
        } catch (IOException e) {
            throw new InternalError();
        }
    }

    public int compareTo(DNSEntry that) {
        byte[] thisBytes = toByteArray();
        byte[] thatBytes = that.toByteArray();
        int n = Math.min(thisBytes.length, thatBytes.length);
        for (int i = 0; i < n; i++) {
            if (thisBytes[i] > thatBytes[i]) {
                return 1;
            }
            if (thisBytes[i] < thatBytes[i]) {
                return -1;
            }
        }
        return thisBytes.length - thatBytes.length;
    }

    public int hashCode() {
        return (getKey().hashCode() + getRecordType().indexValue()) + getRecordClass().indexValue();
    }

    public String toString() {
        StringBuilder aLog = new StringBuilder(200);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(System.identityHashCode(this));
        aLog.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(" type: ");
        stringBuilder.append(getRecordType());
        aLog.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(", class: ");
        stringBuilder.append(getRecordClass());
        aLog.append(stringBuilder.toString());
        aLog.append(this._unique ? "-unique," : ",");
        stringBuilder = new StringBuilder();
        stringBuilder.append(" name: ");
        stringBuilder.append(this._name);
        aLog.append(stringBuilder.toString());
        toString(aLog);
        aLog.append("]");
        return aLog.toString();
    }

    protected void toString(StringBuilder aLog) {
    }
}
