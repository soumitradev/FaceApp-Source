package javax.jmdns.impl;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceInfo$Fields;
import javax.jmdns.impl.DNSOutgoing.MessageOutputStream;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import name.antonsmirnov.firmata.FormatHelper;

public abstract class DNSRecord extends DNSEntry {
    public static final byte[] EMPTY_TXT = new byte[]{(byte) 0};
    private static Logger logger = Logger.getLogger(DNSRecord.class.getName());
    private long _created = System.currentTimeMillis();
    private InetAddress _source;
    private int _ttl;

    public static abstract class Address extends DNSRecord {
        private static Logger logger1 = Logger.getLogger(Address.class.getName());
        InetAddress _addr;

        protected Address(String name, DNSRecordType type, DNSRecordClass recordClass, boolean unique, int ttl, InetAddress addr) {
            super(name, type, recordClass, unique, ttl);
            this._addr = addr;
        }

        protected Address(String name, DNSRecordType type, DNSRecordClass recordClass, boolean unique, int ttl, byte[] rawAddress) {
            super(name, type, recordClass, unique, ttl);
            try {
                this._addr = InetAddress.getByAddress(rawAddress);
            } catch (UnknownHostException exception) {
                logger1.log(Level.WARNING, "Address() exception ", exception);
            }
        }

        boolean same(DNSRecord other) {
            boolean z = false;
            if (!(other instanceof Address)) {
                return false;
            }
            if (sameName(other) && sameValue(other)) {
                z = true;
            }
            return z;
        }

        boolean sameName(DNSRecord other) {
            return getName().equalsIgnoreCase(other.getName());
        }

        boolean sameValue(DNSRecord other) {
            if (!(other instanceof Address)) {
                return false;
            }
            Address address = (Address) other;
            if (getAddress() != null || address.getAddress() == null) {
                return getAddress().equals(address.getAddress());
            }
            return false;
        }

        public boolean isSingleValued() {
            return false;
        }

        InetAddress getAddress() {
            return this._addr;
        }

        protected void toByteArray(DataOutputStream dout) throws IOException {
            super.toByteArray(dout);
            byte[] buffer = getAddress().getAddress();
            for (byte writeByte : buffer) {
                dout.writeByte(writeByte);
            }
        }

        boolean handleQuery(JmDNSImpl dns, long expirationTime) {
            if (!dns.getLocalHost().conflictWithRecord(this)) {
                return false;
            }
            int comparison = compareTo(dns.getLocalHost().getDNSAddressRecord(getRecordType(), isUnique(), 3600));
            if (comparison == 0) {
                logger1.finer("handleQuery() Ignoring an identical address query");
                return false;
            }
            logger1.finer("handleQuery() Conflicting query detected.");
            if (dns.isProbing() && comparison > 0) {
                dns.getLocalHost().incrementHostName();
                dns.getCache().clear();
                for (ServiceInfo serviceInfo : dns.getServices().values()) {
                    ((ServiceInfoImpl) serviceInfo).revertState();
                }
            }
            dns.revertState();
            return true;
        }

        boolean handleResponse(JmDNSImpl dns) {
            if (!dns.getLocalHost().conflictWithRecord(this)) {
                return false;
            }
            logger1.finer("handleResponse() Denial detected");
            if (dns.isProbing()) {
                dns.getLocalHost().incrementHostName();
                dns.getCache().clear();
                for (ServiceInfo serviceInfo : dns.getServices().values()) {
                    ((ServiceInfoImpl) serviceInfo).revertState();
                }
            }
            dns.revertState();
            return true;
        }

        DNSOutgoing addAnswer(JmDNSImpl dns, DNSIncoming in, InetAddress addr, int port, DNSOutgoing out) throws IOException {
            return out;
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, persistent, (byte[]) null);
        }

        public ServiceEvent getServiceEvent(JmDNSImpl dns) {
            ServiceInfo info = getServiceInfo(null);
            ((ServiceInfoImpl) info).setDns(dns);
            return new ServiceEventImpl(dns, info.getType(), info.getName(), info);
        }

        protected void toString(StringBuilder aLog) {
            super.toString(aLog);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" address: '");
            stringBuilder.append(getAddress() != null ? getAddress().getHostAddress() : "null");
            stringBuilder.append(FormatHelper.QUOTE);
            aLog.append(stringBuilder.toString());
        }
    }

    public static class HostInformation extends DNSRecord {
        String _cpu;
        String _os;

        public HostInformation(String name, DNSRecordClass recordClass, boolean unique, int ttl, String cpu, String os) {
            super(name, DNSRecordType.TYPE_HINFO, recordClass, unique, ttl);
            this._cpu = cpu;
            this._os = os;
        }

        DNSOutgoing addAnswer(JmDNSImpl dns, DNSIncoming in, InetAddress addr, int port, DNSOutgoing out) throws IOException {
            return out;
        }

        boolean handleQuery(JmDNSImpl dns, long expirationTime) {
            return false;
        }

        boolean handleResponse(JmDNSImpl dns) {
            return false;
        }

        boolean sameValue(DNSRecord other) {
            boolean z = false;
            if (!(other instanceof HostInformation)) {
                return false;
            }
            HostInformation hinfo = (HostInformation) other;
            if (this._cpu == null && hinfo._cpu != null) {
                return false;
            }
            if (this._os == null && hinfo._os != null) {
                return false;
            }
            if (this._cpu.equals(hinfo._cpu) && this._os.equals(hinfo._os)) {
                z = true;
            }
            return z;
        }

        public boolean isSingleValued() {
            return true;
        }

        void write(MessageOutputStream out) {
            String hostInfo = new StringBuilder();
            hostInfo.append(this._cpu);
            hostInfo.append(FormatHelper.SPACE);
            hostInfo.append(this._os);
            hostInfo = hostInfo.toString();
            out.writeUTF(hostInfo, 0, hostInfo.length());
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            Map<String, String> hinfo = new HashMap(2);
            hinfo.put("cpu", this._cpu);
            hinfo.put("os", this._os);
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, persistent, hinfo);
        }

        public ServiceEvent getServiceEvent(JmDNSImpl dns) {
            ServiceInfo info = getServiceInfo(null);
            ((ServiceInfoImpl) info).setDns(dns);
            return new ServiceEventImpl(dns, info.getType(), info.getName(), info);
        }

        protected void toString(StringBuilder aLog) {
            super.toString(aLog);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" cpu: '");
            stringBuilder.append(this._cpu);
            stringBuilder.append("' os: '");
            stringBuilder.append(this._os);
            stringBuilder.append(FormatHelper.QUOTE);
            aLog.append(stringBuilder.toString());
        }
    }

    public static class Pointer extends DNSRecord {
        private final String _alias;

        public Pointer(String name, DNSRecordClass recordClass, boolean unique, int ttl, String alias) {
            super(name, DNSRecordType.TYPE_PTR, recordClass, unique, ttl);
            this._alias = alias;
        }

        public boolean isSameEntry(DNSEntry entry) {
            return super.isSameEntry(entry) && (entry instanceof Pointer) && sameValue((Pointer) entry);
        }

        void write(MessageOutputStream out) {
            out.writeName(this._alias);
        }

        boolean sameValue(DNSRecord other) {
            if (!(other instanceof Pointer)) {
                return false;
            }
            Pointer pointer = (Pointer) other;
            if (this._alias != null || pointer._alias == null) {
                return this._alias.equals(pointer._alias);
            }
            return false;
        }

        public boolean isSingleValued() {
            return false;
        }

        boolean handleQuery(JmDNSImpl dns, long expirationTime) {
            return false;
        }

        boolean handleResponse(JmDNSImpl dns) {
            return false;
        }

        String getAlias() {
            return this._alias;
        }

        DNSOutgoing addAnswer(JmDNSImpl dns, DNSIncoming in, InetAddress addr, int port, DNSOutgoing out) throws IOException {
            return out;
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            if (isServicesDiscoveryMetaQuery()) {
                return new ServiceInfoImpl(ServiceInfoImpl.decodeQualifiedNameMapForType(getAlias()), 0, 0, 0, persistent, (byte[]) null);
            } else if (isReverseLookup()) {
                return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, persistent, (byte[]) null);
            } else if (isDomainDiscoveryQuery()) {
                return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, persistent, (byte[]) null);
            } else {
                Map<ServiceInfo$Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(getAlias());
                map.put(ServiceInfo$Fields.Subtype, getQualifiedNameMap().get(ServiceInfo$Fields.Subtype));
                return new ServiceInfoImpl(map, 0, 0, 0, persistent, getAlias());
            }
        }

        public ServiceEvent getServiceEvent(JmDNSImpl dns) {
            ServiceInfo info = getServiceInfo(null);
            ((ServiceInfoImpl) info).setDns(dns);
            String domainName = info.getType();
            return new ServiceEventImpl(dns, domainName, JmDNSImpl.toUnqualifiedName(domainName, getAlias()), info);
        }

        protected void toString(StringBuilder aLog) {
            super.toString(aLog);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" alias: '");
            stringBuilder.append(this._alias != null ? this._alias.toString() : "null");
            stringBuilder.append(FormatHelper.QUOTE);
            aLog.append(stringBuilder.toString());
        }
    }

    public static class Service extends DNSRecord {
        private static Logger logger1 = Logger.getLogger(Service.class.getName());
        private final int _port;
        private final int _priority;
        private final String _server;
        private final int _weight;

        public Service(String name, DNSRecordClass recordClass, boolean unique, int ttl, int priority, int weight, int port, String server) {
            super(name, DNSRecordType.TYPE_SRV, recordClass, unique, ttl);
            this._priority = priority;
            this._weight = weight;
            this._port = port;
            this._server = server;
        }

        void write(MessageOutputStream out) {
            out.writeShort(this._priority);
            out.writeShort(this._weight);
            out.writeShort(this._port);
            if (DNSIncoming.USE_DOMAIN_NAME_FORMAT_FOR_SRV_TARGET) {
                out.writeName(this._server);
                return;
            }
            out.writeUTF(this._server, 0, this._server.length());
            out.writeByte(0);
        }

        protected void toByteArray(DataOutputStream dout) throws IOException {
            super.toByteArray(dout);
            dout.writeShort(this._priority);
            dout.writeShort(this._weight);
            dout.writeShort(this._port);
            try {
                dout.write(this._server.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
            }
        }

        String getServer() {
            return this._server;
        }

        public int getPriority() {
            return this._priority;
        }

        public int getWeight() {
            return this._weight;
        }

        public int getPort() {
            return this._port;
        }

        boolean sameValue(DNSRecord other) {
            boolean z = false;
            if (!(other instanceof Service)) {
                return false;
            }
            Service s = (Service) other;
            if (this._priority == s._priority && this._weight == s._weight && this._port == s._port && this._server.equals(s._server)) {
                z = true;
            }
            return z;
        }

        public boolean isSingleValued() {
            return true;
        }

        boolean handleQuery(JmDNSImpl dns, long expirationTime) {
            ServiceInfoImpl info = (ServiceInfoImpl) dns.getServices().get(getKey());
            if (info == null || (!(info.isAnnouncing() || info.isAnnounced()) || (r1._port == info.getPort() && r1._server.equalsIgnoreCase(dns.getLocalHost().getName())))) {
                JmDNSImpl jmDNSImpl = dns;
                return false;
            }
            Logger logger = logger1;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleQuery() Conflicting probe detected from: ");
            stringBuilder.append(getRecordSource());
            logger.finer(stringBuilder.toString());
            Service service = new Service(info.getQualifiedName(), DNSRecordClass.CLASS_IN, true, 3600, info.getPriority(), info.getWeight(), info.getPort(), dns.getLocalHost().getName());
            try {
                if (dns.getInterface().equals(getRecordSource())) {
                    Logger logger2 = logger1;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("Got conflicting probe from ourselves\nincoming: ");
                    stringBuilder2.append(toString());
                    stringBuilder2.append("\n");
                    stringBuilder2.append("local   : ");
                    stringBuilder2.append(service.toString());
                    logger2.warning(stringBuilder2.toString());
                }
            } catch (IOException e) {
                logger1.log(Level.WARNING, "IOException", e);
            }
            int comparison = compareTo(service);
            if (comparison == 0) {
                logger1.finer("handleQuery() Ignoring a identical service query");
                return false;
            } else if (!info.isProbing() || comparison <= 0) {
                jmDNSImpl = dns;
                return false;
            } else {
                String oldName = info.getQualifiedName().toLowerCase();
                info.setName(dns.incrementName(info.getName()));
                dns.getServices().remove(oldName);
                dns.getServices().put(info.getQualifiedName().toLowerCase(), info);
                Logger logger3 = logger1;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("handleQuery() Lost tie break: new unique name chosen:");
                stringBuilder3.append(info.getName());
                logger3.finer(stringBuilder3.toString());
                info.revertState();
                return true;
            }
        }

        boolean handleResponse(JmDNSImpl dns) {
            ServiceInfoImpl info = (ServiceInfoImpl) dns.getServices().get(getKey());
            if (info == null || (this._port == info.getPort() && this._server.equalsIgnoreCase(dns.getLocalHost().getName()))) {
                return false;
            }
            logger1.finer("handleResponse() Denial detected");
            if (info.isProbing()) {
                String oldName = info.getQualifiedName().toLowerCase();
                info.setName(dns.incrementName(info.getName()));
                dns.getServices().remove(oldName);
                dns.getServices().put(info.getQualifiedName().toLowerCase(), info);
                Logger logger = logger1;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("handleResponse() New unique name chose:");
                stringBuilder.append(info.getName());
                logger.finer(stringBuilder.toString());
            }
            info.revertState();
            return true;
        }

        DNSOutgoing addAnswer(JmDNSImpl dns, DNSIncoming in, InetAddress addr, int port, DNSOutgoing out) throws IOException {
            ServiceInfoImpl info = (ServiceInfoImpl) dns.getServices().get(getKey());
            if (info != null) {
                if ((r0._port == info.getPort()) != r0._server.equals(dns.getLocalHost().getName())) {
                    return dns.addAnswer(in, addr, port, out, new Service(info.getQualifiedName(), DNSRecordClass.CLASS_IN, true, 3600, info.getPriority(), info.getWeight(), info.getPort(), dns.getLocalHost().getName()));
                }
            }
            return out;
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            return new ServiceInfoImpl(getQualifiedNameMap(), this._port, this._weight, this._priority, persistent, this._server);
        }

        public ServiceEvent getServiceEvent(JmDNSImpl dns) {
            ServiceInfo info = getServiceInfo(null);
            ((ServiceInfoImpl) info).setDns(dns);
            return new ServiceEventImpl(dns, info.getType(), info.getName(), info);
        }

        protected void toString(StringBuilder aLog) {
            super.toString(aLog);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(" server: '");
            stringBuilder.append(this._server);
            stringBuilder.append(":");
            stringBuilder.append(this._port);
            stringBuilder.append(FormatHelper.QUOTE);
            aLog.append(stringBuilder.toString());
        }
    }

    public static class Text extends DNSRecord {
        private final byte[] _text;

        public Text(String name, DNSRecordClass recordClass, boolean unique, int ttl, byte[] text) {
            super(name, DNSRecordType.TYPE_TXT, recordClass, unique, ttl);
            byte[] bArr = (text == null || text.length <= 0) ? EMPTY_TXT : text;
            this._text = bArr;
        }

        byte[] getText() {
            return this._text;
        }

        void write(MessageOutputStream out) {
            out.writeBytes(this._text, 0, this._text.length);
        }

        boolean sameValue(DNSRecord other) {
            if (!(other instanceof Text)) {
                return false;
            }
            Text txt = (Text) other;
            if ((this._text == null && txt._text != null) || txt._text.length != this._text.length) {
                return false;
            }
            int i = this._text.length;
            while (true) {
                int i2 = i - 1;
                if (i <= 0) {
                    return true;
                }
                if (txt._text[i2] != this._text[i2]) {
                    return false;
                }
                i = i2;
            }
        }

        public boolean isSingleValued() {
            return true;
        }

        boolean handleQuery(JmDNSImpl dns, long expirationTime) {
            return false;
        }

        boolean handleResponse(JmDNSImpl dns) {
            return false;
        }

        DNSOutgoing addAnswer(JmDNSImpl dns, DNSIncoming in, InetAddress addr, int port, DNSOutgoing out) throws IOException {
            return out;
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            return new ServiceInfoImpl(getQualifiedNameMap(), 0, 0, 0, persistent, this._text);
        }

        public ServiceEvent getServiceEvent(JmDNSImpl dns) {
            ServiceInfo info = getServiceInfo(null);
            ((ServiceInfoImpl) info).setDns(dns);
            return new ServiceEventImpl(dns, info.getType(), info.getName(), info);
        }

        protected void toString(StringBuilder aLog) {
            String stringBuilder;
            super.toString(aLog);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(" text: '");
            if (this._text.length > 20) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(new String(this._text, 0, 17));
                stringBuilder3.append("...");
                stringBuilder = stringBuilder3.toString();
            } else {
                stringBuilder = new String(this._text);
            }
            stringBuilder2.append(stringBuilder);
            stringBuilder2.append(FormatHelper.QUOTE);
            aLog.append(stringBuilder2.toString());
        }
    }

    public static class IPv4Address extends Address {
        IPv4Address(String name, DNSRecordClass recordClass, boolean unique, int ttl, InetAddress addr) {
            super(name, DNSRecordType.TYPE_A, recordClass, unique, ttl, addr);
        }

        IPv4Address(String name, DNSRecordClass recordClass, boolean unique, int ttl, byte[] rawAddress) {
            super(name, DNSRecordType.TYPE_A, recordClass, unique, ttl, rawAddress);
        }

        void write(MessageOutputStream out) {
            if (this._addr != null) {
                byte[] buffer = this._addr.getAddress();
                if (!(this._addr instanceof Inet4Address)) {
                    byte[] tempbuffer = buffer;
                    buffer = new byte[4];
                    System.arraycopy(tempbuffer, 12, buffer, 0, 4);
                }
                out.writeBytes(buffer, 0, buffer.length);
            }
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            ServiceInfoImpl info = (ServiceInfoImpl) super.getServiceInfo(persistent);
            info.addAddress((Inet4Address) this._addr);
            return info;
        }
    }

    public static class IPv6Address extends Address {
        IPv6Address(String name, DNSRecordClass recordClass, boolean unique, int ttl, InetAddress addr) {
            super(name, DNSRecordType.TYPE_AAAA, recordClass, unique, ttl, addr);
        }

        IPv6Address(String name, DNSRecordClass recordClass, boolean unique, int ttl, byte[] rawAddress) {
            super(name, DNSRecordType.TYPE_AAAA, recordClass, unique, ttl, rawAddress);
        }

        void write(MessageOutputStream out) {
            if (this._addr != null) {
                byte[] buffer = this._addr.getAddress();
                if (this._addr instanceof Inet4Address) {
                    byte[] tempbuffer = buffer;
                    buffer = new byte[16];
                    for (int i = 0; i < 16; i++) {
                        if (i < 11) {
                            buffer[i] = tempbuffer[i - 12];
                        } else {
                            buffer[i] = (byte) 0;
                        }
                    }
                }
                out.writeBytes(buffer, 0, buffer.length);
            }
        }

        public ServiceInfo getServiceInfo(boolean persistent) {
            ServiceInfoImpl info = (ServiceInfoImpl) super.getServiceInfo(persistent);
            info.addAddress((Inet6Address) this._addr);
            return info;
        }
    }

    abstract DNSOutgoing addAnswer(JmDNSImpl jmDNSImpl, DNSIncoming dNSIncoming, InetAddress inetAddress, int i, DNSOutgoing dNSOutgoing) throws IOException;

    public abstract ServiceEvent getServiceEvent(JmDNSImpl jmDNSImpl);

    public abstract ServiceInfo getServiceInfo(boolean z);

    abstract boolean handleQuery(JmDNSImpl jmDNSImpl, long j);

    abstract boolean handleResponse(JmDNSImpl jmDNSImpl);

    public abstract boolean isSingleValued();

    abstract boolean sameValue(DNSRecord dNSRecord);

    abstract void write(MessageOutputStream messageOutputStream);

    DNSRecord(String name, DNSRecordType type, DNSRecordClass recordClass, boolean unique, int ttl) {
        super(name, type, recordClass, unique);
        this._ttl = ttl;
    }

    public boolean equals(Object other) {
        return (other instanceof DNSRecord) && super.equals(other) && sameValue((DNSRecord) other);
    }

    boolean sameType(DNSRecord other) {
        return getRecordType() == other.getRecordType();
    }

    boolean suppressedBy(DNSIncoming msg) {
        try {
            for (DNSRecord answer : msg.getAllAnswers()) {
                if (suppressedBy(answer)) {
                    return true;
                }
            }
            return false;
        } catch (ArrayIndexOutOfBoundsException e) {
            Logger logger = logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("suppressedBy() message ");
            stringBuilder.append(msg);
            stringBuilder.append(" exception ");
            logger.log(level, stringBuilder.toString(), e);
            return false;
        }
    }

    boolean suppressedBy(DNSRecord other) {
        if (!equals(other) || other._ttl <= this._ttl / 2) {
            return false;
        }
        return true;
    }

    long getExpirationTime(int percent) {
        return this._created + (((long) (this._ttl * percent)) * 10);
    }

    int getRemainingTTL(long now) {
        return (int) Math.max(0, (getExpirationTime(100) - now) / 1000);
    }

    public boolean isExpired(long now) {
        return getExpirationTime(100) <= now;
    }

    public boolean isStale(long now) {
        return getExpirationTime(50) <= now;
    }

    void resetTTL(DNSRecord other) {
        this._created = other._created;
        this._ttl = other._ttl;
    }

    void setWillExpireSoon(long now) {
        this._created = now;
        this._ttl = 1;
    }

    public ServiceInfo getServiceInfo() {
        return getServiceInfo(false);
    }

    public void setRecordSource(InetAddress source) {
        this._source = source;
    }

    public InetAddress getRecordSource() {
        return this._source;
    }

    protected void toString(StringBuilder aLog) {
        super.toString(aLog);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ttl: '");
        stringBuilder.append(getRemainingTTL(System.currentTimeMillis()));
        stringBuilder.append("/");
        stringBuilder.append(this._ttl);
        stringBuilder.append(FormatHelper.QUOTE);
        aLog.append(stringBuilder.toString());
    }

    public void setTTL(int ttl) {
        this._ttl = ttl;
    }

    public int getTTL() {
        return this._ttl;
    }
}
