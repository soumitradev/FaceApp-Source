package org.catrobat.catroid.common;

import java.io.Serializable;

public class NfcTagData implements Cloneable, Comparable<NfcTagData>, Nameable, Serializable {
    private static final long serialVersionUID = 1;
    private String name;
    private String uid;

    public NfcTagData clone() {
        NfcTagData cloneNfcTagData = new NfcTagData();
        cloneNfcTagData.name = this.name;
        cloneNfcTagData.uid = this.uid;
        return cloneNfcTagData;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNfcTagUid(String uid) {
        this.uid = uid;
    }

    public String getNfcTagUid() {
        return this.uid;
    }

    public int compareTo(NfcTagData nfcTagData) {
        return this.uid.compareTo(nfcTagData.uid);
    }

    public String toString() {
        return this.name;
    }
}
