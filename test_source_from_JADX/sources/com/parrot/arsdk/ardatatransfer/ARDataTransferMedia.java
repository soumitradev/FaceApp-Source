package com.parrot.arsdk.ardatatransfer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import java.util.Arrays;

public class ARDataTransferMedia implements Parcelable {
    public static final Creator<ARDataTransferMedia> CREATOR = new C15691();
    private static final int HASH_FIELD = 31;
    private static final int HASH_START = 17;
    private static final String TAG = "ARDataTransferMedia";
    private String date;
    private String filePath;
    private String name;
    private ARDISCOVERY_PRODUCT_ENUM product;
    private String remotePath;
    private String remoteThumb;
    private float size;
    private byte[] thumbnail;
    private String uuid;

    /* renamed from: com.parrot.arsdk.ardatatransfer.ARDataTransferMedia$1 */
    static class C15691 implements Creator<ARDataTransferMedia> {
        C15691() {
        }

        public ARDataTransferMedia createFromParcel(Parcel source) {
            return new ARDataTransferMedia(source);
        }

        public ARDataTransferMedia[] newArray(int size) {
            return new ARDataTransferMedia[size];
        }
    }

    protected ARDataTransferMedia(int productValue, String name, String filePath, String date, String uuid, String remotePath, String remoteThumb, float size, byte[] thumbnail) {
        this.product = null;
        this.name = null;
        this.filePath = null;
        this.date = null;
        this.uuid = null;
        this.remotePath = null;
        this.remoteThumb = null;
        this.size = 0.0f;
        this.thumbnail = null;
        this.product = ARDISCOVERY_PRODUCT_ENUM.getFromValue(productValue);
        this.name = name;
        this.filePath = filePath;
        this.date = date;
        this.uuid = uuid;
        this.remotePath = remotePath;
        this.remoteThumb = remoteThumb;
        this.size = size;
        this.thumbnail = thumbnail;
    }

    private ARDataTransferMedia(Parcel source) {
        this.product = null;
        this.name = null;
        this.filePath = null;
        this.date = null;
        this.uuid = null;
        this.remotePath = null;
        this.remoteThumb = null;
        this.size = 0.0f;
        this.thumbnail = null;
        this.product = ARDISCOVERY_PRODUCT_ENUM.getFromValue(source.readInt());
        this.name = readString(source);
        this.filePath = readString(source);
        this.date = readString(source);
        this.uuid = readString(source);
        this.remotePath = readString(source);
        this.remoteThumb = readString(source);
        this.size = source.readFloat();
        int thumbnailSize = source.readInt();
        if (thumbnailSize > 0) {
            this.thumbnail = new byte[thumbnailSize];
            source.readByteArray(this.thumbnail);
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.product.getValue());
        writeString(dest, this.name);
        writeString(dest, this.filePath);
        writeString(dest, this.date);
        writeString(dest, this.uuid);
        writeString(dest, this.remotePath);
        writeString(dest, this.remoteThumb);
        dest.writeFloat(this.size);
        int thumbnailSize = this.thumbnail != null ? this.thumbnail.length : 0;
        dest.writeInt(thumbnailSize);
        if (thumbnailSize > 0) {
            dest.writeByteArray(this.thumbnail);
        }
    }

    public int describeContents() {
        return 0;
    }

    private static String readString(Parcel source) {
        if (source.readByte() > (byte) 0) {
            return source.readString();
        }
        return null;
    }

    private static void writeString(Parcel dest, String value) {
        dest.writeByte(value != null ? (byte) 1 : (byte) 0);
        if (value != null) {
            dest.writeString(value);
        }
    }

    public ARDISCOVERY_PRODUCT_ENUM getProduct() {
        return this.product;
    }

    public int getProductValue() {
        return this.product.getValue();
    }

    public String getName() {
        return this.name;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public String getDate() {
        return this.date;
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getRemotePath() {
        return this.remotePath;
    }

    public String getRemoteThumb() {
        return this.remoteThumb;
    }

    public float getSize() {
        return this.size;
    }

    public byte[] getThumbnail() {
        return this.thumbnail;
    }

    public void setThumbail(byte[] rawData) {
        this.thumbnail = Arrays.copyOf(rawData, rawData.length);
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof ARDataTransferMedia)) {
            return false;
        }
        ARDataTransferMedia otherMedia = (ARDataTransferMedia) other;
        if (otherMedia.getProductValue() != getProductValue()) {
            return false;
        }
        if ((this.name != null || otherMedia.getName() != null) && (this.name == null || !this.name.equals(otherMedia.getName()))) {
            return false;
        }
        if ((this.filePath != null || otherMedia.getFilePath() != null) && (this.filePath == null || !this.filePath.equals(otherMedia.getFilePath()))) {
            return false;
        }
        if ((this.remotePath != null || otherMedia.getRemotePath() != null) && (this.remotePath == null || !this.remotePath.equals(otherMedia.getRemotePath()))) {
            return false;
        }
        if ((this.remoteThumb != null || otherMedia.getRemoteThumb() != null) && (this.remoteThumb == null || !this.remoteThumb.equals(otherMedia.getRemoteThumb()))) {
            return false;
        }
        if ((this.date != null || otherMedia.getDate() != null) && (this.date == null || !this.date.equals(otherMedia.getDate()))) {
            return false;
        }
        if ((this.uuid != null || otherMedia.getUUID() != null) && (this.uuid == null || !this.uuid.equals(otherMedia.getUUID()))) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = 0;
        int value = ((((((((((((17 * 31) + this.product.getValue()) * 31) + (this.name == null ? 0 : this.name.hashCode())) * 31) + (this.filePath == null ? 0 : this.filePath.hashCode())) * 31) + (this.date == null ? 0 : this.date.hashCode())) * 31) + (this.uuid == null ? 0 : this.uuid.hashCode())) * 31) + (this.remotePath == null ? 0 : this.remotePath.hashCode())) * 31;
        if (this.remoteThumb != null) {
            i = this.remoteThumb.hashCode();
        }
        return value + i;
    }
}
