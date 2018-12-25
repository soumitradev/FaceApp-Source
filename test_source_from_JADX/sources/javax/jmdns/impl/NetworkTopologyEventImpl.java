package javax.jmdns.impl;

import java.net.InetAddress;
import javax.jmdns.JmDNS;
import javax.jmdns.NetworkTopologyEvent;
import javax.jmdns.NetworkTopologyListener;
import name.antonsmirnov.firmata.FormatHelper;

public class NetworkTopologyEventImpl extends NetworkTopologyEvent implements Cloneable {
    private static final long serialVersionUID = 1445606146153550463L;
    private final InetAddress _inetAddress;

    public NetworkTopologyEventImpl(JmDNS jmDNS, InetAddress inetAddress) {
        super(jmDNS);
        this._inetAddress = inetAddress;
    }

    NetworkTopologyEventImpl(NetworkTopologyListener jmmDNS, InetAddress inetAddress) {
        super(jmmDNS);
        this._inetAddress = inetAddress;
    }

    public JmDNS getDNS() {
        return getSource() instanceof JmDNS ? (JmDNS) getSource() : null;
    }

    public InetAddress getInetAddress() {
        return this._inetAddress;
    }

    public String toString() {
        StringBuilder buf = new StringBuilder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append("@");
        stringBuilder.append(System.identityHashCode(this));
        stringBuilder.append(FormatHelper.SPACE);
        buf.append(stringBuilder.toString());
        buf.append("\n\tinetAddress: '");
        buf.append(getInetAddress());
        buf.append("']");
        return buf.toString();
    }

    public NetworkTopologyEventImpl clone() throws CloneNotSupportedException {
        return new NetworkTopologyEventImpl(getDNS(), getInetAddress());
    }
}
