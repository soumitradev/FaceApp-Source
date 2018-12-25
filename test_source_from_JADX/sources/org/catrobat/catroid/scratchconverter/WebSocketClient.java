package org.catrobat.catroid.scratchconverter;

import android.util.Log;
import com.google.android.gms.common.images.WebImage;
import com.google.common.base.Preconditions;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.AsyncHttpClient.WebSocketConnectCallback;
import com.koushikdutta.async.http.WebSocket;
import com.koushikdutta.async.http.WebSocket.StringCallback;
import org.catrobat.catroid.common.Constants;
import org.catrobat.catroid.scratchconverter.Client.ConnectAuthCallback;
import org.catrobat.catroid.scratchconverter.Client.ConvertCallback;
import org.catrobat.catroid.scratchconverter.Client.DownloadCallback;
import org.catrobat.catroid.scratchconverter.Client.State;
import org.catrobat.catroid.scratchconverter.protocol.BaseMessageHandler;
import org.catrobat.catroid.scratchconverter.protocol.Job;
import org.catrobat.catroid.scratchconverter.protocol.MessageListener;
import org.catrobat.catroid.scratchconverter.protocol.command.AuthenticateCommand;
import org.catrobat.catroid.scratchconverter.protocol.command.Command;
import org.catrobat.catroid.scratchconverter.protocol.command.RetrieveInfoCommand;
import org.catrobat.catroid.scratchconverter.protocol.command.ScheduleJobCommand;
import org.catrobat.catroid.scratchconverter.protocol.message.base.BaseMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.ClientIDMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.ErrorMessage;
import org.catrobat.catroid.scratchconverter.protocol.message.base.InfoMessage;

public final class WebSocketClient<T extends MessageListener & StringCallback> implements Client, BaseMessageHandler, CompletedCallback {
    private static final String TAG = WebSocketClient.class.getSimpleName();
    private AsyncHttpClient asyncHttpClient = AsyncHttpClient.getDefaultInstance();
    private long clientID;
    private ConnectAuthCallback connectAuthCallback;
    private ConvertCallback convertCallback;
    private final T messageListener;
    private State state;
    private WebSocket webSocket;

    private interface ConnectCallback {
        void onFailure(ClientException clientException);

        void onSuccess();
    }

    public WebSocketClient(long clientID, T messageListener) {
        this.clientID = clientID;
        this.state = State.NOT_CONNECTED;
        messageListener.setBaseMessageHandler(this);
        this.messageListener = messageListener;
        this.webSocket = null;
        this.connectAuthCallback = null;
        this.convertCallback = null;
    }

    public boolean isConnected() {
        if (this.state != State.CONNECTED) {
            if (this.state != State.CONNECTED_AUTHENTICATED) {
                return false;
            }
        }
        return true;
    }

    public boolean isClosed() {
        return this.state == State.NOT_CONNECTED;
    }

    public boolean isAuthenticated() {
        return this.state == State.CONNECTED_AUTHENTICATED;
    }

    public void setAsyncHttpClient(AsyncHttpClient asyncHttpClient) {
        this.asyncHttpClient = asyncHttpClient;
    }

    public void setConvertCallback(ConvertCallback callback) {
        this.convertCallback = callback;
    }

    private void connect(final ConnectCallback connectCallback) {
        if (this.state == State.CONNECTED) {
            connectCallback.onSuccess();
            return;
        }
        boolean z = false;
        Preconditions.checkState(this.webSocket == null);
        if (this.asyncHttpClient != null) {
            z = true;
        }
        Preconditions.checkState(z);
        final WebSocketClient client = this;
        this.asyncHttpClient.websocket(Constants.SCRATCH_CONVERTER_WEB_SOCKET, null, new WebSocketConnectCallback() {
            public void onCompleted(Exception ex, WebSocket newWebSocket) {
                boolean z = WebSocketClient.this.state != State.CONNECTED && WebSocketClient.this.webSocket == null;
                Preconditions.checkState(z);
                if (ex != null) {
                    connectCallback.onFailure(new ClientException((Throwable) ex));
                    return;
                }
                WebSocketClient.this.state = State.CONNECTED;
                WebSocketClient.this.webSocket = newWebSocket;
                WebSocketClient.this.webSocket.setStringCallback((StringCallback) WebSocketClient.this.messageListener);
                WebSocketClient.this.webSocket.setClosedCallback(client);
                connectCallback.onSuccess();
            }
        });
    }

    public void onCompleted(Exception ex) {
        this.state = State.NOT_CONNECTED;
        this.connectAuthCallback.onConnectionClosed(new ClientException((Throwable) ex));
    }

    public void close() {
        boolean z = false;
        Preconditions.checkState(this.state != State.NOT_CONNECTED);
        Preconditions.checkState(this.webSocket != null);
        if (this.connectAuthCallback != null) {
            z = true;
        }
        Preconditions.checkState(z);
        this.state = State.NOT_CONNECTED;
        this.webSocket.close();
    }

    private void authenticate() {
        boolean z = false;
        Preconditions.checkState(this.state == State.CONNECTED);
        if (this.webSocket != null) {
            z = true;
        }
        Preconditions.checkState(z);
        sendCommand(new AuthenticateCommand(this.clientID));
    }

    public void connectAndAuthenticate(final ConnectAuthCallback connectAuthCallback) {
        this.connectAuthCallback = connectAuthCallback;
        switch (this.state) {
            case NOT_CONNECTED:
                connect(new ConnectCallback() {
                    public void onSuccess() {
                        Log.i(WebSocketClient.TAG, "Successfully connected to WebSocket server");
                        WebSocketClient.this.authenticate();
                    }

                    public void onFailure(ClientException ex) {
                        connectAuthCallback.onConnectionFailure(ex);
                    }
                });
                return;
            case CONNECTED:
                Log.i(TAG, "Already connected to WebSocket server!");
                authenticate();
                return;
            case CONNECTED_AUTHENTICATED:
                Log.i(TAG, "Already authenticated!");
                connectAuthCallback.onSuccess(this.clientID);
                return;
            default:
                return;
        }
    }

    public void retrieveInfo() {
        boolean z = false;
        Preconditions.checkState(this.state == State.CONNECTED_AUTHENTICATED);
        if (this.clientID != -1) {
            z = true;
        }
        Preconditions.checkState(z);
        sendCommand(new RetrieveInfoCommand());
    }

    public boolean isJobInProgress(long jobID) {
        return this.messageListener.isJobInProgress(jobID);
    }

    public int getNumberOfJobsInProgress() {
        return this.messageListener.getNumberOfJobsInProgress();
    }

    public void convertProgram(long jobID, String title, WebImage image, boolean verbose, boolean force) {
        boolean z = false;
        Preconditions.checkState(this.state == State.CONNECTED_AUTHENTICATED);
        if (this.clientID != -1) {
            z = true;
        }
        Preconditions.checkState(z);
        Job job = new Job(jobID, title, image);
        if (this.state == State.CONNECTED_AUTHENTICATED) {
            if (this.webSocket != null) {
                String str;
                StringBuilder stringBuilder;
                if (this.messageListener.scheduleJob(job, force, this.convertCallback)) {
                    str = TAG;
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Scheduling new job with ID: ");
                    stringBuilder.append(jobID);
                    Log.i(str, stringBuilder.toString());
                    sendCommand(new ScheduleJobCommand(jobID, force, verbose));
                    return;
                }
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Cannot schedule job since another job of the same Scratch program is already running (job ID is: ");
                stringBuilder.append(jobID);
                stringBuilder.append(")");
                Log.e(str, stringBuilder.toString());
                this.convertCallback.onConversionFailure(job, new ClientException("Cannot start this job since the job already exists and is in progress! Set force-flag to true to restart the conversion while it is running!"));
                return;
            }
        }
        this.convertCallback.onConversionFailure(job, new ClientException("Not connected!"));
    }

    public void onUserCanceledConversion(long jobID) {
        boolean z = false;
        Preconditions.checkState(this.state == State.CONNECTED_AUTHENTICATED);
        if (this.clientID != -1) {
            z = true;
        }
        Preconditions.checkState(z);
        this.messageListener.onUserCanceledConversion(jobID);
    }

    public void onBaseMessage(BaseMessage baseMessage) {
        boolean z = false;
        if (baseMessage instanceof InfoMessage) {
            InfoMessage infoMessage = (InfoMessage) baseMessage;
            this.convertCallback.onInfo(infoMessage.getCatrobatLanguageVersion(), infoMessage.getJobList());
            Job[] jobs = infoMessage.getJobList();
            int length = jobs.length;
            int i;
            while (i < length) {
                Job job = jobs[i];
                DownloadCallback downloadCallback = this.messageListener.restoreJobIfRunning(job, this.convertCallback);
                if (downloadCallback != null) {
                    this.convertCallback.onConversionAlreadyFinished(job, downloadCallback, job.getDownloadURL());
                }
                i++;
            }
        } else if (baseMessage instanceof ErrorMessage) {
            ErrorMessage errorMessage = (ErrorMessage) baseMessage;
            Log.e(TAG, errorMessage.getMessage());
            if (this.state == State.CONNECTED) {
                if (this.connectAuthCallback != null) {
                    z = true;
                }
                Preconditions.checkState(z);
                this.connectAuthCallback.onAuthenticationFailure(new ClientException(errorMessage.getMessage()));
            } else if (this.state == State.CONNECTED_AUTHENTICATED) {
                this.convertCallback.onConversionFailure(null, new ClientException(errorMessage.getMessage()));
            } else {
                this.convertCallback.onError(errorMessage.getMessage());
            }
        } else if (baseMessage instanceof ClientIDMessage) {
            if (this.state == State.CONNECTED) {
                z = true;
            }
            Preconditions.checkState(z);
            ClientIDMessage clientIDMessage = (ClientIDMessage) baseMessage;
            long oldClientID = this.clientID;
            this.clientID = clientIDMessage.getClientID();
            if (this.clientID != oldClientID) {
                String str = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("New Client ID: ");
                stringBuilder.append(this.clientID);
                Log.d(str, stringBuilder.toString());
            }
            this.state = State.CONNECTED_AUTHENTICATED;
            this.connectAuthCallback.onSuccess(this.clientID);
        } else {
            String str2 = TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("No handler implemented for base message: ");
            stringBuilder2.append(baseMessage);
            Log.e(str2, stringBuilder2.toString());
        }
    }

    private void sendCommand(Command command) {
        boolean z;
        String dataToSend;
        String str;
        StringBuilder stringBuilder;
        boolean z2 = false;
        Preconditions.checkArgument(command != null);
        if (this.state != State.CONNECTED) {
            if (this.state != State.CONNECTED_AUTHENTICATED) {
                z = false;
                Preconditions.checkState(z);
                if (this.webSocket != null) {
                    z2 = true;
                }
                Preconditions.checkState(z2);
                dataToSend = command.toJson().toString();
                str = TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Sending: ");
                stringBuilder.append(dataToSend);
                Log.d(str, stringBuilder.toString());
                this.webSocket.send(dataToSend);
            }
        }
        z = true;
        Preconditions.checkState(z);
        if (this.webSocket != null) {
            z2 = true;
        }
        Preconditions.checkState(z2);
        dataToSend = command.toJson().toString();
        str = TAG;
        stringBuilder = new StringBuilder();
        stringBuilder.append("Sending: ");
        stringBuilder.append(dataToSend);
        Log.d(str, stringBuilder.toString());
        this.webSocket.send(dataToSend);
    }
}
