package org.catrobat.catroid.devices.raspberrypi;

import android.os.AsyncTask;
import android.util.Log;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class AsyncRPiTaskRunner {
    private static final int CONNECTION_ERROR = 2;
    private static final int CONNECTION_TIMEOUT = 3;
    private static final int CONNECTION_UNHANDLED_EXCEPTION = 4;
    private static final String TAG = AsyncRPiTaskRunner.class.getSimpleName();
    private static final int UNKNOWN_HOST = 1;
    private boolean connected;
    private RPiSocketConnection connection = new RPiSocketConnection();
    private String host;
    private int port;

    private class AsyncConnectTask extends AsyncTask<String, Void, Integer> {
        private AsyncConnectTask() {
        }

        protected Integer doInBackground(String... args) {
            try {
                AsyncRPiTaskRunner.this.connection.connect(AsyncRPiTaskRunner.this.host, AsyncRPiTaskRunner.this.port);
                for (Integer pin : RaspberryPiService.getInstance().getPinInterrupts()) {
                    AsyncRPiTaskRunner.this.connection.activatePinInterrupt(pin.intValue());
                }
                return Integer.valueOf(0);
            } catch (UnknownHostException e) {
                return Integer.valueOf(1);
            } catch (ConnectException e2) {
                return Integer.valueOf(2);
            } catch (SocketTimeoutException e3) {
                return Integer.valueOf(3);
            } catch (Exception e4) {
                String access$500 = AsyncRPiTaskRunner.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception during connect: ");
                stringBuilder.append(e4);
                Log.e(access$500, stringBuilder.toString());
                return Integer.valueOf(4);
            }
        }

        protected void onPostExecute(Integer progress) {
            switch (progress.intValue()) {
                case 1:
                    Log.e(AsyncRPiTaskRunner.TAG, "RPi: Host not found!");
                    return;
                case 2:
                    Log.e(AsyncRPiTaskRunner.TAG, "RPi: Could not connect!");
                    return;
                case 3:
                    Log.e(AsyncRPiTaskRunner.TAG, "RPi: Connection timeout!");
                    return;
                case 4:
                    Log.e(AsyncRPiTaskRunner.TAG, "RPi: Connect unhandled error.");
                    return;
                default:
                    AsyncRPiTaskRunner.this.connected = true;
                    return;
            }
        }
    }

    private class AsyncDisconnectTask extends AsyncTask<String, Void, Integer> {
        private AsyncDisconnectTask() {
        }

        protected Integer doInBackground(String... args) {
            try {
                AsyncRPiTaskRunner.this.connection.disconnect();
                return Integer.valueOf(0);
            } catch (Exception e) {
                String access$500 = AsyncRPiTaskRunner.TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception during disconnect ");
                stringBuilder.append(e);
                Log.e(access$500, stringBuilder.toString());
                return Integer.valueOf(1);
            }
        }

        protected void onPostExecute(Integer error) {
            if (error.intValue() == 1) {
                Log.e(AsyncRPiTaskRunner.TAG, "RPi: Some error during disconnect.");
            }
        }
    }

    public Boolean connect(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            new AsyncConnectTask().execute(new String[0]).get(2000, TimeUnit.MILLISECONDS);
            return Boolean.valueOf(this.connected);
        } catch (Exception e) {
            String str = TAG;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RPi connecting took too long");
            stringBuilder.append(e);
            Log.e(str, stringBuilder.toString());
            return Boolean.valueOf(false);
        }
    }

    public RPiSocketConnection getConnection() {
        return this.connection;
    }

    public void disconnect() {
        if (this.connected) {
            new AsyncDisconnectTask().execute(new String[0]);
        }
    }
}
