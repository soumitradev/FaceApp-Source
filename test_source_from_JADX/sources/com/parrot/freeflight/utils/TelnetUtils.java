package com.parrot.freeflight.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TelnetUtils {
    public static final boolean executeRemotely(String host, int port, String command) {
        Socket socket = null;
        OutputStream os = null;
        try {
            socket = new Socket(host, port);
            os = socket.getOutputStream();
            os.write(command.getBytes());
            os.flush();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!(socket == null || socket.isClosed())) {
                try {
                    socket.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
            return true;
        } catch (UnknownHostException e3) {
            e3.printStackTrace();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            if (!(socket == null || socket.isClosed())) {
                try {
                    socket.close();
                } catch (IOException e42) {
                    e42.printStackTrace();
                }
            }
            return false;
        } catch (IOException e5) {
            e5.printStackTrace();
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e422) {
                    e422.printStackTrace();
                }
            }
            if (!(socket == null || socket.isClosed())) {
                try {
                    socket.close();
                } catch (IOException e4222) {
                    e4222.printStackTrace();
                }
            }
            return false;
        } catch (Throwable th) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e52) {
                    e52.printStackTrace();
                }
            }
            if (!(socket == null || socket.isClosed())) {
                try {
                    socket.close();
                } catch (IOException e522) {
                    e522.printStackTrace();
                }
            }
        }
    }
}
