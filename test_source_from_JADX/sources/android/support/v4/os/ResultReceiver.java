package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.os.IResultReceiver.Stub;

@RestrictTo({Scope.LIBRARY_GROUP})
public class ResultReceiver implements Parcelable {
    public static final Creator<ResultReceiver> CREATOR = new ResultReceiver$1();
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    public ResultReceiver(Handler handler) {
        this.mLocal = true;
        this.mHandler = handler;
    }

    public void send(int resultCode, Bundle resultData) {
        if (this.mLocal) {
            if (this.mHandler != null) {
                this.mHandler.post(new ResultReceiver$MyRunnable(this, resultCode, resultData));
            } else {
                onReceiveResult(resultCode, resultData);
            }
            return;
        }
        if (this.mReceiver != null) {
            try {
                this.mReceiver.send(resultCode, resultData);
            } catch (RemoteException e) {
            }
        }
    }

    protected void onReceiveResult(int resultCode, Bundle resultData) {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new ResultReceiver$MyResultReceiver(this);
            }
            out.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    ResultReceiver(Parcel in) {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = Stub.asInterface(in.readStrongBinder());
    }
}
