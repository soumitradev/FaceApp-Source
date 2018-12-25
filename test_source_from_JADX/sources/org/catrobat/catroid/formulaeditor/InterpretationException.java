package org.catrobat.catroid.formulaeditor;

public class InterpretationException extends Exception {
    public InterpretationException(String detailMessage) {
        super(detailMessage);
    }

    public InterpretationException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
