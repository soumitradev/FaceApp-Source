package org.catrobat.catroid.scratchconverter;

public class ClientException extends Exception {
    public ClientException(String detailMessage) {
        super(detailMessage);
    }

    public ClientException(Throwable throwable) {
        super(throwable);
    }
}
