package com.parrot.freeflight.utils;

import java.util.StringTokenizer;

public class Version {
    private int major;
    private int minor;
    private int release;

    public Version(String version) {
        StringTokenizer tokenizer = new StringTokenizer(version, ".");
        int counter = 0;
        while (tokenizer.hasMoreTokens()) {
            int intValue = Integer.parseInt(tokenizer.nextToken());
            if (counter == 0) {
                this.major = intValue;
            } else if (counter == 1) {
                this.minor = intValue;
            } else if (counter == 2) {
                this.release = intValue;
            }
            counter++;
        }
    }

    public boolean isGreater(Version compareTo) {
        if (this.major > compareTo.major) {
            return true;
        }
        if (this.major < compareTo.major) {
            return false;
        }
        if (this.minor > compareTo.minor) {
            return true;
        }
        if (this.minor < compareTo.minor) {
            return false;
        }
        if (this.release > compareTo.release) {
            return true;
        }
        return this.release < compareTo.release ? false : false;
    }

    public boolean isLower(Version compareTo) {
        if (this.major < compareTo.major) {
            return true;
        }
        if (this.major > compareTo.major) {
            return false;
        }
        if (this.minor < compareTo.minor) {
            return true;
        }
        if (this.minor > compareTo.minor) {
            return false;
        }
        if (this.release < compareTo.release) {
            return true;
        }
        return this.release > compareTo.release ? false : false;
    }
}
