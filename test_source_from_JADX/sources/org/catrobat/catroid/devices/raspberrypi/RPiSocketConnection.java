package org.catrobat.catroid.devices.raspberrypi;

import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.eventids.RaspiEventId;

public class RPiSocketConnection {
    private static final String TAG = AsyncRPiTaskRunner.class.getSimpleName();
    private ArrayList<Integer> availableGPIOs;
    private Socket clientSocket;
    private String host;
    private int interruptReceiverPort;
    private boolean isConnected;
    private DataOutputStream outStream;
    private OutputStream outToServer;
    private BufferedReader reader;
    private Thread receiverThread;
    private String rpiVersion;

    public class NoConnectionException extends Exception {
        private static final long serialVersionUID = 1;

        public NoConnectionException(String msg) {
            super(msg);
        }
    }

    public class NoGpioException extends Exception {
        private static final long serialVersionUID = 1;

        public NoGpioException(String msg) {
            super(msg);
        }
    }

    private class RPiSocketReceiver implements Runnable {
        private RPiSocketReceiver() {
        }

        public void run() {
            String receivedLine;
            try {
                Socket receiverSocket = new Socket(RPiSocketConnection.this.host, RPiSocketConnection.this.interruptReceiverPort);
                BufferedReader receiverReader = new BufferedReader(new InputStreamReader(receiverSocket.getInputStream()));
                while (!Thread.interrupted()) {
                    receivedLine = receiverReader.readLine();
                    if (receivedLine == null) {
                        break;
                    }
                    String access$300 = RPiSocketConnection.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Interrupt: ");
                    stringBuilder.append(receivedLine);
                    Log.d(access$300, stringBuilder.toString());
                    RPiSocketConnection.this.callEvent(receivedLine);
                }
                receiverSocket.close();
                Log.d(RPiSocketConnection.TAG, "RPiSocketReceiver closed");
            } catch (IOException e) {
                receivedLine = RPiSocketConnection.TAG;
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Exception ");
                stringBuilder2.append(e);
                Log.e(receivedLine, stringBuilder2.toString());
            }
        }
    }

    public void connect(String host, int port) throws Exception {
        if (this.isConnected) {
            disconnect();
        }
        this.host = host;
        this.clientSocket = new Socket();
        this.clientSocket.connect(new InetSocketAddress(host, port), 2000);
        this.outToServer = this.clientSocket.getOutputStream();
        this.outStream = new DataOutputStream(this.outToServer);
        this.reader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        String hello = this.reader.readLine();
        if (hello.startsWith("quit")) {
            throw new NoConnectionException("Server refused to accept our connection!");
        } else if (hello.startsWith("hello")) {
            this.isConnected = true;
            respondVersion();
            readServerPort();
            this.receiverThread = new Thread(new RPiSocketReceiver());
            this.receiverThread.start();
        }
    }

    public void disconnect() throws IOException {
        if (this.isConnected) {
            try {
                processCommand("quit");
            } catch (NoConnectionException e) {
                Log.d(TAG, "Error during quit, this should not happen!");
            }
            this.isConnected = false;
            this.clientSocket.close();
            this.receiverThread.interrupt();
        }
    }

    private void respondVersion() throws Exception {
        this.rpiVersion = processCommand("rev").split(FormatHelper.SPACE)[1];
        this.availableGPIOs = RaspberryPiService.getInstance().getGpioList(this.rpiVersion);
    }

    private void readServerPort() throws Exception {
        this.interruptReceiverPort = Integer.parseInt(processCommand("serverport").split(FormatHelper.SPACE)[1]);
    }

    private String processCommand(String command) throws IOException, NoConnectionException {
        if (this.isConnected) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Sending:  ");
            stringBuilder.append(command);
            Log.d(str, stringBuilder.toString());
            this.outStream.write(command.getBytes());
            str = this.reader.readLine();
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Received: ");
            stringBuilder2.append(str);
            Log.d(str2, stringBuilder2.toString());
            if (str != null) {
                if (str.startsWith(command.split(FormatHelper.SPACE)[0])) {
                    return str;
                }
            }
            throw new IOException("Error with response");
        }
        throw new NoConnectionException("No active connection!");
    }

    private void callEvent(String broadcastMessage) {
        String[] messageSegments = broadcastMessage.split(FormatHelper.SPACE);
        if (messageSegments.length == 3 && ProjectManager.getInstance().getCurrentProject() != null) {
            ProjectManager.getInstance().getCurrentProject().fireToAllSprites(new EventWrapper(new RaspiEventId(messageSegments[1], messageSegments[2]), 1));
        }
    }

    public void setPin(int pin, boolean value) throws NoConnectionException, IOException, NoGpioException {
        if (!this.isConnected) {
            throw new NoConnectionException("No active connection!");
        } else if (this.availableGPIOs.contains(Integer.valueOf(pin))) {
            short valueShort = (short) value;
            String setRequestMessage = new StringBuilder();
            setRequestMessage.append("set ");
            setRequestMessage.append(pin);
            setRequestMessage.append(FormatHelper.SPACE);
            setRequestMessage.append(valueShort);
            if (processCommand(setRequestMessage.toString()).split(FormatHelper.SPACE).length != 3) {
                throw new IOException("setRequest: Error with response");
            }
        } else {
            throw new NoGpioException("Pin out of range on this model!");
        }
    }

    public boolean getPin(int pin) throws NoConnectionException, IOException, NoGpioException {
        if (this.availableGPIOs.contains(Integer.valueOf(pin))) {
            String readRequestMsg = new StringBuilder();
            readRequestMsg.append("read ");
            readRequestMsg.append(pin);
            String[] tokens = processCommand(readRequestMsg.toString()).split(FormatHelper.SPACE);
            if (tokens.length != 3) {
                throw new IOException("readRequest: Error with response");
            } else if (tokens[2].equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                return true;
            } else {
                if (tokens[2].equals(AppEventsConstants.EVENT_PARAM_VALUE_NO)) {
                    return false;
                }
                throw new IOException("readRequest: Error with response");
            }
        }
        throw new NoGpioException("Pin out of range on this model!");
    }

    public void activatePinInterrupt(int pin) throws NoConnectionException, IOException, NoGpioException {
        if (this.availableGPIOs.contains(Integer.valueOf(pin))) {
            String readRequestMsg = new StringBuilder();
            readRequestMsg.append("readint ");
            readRequestMsg.append(pin);
            if (processCommand(readRequestMsg.toString()).split(FormatHelper.SPACE).length != 3) {
                throw new IOException("readRequest: Error with response");
            }
            return;
        }
        throw new NoGpioException("Pin out of range on this model!");
    }

    public void setPWM(int pin, double frequencyInHz, double dutyCycleInPercent) throws NoConnectionException, IOException, NoGpioException {
        if (this.availableGPIOs.contains(Integer.valueOf(pin))) {
            String pwmRequestMessage = new StringBuilder();
            pwmRequestMessage.append("pwm ");
            pwmRequestMessage.append(pin);
            pwmRequestMessage.append(FormatHelper.SPACE);
            pwmRequestMessage.append(frequencyInHz);
            pwmRequestMessage.append(FormatHelper.SPACE);
            pwmRequestMessage.append(dutyCycleInPercent);
            pwmRequestMessage = pwmRequestMessage.toString();
            if (!pwmRequestMessage.equals(processCommand(pwmRequestMessage))) {
                throw new IOException("pwmRequest: Error with response");
            }
            return;
        }
        throw new NoGpioException("Pin out of range on this model!");
    }

    public String getVersion() throws NoConnectionException {
        if (this.isConnected) {
            return this.rpiVersion;
        }
        throw new NoConnectionException("No active connection!");
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}
