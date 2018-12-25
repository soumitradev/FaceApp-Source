package javax.jmdns.impl;

import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceInfo$Fields;
import javax.jmdns.impl.DNSRecord.Address;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.DNSRecord.Text;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import name.antonsmirnov.firmata.FormatHelper;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import org.billthefarmer.mididriver.GeneralMidiConstants;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.physics.PhysicsCollision;
import org.catrobat.catroid.transfers.MediaDownloadService;

public class ServiceInfoImpl extends ServiceInfo implements DNSListener, DNSStatefulObject {
    private static Logger logger = Logger.getLogger(ServiceInfoImpl.class.getName());
    private String _application;
    private ServiceInfoImpl$Delegate _delegate;
    private String _domain;
    private final Set<Inet4Address> _ipv4Addresses;
    private final Set<Inet6Address> _ipv6Addresses;
    private transient String _key;
    private String _name;
    private boolean _needTextAnnouncing;
    private boolean _persistent;
    private int _port;
    private int _priority;
    private Map<String, byte[]> _props;
    private String _protocol;
    private String _server;
    private final ServiceInfoImpl$ServiceInfoState _state;
    private String _subtype;
    private byte[] _text;
    private int _weight;

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, String text) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, (byte[]) null);
        this._server = text;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(text.length());
            writeUTF(out, text);
            this._text = out.toByteArray();
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected exception: ");
            stringBuilder.append(e);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, Map<String, ?> props) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, textFromProperties(props));
    }

    public ServiceInfoImpl(String type, String name, String subtype, int port, int weight, int priority, boolean persistent, byte[] text) {
        this(decodeQualifiedNameMap(type, name, subtype), port, weight, priority, persistent, text);
    }

    public ServiceInfoImpl(Map<ServiceInfo$Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, Map<String, ?> props) {
        this((Map) qualifiedNameMap, port, weight, priority, persistent, textFromProperties(props));
    }

    ServiceInfoImpl(Map<ServiceInfo$Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, String text) {
        this((Map) qualifiedNameMap, port, weight, priority, persistent, (byte[]) null);
        this._server = text;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream(text.length());
            writeUTF(out, text);
            this._text = out.toByteArray();
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("unexpected exception: ");
            stringBuilder.append(e);
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    ServiceInfoImpl(Map<ServiceInfo$Fields, String> qualifiedNameMap, int port, int weight, int priority, boolean persistent, byte[] text) {
        Map<ServiceInfo$Fields, String> map = checkQualifiedNameMap(qualifiedNameMap);
        this._domain = (String) map.get(ServiceInfo$Fields.Domain);
        this._protocol = (String) map.get(ServiceInfo$Fields.Protocol);
        this._application = (String) map.get(ServiceInfo$Fields.Application);
        this._name = (String) map.get(ServiceInfo$Fields.Instance);
        this._subtype = (String) map.get(ServiceInfo$Fields.Subtype);
        this._port = port;
        this._weight = weight;
        this._priority = priority;
        this._text = text;
        setNeedTextAnnouncing(false);
        this._state = new ServiceInfoImpl$ServiceInfoState(this);
        this._persistent = persistent;
        this._ipv4Addresses = Collections.synchronizedSet(new LinkedHashSet());
        this._ipv6Addresses = Collections.synchronizedSet(new LinkedHashSet());
    }

    ServiceInfoImpl(ServiceInfo info) {
        this._ipv4Addresses = Collections.synchronizedSet(new LinkedHashSet());
        this._ipv6Addresses = Collections.synchronizedSet(new LinkedHashSet());
        if (info != null) {
            this._domain = info.getDomain();
            this._protocol = info.getProtocol();
            this._application = info.getApplication();
            this._name = info.getName();
            this._subtype = info.getSubtype();
            this._port = info.getPort();
            this._weight = info.getWeight();
            this._priority = info.getPriority();
            this._text = info.getTextBytes();
            this._persistent = info.isPersistent();
            for (Inet6Address address : info.getInet6Addresses()) {
                this._ipv6Addresses.add(address);
            }
            for (Inet4Address address2 : info.getInet4Addresses()) {
                this._ipv4Addresses.add(address2);
            }
        }
        this._state = new ServiceInfoImpl$ServiceInfoState(this);
    }

    public static Map<ServiceInfo$Fields, String> decodeQualifiedNameMap(String type, String name, String subtype) {
        Map<ServiceInfo$Fields, String> qualifiedNameMap = decodeQualifiedNameMapForType(type);
        qualifiedNameMap.put(ServiceInfo$Fields.Instance, name);
        qualifiedNameMap.put(ServiceInfo$Fields.Subtype, subtype);
        return checkQualifiedNameMap(qualifiedNameMap);
    }

    public static Map<ServiceInfo$Fields, String> decodeQualifiedNameMapForType(String type) {
        int index;
        Map<ServiceInfo$Fields, String> qualifiedNameMap;
        String casePreservedType = type;
        String aType = type.toLowerCase();
        String application = aType;
        String protocol = "";
        String subtype = "";
        String name = "";
        String domain = "";
        if (!aType.contains("in-addr.arpa")) {
            if (!aType.contains("ip6.arpa")) {
                if (aType.contains(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) || !aType.contains(".")) {
                    if (!aType.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR) || aType.startsWith("_services")) {
                        index = aType.indexOf(46);
                        if (index > 0) {
                            name = casePreservedType.substring(0, index);
                            if (index + 1 < aType.length()) {
                                aType = aType.substring(index + 1);
                                casePreservedType = casePreservedType.substring(index + 1);
                            }
                        }
                    }
                    index = aType.lastIndexOf("._");
                    if (index > 0) {
                        int start = index + 2;
                        protocol = casePreservedType.substring(start, aType.indexOf(46, start));
                    }
                    if (protocol.length() > 0) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                        stringBuilder.append(protocol.toLowerCase());
                        stringBuilder.append(".");
                        index = aType.indexOf(stringBuilder.toString());
                        domain = casePreservedType.substring((protocol.length() + index) + 2, aType.length() - aType.endsWith("."));
                        application = casePreservedType.substring(0, index - 1);
                    }
                    index = application.toLowerCase().indexOf("._sub");
                    if (index > 0) {
                        int start2 = index + 5;
                        subtype = removeSeparators(application.substring(0, index));
                        application = application.substring(start2);
                    }
                    qualifiedNameMap = new HashMap(5);
                    qualifiedNameMap.put(ServiceInfo$Fields.Domain, removeSeparators(domain));
                    qualifiedNameMap.put(ServiceInfo$Fields.Protocol, protocol);
                    qualifiedNameMap.put(ServiceInfo$Fields.Application, removeSeparators(application));
                    qualifiedNameMap.put(ServiceInfo$Fields.Instance, name);
                    qualifiedNameMap.put(ServiceInfo$Fields.Subtype, subtype);
                    return qualifiedNameMap;
                }
                index = aType.indexOf(46);
                name = removeSeparators(casePreservedType.substring(0, index));
                domain = removeSeparators(casePreservedType.substring(index));
                application = "";
                qualifiedNameMap = new HashMap(5);
                qualifiedNameMap.put(ServiceInfo$Fields.Domain, removeSeparators(domain));
                qualifiedNameMap.put(ServiceInfo$Fields.Protocol, protocol);
                qualifiedNameMap.put(ServiceInfo$Fields.Application, removeSeparators(application));
                qualifiedNameMap.put(ServiceInfo$Fields.Instance, name);
                qualifiedNameMap.put(ServiceInfo$Fields.Subtype, subtype);
                return qualifiedNameMap;
            }
        }
        index = aType.indexOf(aType.contains("in-addr.arpa") ? "in-addr.arpa" : "ip6.arpa");
        name = removeSeparators(casePreservedType.substring(0, index));
        domain = casePreservedType.substring(index);
        application = "";
        qualifiedNameMap = new HashMap(5);
        qualifiedNameMap.put(ServiceInfo$Fields.Domain, removeSeparators(domain));
        qualifiedNameMap.put(ServiceInfo$Fields.Protocol, protocol);
        qualifiedNameMap.put(ServiceInfo$Fields.Application, removeSeparators(application));
        qualifiedNameMap.put(ServiceInfo$Fields.Instance, name);
        qualifiedNameMap.put(ServiceInfo$Fields.Subtype, subtype);
        return qualifiedNameMap;
    }

    protected static Map<ServiceInfo$Fields, String> checkQualifiedNameMap(Map<ServiceInfo$Fields, String> qualifiedNameMap) {
        Map<ServiceInfo$Fields, String> checkedQualifiedNameMap = new HashMap(5);
        String domain = qualifiedNameMap.containsKey(ServiceInfo$Fields.Domain) ? (String) qualifiedNameMap.get(ServiceInfo$Fields.Domain) : "local";
        if (domain == null || domain.length() == 0) {
            domain = "local";
        }
        checkedQualifiedNameMap.put(ServiceInfo$Fields.Domain, removeSeparators(domain));
        String protocol = qualifiedNameMap.containsKey(ServiceInfo$Fields.Protocol) ? (String) qualifiedNameMap.get(ServiceInfo$Fields.Protocol) : "tcp";
        if (protocol == null || protocol.length() == 0) {
            protocol = "tcp";
        }
        checkedQualifiedNameMap.put(ServiceInfo$Fields.Protocol, removeSeparators(protocol));
        String application = qualifiedNameMap.containsKey(ServiceInfo$Fields.Application) ? (String) qualifiedNameMap.get(ServiceInfo$Fields.Application) : "";
        if (application == null || application.length() == 0) {
            application = "";
        }
        checkedQualifiedNameMap.put(ServiceInfo$Fields.Application, removeSeparators(application));
        String instance = qualifiedNameMap.containsKey(ServiceInfo$Fields.Instance) ? (String) qualifiedNameMap.get(ServiceInfo$Fields.Instance) : "";
        if (instance == null || instance.length() == 0) {
            instance = "";
        }
        checkedQualifiedNameMap.put(ServiceInfo$Fields.Instance, removeSeparators(instance));
        String subtype = qualifiedNameMap.containsKey(ServiceInfo$Fields.Subtype) ? (String) qualifiedNameMap.get(ServiceInfo$Fields.Subtype) : "";
        if (subtype == null || subtype.length() == 0) {
            subtype = "";
        }
        checkedQualifiedNameMap.put(ServiceInfo$Fields.Subtype, removeSeparators(subtype));
        return checkedQualifiedNameMap;
    }

    private static String removeSeparators(String name) {
        if (name == null) {
            return "";
        }
        String newName = name.trim();
        if (newName.startsWith(".")) {
            newName = newName.substring(1);
        }
        if (newName.startsWith(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR)) {
            newName = newName.substring(1);
        }
        if (newName.endsWith(".")) {
            newName = newName.substring(0, newName.length() - 1);
        }
        return newName;
    }

    public String getType() {
        String stringBuilder;
        String domain = getDomain();
        String protocol = getProtocol();
        String application = getApplication();
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
        return stringBuilder2.toString();
    }

    public String getTypeWithSubtype() {
        String stringBuilder;
        String subtype = getSubtype();
        StringBuilder stringBuilder2 = new StringBuilder();
        if (subtype.length() > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder3.append(subtype.toLowerCase());
            stringBuilder3.append("._sub.");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(getType());
        return stringBuilder2.toString();
    }

    public String getName() {
        return this._name != null ? this._name : "";
    }

    public String getKey() {
        if (this._key == null) {
            this._key = getQualifiedName().toLowerCase();
        }
        return this._key;
    }

    void setName(String name) {
        this._name = name;
        this._key = null;
    }

    public String getQualifiedName() {
        String stringBuilder;
        String domain = getDomain();
        String protocol = getProtocol();
        String application = getApplication();
        String instance = getName();
        StringBuilder stringBuilder2 = new StringBuilder();
        if (instance.length() > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(instance);
            stringBuilder3.append(".");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        if (application.length() > 0) {
            stringBuilder3 = new StringBuilder();
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
        return stringBuilder2.toString();
    }

    public String getServer() {
        return this._server != null ? this._server : "";
    }

    void setServer(String server) {
        this._server = server;
    }

    @Deprecated
    public String getHostAddress() {
        String[] names = getHostAddresses();
        return names.length > 0 ? names[0] : "";
    }

    public String[] getHostAddresses() {
        int i;
        Inet4Address[] ip4Aaddresses = getInet4Addresses();
        Inet6Address[] ip6Aaddresses = getInet6Addresses();
        String[] names = new String[(ip4Aaddresses.length + ip6Aaddresses.length)];
        for (i = 0; i < ip4Aaddresses.length; i++) {
            names[i] = ip4Aaddresses[i].getHostAddress();
        }
        for (int i2 = 0; i2 < ip6Aaddresses.length; i2++) {
            i = ip4Aaddresses.length + i2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[");
            stringBuilder.append(ip6Aaddresses[i2].getHostAddress());
            stringBuilder.append("]");
            names[i] = stringBuilder.toString();
        }
        return names;
    }

    void addAddress(Inet4Address addr) {
        this._ipv4Addresses.add(addr);
    }

    void addAddress(Inet6Address addr) {
        this._ipv6Addresses.add(addr);
    }

    @Deprecated
    public InetAddress getAddress() {
        return getInetAddress();
    }

    @Deprecated
    public InetAddress getInetAddress() {
        InetAddress[] addresses = getInetAddresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    @Deprecated
    public Inet4Address getInet4Address() {
        Inet4Address[] addresses = getInet4Addresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    @Deprecated
    public Inet6Address getInet6Address() {
        Inet6Address[] addresses = getInet6Addresses();
        return addresses.length > 0 ? addresses[0] : null;
    }

    public InetAddress[] getInetAddresses() {
        List<InetAddress> aList = new ArrayList(this._ipv4Addresses.size() + this._ipv6Addresses.size());
        aList.addAll(this._ipv4Addresses);
        aList.addAll(this._ipv6Addresses);
        return (InetAddress[]) aList.toArray(new InetAddress[aList.size()]);
    }

    public Inet4Address[] getInet4Addresses() {
        return (Inet4Address[]) this._ipv4Addresses.toArray(new Inet4Address[this._ipv4Addresses.size()]);
    }

    public Inet6Address[] getInet6Addresses() {
        return (Inet6Address[]) this._ipv6Addresses.toArray(new Inet6Address[this._ipv6Addresses.size()]);
    }

    public int getPort() {
        return this._port;
    }

    public int getPriority() {
        return this._priority;
    }

    public int getWeight() {
        return this._weight;
    }

    public byte[] getTextBytes() {
        return (this._text == null || this._text.length <= 0) ? DNSRecord.EMPTY_TXT : this._text;
    }

    @Deprecated
    public String getTextString() {
        Map<String, byte[]> properties = getProperties();
        Iterator i$ = properties.keySet().iterator();
        if (!i$.hasNext()) {
            return "";
        }
        String key = (String) i$.next();
        byte[] value = (byte[]) properties.get(key);
        if (value == null || value.length <= 0) {
            return key;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(key);
        stringBuilder.append("=");
        stringBuilder.append(new String(value));
        return stringBuilder.toString();
    }

    @Deprecated
    public String getURL() {
        return getURL("http");
    }

    public String[] getURLs() {
        return getURLs("http");
    }

    @Deprecated
    public String getURL(String protocol) {
        String[] urls = getURLs(protocol);
        if (urls.length > 0) {
            return urls[0];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(protocol);
        stringBuilder.append("://null:");
        stringBuilder.append(getPort());
        return stringBuilder.toString();
    }

    public String[] getURLs(String protocol) {
        InetAddress[] addresses = getInetAddresses();
        String[] urls = new String[addresses.length];
        for (int i = 0; i < addresses.length; i++) {
            String url = new StringBuilder();
            url.append(protocol);
            url.append("://");
            url.append(addresses[i].getHostAddress());
            url.append(":");
            url.append(getPort());
            url = url.toString();
            String path = getPropertyString(MediaDownloadService.MEDIA_FILE_PATH);
            if (path != null) {
                if (path.indexOf("://") >= 0) {
                    url = path;
                } else {
                    String str;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(url);
                    if (path.startsWith("/")) {
                        str = path;
                    } else {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("/");
                        stringBuilder2.append(path);
                        str = stringBuilder2.toString();
                    }
                    stringBuilder.append(str);
                    url = stringBuilder.toString();
                }
            }
            urls[i] = url;
        }
        return urls;
    }

    public synchronized byte[] getPropertyBytes(String name) {
        return (byte[]) getProperties().get(name);
    }

    public synchronized String getPropertyString(String name) {
        byte[] data = (byte[]) getProperties().get(name);
        if (data == null) {
            return null;
        }
        if (data == NO_VALUE) {
            return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE;
        }
        return readUTF(data, 0, data.length);
    }

    public Enumeration<String> getPropertyNames() {
        Map<String, byte[]> properties = getProperties();
        return new Vector(properties != null ? properties.keySet() : Collections.emptySet()).elements();
    }

    public String getApplication() {
        return this._application != null ? this._application : "";
    }

    public String getDomain() {
        return this._domain != null ? this._domain : "local";
    }

    public String getProtocol() {
        return this._protocol != null ? this._protocol : "tcp";
    }

    public String getSubtype() {
        return this._subtype != null ? this._subtype : "";
    }

    public Map<ServiceInfo$Fields, String> getQualifiedNameMap() {
        Map<ServiceInfo$Fields, String> map = new HashMap(5);
        map.put(ServiceInfo$Fields.Domain, getDomain());
        map.put(ServiceInfo$Fields.Protocol, getProtocol());
        map.put(ServiceInfo$Fields.Application, getApplication());
        map.put(ServiceInfo$Fields.Instance, getName());
        map.put(ServiceInfo$Fields.Subtype, getSubtype());
        return map;
    }

    static void writeUTF(OutputStream out, String str) throws IOException {
        int len = str.length();
        for (int i = 0; i < len; i++) {
            int c = str.charAt(i);
            if (c >= 1 && c <= MetaEvent.SEQUENCER_SPECIFIC) {
                out.write(c);
            } else if (c > 2047) {
                out.write(((c >> 12) & 15) | AnalogMessageWriter.COMMAND);
                out.write(((c >> 6) & 63) | 128);
                out.write(((c >> 0) & 63) | 128);
            } else {
                out.write(((c >> 6) & 31) | ReportAnalogPinMessageWriter.COMMAND);
                out.write(((c >> 0) & 63) | 128);
            }
        }
    }

    String readUTF(byte[] data, int off, int len) {
        int offset = off;
        StringBuffer buf = new StringBuffer();
        int end = offset + len;
        while (offset < end) {
            int offset2;
            int offset3 = offset + 1;
            offset = data[offset] & 255;
            int i = offset >> 4;
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    offset2 = offset3;
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                            if (offset3 < len) {
                                offset2 = offset3 + 1;
                                offset = ((offset & 31) << 6) | (data[offset3] & 63);
                                break;
                            }
                            return null;
                        case 14:
                            if (offset3 + 2 < len) {
                                offset2 = offset3 + 1;
                                offset3 = ((data[offset3] & 63) << 6) | ((offset & 15) << 12);
                                offset = offset3 | (data[offset2] & 63);
                                offset2++;
                                break;
                            }
                            return null;
                        default:
                            if (offset3 + 1 < len) {
                                offset2 = offset3 + 1;
                                offset = ((offset & 63) << 4) | (data[offset3] & 15);
                                break;
                            }
                            return null;
                    }
            }
            buf.append((char) offset);
            offset = offset2;
        }
        return buf.toString();
    }

    synchronized Map<String, byte[]> getProperties() {
        if (this._props == null && getTextBytes() != null) {
            Hashtable<String, byte[]> properties = new Hashtable();
            int off = 0;
            while (off < getTextBytes().length) {
                try {
                    int off2 = off + 1;
                    off = getTextBytes()[off] & 255;
                    if (off != 0) {
                        if (off2 + off <= getTextBytes().length) {
                            int i = 0;
                            while (i < off && getTextBytes()[off2 + i] != GeneralMidiConstants.BRASS_SECTION) {
                                i++;
                            }
                            String name = readUTF(getTextBytes(), off2, i);
                            if (name == null) {
                                properties.clear();
                                break;
                            }
                            if (i == off) {
                                properties.put(name, NO_VALUE);
                            } else {
                                i++;
                                byte[] value = new byte[(off - i)];
                                System.arraycopy(getTextBytes(), off2 + i, value, 0, off - i);
                                properties.put(name, value);
                                off2 += off;
                            }
                            off = off2;
                        }
                    }
                    properties.clear();
                } catch (Exception exception) {
                    logger.log(Level.WARNING, "Malformed TXT Field ", exception);
                }
            }
            this._props = properties;
        }
        return this._props != null ? this._props : Collections.emptyMap();
    }

    public void updateRecord(DNSCache dnsCache, long now, DNSEntry rec) {
        if ((rec instanceof DNSRecord) && !rec.isExpired(now)) {
            boolean serviceUpdated = false;
            switch (ServiceInfoImpl$1.$SwitchMap$javax$jmdns$impl$constants$DNSRecordType[rec.getRecordType().ordinal()]) {
                case 1:
                    if (rec.getName().equalsIgnoreCase(getServer())) {
                        this._ipv4Addresses.add((Inet4Address) ((Address) rec).getAddress());
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case 2:
                    if (rec.getName().equalsIgnoreCase(getServer())) {
                        this._ipv6Addresses.add((Inet6Address) ((Address) rec).getAddress());
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case 3:
                    if (rec.getName().equalsIgnoreCase(getQualifiedName())) {
                        boolean serverChanged;
                        Service srv = (Service) rec;
                        if (this._server != null) {
                            if (this._server.equalsIgnoreCase(srv.getServer())) {
                                serverChanged = false;
                                this._server = srv.getServer();
                                this._port = srv.getPort();
                                this._weight = srv.getWeight();
                                this._priority = srv.getPriority();
                                if (serverChanged) {
                                    serviceUpdated = true;
                                } else {
                                    this._ipv4Addresses.clear();
                                    this._ipv6Addresses.clear();
                                    for (DNSEntry entry : dnsCache.getDNSEntryList(this._server, DNSRecordType.TYPE_A, DNSRecordClass.CLASS_IN)) {
                                        updateRecord(dnsCache, now, entry);
                                    }
                                    for (DNSEntry entry2 : dnsCache.getDNSEntryList(this._server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_IN)) {
                                        updateRecord(dnsCache, now, entry2);
                                    }
                                }
                                break;
                            }
                        }
                        serverChanged = true;
                        this._server = srv.getServer();
                        this._port = srv.getPort();
                        this._weight = srv.getWeight();
                        this._priority = srv.getPriority();
                        if (serverChanged) {
                            serviceUpdated = true;
                        } else {
                            this._ipv4Addresses.clear();
                            this._ipv6Addresses.clear();
                            while (i$.hasNext()) {
                                updateRecord(dnsCache, now, entry2);
                            }
                            while (i$.hasNext()) {
                                updateRecord(dnsCache, now, entry2);
                            }
                        }
                    }
                    break;
                case 4:
                    if (rec.getName().equalsIgnoreCase(getQualifiedName())) {
                        this._text = ((Text) rec).getText();
                        serviceUpdated = true;
                        break;
                    }
                    break;
                case 5:
                    if (getSubtype().length() == 0 && rec.getSubtype().length() != 0) {
                        this._subtype = rec.getSubtype();
                        serviceUpdated = true;
                        break;
                    }
                default:
                    break;
            }
            if (serviceUpdated && hasData()) {
                JmDNSImpl dns = getDns();
                if (dns != null) {
                    ServiceEvent event = ((DNSRecord) rec).getServiceEvent(dns);
                    dns.handleServiceResolved(new ServiceEventImpl(dns, event.getType(), event.getName(), this));
                }
            }
            synchronized (this) {
                notifyAll();
            }
        }
    }

    public synchronized boolean hasData() {
        boolean z;
        z = getServer() != null && hasInetAddress() && getTextBytes() != null && getTextBytes().length > 0;
        return z;
    }

    private final boolean hasInetAddress() {
        if (this._ipv4Addresses.size() <= 0) {
            if (this._ipv6Addresses.size() <= 0) {
                return false;
            }
        }
        return true;
    }

    public boolean advanceState(DNSTask task) {
        return this._state.advanceState(task);
    }

    public boolean revertState() {
        return this._state.revertState();
    }

    public boolean cancelState() {
        return this._state.cancelState();
    }

    public boolean closeState() {
        return this._state.closeState();
    }

    public boolean recoverState() {
        return this._state.recoverState();
    }

    public void removeAssociationWithTask(DNSTask task) {
        this._state.removeAssociationWithTask(task);
    }

    public void associateWithTask(DNSTask task, DNSState state) {
        this._state.associateWithTask(task, state);
    }

    public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
        return this._state.isAssociatedWithTask(task, state);
    }

    public boolean isProbing() {
        return this._state.isProbing();
    }

    public boolean isAnnouncing() {
        return this._state.isAnnouncing();
    }

    public boolean isAnnounced() {
        return this._state.isAnnounced();
    }

    public boolean isCanceling() {
        return this._state.isCanceling();
    }

    public boolean isCanceled() {
        return this._state.isCanceled();
    }

    public boolean isClosing() {
        return this._state.isClosing();
    }

    public boolean isClosed() {
        return this._state.isClosed();
    }

    public boolean waitForAnnounced(long timeout) {
        return this._state.waitForAnnounced(timeout);
    }

    public boolean waitForCanceled(long timeout) {
        return this._state.waitForCanceled(timeout);
    }

    public int hashCode() {
        return getQualifiedName().hashCode();
    }

    public boolean equals(Object obj) {
        return (obj instanceof ServiceInfoImpl) && getQualifiedName().equals(((ServiceInfoImpl) obj).getQualifiedName());
    }

    public String getNiceTextString() {
        StringBuffer buf = new StringBuffer();
        int len = getTextBytes().length;
        for (int i = 0; i < len; i++) {
            if (i >= 200) {
                buf.append("...");
                break;
            }
            int ch = getTextBytes()[i] & 255;
            if (ch >= 32) {
                if (ch <= MetaEvent.SEQUENCER_SPECIFIC) {
                    buf.append((char) ch);
                }
            }
            buf.append("\\0");
            buf.append(Integer.toString(ch, 8));
        }
        return buf.toString();
    }

    public ServiceInfoImpl clone() {
        ServiceInfoImpl serviceInfo = new ServiceInfoImpl(getQualifiedNameMap(), this._port, this._weight, this._priority, this._persistent, this._text);
        for (Inet6Address address : getInet6Addresses()) {
            serviceInfo._ipv6Addresses.add(address);
        }
        for (Inet4Address address2 : getInet4Addresses()) {
            serviceInfo._ipv4Addresses.add(address2);
        }
        return serviceInfo;
    }

    public String toString() {
        String stringBuilder;
        StringBuilder buf = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("[");
        stringBuilder2.append(getClass().getSimpleName());
        stringBuilder2.append("@");
        stringBuilder2.append(System.identityHashCode(this));
        stringBuilder2.append(FormatHelper.SPACE);
        buf.append(stringBuilder2.toString());
        buf.append("name: '");
        stringBuilder2 = new StringBuilder();
        if (getName().length() > 0) {
            StringBuilder stringBuilder3 = new StringBuilder();
            stringBuilder3.append(getName());
            stringBuilder3.append(".");
            stringBuilder = stringBuilder3.toString();
        } else {
            stringBuilder = "";
        }
        stringBuilder2.append(stringBuilder);
        stringBuilder2.append(getTypeWithSubtype());
        buf.append(stringBuilder2.toString());
        buf.append("' address: '");
        InetAddress[] addresses = getInetAddresses();
        if (addresses.length > 0) {
            for (InetAddress address : addresses) {
                buf.append(address);
                buf.append(':');
                buf.append(getPort());
                buf.append(' ');
            }
        } else {
            buf.append("(null):");
            buf.append(getPort());
        }
        buf.append("' status: '");
        buf.append(this._state.toString());
        buf.append(isPersistent() ? "' is persistent," : "',");
        buf.append(" has ");
        buf.append(hasData() ? "" : "NO ");
        buf.append(ShareConstants.WEB_DIALOG_PARAM_DATA);
        if (getTextBytes().length > 0) {
            Map<String, byte[]> properties = getProperties();
            if (properties.isEmpty()) {
                buf.append(" empty");
            } else {
                buf.append("\n");
                for (String key : properties.keySet()) {
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(PhysicsCollision.COLLISION_MESSAGE_ESCAPE_CHAR);
                    stringBuilder4.append(key);
                    stringBuilder4.append(": ");
                    stringBuilder4.append(new String((byte[]) properties.get(key)));
                    stringBuilder4.append("\n");
                    buf.append(stringBuilder4.toString());
                }
            }
        }
        buf.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        return buf.toString();
    }

    public Collection<DNSRecord> answers(boolean unique, int ttl, HostInfo localHost) {
        ServiceInfoImpl serviceInfoImpl = this;
        List<DNSRecord> list = new ArrayList();
        if (getSubtype().length() > 0) {
            list.add(new Pointer(getTypeWithSubtype(), DNSRecordClass.CLASS_IN, false, ttl, getQualifiedName()));
        }
        list.add(new Pointer(getType(), DNSRecordClass.CLASS_IN, false, ttl, getQualifiedName()));
        String qualifiedName = getQualifiedName();
        DNSRecordClass dNSRecordClass = DNSRecordClass.CLASS_IN;
        int i = serviceInfoImpl._priority;
        int i2 = serviceInfoImpl._weight;
        list.add(new Service(qualifiedName, dNSRecordClass, unique, ttl, i, i2, serviceInfoImpl._port, localHost.getName()));
        list.add(new Text(getQualifiedName(), DNSRecordClass.CLASS_IN, unique, ttl, getTextBytes()));
        return list;
    }

    public void setText(byte[] text) throws IllegalStateException {
        synchronized (this) {
            this._text = text;
            this._props = null;
            setNeedTextAnnouncing(true);
        }
    }

    public void setText(Map<String, ?> props) throws IllegalStateException {
        setText(textFromProperties(props));
    }

    void _setText(byte[] text) {
        this._text = text;
        this._props = null;
    }

    private static byte[] textFromProperties(Map<String, ?> props) {
        byte[] text = null;
        if (props != null) {
            try {
                ByteArrayOutputStream out = new ByteArrayOutputStream(256);
                for (String key : props.keySet()) {
                    byte[] bval;
                    Object val = props.get(key);
                    ByteArrayOutputStream out2 = new ByteArrayOutputStream(100);
                    writeUTF(out2, key);
                    if (val != null) {
                        if (val instanceof String) {
                            out2.write(61);
                            writeUTF(out2, (String) val);
                        } else if (val instanceof byte[]) {
                            bval = (byte[]) val;
                            if (bval.length > 0) {
                                out2.write(61);
                                out2.write(bval, 0, bval.length);
                            } else {
                                val = null;
                            }
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("invalid property value: ");
                            stringBuilder.append(val);
                            throw new IllegalArgumentException(stringBuilder.toString());
                        }
                    }
                    bval = out2.toByteArray();
                    if (bval.length > 255) {
                        String str;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Cannot have individual values larger that 255 chars. Offending value: ");
                        stringBuilder2.append(key);
                        if (val != null) {
                            str = "";
                        } else {
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("=");
                            stringBuilder3.append(val);
                            str = stringBuilder3.toString();
                        }
                        stringBuilder2.append(str);
                        throw new IOException(stringBuilder2.toString());
                    }
                    out.write((byte) bval.length);
                    out.write(bval, 0, bval.length);
                }
                text = out.toByteArray();
            } catch (IOException e) {
                StringBuilder stringBuilder4 = new StringBuilder();
                stringBuilder4.append("unexpected exception: ");
                stringBuilder4.append(e);
                throw new RuntimeException(stringBuilder4.toString());
            }
        }
        return (text == null || text.length <= 0) ? DNSRecord.EMPTY_TXT : text;
    }

    public void setDns(JmDNSImpl dns) {
        this._state.setDns(dns);
    }

    public JmDNSImpl getDns() {
        return this._state.getDns();
    }

    public boolean isPersistent() {
        return this._persistent;
    }

    public void setNeedTextAnnouncing(boolean needTextAnnouncing) {
        this._needTextAnnouncing = needTextAnnouncing;
        if (this._needTextAnnouncing) {
            this._state.setTask(null);
        }
    }

    public boolean needTextAnnouncing() {
        return this._needTextAnnouncing;
    }

    ServiceInfoImpl$Delegate getDelegate() {
        return this._delegate;
    }

    void setDelegate(ServiceInfoImpl$Delegate delegate) {
        this._delegate = delegate;
    }
}
