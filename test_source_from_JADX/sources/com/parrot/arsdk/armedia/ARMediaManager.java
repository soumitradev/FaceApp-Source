package com.parrot.arsdk.armedia;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.MergeCursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video.Media;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import com.parrot.arsdk.armedia.Exif2Interface.Tag;
import com.parrot.arsdk.arsal.ARSALPrint;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class ARMediaManager {
    private static final String ARMEDIA_MANAGER_DATABASE_FILENAME = "ARMediaDB";
    public static final String ARMEDIA_MANAGER_JPG = ".jpg";
    public static final String ARMEDIA_MANAGER_MOV = ".mov";
    public static final String ARMEDIA_MANAGER_MP4 = ".mp4";
    public static final String ARMediaManagerNotificationDictionary = "ARMediaManagerNotificationDictionary";
    public static final String ARMediaManagerNotificationDictionaryIsInitKey = "ARMediaManagerNotificationDictionaryIsInitKey";
    public static final String ARMediaManagerNotificationDictionaryMediaAddedKey = "ARMediaManagerNotificationDictionaryMediaAddedKey";
    public static final String ARMediaManagerNotificationDictionaryUpdatedKey = "ARMediaManagerNotificationDictionaryUpdatedKey";
    public static final String ARMediaManagerNotificationDictionaryUpdatingKey = "ARMediaManagerNotificationDictionaryUpdatingKey";
    private static final String ARMediaManagerObjectDate = "ARMediaManagerObjectDate";
    private static final String ARMediaManagerObjectFilePath = "ARMediaManagerObjectFilePath";
    private static final String ARMediaManagerObjectMediaType = "ARMediaManagerObjectMediaType";
    private static final String ARMediaManagerObjectName = "ARMediaManagerObjectName";
    private static final String ARMediaManagerObjectProduct = "ARMediaManagerObjectProduct";
    private static final String ARMediaManagerObjectProductId = "ARMediaManagerObjectProductId";
    private static final String ARMediaManagerObjectRunDate = "ARMediaManagerObjectRunDate";
    private static final String ARMediaManagerObjectSize = "ARMediaManagerObjectSize";
    public static final String ARMediaManagerPVATMediaDateKey = "media_date";
    public static final String ARMediaManagerPVATProductIdKey = "product_id";
    public static final String ARMediaManagerPVATRunDateKey = "run_date";
    public static final String ARMediaManagerPVATuuidKey = "uuid";
    private static final String ARMediaManagerProjectAssetURLKey = "ARMediaManagerProjectAssetURLKey";
    private static final String ARMediaManagerProjectProductName = "ARMediaManagerProjectProductName";
    public static final String DOWNLOADING_PREFIX = "downloading_";
    public static final String LOCAL_MEDIA_MASS_STORAGE_PATH;
    private static ARMediaManager instance = null;
    private static final String kARMediaManagerKey = "kARMediaManagerKey";
    private static final String kARMediaManagerProjectDicCount = "kARMediaManagerProjectDicCount";
    private String TAG = ARMediaManager.class.getSimpleName();
    private ContentResolver contentResolver;
    private Context context;
    private Activity currentActivity;
    private boolean isInit = false;
    private boolean isUpdate = false;
    private HashMap<String, Object> projectsDictionary;
    private int valueKARMediaManagerKey;

    static {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath());
        stringBuilder.append(File.separator);
        LOCAL_MEDIA_MASS_STORAGE_PATH = stringBuilder.toString();
    }

    public static ARMediaManager getInstance(Context context) {
        if (instance == null) {
            instance = new ARMediaManager(context);
        }
        return instance;
    }

    private ARMediaManager(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
        this.isInit = false;
        this.isUpdate = false;
        this.projectsDictionary = new HashMap();
    }

    public ARMEDIA_ERROR_ENUM initWithProjectIDs(ArrayList<String> projectIDs) {
        ARMEDIA_ERROR_ENUM returnVal = ARMEDIA_ERROR_ENUM.ARMEDIA_OK;
        if (this.isInit) {
            return returnVal;
        }
        Iterator it = projectIDs.iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            if (!this.projectsDictionary.containsKey(key)) {
                this.projectsDictionary.put(key, new HashMap());
            }
        }
        Bundle notificationBundle = new Bundle();
        notificationBundle.putString(ARMediaManagerNotificationDictionaryIsInitKey, "");
        Intent intentDicChanged = new Intent(ARMediaManagerNotificationDictionary);
        intentDicChanged.putExtras(notificationBundle);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intentDicChanged);
        this.isInit = true;
        return ARMEDIA_ERROR_ENUM.ARMEDIA_OK;
    }

    public HashMap<String, Object> retrieveProjectsDictionary(String project) {
        HashMap<String, Object> retProjectictionary = new HashMap();
        if (project == null) {
            for (String key : this.projectsDictionary.keySet()) {
                retProjectictionary.put(key, this.projectsDictionary.get(key));
            }
        } else {
            retProjectictionary.put(project, this.projectsDictionary.get(project));
        }
        return this.projectsDictionary;
    }

    public ARMEDIA_ERROR_ENUM update() {
        String key;
        Throwable th;
        ARSALPrint.m536v(this.TAG, "update MediaManager");
        if (!this.isInit) {
            return ARMEDIA_ERROR_ENUM.ARMEDIA_ERROR_MANAGER_NOT_INITIALIZED;
        }
        int i = 0;
        r1.isUpdate = false;
        int totalMediaInFoldersCount = 0;
        for (String key2 : r1.projectsDictionary.keySet()) {
            String directoryName = new StringBuilder();
            directoryName.append(LOCAL_MEDIA_MASS_STORAGE_PATH);
            directoryName.append(key2);
            File directory = new File(directoryName.toString());
            if (directory.listFiles() != null) {
                totalMediaInFoldersCount += directory.listFiles().length;
            }
        }
        Iterator it = r1.projectsDictionary.keySet().iterator();
        while (it.hasNext()) {
            int i2;
            String str;
            key2 = (String) it.next();
            String[] requestedColumnsImg = new String[]{"title", "_data"};
            String[] requestedColumnsVideo = new String[]{"title", "_data"};
            directoryName = "bucket_display_name =?";
            String[] selectionArgs = new String[]{key2.toString()};
            Cursor cursorVideoExterne = r1.context.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, requestedColumnsVideo, directoryName, selectionArgs, null);
            Cursor cursorPhotoExterne = r1.context.getContentResolver().query(Images.Media.EXTERNAL_CONTENT_URI, requestedColumnsImg, directoryName, selectionArgs, null);
            String str2 = directoryName;
            String[] strArr = selectionArgs;
            Cursor cursorVideoInterne = r1.context.getContentResolver().query(Media.INTERNAL_CONTENT_URI, requestedColumnsVideo, str2, strArr, null);
            Cursor cursorPhotoInterne = r1.context.getContentResolver().query(Images.Media.INTERNAL_CONTENT_URI, requestedColumnsImg, str2, strArr, null);
            MergeCursor cursorPhoto = new MergeCursor(new Cursor[]{cursorPhotoExterne, cursorPhotoInterne});
            MergeCursor cursorVideo = new MergeCursor(new Cursor[]{cursorVideoExterne, cursorVideoInterne});
            int currentCount = 0;
            arMediaManagerNotificationUpdating((((double) null) / ((double) totalMediaInFoldersCount)) * 100.0d);
            if (cursorPhoto.moveToFirst()) {
                while (true) {
                    try {
                        directoryName = cursorPhoto.getString(cursorPhoto.getColumnIndex("_data"));
                        String mediaFilePath = directoryName.substring(Environment.getExternalStorageDirectory().toString().length());
                        if (directoryName.endsWith(ARMEDIA_MANAGER_JPG)) {
                            addPhotoToProjectDictionary(directoryName, mediaFilePath);
                        }
                        currentCount++;
                        arMediaManagerNotificationUpdating((((double) currentCount) / ((double) totalMediaInFoldersCount)) * 100.0d);
                        if (!cursorPhoto.moveToNext()) {
                            break;
                        }
                    } catch (Throwable th2) {
                        i2 = totalMediaInFoldersCount;
                        str = key2;
                        th = th2;
                    }
                }
            } else {
                try {
                    directoryName = r1.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("No Photo files for album: ");
                    stringBuilder.append(key2);
                    ARSALPrint.m536v(directoryName, stringBuilder.toString());
                } catch (Throwable th22) {
                    th = th22;
                    i2 = totalMediaInFoldersCount;
                    str = key2;
                }
            }
            if (cursorPhoto != null) {
                cursorPhoto.close();
            }
            if (cursorVideo.moveToFirst()) {
                do {
                    directoryName = cursorVideo.getString(cursorVideo.getColumnIndex("_data"));
                    mediaFilePath = cursorVideo.getString(cursorVideo.getColumnIndex("title"));
                    if (!directoryName.contains(DOWNLOADING_PREFIX)) {
                        try {
                            if (isValidVideoFile(directoryName)) {
                                String str3 = r1.TAG;
                                StringBuilder stringBuilder2 = new StringBuilder();
                                stringBuilder2.append("adding video:");
                                stringBuilder2.append(directoryName);
                                ARSALPrint.m536v(str3, stringBuilder2.toString());
                                addARMediaVideoToProjectDictionary(directoryName);
                            }
                        } catch (Throwable th222) {
                            th = th222;
                            i2 = totalMediaInFoldersCount;
                            str = key2;
                        }
                    }
                    currentCount++;
                    try {
                        arMediaManagerNotificationUpdating((((double) currentCount) / ((double) totalMediaInFoldersCount)) * 100.0d);
                    } catch (Throwable th2222) {
                        i2 = totalMediaInFoldersCount;
                        str = key2;
                        th = th2222;
                    }
                } while (cursorVideo.moveToNext());
            } else {
                directoryName = r1.TAG;
                stringBuilder = new StringBuilder();
                stringBuilder.append("No Video for :");
                stringBuilder.append(key2);
                ARSALPrint.m530d(directoryName, stringBuilder.toString());
            }
            if (cursorVideo != null) {
                cursorVideo.close();
            }
            directoryName = new StringBuilder();
            directoryName.append(LOCAL_MEDIA_MASS_STORAGE_PATH);
            directoryName.append(key2);
            File[] fList = new File(directoryName.toString()).listFiles();
            if (fList != null) {
                ARSALPrint.m536v(r1.TAG, "Fetching files in DCIM folder");
                int length = fList.length;
                int i3 = 0;
                while (i3 < length) {
                    Iterator it2;
                    File file = fList[i3];
                    i2 = totalMediaInFoldersCount;
                    String filePath = file.getAbsolutePath();
                    if (filePath.startsWith(DOWNLOADING_PREFIX)) {
                        it2 = it;
                        str = key2;
                    } else if (isValidVideoFile(filePath)) {
                        String str4 = r1.TAG;
                        it2 = it;
                        StringBuilder stringBuilder3 = new StringBuilder();
                        str = key2;
                        stringBuilder3.append("adding video:");
                        stringBuilder3.append(filePath);
                        ARSALPrint.m536v(str4, stringBuilder3.toString());
                        addARMediaVideoToProjectDictionary(filePath);
                    } else {
                        it2 = it;
                        str = key2;
                        if (filePath.endsWith(ARMEDIA_MANAGER_JPG)) {
                            addPhotoToProjectDictionary(filePath, null);
                        }
                    }
                    i3++;
                    totalMediaInFoldersCount = i2;
                    it = it2;
                    key2 = str;
                }
            }
            totalMediaInFoldersCount = totalMediaInFoldersCount;
            it = it;
            i = 0;
        }
        Bundle notificationBundle = new Bundle();
        notificationBundle.putBoolean(ARMediaManagerNotificationDictionaryUpdatedKey, true);
        Intent intentDicChanged = new Intent(ARMediaManagerNotificationDictionary);
        intentDicChanged.putExtras(notificationBundle);
        LocalBroadcastManager.getInstance(r1.context).sendBroadcast(intentDicChanged);
        r1.isUpdate = true;
        return ARMEDIA_ERROR_ENUM.ARMEDIA_OK;
        if (cursorPhoto != null) {
            cursorPhoto.close();
        }
        throw th;
        if (cursorVideo != null) {
            cursorVideo.close();
        }
        throw th;
    }

    public boolean addMedia(File mediaFile) {
        if (!this.isUpdate) {
            return false;
        }
        this.isUpdate = false;
        return saveMedia(mediaFile);
    }

    public boolean isUpdate() {
        return this.isUpdate;
    }

    private boolean saveMedia(File file) {
        StringBuilder stringBuilder;
        boolean added = false;
        boolean toAdd = false;
        ARMediaObject mediaObject = null;
        String productName = null;
        String exifProductID = null;
        String filename = file.getName();
        String str = this.TAG;
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Save media:");
        stringBuilder2.append(file.getPath());
        ARSALPrint.m536v(str, stringBuilder2.toString());
        String description;
        String str2;
        StringBuilder stringBuilder3;
        int productID;
        if (filename.endsWith(ARMEDIA_MANAGER_JPG)) {
            try {
                description = new Exif2Interface(file.getPath()).getAttribute(Tag.IMAGE_DESCRIPTION);
                str2 = this.TAG;
                stringBuilder3 = new StringBuilder();
                stringBuilder3.append("image description:");
                stringBuilder3.append(description);
                ARSALPrint.m536v(str2, stringBuilder3.toString());
                if (description != null) {
                    JSONObject jsonReader = new JSONObject(description);
                    if (jsonReader.has(ARMediaManagerPVATProductIdKey)) {
                        exifProductID = jsonReader.getString(ARMediaManagerPVATProductIdKey);
                    }
                    productName = ARDiscoveryService.getProductName(ARDiscoveryService.getProductFromProductID((int) Long.parseLong(exifProductID, 16)));
                    String str3 = this.TAG;
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append("new image product=");
                    stringBuilder4.append(productName);
                    ARSALPrint.m536v(str3, stringBuilder4.toString());
                    if (this.projectsDictionary.keySet().contains(productName)) {
                        productID = Integer.parseInt(jsonReader.getString(ARMediaManagerPVATProductIdKey), 16);
                        stringBuilder3 = new StringBuilder();
                        stringBuilder3.append(File.separator);
                        stringBuilder3.append(Environment.DIRECTORY_DCIM);
                        stringBuilder3.append(File.separator);
                        stringBuilder3.append(productName);
                        stringBuilder3.append(File.separator);
                        stringBuilder3.append(filename);
                        mediaObject = createMediaObjectFromJson(stringBuilder3.toString(), jsonReader);
                        if (mediaObject != null) {
                            str3 = this.TAG;
                            stringBuilder4 = new StringBuilder();
                            stringBuilder4.append("new image path:");
                            stringBuilder4.append(mediaObject.getFilePath());
                            ARSALPrint.m536v(str3, stringBuilder4.toString());
                            mediaObject.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_PHOTO;
                        }
                        toAdd = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else if (isValidVideoFile(filename)) {
            str = ARMediaVideoAtoms.getPvat(file.getAbsolutePath());
            description = this.TAG;
            stringBuilder = new StringBuilder();
            stringBuilder.append("video description:");
            stringBuilder.append(str);
            ARSALPrint.m536v(description, stringBuilder.toString());
            if (str != null) {
                try {
                    JSONObject jsonReader2 = new JSONObject(str);
                    if (jsonReader2.has(ARMediaManagerPVATProductIdKey)) {
                        exifProductID = jsonReader2.getString(ARMediaManagerPVATProductIdKey);
                    }
                    productName = ARDiscoveryService.getProductName(ARDiscoveryService.getProductFromProductID((int) Long.parseLong(exifProductID, 16)));
                    str2 = this.TAG;
                    stringBuilder3 = new StringBuilder();
                    stringBuilder3.append("new video product=");
                    stringBuilder3.append(productName);
                    ARSALPrint.m536v(str2, stringBuilder3.toString());
                    if (this.projectsDictionary.keySet().contains(productName)) {
                        productID = Integer.parseInt(jsonReader2.getString(ARMediaManagerPVATProductIdKey), 16);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(File.separator);
                        stringBuilder.append(Environment.DIRECTORY_DCIM);
                        stringBuilder.append(File.separator);
                        stringBuilder.append(productName);
                        stringBuilder.append(File.separator);
                        stringBuilder.append(filename);
                        mediaObject = createMediaObjectFromJson(stringBuilder.toString(), jsonReader2);
                        if (mediaObject != null) {
                            str2 = this.TAG;
                            stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("new video path:");
                            stringBuilder3.append(mediaObject.getFilePath());
                            ARSALPrint.m536v(str2, stringBuilder3.toString());
                            mediaObject.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_VIDEO;
                        }
                        toAdd = true;
                    }
                } catch (JSONException e3) {
                    e3.printStackTrace();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
        } else {
            added = false;
        }
        if (toAdd) {
            str = new StringBuilder();
            str.append(LOCAL_MEDIA_MASS_STORAGE_PATH);
            str.append(productName);
            str = str.toString();
            File directoryFolder = new File(str);
            if (!(directoryFolder.exists() && directoryFolder.isDirectory())) {
                directoryFolder.mkdir();
            }
            stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append(File.separator);
            stringBuilder.append(filename);
            File destination = new File(stringBuilder.toString());
            if (mediaObject != null) {
                this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(destination)));
                ((HashMap) this.projectsDictionary.get(productName)).put(destination.getPath().substring(Environment.getExternalStorageDirectory().toString().length()), mediaObject);
                arMediaManagerNotificationMediaAdded(destination.getPath().substring(Environment.getExternalStorageDirectory().toString().length()));
                added = true;
            }
        }
        this.isUpdate = true;
        return added;
    }

    private void addPhotoToProjectDictionary(@NonNull String mediaFileAbsolutPath, @Nullable String mediaFilePath) {
        IOException e = null;
        if (mediaFilePath == null) {
            try {
                mediaFilePath = mediaFileAbsolutPath.substring(Environment.getExternalStorageDirectory().toString().length());
            } catch (IOException e2) {
                e2.printStackTrace();
            } catch (IOException e22) {
                e22.printStackTrace();
                return;
            }
        }
        String description = new Exif2Interface(mediaFileAbsolutPath).getAttribute(Tag.IMAGE_DESCRIPTION);
        String str = this.TAG;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("image:");
        stringBuilder.append(mediaFileAbsolutPath);
        stringBuilder.append(", desc=");
        stringBuilder.append(description);
        ARSALPrint.m536v(str, stringBuilder.toString());
        if (description != null) {
            JSONObject jsonReader = new JSONObject(description);
            if (jsonReader.has(ARMediaManagerPVATProductIdKey)) {
                e22 = jsonReader.getString(ARMediaManagerPVATProductIdKey);
            }
            String productName = ARDiscoveryService.getProductName(ARDiscoveryService.getProductFromProductID((int) Long.parseLong(e22, 16)));
            String str2 = this.TAG;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("image product=");
            stringBuilder2.append(productName);
            ARSALPrint.m536v(str2, stringBuilder2.toString());
            if (this.projectsDictionary.containsKey(productName)) {
                HashMap<String, Object> hashMap = (HashMap) this.projectsDictionary.get(productName);
                if (!hashMap.containsKey(mediaFilePath)) {
                    ARMediaObject mediaObject = createMediaObjectFromJson(mediaFilePath, jsonReader);
                    if (mediaObject != null) {
                        mediaObject.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_PHOTO;
                        String str3 = this.TAG;
                        StringBuilder stringBuilder3 = new StringBuilder();
                        stringBuilder3.append("add photo:");
                        stringBuilder3.append(mediaFilePath);
                        ARSALPrint.m536v(str3, stringBuilder3.toString());
                        hashMap.put(mediaFilePath, mediaObject);
                    }
                }
            }
        }
    }

    private void addARMediaVideoToProjectDictionary(String mediaFileAbsolutPath) {
        String mediaFilePath = mediaFileAbsolutPath.substring(Environment.getExternalStorageDirectory().toString().length());
        String description = ARMediaVideoAtoms.getPvat(mediaFileAbsolutPath);
        if (description != null) {
            String atomProductID = null;
            try {
                JSONObject jsonReader = new JSONObject(description);
                if (jsonReader.has(ARMediaManagerPVATProductIdKey)) {
                    atomProductID = jsonReader.getString(ARMediaManagerPVATProductIdKey);
                }
                String productName = ARDiscoveryService.getProductName(ARDiscoveryService.getProductFromProductID((int) Long.parseLong(atomProductID, 16)));
                if (this.projectsDictionary.keySet().contains(productName)) {
                    HashMap<String, Object> hashMap = (HashMap) this.projectsDictionary.get(jsonReader.getString(ARMediaManagerPVATProductIdKey));
                    if (hashMap == null || !hashMap.containsKey(mediaFilePath)) {
                        ARMediaObject mediaObject = createMediaObjectFromJson(mediaFilePath, jsonReader);
                        if (mediaObject != null) {
                            mediaObject.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_VIDEO;
                            ((HashMap) this.projectsDictionary.get(productName)).put(mediaFilePath, mediaObject);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private ARMediaObject createMediaObjectFromJson(String mediaPath, JSONObject jsonReader) {
        ARMediaObject mediaObject = null;
        if (jsonReader != null) {
            mediaObject = new ARMediaObject();
            try {
                mediaObject.productId = jsonReader.getString(ARMediaManagerPVATProductIdKey);
                mediaObject.product = ARDiscoveryService.getProductFromProductID((int) Long.parseLong(mediaObject.productId, 16));
                if (jsonReader.has(ARMediaManagerPVATMediaDateKey)) {
                    mediaObject.date = jsonReader.getString(ARMediaManagerPVATMediaDateKey);
                }
                if (jsonReader.has(ARMediaManagerPVATRunDateKey)) {
                    mediaObject.runDate = jsonReader.getString(ARMediaManagerPVATRunDateKey);
                }
                if (jsonReader.has(ARMediaManagerPVATuuidKey)) {
                    mediaObject.uuid = jsonReader.getString(ARMediaManagerPVATuuidKey);
                }
                mediaObject.filePath = mediaPath;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mediaObject;
    }

    private void arMediaManagerNotificationMediaAdded(String mediaPath) {
        Bundle notificationBundle = new Bundle();
        notificationBundle.putString(ARMediaManagerNotificationDictionaryMediaAddedKey, mediaPath);
        Intent intentDicChanged = new Intent(ARMediaManagerNotificationDictionary);
        intentDicChanged.putExtras(notificationBundle);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intentDicChanged);
    }

    private void arMediaManagerNotificationUpdating(double percent) {
        Bundle notificationBundle = new Bundle();
        notificationBundle.putDouble(ARMediaManagerNotificationDictionaryUpdatingKey, percent);
        Intent intentDicChanged = new Intent(ARMediaManagerNotificationDictionary);
        intentDicChanged.putExtras(notificationBundle);
        LocalBroadcastManager.getInstance(this.context).sendBroadcast(intentDicChanged);
    }

    private boolean isValidVideoFile(String filename) {
        if (!filename.endsWith(ARMEDIA_MANAGER_MP4)) {
            if (!filename.endsWith(ARMEDIA_MANAGER_MOV)) {
                return false;
            }
        }
        return true;
    }
}
