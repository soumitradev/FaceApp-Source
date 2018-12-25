package org.catrobat.catroid.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.images.WebImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ScratchProgramData implements Serializable, Parcelable {
    public static final Creator<ScratchProgramData> CREATOR = new C17591();
    private static final long serialVersionUID = 1;
    private Date createdDate;
    private int favorites;
    private long id;
    private WebImage image;
    private String instructions;
    private int loves;
    private Date modifiedDate;
    private String notesAndCredits;
    private String owner;
    private List<ScratchProgramData> remixes;
    private Date sharedDate;
    private List<String> tags;
    private String title;
    private int views;
    private ScratchVisibilityState visibilityState;

    /* renamed from: org.catrobat.catroid.common.ScratchProgramData$1 */
    static class C17591 implements Creator<ScratchProgramData> {
        C17591() {
        }

        public ScratchProgramData createFromParcel(Parcel source) {
            return new ScratchProgramData(source);
        }

        public ScratchProgramData[] newArray(int size) {
            return new ScratchProgramData[size];
        }
    }

    public ScratchProgramData(long id, String title, String owner, WebImage image) {
        this.remixes = new ArrayList();
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.image = image;
        this.instructions = null;
        this.notesAndCredits = null;
        this.views = 0;
        this.favorites = 0;
        this.loves = 0;
        this.createdDate = null;
        this.modifiedDate = null;
        this.sharedDate = null;
        this.tags = new ArrayList();
        this.visibilityState = null;
        this.remixes = new ArrayList();
    }

    private ScratchProgramData(Parcel in) {
        this.remixes = new ArrayList();
        this.id = in.readLong();
        this.title = in.readString();
        this.owner = in.readString();
        this.image = (WebImage) in.readParcelable(WebImage.class.getClassLoader());
        this.instructions = in.readString();
        this.notesAndCredits = in.readString();
        this.views = in.readInt();
        this.favorites = in.readInt();
        this.loves = in.readInt();
        long createdDateTime = in.readLong();
        Date date = null;
        this.createdDate = createdDateTime == 0 ? null : new Date(createdDateTime);
        long modifiedDateTime = in.readLong();
        this.modifiedDate = modifiedDateTime == 0 ? null : new Date(modifiedDateTime);
        long sharedDateTime = in.readLong();
        if (sharedDateTime != 0) {
            date = new Date(sharedDateTime);
        }
        this.sharedDate = date;
        this.tags = new ArrayList();
        in.readStringList(this.tags);
        this.visibilityState = (ScratchVisibilityState) in.readParcelable(ScratchVisibilityState.class.getClassLoader());
        this.remixes = new ArrayList();
        in.readTypedList(this.remixes, CREATOR);
    }

    public long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOwner() {
        return this.owner;
    }

    public WebImage getImage() {
        return this.image;
    }

    public void setImage(WebImage image) {
        this.image = image;
    }

    public String getInstructions() {
        return this.instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getNotesAndCredits() {
        return this.notesAndCredits;
    }

    public void setNotesAndCredits(String notesAndCredits) {
        this.notesAndCredits = notesAndCredits;
    }

    public void addRemixProgram(ScratchProgramData remixProgramData) {
        this.remixes.add(remixProgramData);
    }

    public int getViews() {
        return this.views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getFavorites() {
        return this.favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getLoves() {
        return this.loves;
    }

    public void setLoves(int loves) {
        this.loves = loves;
    }

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSharedDate() {
        return this.sharedDate;
    }

    public void setSharedDate(Date sharedDate) {
        this.sharedDate = sharedDate;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void addTag(String tagName) {
        this.tags.add(tagName);
    }

    public ScratchVisibilityState getVisibilityState() {
        return this.visibilityState;
    }

    public void setVisibilityState(ScratchVisibilityState visibilityState) {
        this.visibilityState = visibilityState;
    }

    public List<ScratchProgramData> getRemixes() {
        return this.remixes;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.owner);
        dest.writeParcelable(this.image, flags);
        dest.writeString(this.instructions);
        dest.writeString(this.notesAndCredits);
        dest.writeInt(this.views);
        dest.writeInt(this.favorites);
        dest.writeInt(this.loves);
        long j = 0;
        dest.writeLong(this.createdDate != null ? this.createdDate.getTime() : 0);
        dest.writeLong(this.modifiedDate != null ? this.modifiedDate.getTime() : 0);
        if (this.sharedDate != null) {
            j = this.sharedDate.getTime();
        }
        dest.writeLong(j);
        dest.writeStringList(this.tags);
        dest.writeParcelable(this.visibilityState, flags);
        dest.writeTypedList(this.remixes);
    }
}
