package javax.jmdns.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.JmDNS;
import javax.jmdns.JmmDNS;
import javax.jmdns.NetworkTopologyDiscovery;
import javax.jmdns.NetworkTopologyDiscovery.Factory;
import javax.jmdns.NetworkTopologyEvent;
import javax.jmdns.NetworkTopologyListener;
import javax.jmdns.ServiceInfo;
import javax.jmdns.ServiceListener;
import javax.jmdns.ServiceTypeListener;
import javax.jmdns.impl.constants.DNSConstants;

public class JmmDNSImpl implements JmmDNS, NetworkTopologyListener, ServiceInfoImpl$Delegate {
    private static Logger logger = Logger.getLogger(JmmDNSImpl.class.getName());
    private final ExecutorService _ListenerExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService _jmDNSExecutor = Executors.newCachedThreadPool();
    private final ConcurrentMap<InetAddress, JmDNS> _knownMDNS = new ConcurrentHashMap();
    private final Set<NetworkTopologyListener> _networkListeners = Collections.synchronizedSet(new HashSet());
    private final ConcurrentMap<String, ServiceInfo> _services = new ConcurrentHashMap(20);
    private final Timer _timer = new Timer("Multihommed mDNS.Timer", true);

    static class NetworkChecker extends TimerTask {
        private static Logger logger1 = Logger.getLogger(NetworkChecker.class.getName());
        private Set<InetAddress> _knownAddresses = Collections.synchronizedSet(new HashSet());
        private final NetworkTopologyListener _mmDNS;
        private final NetworkTopologyDiscovery _topology;

        public NetworkChecker(NetworkTopologyListener mmDNS, NetworkTopologyDiscovery topology) {
            this._mmDNS = mmDNS;
            this._topology = topology;
        }

        public void start(Timer timer) {
            timer.schedule(this, 0, 10000);
        }

        public void run() {
            try {
                InetAddress[] curentAddresses = this._topology.getInetAddresses();
                Set<InetAddress> current = new HashSet(curentAddresses.length);
                for (InetAddress address : curentAddresses) {
                    current.add(address);
                    if (!this._knownAddresses.contains(address)) {
                        this._mmDNS.inetAddressAdded(new NetworkTopologyEventImpl(this._mmDNS, address));
                    }
                }
                for (InetAddress address2 : this._knownAddresses) {
                    if (!current.contains(address2)) {
                        this._mmDNS.inetAddressRemoved(new NetworkTopologyEventImpl(this._mmDNS, address2));
                    }
                }
                this._knownAddresses = current;
            } catch (Exception e) {
                Logger logger = logger1;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected unhandled exception: ");
                stringBuilder.append(e);
                logger.warning(stringBuilder.toString());
            }
        }
    }

    public JmmDNSImpl() {
        new NetworkChecker(this, Factory.getInstance()).start(this._timer);
    }

    public void close() throws IOException {
        if (logger.isLoggable(Level.FINER)) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cancelling JmmDNS: ");
            stringBuilder.append(this);
            logger.finer(stringBuilder.toString());
        }
        this._timer.cancel();
        this._ListenerExecutor.shutdown();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (final JmDNS mDNS : this._knownMDNS.values()) {
            executor.submit(new Runnable() {
                public void run() {
                    try {
                        mDNS.close();
                    } catch (IOException e) {
                    }
                }
            });
        }
        executor.shutdown();
        try {
            executor.awaitTermination(DNSConstants.CLOSE_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.WARNING, "Exception ", exception);
        }
        this._knownMDNS.clear();
    }

    public String[] getNames() {
        Set<String> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getName());
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public String[] getHostNames() {
        Set<String> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getHostName());
        }
        return (String[]) result.toArray(new String[result.size()]);
    }

    public InetAddress[] getInterfaces() throws IOException {
        Set<InetAddress> result = new HashSet();
        for (JmDNS mDNS : this._knownMDNS.values()) {
            result.add(mDNS.getInterface());
        }
        return (InetAddress[]) result.toArray(new InetAddress[result.size()]);
    }

    public ServiceInfo[] getServiceInfos(String type, String name) {
        return getServiceInfos(type, name, false, 6000);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, long timeout) {
        return getServiceInfos(type, name, false, timeout);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, boolean persistent) {
        return getServiceInfos(type, name, persistent, 6000);
    }

    public ServiceInfo[] getServiceInfos(String type, String name, boolean persistent, long timeout) {
        InterruptedException e;
        Set<ServiceInfo> result = Collections.synchronizedSet(new HashSet(this._knownMDNS.size()));
        ExecutorService executor = Executors.newCachedThreadPool();
        Iterator i$ = this._knownMDNS.values().iterator();
        while (true) {
            Iterator i$2 = i$;
            if (!i$2.hasNext()) {
                break;
            }
            final Set<ServiceInfo> set = result;
            final JmDNS jmDNS = (JmDNS) i$2.next();
            final String str = type;
            final String str2 = name;
            final boolean z = persistent;
            final long j = timeout;
            executor.submit(new Runnable() {
                public void run() {
                    set.add(jmDNS.getServiceInfo(str, str2, z, j));
                }
            });
            i$ = i$2;
        }
        executor.shutdown();
        try {
            try {
                executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e = e2;
                logger.log(Level.WARNING, "Exception ", e);
                return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
            }
        } catch (InterruptedException e3) {
            e = e3;
            long j2 = timeout;
            logger.log(Level.WARNING, "Exception ", e);
            return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
        }
        return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
    }

    public void requestServiceInfo(String type, String name) {
        requestServiceInfo(type, name, false, 6000);
    }

    public void requestServiceInfo(String type, String name, boolean persistent) {
        requestServiceInfo(type, name, persistent, 6000);
    }

    public void requestServiceInfo(String type, String name, long timeout) {
        requestServiceInfo(type, name, false, timeout);
    }

    public void requestServiceInfo(String type, String name, boolean persistent, long timeout) {
        Iterator i$ = this._knownMDNS.values().iterator();
        while (true) {
            Iterator i$2 = i$;
            if (i$2.hasNext()) {
                final JmDNS jmDNS = (JmDNS) i$2.next();
                final String str = type;
                final String str2 = name;
                final boolean z = persistent;
                final long j = timeout;
                r8._jmDNSExecutor.submit(new Runnable() {
                    public void run() {
                        jmDNS.requestServiceInfo(str, str2, z, j);
                    }
                });
                i$ = i$2;
            } else {
                return;
            }
        }
    }

    public void addServiceTypeListener(ServiceTypeListener listener) throws IOException {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.addServiceTypeListener(listener);
        }
    }

    public void removeServiceTypeListener(ServiceTypeListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.removeServiceTypeListener(listener);
        }
    }

    public void addServiceListener(String type, ServiceListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.addServiceListener(type, listener);
        }
    }

    public void removeServiceListener(String type, ServiceListener listener) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.removeServiceListener(type, listener);
        }
    }

    public void textValueUpdated(ServiceInfo target, byte[] value) {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                ServiceInfo info = (ServiceInfo) ((JmDNSImpl) mDNS).getServices().get(target.getQualifiedName());
                if (info != null) {
                    info.setText(value);
                } else {
                    logger.warning("We have a mDNS that does not know about the service info being updated.");
                }
            }
        }
    }

    public void registerService(ServiceInfo info) throws IOException {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.registerService(info.clone());
            }
            ((ServiceInfoImpl) info).setDelegate(this);
            this._services.put(info.getQualifiedName(), info);
        }
    }

    public void unregisterService(ServiceInfo info) {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.unregisterService(info);
            }
            ((ServiceInfoImpl) info).setDelegate(null);
            this._services.remove(info.getQualifiedName());
        }
    }

    public void unregisterAllServices() {
        synchronized (this._services) {
            for (JmDNS mDNS : this._knownMDNS.values()) {
                mDNS.unregisterAllServices();
            }
            this._services.clear();
        }
    }

    public void registerServiceType(String type) {
        for (JmDNS mDNS : this._knownMDNS.values()) {
            mDNS.registerServiceType(type);
        }
    }

    public ServiceInfo[] list(String type) {
        return list(type, 6000);
    }

    public ServiceInfo[] list(String type, long timeout) {
        Set<ServiceInfo> result = Collections.synchronizedSet(new HashSet(this._knownMDNS.size() * 5));
        ExecutorService executor = Executors.newCachedThreadPool();
        Iterator i$ = this._knownMDNS.values().iterator();
        while (true) {
            Iterator i$2 = i$;
            if (!i$2.hasNext()) {
                break;
            }
            final Set<ServiceInfo> set = result;
            final JmDNS jmDNS = (JmDNS) i$2.next();
            final String str = type;
            final long j = timeout;
            executor.submit(new Runnable() {
                public void run() {
                    set.addAll(Arrays.asList(jmDNS.list(str, j)));
                }
            });
            i$ = i$2;
        }
        executor.shutdown();
        try {
            executor.awaitTermination(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException exception) {
            logger.log(Level.WARNING, "Exception ", exception);
        }
        return (ServiceInfo[]) result.toArray(new ServiceInfo[result.size()]);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type) {
        return listBySubtype(type, 6000);
    }

    public Map<String, ServiceInfo[]> listBySubtype(String type, long timeout) {
        Map<String, List<ServiceInfo>> map = new HashMap(5);
        for (ServiceInfo info : list(type, timeout)) {
            String subtype = info.getSubtype();
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

    public void addNetworkTopologyListener(NetworkTopologyListener listener) {
        this._networkListeners.add(listener);
    }

    public void removeNetworkTopologyListener(NetworkTopologyListener listener) {
        this._networkListeners.remove(listener);
    }

    public NetworkTopologyListener[] networkListeners() {
        return (NetworkTopologyListener[]) this._networkListeners.toArray(new NetworkTopologyListener[this._networkListeners.size()]);
    }

    public void inetAddressAdded(NetworkTopologyEvent event) {
        InetAddress address = event.getInetAddress();
        try {
            synchronized (this) {
                if (!this._knownMDNS.containsKey(address)) {
                    this._knownMDNS.put(address, JmDNS.create(address));
                    final NetworkTopologyEvent jmdnsEvent = new NetworkTopologyEventImpl((JmDNS) this._knownMDNS.get(address), address);
                    for (final NetworkTopologyListener listener : networkListeners()) {
                        this._ListenerExecutor.submit(new Runnable() {
                            public void run() {
                                listener.inetAddressAdded(jmdnsEvent);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected unhandled exception: ");
            stringBuilder.append(e);
            logger.warning(stringBuilder.toString());
        }
    }

    public void inetAddressRemoved(NetworkTopologyEvent event) {
        InetAddress address = event.getInetAddress();
        try {
            synchronized (this) {
                if (this._knownMDNS.containsKey(address)) {
                    JmDNS mDNS = (JmDNS) this._knownMDNS.remove(address);
                    mDNS.close();
                    final NetworkTopologyEvent jmdnsEvent = new NetworkTopologyEventImpl(mDNS, address);
                    for (final NetworkTopologyListener listener : networkListeners()) {
                        this._ListenerExecutor.submit(new Runnable() {
                            public void run() {
                                listener.inetAddressRemoved(jmdnsEvent);
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            Logger logger = logger;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected unhandled exception: ");
            stringBuilder.append(e);
            logger.warning(stringBuilder.toString());
        }
    }
}
