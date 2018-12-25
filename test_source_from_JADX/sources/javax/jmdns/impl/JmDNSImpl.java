package javax.jmdns.impl;

import com.facebook.internal.ServerProtocol;
import io.fabric.sdk.android.services.events.EventsFilesManager;
import java.io.IOException;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.JmDNS$Delegate;
import javax.jmdns.ServiceEvent;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceInfo$Fields;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.DNSRecord.Pointer;
import javax.jmdns.impl.DNSRecord.Service;
import javax.jmdns.impl.DNSTaskStarter.Factory;
import javax.jmdns.impl.ListenerStatus.ServiceListenerStatus;
import javax.jmdns.impl.ListenerStatus.ServiceTypeListenerStatus;
import javax.jmdns.impl.constants.DNSConstants;
import javax.jmdns.impl.constants.DNSRecordClass;
import javax.jmdns.impl.constants.DNSRecordType;
import javax.jmdns.impl.constants.DNSState;
import javax.jmdns.impl.tasks.DNSTask;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;

public class JmDNSImpl extends JmDNS implements DNSStatefulObject, DNSTaskStarter {
    private static final Random _random = new Random();
    private static Logger logger = Logger.getLogger(JmDNSImpl.class.getName());
    private final DNSCache _cache;
    private volatile JmDNS$Delegate _delegate;
    private final ExecutorService _executor = Executors.newSingleThreadExecutor();
    private volatile InetAddress _group;
    private Thread _incomingListener;
    private final ReentrantLock _ioLock = new ReentrantLock();
    private long _lastThrottleIncrement;
    private final List<DNSListener> _listeners;
    private HostInfo _localHost;
    private final String _name;
    private DNSIncoming _plannedAnswer;
    private final Object _recoverLock = new Object();
    private final ConcurrentMap<String, JmDNSImpl$ServiceCollector> _serviceCollectors;
    private final ConcurrentMap<String, List<ServiceListenerStatus>> _serviceListeners;
    private final ConcurrentMap<String, JmDNSImpl$ServiceTypeEntry> _serviceTypes;
    private final ConcurrentMap<String, ServiceInfo> _services;
    protected Thread _shutdown;
    private volatile MulticastSocket _socket;
    private int _throttle;
    private final Set<ServiceTypeListenerStatus> _typeListeners;

    public static void main(String[] argv) {
        String version;
        try {
            Properties pomProperties = new Properties();
            pomProperties.load(JmDNSImpl.class.getResourceAsStream("/META-INF/maven/javax.jmdns/jmdns/pom.properties"));
            version = pomProperties.getProperty(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION);
        } catch (Exception e) {
            version = "RUNNING.IN.IDE.FULL";
        }
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JmDNS version \"");
        stringBuilder.append(version);
        stringBuilder.append("\"");
        printStream.println(stringBuilder.toString());
        System.out.println(FormatHelper.SPACE);
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Running on java version \"");
        stringBuilder.append(System.getProperty("java.version"));
        stringBuilder.append("\"");
        stringBuilder.append(" (build ");
        stringBuilder.append(System.getProperty("java.runtime.version"));
        stringBuilder.append(")");
        stringBuilder.append(" from ");
        stringBuilder.append(System.getProperty("java.vendor"));
        printStream.println(stringBuilder.toString());
        printStream = System.out;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Operating environment \"");
        stringBuilder.append(System.getProperty("os.name"));
        stringBuilder.append("\"");
        stringBuilder.append(" version ");
        stringBuilder.append(System.getProperty("os.version"));
        stringBuilder.append(" on ");
        stringBuilder.append(System.getProperty("os.arch"));
        printStream.println(stringBuilder.toString());
        System.out.println("For more information on JmDNS please visit https://sourceforge.net/projects/jmdns/");
    }

    public JmDNSImpl(InetAddress address, String name) throws IOException {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("JmDNS instance created");
        }
        this._cache = new DNSCache(100);
        this._listeners = Collections.synchronizedList(new ArrayList());
        this._serviceListeners = new ConcurrentHashMap();
        this._typeListeners = Collections.synchronizedSet(new HashSet());
        this._serviceCollectors = new ConcurrentHashMap();
        this._services = new ConcurrentHashMap(20);
        this._serviceTypes = new ConcurrentHashMap(20);
        this._localHost = HostInfo.newHostInfo(address, this, name);
        this._name = name != null ? name : this._localHost.getName();
        openMulticastSocket(getLocalHost());
        start(getServices().values());
        startReaper();
    }

    private void start(Collection<? extends ServiceInfo> serviceInfos) {
        if (this._incomingListener == null) {
            this._incomingListener = new SocketListener(this);
            this._incomingListener.start();
        }
        startProber();
        for (ServiceInfo info : serviceInfos) {
            try {
                registerService(new ServiceInfoImpl(info));
            } catch (Exception exception) {
                logger.log(Level.WARNING, "start() Registration exception ", exception);
            }
        }
    }

    private void openMulticastSocket(HostInfo hostInfo) throws IOException {
        if (this._group == null) {
            if (hostInfo.getInetAddress() instanceof Inet6Address) {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP_IPV6);
            } else {
                this._group = InetAddress.getByName(DNSConstants.MDNS_GROUP);
            }
        }
        if (this._socket != null) {
            closeMulticastSocket();
        }
        this._socket = new MulticastSocket(DNSConstants.MDNS_PORT);
        if (!(hostInfo == null || hostInfo.getInterface() == null)) {
            try {
                this._socket.setNetworkInterface(hostInfo.getInterface());
            } catch (SocketException e) {
                if (logger.isLoggable(Level.FINE)) {
                    Logger logger = logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("openMulticastSocket() Set network interface exception: ");
                    stringBuilder.append(e.getMessage());
                    logger.fine(stringBuilder.toString());
                }
            }
        }
        this._socket.setTimeToLive(255);
        this._socket.joinGroup(this._group);
    }

    private void closeMulticastSocket() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("closeMulticastSocket()");
        }
        if (this._socket != null) {
            try {
                this._socket.leaveGroup(this._group);
            } catch (SocketException e) {
            }
            try {
                this._socket.close();
                while (this._incomingListener != null && this._incomingListener.isAlive()) {
                    synchronized (this) {
                        try {
                            if (this._incomingListener != null && this._incomingListener.isAlive()) {
                                if (logger.isLoggable(Level.FINER)) {
                                    logger.finer("closeMulticastSocket(): waiting for jmDNS monitor");
                                }
                                wait(1000);
                            }
                        } catch (InterruptedException e2) {
                        }
                    }
                }
                this._incomingListener = null;
            } catch (Exception exception) {
                logger.log(Level.WARNING, "closeMulticastSocket() Close socket exception ", exception);
            }
            this._socket = null;
        }
    }

    public boolean advanceState(DNSTask task) {
        return this._localHost.advanceState(task);
    }

    public boolean revertState() {
        return this._localHost.revertState();
    }

    public boolean cancelState() {
        return this._localHost.cancelState();
    }

    public boolean closeState() {
        return this._localHost.closeState();
    }

    public boolean recoverState() {
        return this._localHost.recoverState();
    }

    public JmDNSImpl getDns() {
        return this;
    }

    public void associateWithTask(DNSTask task, DNSState state) {
        this._localHost.associateWithTask(task, state);
    }

    public void removeAssociationWithTask(DNSTask task) {
        this._localHost.removeAssociationWithTask(task);
    }

    public boolean isAssociatedWithTask(DNSTask task, DNSState state) {
        return this._localHost.isAssociatedWithTask(task, state);
    }

    public boolean isProbing() {
        return this._localHost.isProbing();
    }

    public boolean isAnnouncing() {
        return this._localHost.isAnnouncing();
    }

    public boolean isAnnounced() {
        return this._localHost.isAnnounced();
    }

    public boolean isCanceling() {
        return this._localHost.isCanceling();
    }

    public boolean isCanceled() {
        return this._localHost.isCanceled();
    }

    public boolean isClosing() {
        return this._localHost.isClosing();
    }

    public boolean isClosed() {
        return this._localHost.isClosed();
    }

    public boolean waitForAnnounced(long timeout) {
        return this._localHost.waitForAnnounced(timeout);
    }

    public boolean waitForCanceled(long timeout) {
        return this._localHost.waitForCanceled(timeout);
    }

    public DNSCache getCache() {
        return this._cache;
    }

    public String getName() {
        return this._name;
    }

    public String getHostName() {
        return this._localHost.getName();
    }

    public HostInfo getLocalHost() {
        return this._localHost;
    }

    public InetAddress getInterface() throws IOException {
        return this._socket.getInterface();
    }

    public ServiceInfo getServiceInfo(String type, String name) {
        return getServiceInfo(type, name, false, 6000);
    }

    public ServiceInfo getServiceInfo(String type, String name, long timeout) {
        return getServiceInfo(type, name, false, timeout);
    }

    public ServiceInfo getServiceInfo(String type, String name, boolean persistent) {
        return getServiceInfo(type, name, persistent, 6000);
    }

    public ServiceInfo getServiceInfo(String type, String name, boolean persistent, long timeout) {
        ServiceInfo info = resolveServiceInfo(type, name, "", persistent);
        waitForInfoData(info, timeout);
        return info.hasData() ? info : null;
    }

    ServiceInfoImpl resolveServiceInfo(String type, String name, String subtype, boolean persistent) {
        cleanCache();
        String loType = type.toLowerCase();
        registerServiceType(type);
        if (this._serviceCollectors.putIfAbsent(loType, new JmDNSImpl$ServiceCollector(type)) == null) {
            addServiceListener(loType, (ServiceListener) this._serviceCollectors.get(loType), true);
        }
        ServiceInfoImpl info = getServiceInfoFromCache(type, name, subtype, persistent);
        startServiceInfoResolver(info);
        return info;
    }

    ServiceInfoImpl getServiceInfoFromCache(String type, String name, String subtype, boolean persistent) {
        boolean z = persistent;
        byte[] bArr = (byte[]) null;
        ServiceInfoImpl info = new ServiceInfoImpl(type, name, subtype, 0, 0, 0, z, bArr);
        DNSEntry pointerEntry = getCache().getDNSEntry(new Pointer(type, DNSRecordClass.CLASS_ANY, false, 0, info.getQualifiedName()));
        if (pointerEntry instanceof DNSRecord) {
            ServiceInfoImpl cachedInfo = (ServiceInfoImpl) ((DNSRecord) pointerEntry).getServiceInfo(z);
            if (cachedInfo != null) {
                ServiceInfoImpl info2;
                DNSEntry addressEntry;
                ServiceInfo cachedAddressInfo;
                DNSEntry textEntry;
                ServiceInfo cachedTextInfo;
                Map<ServiceInfo$Fields, String> map = cachedInfo.getQualifiedNameMap();
                byte[] srvBytes = null;
                String server = "";
                DNSEntry serviceEntry = getCache().getDNSEntry(info.getQualifiedName(), DNSRecordType.TYPE_SRV, DNSRecordClass.CLASS_ANY);
                if (serviceEntry instanceof DNSRecord) {
                    ServiceInfo cachedServiceEntryInfo = ((DNSRecord) serviceEntry).getServiceInfo(z);
                    if (cachedServiceEntryInfo != null) {
                        info2 = info;
                        ServiceInfo info3 = cachedServiceEntryInfo;
                        cachedInfo = new ServiceInfoImpl((Map) map, cachedServiceEntryInfo.getPort(), cachedServiceEntryInfo.getWeight(), cachedServiceEntryInfo.getPriority(), z, bArr);
                        srvBytes = info3.getTextBytes();
                        server = info3.getServer();
                        addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_A, DNSRecordClass.CLASS_ANY);
                        if (addressEntry instanceof DNSRecord) {
                            cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(z);
                            if (cachedAddressInfo != null) {
                                for (Inet4Address address : cachedAddressInfo.getInet4Addresses()) {
                                    cachedInfo.addAddress(address);
                                }
                                cachedInfo._setText(cachedAddressInfo.getTextBytes());
                            }
                        }
                        addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_ANY);
                        if (addressEntry instanceof DNSRecord) {
                            cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(z);
                            if (cachedAddressInfo != null) {
                                for (Inet6Address address2 : cachedAddressInfo.getInet6Addresses()) {
                                    cachedInfo.addAddress(address2);
                                }
                                cachedInfo._setText(cachedAddressInfo.getTextBytes());
                            }
                        }
                        textEntry = getCache().getDNSEntry(cachedInfo.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_ANY);
                        if (textEntry instanceof DNSRecord) {
                            cachedTextInfo = ((DNSRecord) textEntry).getServiceInfo(z);
                            if (cachedTextInfo != null) {
                                cachedInfo._setText(cachedTextInfo.getTextBytes());
                            }
                        }
                        if (cachedInfo.getTextBytes().length == 0) {
                            cachedInfo._setText(srvBytes);
                        }
                        return cachedInfo.hasData() ? cachedInfo : info2;
                    }
                }
                info2 = info;
                addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_A, DNSRecordClass.CLASS_ANY);
                if (addressEntry instanceof DNSRecord) {
                    cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(z);
                    if (cachedAddressInfo != null) {
                        while (i$ < len$) {
                            cachedInfo.addAddress(address);
                        }
                        cachedInfo._setText(cachedAddressInfo.getTextBytes());
                    }
                }
                addressEntry = getCache().getDNSEntry(server, DNSRecordType.TYPE_AAAA, DNSRecordClass.CLASS_ANY);
                if (addressEntry instanceof DNSRecord) {
                    cachedAddressInfo = ((DNSRecord) addressEntry).getServiceInfo(z);
                    if (cachedAddressInfo != null) {
                        while (i$ < len$) {
                            cachedInfo.addAddress(address2);
                        }
                        cachedInfo._setText(cachedAddressInfo.getTextBytes());
                    }
                }
                textEntry = getCache().getDNSEntry(cachedInfo.getQualifiedName(), DNSRecordType.TYPE_TXT, DNSRecordClass.CLASS_ANY);
                if (textEntry instanceof DNSRecord) {
                    cachedTextInfo = ((DNSRecord) textEntry).getServiceInfo(z);
                    if (cachedTextInfo != null) {
                        cachedInfo._setText(cachedTextInfo.getTextBytes());
                    }
                }
                if (cachedInfo.getTextBytes().length == 0) {
                    cachedInfo._setText(srvBytes);
                }
                if (cachedInfo.hasData()) {
                }
            }
        }
        return info;
    }

    private void waitForInfoData(ServiceInfo info, long timeout) {
        synchronized (info) {
            long loops = timeout / 200;
            if (loops < 1) {
                loops = 1;
            }
            for (int i = 0; ((long) i) < loops; i++) {
                if (info.hasData()) {
                    break;
                }
                try {
                    info.wait(200);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public void requestServiceInfo(String type, String name) {
        requestServiceInfo(type, name, false, 6000);
    }

    public void requestServiceInfo(String type, String name, boolean persistent) {
        requestServiceInfo(type, name, persistent, 6000);
    }

    public void requestServiceInfo(String type, String name, long timeout) {
        requestServiceInfo(type, name, false, 6000);
    }

    public void requestServiceInfo(String type, String name, boolean persistent, long timeout) {
        waitForInfoData(resolveServiceInfo(type, name, "", persistent), timeout);
    }

    void handleServiceResolved(ServiceEvent event) {
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(event.getType().toLowerCase());
        if (list != null && !list.isEmpty() && event.getInfo() != null && event.getInfo().hasData()) {
            ServiceEvent localEvent = event;
            synchronized (list) {
                ArrayList<ServiceListenerStatus> listCopy = new ArrayList(list);
            }
            for (ServiceListenerStatus listener : listCopy) {
                this._executor.submit(new JmDNSImpl$1(this, listener, localEvent));
            }
        }
    }

    public void addServiceTypeListener(ServiceTypeListener listener) throws IOException {
        ServiceTypeListenerStatus status = new ServiceTypeListenerStatus(listener, false);
        this._typeListeners.add(status);
        for (String type : this._serviceTypes.keySet()) {
            status.serviceTypeAdded(new ServiceEventImpl(this, type, "", null));
        }
        startTypeResolver();
    }

    public void removeServiceTypeListener(ServiceTypeListener listener) {
        this._typeListeners.remove(new ServiceTypeListenerStatus(listener, false));
    }

    public void addServiceListener(String type, ServiceListener listener) {
        addServiceListener(type, listener, false);
    }

    private void addServiceListener(String type, ServiceListener listener, boolean synch) {
        ServiceListenerStatus status = new ServiceListenerStatus(listener, synch);
        String loType = type.toLowerCase();
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(loType);
        if (list == null) {
            if (this._serviceListeners.putIfAbsent(loType, new LinkedList()) == null && this._serviceCollectors.putIfAbsent(loType, new JmDNSImpl$ServiceCollector(type)) == null) {
                addServiceListener(loType, (ServiceListener) this._serviceCollectors.get(loType), true);
            }
            list = (List) this._serviceListeners.get(loType);
        }
        if (list != null) {
            synchronized (list) {
                if (!list.contains(listener)) {
                    list.add(status);
                }
            }
        }
        List<ServiceEvent> serviceEvents = new ArrayList();
        for (DNSEntry entry : getCache().allValues()) {
            DNSRecord record = (DNSRecord) entry;
            if (record.getRecordType() == DNSRecordType.TYPE_SRV && record.getKey().endsWith(loType)) {
                serviceEvents.add(new ServiceEventImpl(this, record.getType(), toUnqualifiedName(record.getType(), record.getName()), record.getServiceInfo()));
            }
        }
        for (ServiceEvent serviceEvent : serviceEvents) {
            status.serviceAdded(serviceEvent);
        }
        startServiceResolver(type);
    }

    public void removeServiceListener(String type, ServiceListener listener) {
        String loType = type.toLowerCase();
        List<ServiceListenerStatus> list = (List) this._serviceListeners.get(loType);
        if (list != null) {
            synchronized (list) {
                list.remove(new ServiceListenerStatus(listener, false));
                if (list.isEmpty()) {
                    this._serviceListeners.remove(loType, list);
                }
            }
        }
    }

    public void registerService(ServiceInfo infoAbstract) throws IOException {
        if (!isClosing()) {
            if (!isClosed()) {
                ServiceInfoImpl info = (ServiceInfoImpl) infoAbstract;
                if (info.getDns() != null) {
                    if (info.getDns() != this) {
                        throw new IllegalStateException("A service information can only be registered with a single instamce of JmDNS.");
                    } else if (this._services.get(info.getKey()) != null) {
                        throw new IllegalStateException("A service information can only be registered once.");
                    }
                }
                info.setDns(this);
                registerServiceType(info.getTypeWithSubtype());
                info.recoverState();
                info.setServer(this._localHost.getName());
                info.addAddress(this._localHost.getInet4Address());
                info.addAddress(this._localHost.getInet6Address());
                waitForAnnounced(6000);
                makeServiceNameUnique(info);
                while (this._services.putIfAbsent(info.getKey(), info) != null) {
                    makeServiceNameUnique(info);
                }
                startProber();
                info.waitForAnnounced(6000);
                if (logger.isLoggable(Level.FINE)) {
                    Logger logger = logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("registerService() JmDNS registered service as ");
                    stringBuilder.append(info);
                    logger.fine(stringBuilder.toString());
                    return;
                }
                return;
            }
        }
        throw new IllegalStateException("This DNS is closed.");
    }

    public void unregisterService(ServiceInfo infoAbstract) {
        ServiceInfoImpl info = (ServiceInfoImpl) this._services.get(infoAbstract.getKey());
        if (info != null) {
            info.cancelState();
            startCanceler();
            info.waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
            this._services.remove(info.getKey(), info);
            if (logger.isLoggable(Level.FINE)) {
                Logger logger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unregisterService() JmDNS unregistered service as ");
                stringBuilder.append(info);
                logger.fine(stringBuilder.toString());
                return;
            }
            return;
        }
        logger = logger;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Removing unregistered service info: ");
        stringBuilder.append(infoAbstract.getKey());
        logger.warning(stringBuilder.toString());
    }

    public void unregisterAllServices() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("unregisterAllServices()");
        }
        for (String name : this._services.keySet()) {
            ServiceInfoImpl info = (ServiceInfoImpl) this._services.get(name);
            if (info != null) {
                if (logger.isLoggable(Level.FINER)) {
                    Logger logger = logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cancelling service info: ");
                    stringBuilder.append(info);
                    logger.finer(stringBuilder.toString());
                }
                info.cancelState();
            }
        }
        startCanceler();
        for (String name2 : this._services.keySet()) {
            info = (ServiceInfoImpl) this._services.get(name2);
            if (info != null) {
                if (logger.isLoggable(Level.FINER)) {
                    logger = logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wait for service info cancel: ");
                    stringBuilder.append(info);
                    logger.finer(stringBuilder.toString());
                }
                info.waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
                this._services.remove(name2, info);
            }
        }
    }

    public boolean registerServiceType(String type) {
        String stringBuilder;
        int len$;
        Throwable th;
        Throwable th2;
        JmDNSImpl jmDNSImpl = this;
        boolean z = false;
        Map<ServiceInfo$Fields, String> map = ServiceInfoImpl.decodeQualifiedNameMapForType(type);
        String domain = (String) map.get(ServiceInfo$Fields.Domain);
        String protocol = (String) map.get(ServiceInfo$Fields.Protocol);
        String application = (String) map.get(ServiceInfo$Fields.Application);
        String subtype = (String) map.get(ServiceInfo$Fields.Subtype);
        String name = new StringBuilder();
        if (application.length() > 0) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder2.append(application);
            stringBuilder2.append(".");
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "";
        }
        name.append(stringBuilder);
        if (protocol.length() > 0) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
            stringBuilder2.append(protocol);
            stringBuilder2.append(".");
            stringBuilder = stringBuilder2.toString();
        } else {
            stringBuilder = "";
        }
        name.append(stringBuilder);
        name.append(domain);
        name.append(".");
        name = name.toString();
        stringBuilder = name.toLowerCase();
        if (logger.isLoggable(Level.FINE)) {
            String stringBuilder3;
            Logger logger = logger;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(getName());
            stringBuilder4.append(".registering service type: ");
            stringBuilder4.append(type);
            stringBuilder4.append(" as: ");
            stringBuilder4.append(name);
            if (subtype.length() > 0) {
                StringBuilder stringBuilder5 = new StringBuilder();
                stringBuilder5.append(" subtype: ");
                stringBuilder5.append(subtype);
                stringBuilder3 = stringBuilder5.toString();
            } else {
                stringBuilder3 = "";
            }
            stringBuilder4.append(stringBuilder3);
            logger.fine(stringBuilder4.toString());
        } else {
            String str = type;
        }
        String str2;
        if (jmDNSImpl._serviceTypes.containsKey(stringBuilder) || application.toLowerCase().equals("dns-sd") || domain.toLowerCase().endsWith("in-addr.arpa") || domain.toLowerCase().endsWith("ip6.arpa")) {
            str2 = domain;
        } else {
            Map<ServiceInfo$Fields, String> map2;
            z = jmDNSImpl._serviceTypes.putIfAbsent(stringBuilder, new JmDNSImpl$ServiceTypeEntry(name)) == null;
            if (z) {
                ServiceTypeListenerStatus[] list = (ServiceTypeListenerStatus[]) jmDNSImpl._typeListeners.toArray(new ServiceTypeListenerStatus[jmDNSImpl._typeListeners.size()]);
                ServiceEvent event = new ServiceEventImpl(jmDNSImpl, name, "", null);
                ServiceTypeListenerStatus[] arr$ = list;
                len$ = arr$.length;
                int i$ = 0;
                while (true) {
                    int i$2 = i$;
                    if (i$2 >= len$) {
                        break;
                    }
                    boolean typeAdded = z;
                    map2 = map;
                    str2 = domain;
                    jmDNSImpl._executor.submit(new JmDNSImpl$2(jmDNSImpl, arr$[i$2], event));
                    i$ = i$2 + 1;
                    z = typeAdded;
                    map = map2;
                    domain = str2;
                }
            }
            map2 = map;
            str2 = domain;
            z = z;
        }
        if (subtype.length() > 0) {
            JmDNSImpl$ServiceTypeEntry subtypes = (JmDNSImpl$ServiceTypeEntry) jmDNSImpl._serviceTypes.get(stringBuilder);
            if (!(subtypes == null || subtypes.contains(subtype))) {
                synchronized (subtypes) {
                    boolean typeAdded2;
                    try {
                        if (!subtypes.contains(subtype)) {
                            z = true;
                            try {
                                subtypes.add(subtype);
                                ServiceTypeListenerStatus[] list2 = (ServiceTypeListenerStatus[]) jmDNSImpl._typeListeners.toArray(new ServiceTypeListenerStatus[jmDNSImpl._typeListeners.size()]);
                                stringBuilder4 = new StringBuilder();
                                stringBuilder4.append(EventsFilesManager.ROLL_OVER_FILE_NAME_SEPARATOR);
                                stringBuilder4.append(subtype);
                                stringBuilder4.append("._sub.");
                                stringBuilder4.append(name);
                                ServiceEvent event2 = new ServiceEventImpl(jmDNSImpl, stringBuilder4.toString(), "", null);
                                ServiceTypeListenerStatus[] arr$2 = list2;
                                len$ = arr$2.length;
                                int i$3 = 0;
                                while (true) {
                                    int i$4 = i$3;
                                    if (i$4 >= len$) {
                                        break;
                                    }
                                    typeAdded2 = z;
                                    try {
                                        ServiceTypeListenerStatus[] list3 = list2;
                                        jmDNSImpl._executor.submit(new JmDNSImpl$3(jmDNSImpl, arr$2[i$4], event2));
                                        i$3 = i$4 + 1;
                                        z = typeAdded2;
                                        list2 = list3;
                                    } catch (Throwable th3) {
                                        th = th3;
                                    }
                                }
                                typeAdded2 = z;
                            } catch (Throwable th4) {
                                typeAdded2 = true;
                                th2 = th4;
                                throw th2;
                            }
                        }
                    } catch (Throwable th5) {
                        th4 = th5;
                        typeAdded2 = z;
                        th2 = th4;
                        throw th2;
                    }
                }
            }
        }
        return z;
    }

    private boolean makeServiceNameUnique(ServiceInfoImpl info) {
        String originalQualifiedName = info.getKey();
        long now = System.currentTimeMillis();
        boolean collision;
        do {
            ServiceInfo selfService;
            collision = false;
            for (DNSEntry dnsEntry : getCache().getDNSEntryList(info.getKey())) {
                if (DNSRecordType.TYPE_SRV.equals(dnsEntry.getRecordType()) && !dnsEntry.isExpired(now)) {
                    Service s = (Service) dnsEntry;
                    if (s.getPort() != info.getPort() || !s.getServer().equals(this._localHost.getName())) {
                        if (logger.isLoggable(Level.FINER)) {
                            Logger logger = logger;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("makeServiceNameUnique() JmDNS.makeServiceNameUnique srv collision:");
                            stringBuilder.append(dnsEntry);
                            stringBuilder.append(" s.server=");
                            stringBuilder.append(s.getServer());
                            stringBuilder.append(FormatHelper.SPACE);
                            stringBuilder.append(this._localHost.getName());
                            stringBuilder.append(" equals:");
                            stringBuilder.append(s.getServer().equals(this._localHost.getName()));
                            logger.finer(stringBuilder.toString());
                        }
                        info.setName(incrementName(info.getName()));
                        collision = true;
                        selfService = (ServiceInfo) this._services.get(info.getKey());
                        if (!(selfService == null || selfService == info)) {
                            info.setName(incrementName(info.getName()));
                            collision = true;
                            continue;
                        }
                    }
                }
            }
            selfService = (ServiceInfo) this._services.get(info.getKey());
            info.setName(incrementName(info.getName()));
            collision = true;
            continue;
        } while (collision);
        return originalQualifiedName.equals(info.getKey()) ^ 1;
    }

    String incrementName(String name) {
        String aName = name;
        try {
            int l = aName.lastIndexOf(40);
            int r = aName.lastIndexOf(41);
            StringBuilder stringBuilder;
            if (l < 0 || l >= r) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(aName);
                stringBuilder.append(" (2)");
                aName = stringBuilder.toString();
            } else {
                stringBuilder = new StringBuilder();
                stringBuilder.append(aName.substring(0, l));
                stringBuilder.append(Constants.OPENING_BRACE);
                stringBuilder.append(Integer.parseInt(aName.substring(l + 1, r)) + 1);
                stringBuilder.append(")");
                aName = stringBuilder.toString();
            }
            return aName;
        } catch (NumberFormatException e) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(aName);
            stringBuilder2.append(" (2)");
            return stringBuilder2.toString();
        }
    }

    public void addListener(DNSListener listener, DNSQuestion question) {
        long now = System.currentTimeMillis();
        this._listeners.add(listener);
        if (question != null) {
            for (DNSEntry dnsEntry : getCache().getDNSEntryList(question.getName().toLowerCase())) {
                if (question.answeredBy(dnsEntry) && !dnsEntry.isExpired(now)) {
                    listener.updateRecord(getCache(), now, dnsEntry);
                }
            }
        }
    }

    public void removeListener(DNSListener listener) {
        this._listeners.remove(listener);
    }

    public void renewServiceCollector(DNSRecord record) {
        ServiceInfo info = record.getServiceInfo();
        if (this._serviceCollectors.containsKey(info.getType().toLowerCase())) {
            startServiceResolver(info.getType());
        }
    }

    public void updateRecord(long now, DNSRecord rec, JmDNSImpl$Operation operation) {
        synchronized (this._listeners) {
            ArrayList<DNSListener> listenerList = new ArrayList(this._listeners);
        }
        for (DNSListener serviceListenerList : listenerList) {
            serviceListenerList.updateRecord(getCache(), now, rec);
        }
        if (DNSRecordType.TYPE_PTR.equals(rec.getRecordType())) {
            List<ServiceListenerStatus> serviceListenerList2;
            List<DNSListener> listenerList2 = rec.getServiceEvent(this);
            if (listenerList2.getInfo() == null || !listenerList2.getInfo().hasData()) {
                ServiceInfo info = getServiceInfoFromCache(listenerList2.getType(), listenerList2.getName(), "", false);
                if (info.hasData()) {
                    listenerList2 = new ServiceEventImpl(this, listenerList2.getType(), listenerList2.getName(), info);
                }
            }
            List<ServiceListenerStatus> list = (List) this._serviceListeners.get(listenerList2.getType().toLowerCase());
            if (list != null) {
                synchronized (list) {
                    serviceListenerList2 = new ArrayList(list);
                }
            } else {
                serviceListenerList2 = Collections.emptyList();
            }
            if (logger.isLoggable(Level.FINEST)) {
                Logger logger = logger;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getName());
                stringBuilder.append(".updating record for event: ");
                stringBuilder.append(listenerList2);
                stringBuilder.append(" list ");
                stringBuilder.append(serviceListenerList2);
                stringBuilder.append(" operation: ");
                stringBuilder.append(operation);
                logger.finest(stringBuilder.toString());
            }
            if (!serviceListenerList2.isEmpty()) {
                List<DNSListener> localEvent = listenerList2;
                switch (JmDNSImpl$7.$SwitchMap$javax$jmdns$impl$JmDNSImpl$Operation[operation.ordinal()]) {
                    case 1:
                        for (ServiceListenerStatus listener : serviceListenerList2) {
                            if (listener.isSynchronous()) {
                                listener.serviceAdded(localEvent);
                            } else {
                                this._executor.submit(new JmDNSImpl$4(this, listener, localEvent));
                            }
                        }
                        return;
                    case 2:
                        for (ServiceListenerStatus listener2 : serviceListenerList2) {
                            if (listener2.isSynchronous()) {
                                listener2.serviceRemoved(localEvent);
                            } else {
                                this._executor.submit(new JmDNSImpl$5(this, listener2, localEvent));
                            }
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    void handleRecord(DNSRecord record, long now) {
        DNSRecord newRecord = record;
        JmDNSImpl$Operation cacheOperation = JmDNSImpl$Operation.Noop;
        boolean expired = newRecord.isExpired(now);
        if (logger.isLoggable(Level.FINE)) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getName());
            stringBuilder.append(" handle response: ");
            stringBuilder.append(newRecord);
            logger.fine(stringBuilder.toString());
        }
        if (!(newRecord.isServicesDiscoveryMetaQuery() || newRecord.isDomainDiscoveryQuery())) {
            boolean unique = newRecord.isUnique();
            DNSEntry cachedRecord = (DNSRecord) getCache().getDNSEntry(newRecord);
            if (logger.isLoggable(Level.FINE)) {
                Logger logger2 = logger;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(getName());
                stringBuilder2.append(" handle response cached record: ");
                stringBuilder2.append(cachedRecord);
                logger2.fine(stringBuilder2.toString());
            }
            if (unique) {
                for (DNSEntry entry : getCache().getDNSEntryList(newRecord.getKey())) {
                    if (newRecord.getRecordType().equals(entry.getRecordType()) && newRecord.getRecordClass().equals(entry.getRecordClass()) && entry != cachedRecord) {
                        ((DNSRecord) entry).setWillExpireSoon(now);
                    }
                }
            }
            if (cachedRecord != null) {
                if (!expired) {
                    if (newRecord.sameValue(cachedRecord)) {
                        if (newRecord.sameSubtype(cachedRecord) || newRecord.getSubtype().length() <= 0) {
                            cachedRecord.resetTTL(newRecord);
                            newRecord = cachedRecord;
                        }
                    }
                    if (newRecord.isSingleValued()) {
                        cacheOperation = JmDNSImpl$Operation.Update;
                        getCache().replaceDNSEntry(newRecord, cachedRecord);
                    } else {
                        cacheOperation = JmDNSImpl$Operation.Add;
                        getCache().addDNSEntry(newRecord);
                    }
                } else if (newRecord.getTTL() == 0) {
                    cacheOperation = JmDNSImpl$Operation.Noop;
                    cachedRecord.setWillExpireSoon(now);
                } else {
                    cacheOperation = JmDNSImpl$Operation.Remove;
                    getCache().removeDNSEntry(cachedRecord);
                }
            } else if (!expired) {
                cacheOperation = JmDNSImpl$Operation.Add;
                getCache().addDNSEntry(newRecord);
            }
        }
        if (newRecord.getRecordType() == DNSRecordType.TYPE_PTR) {
            if (newRecord.isServicesDiscoveryMetaQuery()) {
                if (!expired) {
                    unique = registerServiceType(((Pointer) newRecord).getAlias());
                }
                return;
            } else if ((false | registerServiceType(newRecord.getName())) && cacheOperation == JmDNSImpl$Operation.Noop) {
                cacheOperation = JmDNSImpl$Operation.RegisterServiceType;
            }
        }
        if (cacheOperation != JmDNSImpl$Operation.Noop) {
            updateRecord(now, newRecord, cacheOperation);
        }
    }

    void handleResponse(DNSIncoming msg) throws IOException {
        long now = System.currentTimeMillis();
        boolean hostConflictDetected = false;
        boolean serviceConflictDetected = false;
        for (DNSRecord newRecord : msg.getAllAnswers()) {
            handleRecord(newRecord, now);
            if (!DNSRecordType.TYPE_A.equals(newRecord.getRecordType())) {
                if (!DNSRecordType.TYPE_AAAA.equals(newRecord.getRecordType())) {
                    serviceConflictDetected |= newRecord.handleResponse(this);
                }
            }
            hostConflictDetected |= newRecord.handleResponse(this);
        }
        if (hostConflictDetected || serviceConflictDetected) {
            startProber();
        }
    }

    void handleQuery(DNSIncoming in, InetAddress addr, int port) throws IOException {
        if (logger.isLoggable(Level.FINE)) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getName());
            stringBuilder.append(".handle query: ");
            stringBuilder.append(in);
            logger.fine(stringBuilder.toString());
        }
        boolean conflictDetected = false;
        long expirationTime = System.currentTimeMillis() + 120;
        for (DNSRecord answer : in.getAllAnswers()) {
            conflictDetected |= answer.handleQuery(this, expirationTime);
        }
        ioLock();
        try {
            if (this._plannedAnswer != null) {
                this._plannedAnswer.append(in);
            } else {
                DNSIncoming plannedAnswer = in.clone();
                if (in.isTruncated()) {
                    this._plannedAnswer = plannedAnswer;
                }
                startResponder(plannedAnswer, port);
            }
            ioUnlock();
            long now = System.currentTimeMillis();
            for (DNSRecord answer2 : in.getAnswers()) {
                handleRecord(answer2, now);
            }
            if (conflictDetected) {
                startProber();
            }
        } catch (Throwable th) {
            ioUnlock();
        }
    }

    public void respondToQuery(DNSIncoming in) {
        ioLock();
        try {
            if (this._plannedAnswer == in) {
                this._plannedAnswer = null;
            }
            ioUnlock();
        } catch (Throwable th) {
            ioUnlock();
        }
    }

    public DNSOutgoing addAnswer(DNSIncoming in, InetAddress addr, int port, DNSOutgoing out, DNSRecord rec) throws IOException {
        DNSOutgoing newOut = out;
        if (newOut == null) {
            newOut = new DNSOutgoing(33792, false, in.getSenderUDPPayload());
        }
        try {
            newOut.addAnswer(in, rec);
            return newOut;
        } catch (IOException e) {
            newOut.setFlags(newOut.getFlags() | 512);
            newOut.setId(in.getId());
            send(newOut);
            newOut = new DNSOutgoing(33792, false, in.getSenderUDPPayload());
            newOut.addAnswer(in, rec);
            return newOut;
        }
    }

    public void send(DNSOutgoing out) throws IOException {
        Logger logger;
        if (!out.isEmpty()) {
            byte[] message = out.data();
            DatagramPacket packet = new DatagramPacket(message, message.length, this._group, DNSConstants.MDNS_PORT);
            if (logger.isLoggable(Level.FINEST)) {
                try {
                    DNSIncoming msg = new DNSIncoming(packet);
                    if (logger.isLoggable(Level.FINEST)) {
                        logger = logger;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("send(");
                        stringBuilder.append(getName());
                        stringBuilder.append(") JmDNS out:");
                        stringBuilder.append(msg.print(true));
                        logger.finest(stringBuilder.toString());
                    }
                } catch (IOException e) {
                    logger = logger;
                    String cls = getClass().toString();
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("send(");
                    stringBuilder2.append(getName());
                    stringBuilder2.append(") - JmDNS can not parse what it sends!!!");
                    logger.throwing(cls, stringBuilder2.toString(), e);
                }
            }
            MulticastSocket ms = this._socket;
            if (ms != null && !ms.isClosed()) {
                ms.send(packet);
            }
        }
    }

    public void purgeTimer() {
        Factory.getInstance().getStarter(getDns()).purgeTimer();
    }

    public void purgeStateTimer() {
        Factory.getInstance().getStarter(getDns()).purgeStateTimer();
    }

    public void cancelTimer() {
        Factory.getInstance().getStarter(getDns()).cancelTimer();
    }

    public void cancelStateTimer() {
        Factory.getInstance().getStarter(getDns()).cancelStateTimer();
    }

    public void startProber() {
        Factory.getInstance().getStarter(getDns()).startProber();
    }

    public void startAnnouncer() {
        Factory.getInstance().getStarter(getDns()).startAnnouncer();
    }

    public void startRenewer() {
        Factory.getInstance().getStarter(getDns()).startRenewer();
    }

    public void startCanceler() {
        Factory.getInstance().getStarter(getDns()).startCanceler();
    }

    public void startReaper() {
        Factory.getInstance().getStarter(getDns()).startReaper();
    }

    public void startServiceInfoResolver(ServiceInfoImpl info) {
        Factory.getInstance().getStarter(getDns()).startServiceInfoResolver(info);
    }

    public void startTypeResolver() {
        Factory.getInstance().getStarter(getDns()).startTypeResolver();
    }

    public void startServiceResolver(String type) {
        Factory.getInstance().getStarter(getDns()).startServiceResolver(type);
    }

    public void startResponder(DNSIncoming in, int port) {
        Factory.getInstance().getStarter(getDns()).startResponder(in, port);
    }

    public void recover() {
        Logger logger = logger;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getName());
        stringBuilder.append("recover()");
        logger.finer(stringBuilder.toString());
        if (!(isClosing() || isClosed() || isCanceling())) {
            if (!isCanceled()) {
                synchronized (this._recoverLock) {
                    if (cancelState()) {
                        Logger logger2 = logger;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(getName());
                        stringBuilder2.append("recover() thread ");
                        stringBuilder2.append(Thread.currentThread().getName());
                        logger2.finer(stringBuilder2.toString());
                        stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(getName());
                        stringBuilder2.append(".recover()");
                        new JmDNSImpl$6(this, stringBuilder2.toString()).start();
                    }
                }
            }
        }
    }

    void __recover() {
        if (logger.isLoggable(Level.FINER)) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(getName());
            stringBuilder.append("recover() Cleanning up");
            logger.finer(stringBuilder.toString());
        }
        logger.warning("RECOVERING");
        purgeTimer();
        Collection<ServiceInfo> oldServiceInfos = new ArrayList(getServices().values());
        unregisterAllServices();
        disposeServiceCollectors();
        waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
        purgeStateTimer();
        closeMulticastSocket();
        getCache().clear();
        if (logger.isLoggable(Level.FINER)) {
            Logger logger2 = logger;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(getName());
            stringBuilder2.append("recover() All is clean");
            logger2.finer(stringBuilder2.toString());
        }
        if (isCanceled()) {
            for (ServiceInfo info : oldServiceInfos) {
                ((ServiceInfoImpl) info).recoverState();
            }
            recoverState();
            try {
                openMulticastSocket(getLocalHost());
                start(oldServiceInfos);
            } catch (Exception exception) {
                Logger logger3 = logger;
                Level level = Level.WARNING;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(getName());
                stringBuilder3.append("recover() Start services exception ");
                logger3.log(level, stringBuilder3.toString(), exception);
            }
            logger2 = logger;
            Level level2 = Level.WARNING;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(getName());
            stringBuilder4.append("recover() We are back!");
            logger2.log(level2, stringBuilder4.toString());
            return;
        }
        logger2 = logger;
        level2 = Level.WARNING;
        stringBuilder4 = new StringBuilder();
        stringBuilder4.append(getName());
        stringBuilder4.append("recover() Could not recover we are Down!");
        logger2.log(level2, stringBuilder4.toString());
        if (getDelegate() != null) {
            getDelegate().cannotRecoverFromIOError(getDns(), oldServiceInfos);
        }
    }

    public void cleanCache() {
        long now = System.currentTimeMillis();
        for (DNSEntry entry : getCache().allValues()) {
            try {
                DNSRecord record = (DNSRecord) entry;
                if (record.isExpired(now)) {
                    updateRecord(now, record, JmDNSImpl$Operation.Remove);
                    getCache().removeDNSEntry(record);
                } else if (record.isStale(now)) {
                    renewServiceCollector(record);
                }
            } catch (Exception exception) {
                Logger logger = logger;
                Level level = Level.SEVERE;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(getName());
                stringBuilder.append(".Error while reaping records: ");
                stringBuilder.append(entry);
                logger.log(level, stringBuilder.toString(), exception);
                logger.severe(toString());
            }
        }
    }

    public void close() {
        if (!isClosing()) {
            Logger logger;
            StringBuilder stringBuilder;
            if (logger.isLoggable(Level.FINER)) {
                logger = logger;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cancelling JmDNS: ");
                stringBuilder.append(this);
                logger.finer(stringBuilder.toString());
            }
            if (closeState()) {
                logger.finer("Canceling the timer");
                cancelTimer();
                unregisterAllServices();
                disposeServiceCollectors();
                if (logger.isLoggable(Level.FINER)) {
                    logger = logger;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Wait for JmDNS cancel: ");
                    stringBuilder.append(this);
                    logger.finer(stringBuilder.toString());
                }
                waitForCanceled(DNSConstants.CLOSE_TIMEOUT);
                logger.finer("Canceling the state timer");
                cancelStateTimer();
                this._executor.shutdown();
                closeMulticastSocket();
                if (this._shutdown != null) {
                    Runtime.getRuntime().removeShutdownHook(this._shutdown);
                }
                if (logger.isLoggable(Level.FINER)) {
                    logger.finer("JmDNS closed.");
                }
            }
            advanceState(null);
        }
    }

    @Deprecated
    public void printServices() {
        System.err.println(toString());
    }

    public String toString() {
        StringBuilder aLog = new StringBuilder(2048);
        aLog.append("\t---- Local Host -----");
        aLog.append("\n\t");
        aLog.append(this._localHost);
        aLog.append("\n\t---- Services -----");
        for (String key : this._services.keySet()) {
            aLog.append("\n\t\tService: ");
            aLog.append(key);
            aLog.append(": ");
            aLog.append(this._services.get(key));
        }
        aLog.append("\n");
        aLog.append("\t---- Types ----");
        for (String key2 : this._serviceTypes.keySet()) {
            JmDNSImpl$ServiceTypeEntry subtypes = (JmDNSImpl$ServiceTypeEntry) this._serviceTypes.get(key2);
            aLog.append("\n\t\tType: ");
            aLog.append(subtypes.getType());
            aLog.append(": ");
            aLog.append(subtypes.isEmpty() ? "no subtypes" : subtypes);
        }
        aLog.append("\n");
        aLog.append(this._cache.toString());
        aLog.append("\n");
        aLog.append("\t---- Service Collectors ----");
        for (String key22 : this._serviceCollectors.keySet()) {
            aLog.append("\n\t\tService Collector: ");
            aLog.append(key22);
            aLog.append(": ");
            aLog.append(this._serviceCollectors.get(key22));
        }
        aLog.append("\n");
        aLog.append("\t---- Service Listeners ----");
        for (String key222 : this._serviceListeners.keySet()) {
            aLog.append("\n\t\tService Listener: ");
            aLog.append(key222);
            aLog.append(": ");
            aLog.append(this._serviceListeners.get(key222));
        }
        return aLog.toString();
    }

    public ServiceInfo[] list(String type) {
        return list(type, 6000);
    }

    public ServiceInfo[] list(String type, long timeout) {
        cleanCache();
        String loType = type.toLowerCase();
        if (!isCanceling()) {
            if (!isCanceled()) {
                JmDNSImpl$ServiceCollector collector = (JmDNSImpl$ServiceCollector) this._serviceCollectors.get(loType);
                if (collector == null) {
                    collector = (JmDNSImpl$ServiceCollector) this._serviceCollectors.get(loType);
                    if (this._serviceCollectors.putIfAbsent(loType, new JmDNSImpl$ServiceCollector(type)) == null) {
                        addServiceListener(type, collector, true);
                    }
                }
                if (logger.isLoggable(Level.FINER)) {
                    Logger logger = logger;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(getName());
                    stringBuilder.append(".collector: ");
                    stringBuilder.append(collector);
                    logger.finer(stringBuilder.toString());
                }
                return collector != null ? collector.list(timeout) : new ServiceInfo[0];
            }
        }
        return new ServiceInfo[0];
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type) {
        return listBySubtype(type, 6000);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type, long timeout) {
        Map<String, List<ServiceInfo>> map = new HashMap(5);
        for (ServiceInfo info : list(type, timeout)) {
            String subtype = info.getSubtype().toLowerCase();
            if (!map.containsKey(subtype)) {
                map.put(subtype, new ArrayList(10));
            }
            ((List) map.get(subtype)).add(info);
        }
        Map<String, ServiceInfo[]> result = new HashMap(map.size());
        for (String subtype2 : map.keySet()) {
            List<ServiceInfo> infoForSubType = (List) map.get(subtype2);
            result.put(subtype2, infoForSubType.toArray(new ServiceInfo[infoForSubType.size()]));
        }
        return result;
    }

    private void disposeServiceCollectors() {
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("disposeServiceCollectors()");
        }
        for (String type : this._serviceCollectors.keySet()) {
            JmDNSImpl$ServiceCollector collector = (JmDNSImpl$ServiceCollector) this._serviceCollectors.get(type);
            if (collector != null) {
                removeServiceListener(type, collector);
                this._serviceCollectors.remove(type, collector);
            }
        }
    }

    static String toUnqualifiedName(String type, String qualifiedName) {
        String loType = type.toLowerCase();
        String loQualifiedName = qualifiedName.toLowerCase();
        if (!loQualifiedName.endsWith(loType) || loQualifiedName.equals(loType)) {
            return qualifiedName;
        }
        return qualifiedName.substring(0, (qualifiedName.length() - type.length()) - 1);
    }

    public Map<String, ServiceInfo> getServices() {
        return this._services;
    }

    public void setLastThrottleIncrement(long lastThrottleIncrement) {
        this._lastThrottleIncrement = lastThrottleIncrement;
    }

    public long getLastThrottleIncrement() {
        return this._lastThrottleIncrement;
    }

    public void setThrottle(int throttle) {
        this._throttle = throttle;
    }

    public int getThrottle() {
        return this._throttle;
    }

    public static Random getRandom() {
        return _random;
    }

    public void ioLock() {
        this._ioLock.lock();
    }

    public void ioUnlock() {
        this._ioLock.unlock();
    }

    public void setPlannedAnswer(DNSIncoming plannedAnswer) {
        this._plannedAnswer = plannedAnswer;
    }

    public DNSIncoming getPlannedAnswer() {
        return this._plannedAnswer;
    }

    void setLocalHost(HostInfo localHost) {
        this._localHost = localHost;
    }

    public Map<String, JmDNSImpl$ServiceTypeEntry> getServiceTypes() {
        return this._serviceTypes;
    }

    public MulticastSocket getSocket() {
        return this._socket;
    }

    public InetAddress getGroup() {
        return this._group;
    }

    public JmDNS$Delegate getDelegate() {
        return this._delegate;
    }

    public JmDNS$Delegate setDelegate(JmDNS$Delegate delegate) {
        JmDNS$Delegate previous = this._delegate;
        this._delegate = delegate;
        return previous;
    }
}
