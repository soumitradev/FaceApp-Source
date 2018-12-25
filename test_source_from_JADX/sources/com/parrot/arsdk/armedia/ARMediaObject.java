package com.parrot.arsdk.armedia;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.parrot.arsdk.ardatatransfer.ARDataTransferMedia;
import com.parrot.arsdk.ardiscovery.ARDISCOVERY_PRODUCT_ENUM;
import com.parrot.arsdk.ardiscovery.ARDiscoveryService;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ARMediaObject implements Cloneable, Parcelable, Serializable, Comparable<ARMediaObject> {
    public static final Creator<ARMediaObject> CREATOR = new C15981();
    private static final String TAG = ARMediaObject.class.getSimpleName();
    public String date = null;
    private SimpleDateFormat dateFormater;
    public String filePath = null;
    public ARDataTransferMedia media = null;
    public MEDIA_TYPE_ENUM mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_MAX;
    public String name = null;
    public ARDISCOVERY_PRODUCT_ENUM product = ARDISCOVERY_PRODUCT_ENUM.ARDISCOVERY_PRODUCT_MAX;
    public String productId = null;
    public String runDate = null;
    public float size = 0.0f;
    public Drawable thumbnail = null;
    public String uuid;

    /* renamed from: com.parrot.arsdk.armedia.ARMediaObject$1 */
    static class C15981 implements Creator<ARMediaObject> {
        C15981() {
        }

        public ARMediaObject createFromParcel(Parcel source) {
            ARMediaObject object = new ARMediaObject();
            object.runDate = source.readString();
            object.product = ARDISCOVERY_PRODUCT_ENUM.values()[source.readInt()];
            object.name = source.readString();
            object.date = source.readString();
            object.filePath = source.readString();
            object.size = source.readFloat();
            object.thumbnail = null;
            object.mediaType = MEDIA_TYPE_ENUM.values()[source.readInt()];
            object.uuid = source.readString();
            object.media = (ARDataTransferMedia) source.readParcelable(null);
            return object;
        }

        public ARMediaObject[] newArray(int size) {
            return new ARMediaObject[size];
        }
    }

    public void updateDataTransferMedia(Resources resources, ARDataTransferMedia media) {
        if (media != null) {
            this.media = media;
            this.name = media.getName();
            this.filePath = media.getFilePath();
            this.date = media.getDate();
            this.size = media.getSize();
            this.uuid = media.getUUID();
            this.product = media.getProduct();
            this.productId = String.format("%04x", new Object[]{Integer.valueOf(ARDiscoveryService.getProductID(this.product))});
            Bitmap thumbnailBmp = BitmapFactory.decodeByteArray(media.getThumbnail(), 0, media.getThumbnail().length);
            if (thumbnailBmp != null) {
                this.thumbnail = new BitmapDrawable(resources, Exif2Interface.handleOrientation(thumbnailBmp, media.getThumbnail()));
            }
        }
        if (this.name != null) {
            String extension = this.name.substring(this.name.lastIndexOf(".") + 1, this.name.length());
            if (extension.equals("jpg")) {
                this.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_PHOTO;
            } else if (extension.equals("mp4")) {
                this.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_VIDEO;
            } else if (extension.equals("mov")) {
                this.mediaType = MEDIA_TYPE_ENUM.MEDIA_TYPE_VIDEO;
            }
        }
    }

    public void updateThumbnailWithDataTransferMedia(Resources resources, ARDataTransferMedia media) {
        this.media = media;
        Bitmap thumbnailBmp = BitmapFactory.decodeByteArray(media.getThumbnail(), 0, media.getThumbnail().length);
        if (thumbnailBmp != null) {
            this.thumbnail = new BitmapDrawable(resources, Exif2Interface.handleOrientation(thumbnailBmp, media.getThumbnail()));
        }
    }

    public void updateThumbnailWithUrl(AssetManager assetManager, String assetUrl) {
        try {
            this.thumbnail = Drawable.createFromStream(assetManager.open(assetUrl), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRunDate() {
        return this.runDate;
    }

    public ARDISCOVERY_PRODUCT_ENUM getProduct() {
        return this.product;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getName() {
        return this.name;
    }

    public String getDate() {
        return this.date;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public float getSize() {
        return this.size;
    }

    public Drawable getThumbnail() {
        return this.thumbnail;
    }

    public MEDIA_TYPE_ENUM getMediaType() {
        return this.mediaType;
    }

    public String getUUID() {
        return this.uuid;
    }

    public Object clone() {
        ARMediaObject model = null;
        try {
            model = (ARMediaObject) super.clone();
            model.runDate = this.runDate;
            model.product = this.product;
            model.productId = this.productId;
            model.name = this.name;
            model.date = this.date;
            model.filePath = this.filePath;
            model.size = this.size;
            model.thumbnail = this.thumbnail;
            model.mediaType = this.mediaType;
            model.uuid = this.uuid;
            model.media = this.media;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return model;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.runDate);
        dest.writeInt(this.product.ordinal());
        dest.writeString(this.name);
        dest.writeString(this.date);
        dest.writeString(this.filePath);
        dest.writeFloat(this.size);
        dest.writeInt(this.mediaType.ordinal());
        dest.writeString(this.uuid);
        dest.writeParcelable(this.media, flags);
    }

    public int compareTo(ARMediaObject obj) {
        if (this.dateFormater == null) {
            this.dateFormater = new SimpleDateFormat("yyyy-MM-dd'T'HHmmss");
        }
        int retVal = 0;
        try {
            Date dateObj1 = this.dateFormater.parse(obj.date);
            Date dateObj2 = this.dateFormater.parse(this.date);
            if (dateObj1.after(dateObj2)) {
                retVal = -1;
            } else if (dateObj1.before(dateObj2)) {
                retVal = 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return retVal;
    }

    private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
        this.runDate = aInputStream.readUTF();
        this.product = ARDISCOVERY_PRODUCT_ENUM.values()[aInputStream.readInt()];
        this.productId = aInputStream.readUTF();
        this.name = aInputStream.readUTF();
        this.date = aInputStream.readUTF();
        this.filePath = aInputStream.readUTF();
        this.uuid = aInputStream.readUTF();
        this.size = aInputStream.readFloat();
        this.thumbnail = null;
        this.mediaType = MEDIA_TYPE_ENUM.values()[aInputStream.readInt()];
    }

    private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
        if (this.runDate != null) {
            aOutputStream.writeUTF(this.runDate);
        } else {
            aOutputStream.writeUTF("");
        }
        aOutputStream.writeInt(this.product.ordinal());
        if (this.productId != null) {
            aOutputStream.writeUTF(this.productId);
        } else {
            aOutputStream.writeUTF("");
        }
        if (this.name != null) {
            aOutputStream.writeUTF(this.name);
        } else {
            aOutputStream.writeUTF("");
        }
        if (this.date != null) {
            aOutputStream.writeUTF(this.date);
        } else {
            aOutputStream.writeUTF("");
        }
        if (this.filePath != null) {
            aOutputStream.writeUTF(this.filePath);
        } else {
            aOutputStream.writeUTF("");
        }
        if (this.uuid != null) {
            aOutputStream.writeUTF(this.uuid);
        } else {
            aOutputStream.writeUTF("");
        }
        aOutputStream.writeFloat(this.size);
        aOutputStream.writeInt(this.mediaType.ordinal());
    }
}
