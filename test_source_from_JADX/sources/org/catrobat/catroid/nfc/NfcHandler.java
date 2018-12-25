package org.catrobat.catroid.nfc;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Parcelable;
import android.util.Log;
import com.koushikdutta.async.http.body.StringBody;
import java.nio.charset.Charset;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.EventWrapper;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.eventids.EventId;
import org.catrobat.catroid.content.eventids.NfcEventId;

public final class NfcHandler {
    private static final String TAG = NfcHandler.class.getSimpleName();
    private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");
    private static String nfcTagId = "0x0";
    private static String nfcTagMessage = "";

    private NfcHandler() {
    }

    public static void processIntent(Intent intent) {
        if (intent != null) {
            String uid = getTagIdFromIntent(intent);
            if (uid != null) {
                setLastNfcTagId(uid);
                setLastNfcTagMessage(getMessageFromIntent(intent));
                fireNfcEvents(uid);
            }
        }
    }

    public static String getTagIdFromIntent(Intent intent) {
        if (!("android.nfc.action.TAG_DISCOVERED".equals(intent.getAction()) || "android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction()))) {
            if (!"android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) {
                return null;
            }
        }
        String uidHex = String.valueOf(byteArrayToHex(intent.getByteArrayExtra("android.nfc.extra.ID")));
        setLastNfcTagId(uidHex);
        String str = TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("read successful. uid = int: ");
        stringBuilder.append(uidHex);
        Log.d(str, stringBuilder.toString());
        return uidHex;
    }

    public static String getMessageFromIntent(Intent intent) {
        if ("android.nfc.action.TAG_DISCOVERED".equals(intent.getAction()) || "android.nfc.action.NDEF_DISCOVERED".equals(intent.getAction()) || "android.nfc.action.TECH_DISCOVERED".equals(intent.getAction())) {
            Parcelable[] rawMsgs = intent.getParcelableArrayExtra("android.nfc.extra.NDEF_MESSAGES");
            if (rawMsgs != null) {
                NdefMessage[] messages = new NdefMessage[rawMsgs.length];
                for (int i = 0; i < rawMsgs.length; i++) {
                    messages[i] = (NdefMessage) rawMsgs[i];
                }
                if (messages[0] != null) {
                    String result = "";
                    byte[] payload = messages[0].getRecords()[0].getPayload();
                    if (payload.length > 0) {
                        for (int i2 = payloadStartContainsText(payload[0]) ^ 1; i2 < payload.length; i2++) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append(result);
                            stringBuilder.append((char) payload[i2]);
                            result = stringBuilder.toString();
                        }
                    }
                    return result;
                }
            }
        }
        return null;
    }

    private static void fireNfcEvents(String uid) {
        EventWrapper nfcEvent = new EventWrapper(new NfcEventId(uid), 1);
        EventWrapper anyNfcEvent = new EventWrapper(new EventId(5), 1);
        Project currentProject = ProjectManager.getInstance().getCurrentProject();
        currentProject.fireToAllSprites(nfcEvent);
        currentProject.fireToAllSprites(anyNfcEvent);
    }

    private static boolean payloadStartContainsText(byte payloadStart) {
        return payloadStart != (byte) 1;
    }

    public static Object getLastNfcTagMessage() {
        return nfcTagMessage;
    }

    public static String getLastNfcTagId() {
        return nfcTagId;
    }

    public static void setLastNfcTagId(String tagID) {
        nfcTagId = tagID;
    }

    public static void setLastNfcTagMessage(String message) {
        nfcTagMessage = message;
    }

    public static String byteArrayToHex(byte[] a) {
        if (a == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int length = a.length;
        for (int i = 0; i < length; i++) {
            sb.append(String.format("%02x", new Object[]{Integer.valueOf(a[i] & 255)}));
        }
        return sb.toString();
    }

    public static void writeTag(Tag tag, NdefMessage message) {
        if (tag != null) {
            try {
                Ndef ndefTag = Ndef.get(tag);
                if (ndefTag == null) {
                    NdefFormatable nForm = NdefFormatable.get(tag);
                    if (nForm != null) {
                        nForm.connect();
                        nForm.format(message);
                        nForm.close();
                    }
                } else {
                    ndefTag.connect();
                    ndefTag.writeNdefMessage(message);
                    ndefTag.close();
                }
            } catch (Exception e) {
                Log.d(TAG, "Couldn't create message", e);
            }
        }
    }

    public static NdefMessage createMessage(String message, int spinnerSelection) {
        NdefRecord ndefRecord;
        NdefRecord createExternal;
        byte[] type;
        byte[] id;
        byte[] uriField;
        byte[] payload;
        switch (spinnerSelection) {
            case 0:
                ndefRecord = new NdefRecord((short) 2, StringBody.CONTENT_TYPE.getBytes(UTF8_CHARSET), new byte[0], message.getBytes(UTF8_CHARSET));
                break;
            case 1:
                type = NdefRecord.RTD_URI;
                id = new byte[0];
                uriField = deleteProtocolPrefixIfExist(message).getBytes(UTF8_CHARSET);
                payload = new byte[(uriField.length + 1)];
                payload[0] = (byte) 3;
                System.arraycopy(uriField, 0, payload, 1, uriField.length);
                ndefRecord = new NdefRecord((short) 1, type, id, payload);
                break;
            case 2:
                type = NdefRecord.RTD_URI;
                id = new byte[0];
                uriField = deleteProtocolPrefixIfExist(message).getBytes(UTF8_CHARSET);
                payload = new byte[(uriField.length + 1)];
                payload[0] = (byte) 4;
                System.arraycopy(uriField, 0, payload, 1, uriField.length);
                ndefRecord = new NdefRecord((short) 1, type, id, payload);
                break;
            case 3:
                type = "nfclab.com:smsService".getBytes(UTF8_CHARSET);
                id = new byte[0];
                String smsMessage = new StringBuilder();
                smsMessage.append("sms:");
                smsMessage.append(message);
                smsMessage.append("?body=");
                smsMessage.append("SMS from Catrobat");
                ndefRecord = new NdefRecord((short) 4, type, id, smsMessage.toString().getBytes(UTF8_CHARSET));
                break;
            case 4:
                type = NdefRecord.RTD_URI;
                id = new byte[0];
                uriField = message.getBytes(UTF8_CHARSET);
                payload = new byte[(uriField.length + 1)];
                payload[0] = (byte) 5;
                System.arraycopy(uriField, 0, payload, 1, uriField.length);
                ndefRecord = new NdefRecord((short) 1, type, id, payload);
                break;
            case 5:
                type = NdefRecord.RTD_URI;
                id = new byte[0];
                uriField = message.getBytes(UTF8_CHARSET);
                payload = new byte[(uriField.length + 1)];
                payload[0] = (byte) 6;
                System.arraycopy(uriField, 0, payload, 1, uriField.length);
                ndefRecord = new NdefRecord((short) 1, type, id, payload);
                break;
            case 6:
                createExternal = NdefRecord.createExternal("catrobat.com", "catroid", message.getBytes(UTF8_CHARSET));
                break;
            case 7:
                createExternal = new NdefRecord((short) 0, new byte[0], new byte[0], new byte[0]);
                break;
            default:
                createExternal = NdefRecord.createUri(message);
                break;
        }
        ndefRecord = createExternal;
        return new NdefMessage(new NdefRecord[]{ndefRecord});
    }

    public static String deleteProtocolPrefixIfExist(String url) {
        return url.replaceFirst("^\\w+://", "");
    }
}
