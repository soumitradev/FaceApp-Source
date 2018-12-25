package com.parrot.arsdk.ardiscovery.mdnssdmin;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.parrot.arsdk.ardiscovery.mdnssdmin.internal.MdnsSdIncomingResponse;
import com.parrot.arsdk.ardiscovery.mdnssdmin.internal.MdnsSdOutgoingQuery;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import javax.jmdns.impl.constants.DNSConstants;

public class MdnsSdMin {
    private static final int QUERY_DURATION_MS = 5000;
    private static final int QUERY_INTERVAL_MS = 250;
    private static final String TAG = "MdnsSdMin";
    private final String MDNS_MULTICAST_ADDR = DNSConstants.MDNS_GROUP;
    private final int MDNS_MULTICAST_PORT = 5353;
    private final Listener listener;
    private final MdnsSdOutgoingQuery query;
    private Handler queryHandler;
    private HandlerThread queryThread;
    private Thread receiveThread;
    private final String[] services;
    private MulticastSocket socket;

    public interface Listener {
        void onServiceAdded(String str, String str2, String str3, int i, String[] strArr);

        void onServiceRemoved(String str, String str2);
    }

    private class QueryThread extends HandlerThread {
        private final DatagramSocket socket;

        public QueryThread(DatagramSocket socket) {
            super("MdnsSd-query");
            this.socket = socket;
        }

        protected void onLooperPrepared() {
            if (this.socket.isClosed()) {
                getLooper().quit();
                return;
            }
            MdnsSdMin.this.queryHandler = new Handler(getLooper()) {
                public void handleMessage(Message msg) {
                    ARSALPrint.m530d(MdnsSdMin.TAG, "sending query packet");
                    try {
                        byte[] buf = MdnsSdMin.this.query.encode();
                        QueryThread.this.socket.send(new DatagramPacket(buf, buf.length, InetAddress.getByName(DNSConstants.MDNS_GROUP), 5353));
                    } catch (IOException e) {
                        ARSALPrint.m533e(MdnsSdMin.TAG, "unable to start query", e);
                    }
                }
            };
            MdnsSdMin.this.sendQueries();
        }
    }

    private class ReceiverThread extends Thread {
        private final DatagramSocket socket;

        public ReceiverThread(DatagramSocket socket) {
            super("MdnsSd-receiver");
            this.socket = socket;
        }

        public void run() {
            byte[] buffer = new byte[1500];
            while (!this.socket.isClosed()) {
                try {
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    this.socket.receive(packet);
                    handleResponse(new MdnsSdIncomingResponse(packet.getData()));
                } catch (Throwable e) {
                    String str = MdnsSdMin.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Ignoring received packet due to ");
                    stringBuilder.append(e.getMessage());
                    ARSALPrint.m538w(str, stringBuilder.toString());
                }
            }
        }

        private void handleResponse(MdnsSdIncomingResponse response) {
            MdnsSdIncomingResponse mdnsSdIncomingResponse = response;
            String[] access$000 = MdnsSdMin.this.services;
            int length = access$000.length;
            int i = 0;
            int i2 = 0;
            while (i2 < length) {
                String question = access$000[i2];
                String ptr = mdnsSdIncomingResponse.getPtr(question);
                if (ptr != null) {
                    MdnsSrvData srv = mdnsSdIncomingResponse.getService(ptr);
                    if (srv != null) {
                        String address = mdnsSdIncomingResponse.getAddress(srv.getTarget());
                        String[] txts = mdnsSdIncomingResponse.getTexts(ptr);
                        if (address != null) {
                            int pos = -1;
                            if (ptr.endsWith(question)) {
                                pos = ptr.length() - question.length();
                            }
                            int pos2 = pos;
                            String name = ptr.substring(i, pos2 > 0 ? pos2 - 1 : ptr.length());
                            String str;
                            StringBuilder stringBuilder;
                            if (srv.getTtl() > 0) {
                                str = MdnsSdMin.TAG;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("New service ");
                                stringBuilder.append(name);
                                ARSALPrint.m530d(str, stringBuilder.toString());
                                MdnsSdMin.this.listener.onServiceAdded(name, question, address, srv.getPort(), txts);
                            } else {
                                String name2 = name;
                                str = MdnsSdMin.TAG;
                                stringBuilder = new StringBuilder();
                                stringBuilder.append("Service removed ");
                                stringBuilder.append(name2);
                                ARSALPrint.m530d(str, stringBuilder.toString());
                                MdnsSdMin.this.listener.onServiceRemoved(name2, question);
                            }
                        }
                    }
                }
                i2++;
                i = 0;
            }
        }
    }

    public MdnsSdMin(String[] services, Listener listener) {
        this.services = services;
        this.listener = listener;
        this.query = new MdnsSdOutgoingQuery(services);
    }

    public void start(NetworkInterface netInterface) {
        ARSALPrint.m530d(TAG, "Starting MdsnSd");
        if (this.socket == null) {
            try {
                this.socket = new MulticastSocket(5353);
                if (netInterface != null) {
                    this.socket.setNetworkInterface(netInterface);
                    this.socket.joinGroup(new InetSocketAddress(InetAddress.getByName(DNSConstants.MDNS_GROUP), 5353), netInterface);
                } else {
                    this.socket.joinGroup(InetAddress.getByName(DNSConstants.MDNS_GROUP));
                }
                this.socket.setTimeToLive(255);
                this.receiveThread = new ReceiverThread(this.socket);
                this.receiveThread.start();
                this.queryThread = new QueryThread(this.socket);
                this.queryThread.start();
            } catch (IOException e) {
                ARSALPrint.m533e(TAG, "unable to start MdsnSd", e);
            }
        }
    }

    public void stop() {
        ARSALPrint.m530d(TAG, "Stopping MdsnSd");
        if (this.socket != null) {
            this.socket.close();
            this.socket = null;
            this.receiveThread = null;
            if (this.queryThread != null) {
                this.queryThread.quit();
                this.queryThread = null;
            }
        }
    }

    public void sendQueries() {
        ARSALPrint.m530d(TAG, "Sending queries");
        for (int t = 0; t < 5000; t += 250) {
            this.queryHandler.sendMessageDelayed(this.queryHandler.obtainMessage(0), (long) t);
        }
    }

    public void cancelSendQueries() {
        ARSALPrint.m530d(TAG, "Cancel sending queries");
        this.queryHandler.removeMessages(0);
    }
}
