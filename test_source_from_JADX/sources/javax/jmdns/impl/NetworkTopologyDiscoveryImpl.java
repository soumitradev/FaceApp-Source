package javax.jmdns.impl;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.NetworkTopologyDiscovery;

public class NetworkTopologyDiscoveryImpl implements NetworkTopologyDiscovery {
    private static final Logger logger = Logger.getLogger(NetworkTopologyDiscoveryImpl.class.getName());
    private final Method _isUp;
    private final Method _supportsMulticast;

    public NetworkTopologyDiscoveryImpl() {
        Method isUp;
        Method supportsMulticast;
        try {
            isUp = NetworkInterface.class.getMethod("isUp", (Class[]) null);
            Object obj = null;
        } catch (Exception e) {
            Exception supportsMulticast2 = e;
            isUp = null;
        }
        this._isUp = isUp;
        try {
            supportsMulticast = NetworkInterface.class.getMethod("supportsMulticast", (Class[]) null);
        } catch (Exception e2) {
            supportsMulticast = null;
        }
        this._supportsMulticast = supportsMulticast;
    }

    public InetAddress[] getInetAddresses() {
        Set<InetAddress> result = new HashSet();
        try {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            while (nifs.hasMoreElements()) {
                NetworkInterface nif = (NetworkInterface) nifs.nextElement();
                Enumeration<InetAddress> iaenum = nif.getInetAddresses();
                while (iaenum.hasMoreElements()) {
                    InetAddress interfaceAddress = (InetAddress) iaenum.nextElement();
                    if (logger.isLoggable(Level.FINEST)) {
                        Logger logger = logger;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Found NetworkInterface/InetAddress: ");
                        stringBuilder.append(nif);
                        stringBuilder.append(" -- ");
                        stringBuilder.append(interfaceAddress);
                        logger.finest(stringBuilder.toString());
                    }
                    if (useInetAddress(nif, interfaceAddress)) {
                        result.add(interfaceAddress);
                    }
                }
            }
        } catch (SocketException se) {
            Logger logger2 = logger;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Error while fetching network interfaces addresses: ");
            stringBuilder2.append(se);
            logger2.warning(stringBuilder2.toString());
        }
        return (InetAddress[]) result.toArray(new InetAddress[result.size()]);
    }

    public boolean useInetAddress(NetworkInterface networkInterface, InetAddress interfaceAddress) {
        if (this._isUp != null) {
            try {
                if (!((Boolean) this._isUp.invoke(networkInterface, (Object[]) null)).booleanValue()) {
                    return false;
                }
            } catch (Exception e) {
            }
        }
        try {
            if (this._supportsMulticast != null) {
                try {
                    if (!((Boolean) this._supportsMulticast.invoke(networkInterface, (Object[]) null)).booleanValue()) {
                        return false;
                    }
                } catch (Exception e2) {
                }
            }
            if (interfaceAddress.isLoopbackAddress()) {
                return false;
            }
            return true;
        } catch (Exception e3) {
            return false;
        }
    }

    public void lockInetAddress(InetAddress interfaceAddress) {
    }

    public void unlockInetAddress(InetAddress interfaceAddress) {
    }
}
