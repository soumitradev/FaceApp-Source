package android.support.customtabs;

import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public interface ICustomTabsService extends IInterface {

    public static abstract class Stub extends Binder implements ICustomTabsService {
        private static final String DESCRIPTOR = "android.support.customtabs.ICustomTabsService";
        static final int TRANSACTION_extraCommand = 5;
        static final int TRANSACTION_mayLaunchUrl = 4;
        static final int TRANSACTION_newSession = 3;
        static final int TRANSACTION_postMessage = 8;
        static final int TRANSACTION_requestPostMessageChannel = 7;
        static final int TRANSACTION_updateVisuals = 6;
        static final int TRANSACTION_validateRelationship = 9;
        static final int TRANSACTION_warmup = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICustomTabsService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ICustomTabsService)) {
                return new ICustomTabsService$Stub$Proxy(obj);
            }
            return (ICustomTabsService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code != 1598968902) {
                Bundle _arg2 = null;
                ICustomTabsCallback _arg0;
                boolean _result;
                boolean _result2;
                switch (code) {
                    case 2:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result3 = warmup(data.readLong());
                        reply.writeNoException();
                        reply.writeInt(_result3);
                        return true;
                    case 3:
                        data.enforceInterface(DESCRIPTOR);
                        boolean _result4 = newSession(android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder()));
                        reply.writeNoException();
                        reply.writeInt(_result4);
                        return true;
                    case 4:
                        Uri _arg1;
                        data.enforceInterface(DESCRIPTOR);
                        _arg0 = android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder());
                        if (data.readInt() != 0) {
                            _arg1 = (Uri) Uri.CREATOR.createFromParcel(data);
                        } else {
                            _arg1 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                        }
                        _result = mayLaunchUrl(_arg0, _arg1, _arg2, data.createTypedArrayList(Bundle.CREATOR));
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    case 5:
                        data.enforceInterface(DESCRIPTOR);
                        String _arg02 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                        }
                        Bundle _result5 = extraCommand(_arg02, _arg2);
                        reply.writeNoException();
                        if (_result5 != null) {
                            reply.writeInt(1);
                            _result5.writeToParcel(reply, 1);
                        } else {
                            reply.writeInt(0);
                        }
                        return true;
                    case 6:
                        data.enforceInterface(DESCRIPTOR);
                        _arg0 = android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder());
                        if (data.readInt() != 0) {
                            _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                        }
                        _result2 = updateVisuals(_arg0, _arg2);
                        reply.writeNoException();
                        reply.writeInt(_result2);
                        return true;
                    case 7:
                        Uri _arg12;
                        data.enforceInterface(DESCRIPTOR);
                        _arg0 = android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder());
                        if (data.readInt() != 0) {
                            _arg12 = (Uri) Uri.CREATOR.createFromParcel(data);
                        }
                        _result2 = requestPostMessageChannel(_arg0, _arg12);
                        reply.writeNoException();
                        reply.writeInt(_result2);
                        return true;
                    case 8:
                        data.enforceInterface(DESCRIPTOR);
                        _arg0 = android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder());
                        String _arg13 = data.readString();
                        if (data.readInt() != 0) {
                            _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                        }
                        int _result6 = postMessage(_arg0, _arg13, _arg2);
                        reply.writeNoException();
                        reply.writeInt(_result6);
                        return true;
                    case 9:
                        Uri _arg22;
                        data.enforceInterface(DESCRIPTOR);
                        _arg0 = android.support.customtabs.ICustomTabsCallback.Stub.asInterface(data.readStrongBinder());
                        int _arg14 = data.readInt();
                        if (data.readInt() != 0) {
                            _arg22 = (Uri) Uri.CREATOR.createFromParcel(data);
                        } else {
                            _arg22 = null;
                        }
                        if (data.readInt() != 0) {
                            _arg2 = (Bundle) Bundle.CREATOR.createFromParcel(data);
                        }
                        _result = validateRelationship(_arg0, _arg14, _arg22, _arg2);
                        reply.writeNoException();
                        reply.writeInt(_result);
                        return true;
                    default:
                        return super.onTransact(code, data, reply, flags);
                }
            }
            reply.writeString(DESCRIPTOR);
            return true;
        }
    }

    Bundle extraCommand(String str, Bundle bundle) throws RemoteException;

    boolean mayLaunchUrl(ICustomTabsCallback iCustomTabsCallback, Uri uri, Bundle bundle, List<Bundle> list) throws RemoteException;

    boolean newSession(ICustomTabsCallback iCustomTabsCallback) throws RemoteException;

    int postMessage(ICustomTabsCallback iCustomTabsCallback, String str, Bundle bundle) throws RemoteException;

    boolean requestPostMessageChannel(ICustomTabsCallback iCustomTabsCallback, Uri uri) throws RemoteException;

    boolean updateVisuals(ICustomTabsCallback iCustomTabsCallback, Bundle bundle) throws RemoteException;

    boolean validateRelationship(ICustomTabsCallback iCustomTabsCallback, int i, Uri uri, Bundle bundle) throws RemoteException;

    boolean warmup(long j) throws RemoteException;
}
