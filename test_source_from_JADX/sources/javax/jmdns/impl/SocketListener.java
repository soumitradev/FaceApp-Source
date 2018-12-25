package javax.jmdns.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jmdns.impl.constants.DNSConstants;

class SocketListener extends Thread {
    static Logger logger = Logger.getLogger(SocketListener.class.getName());
    private final JmDNSImpl _jmDNSImpl;

    SocketListener(JmDNSImpl jmDNSImpl) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SocketListener(");
        stringBuilder.append(jmDNSImpl != null ? jmDNSImpl.getName() : "");
        stringBuilder.append(")");
        super(stringBuilder.toString());
        setDaemon(true);
        this._jmDNSImpl = jmDNSImpl;
    }

    public void run() {
        Logger logger;
        try {
            byte[] buf = new byte[8972];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            while (!this._jmDNSImpl.isCanceling() && !this._jmDNSImpl.isCanceled()) {
                packet.setLength(buf.length);
                this._jmDNSImpl.getSocket().receive(packet);
                if (this._jmDNSImpl.isCanceling() || this._jmDNSImpl.isCanceled() || this._jmDNSImpl.isClosing()) {
                    break;
                } else if (this._jmDNSImpl.isClosed()) {
                    break;
                } else {
                    try {
                        if (!this._jmDNSImpl.getLocalHost().shouldIgnorePacket(packet)) {
                            DNSIncoming msg = new DNSIncoming(packet);
                            if (logger.isLoggable(Level.FINEST)) {
                                logger = logger;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append(getName());
                                stringBuilder.append(".run() JmDNS in:");
                                stringBuilder.append(msg.print(true));
                                logger.finest(stringBuilder.toString());
                            }
                            if (msg.isQuery()) {
                                if (packet.getPort() != DNSConstants.MDNS_PORT) {
                                    this._jmDNSImpl.handleQuery(msg, packet.getAddress(), packet.getPort());
                                }
                                this._jmDNSImpl.handleQuery(msg, this._jmDNSImpl.getGroup(), DNSConstants.MDNS_PORT);
                            } else {
                                this._jmDNSImpl.handleResponse(msg);
                            }
                        }
                    } catch (IOException e) {
                        logger = logger;
                        Level level = Level.WARNING;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(getName());
                        stringBuilder2.append(".run() exception ");
                        logger.log(level, stringBuilder2.toString(), e);
                    }
                }
            }
        } catch (IOException e2) {
            if (!(this._jmDNSImpl.isCanceling() || this._jmDNSImpl.isCanceled() || this._jmDNSImpl.isClosing() || this._jmDNSImpl.isClosed())) {
                Logger logger2 = logger;
                Level level2 = Level.WARNING;
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append(getName());
                stringBuilder3.append(".run() exception ");
                logger2.log(level2, stringBuilder3.toString(), e2);
                this._jmDNSImpl.recover();
            }
        }
        if (logger.isLoggable(Level.FINEST)) {
            Logger logger3 = logger;
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(getName());
            stringBuilder4.append(".run() exiting.");
            logger3.finest(stringBuilder4.toString());
        }
    }

    public JmDNSImpl getDns() {
        return this._jmDNSImpl;
    }
}
