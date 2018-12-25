package org.tukaani.xz.lz;

final class HC4 extends LZEncoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static /* synthetic */ Class class$org$tukaani$xz$lz$HC4 = class$("org.tukaani.xz.lz.HC4");
    private final int[] chain;
    private int cyclicPos = -1;
    private final int cyclicSize;
    private final int depthLimit;
    private final Hash234 hash;
    private int lzPos;
    private final Matches matches;

    static {
        if (class$org$tukaani$xz$lz$HC4 == null) {
        } else {
            Class cls = class$org$tukaani$xz$lz$HC4;
        }
    }

    HC4(int i, int i2, int i3, int i4, int i5, int i6) {
        super(i, i2, i3, i4, i5);
        this.hash = new Hash234(i);
        this.cyclicSize = i + 1;
        this.chain = new int[this.cyclicSize];
        this.lzPos = this.cyclicSize;
        this.matches = new Matches(i4 - 1);
        if (i6 <= 0) {
            i6 = (i4 / 4) + 4;
        }
        this.depthLimit = i6;
    }

    static /* synthetic */ Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (Throwable e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static int getMemoryUsage(int i) {
        return (Hash234.getMemoryUsage(i) + (i / 256)) + 10;
    }

    private int movePos() {
        int movePos = movePos(4, 4);
        if (movePos != 0) {
            int i = this.lzPos + 1;
            this.lzPos = i;
            if (i == Integer.MAX_VALUE) {
                int i2 = Integer.MAX_VALUE - this.cyclicSize;
                this.hash.normalize(i2);
                LZEncoder.normalize(this.chain, i2);
                this.lzPos -= i2;
            }
            i = this.cyclicPos + 1;
            this.cyclicPos = i;
            if (i == this.cyclicSize) {
                this.cyclicPos = 0;
            }
        }
        return movePos;
    }

    public Matches getMatches() {
        this.matches.count = 0;
        int i = this.matchLenMax;
        int i2 = this.niceLen;
        int movePos = movePos();
        if (movePos >= i) {
            movePos = i;
        } else if (movePos == 0) {
            return this.matches;
        } else {
            if (i2 > movePos) {
                i2 = movePos;
            }
        }
        this.hash.calcHashes(this.buf, this.readPos);
        i = this.lzPos - this.hash.getHash2Pos();
        int hash3Pos = this.lzPos - this.hash.getHash3Pos();
        int hash4Pos = this.hash.getHash4Pos();
        this.hash.updateTables(this.lzPos);
        this.chain[this.cyclicPos] = hash4Pos;
        int i3 = 2;
        if (i >= this.cyclicSize || this.buf[this.readPos - i] != this.buf[this.readPos]) {
            i3 = 0;
        } else {
            this.matches.len[0] = 2;
            this.matches.dist[0] = i - 1;
            this.matches.count = 1;
        }
        int i4 = 3;
        if (i != hash3Pos && hash3Pos < this.cyclicSize && this.buf[this.readPos - hash3Pos] == this.buf[this.readPos]) {
            int[] iArr = this.matches.dist;
            Matches matches = this.matches;
            int i5 = matches.count;
            matches.count = i5 + 1;
            iArr[i5] = hash3Pos - 1;
            i = hash3Pos;
            i3 = 3;
        }
        if (this.matches.count > 0) {
            while (i3 < movePos && this.buf[(this.readPos + i3) - r0] == this.buf[this.readPos + i3]) {
                i3++;
            }
            this.matches.len[this.matches.count - 1] = i3;
            if (i3 >= i2) {
                return this.matches;
            }
        }
        if (i3 >= 3) {
            i4 = i3;
        }
        i = this.depthLimit;
        while (true) {
            hash3Pos = this.lzPos - hash4Pos;
            hash4Pos = i - 1;
            if (i == 0) {
                break;
            } else if (hash3Pos >= this.cyclicSize) {
                break;
            } else {
                i = this.chain[(this.cyclicPos - hash3Pos) + (hash3Pos > this.cyclicPos ? this.cyclicSize : 0)];
                if (this.buf[(this.readPos + i4) - hash3Pos] == this.buf[this.readPos + i4] && this.buf[this.readPos - hash3Pos] == this.buf[this.readPos]) {
                    i3 = 0;
                    do {
                        i3++;
                        if (i3 >= movePos) {
                            break;
                        }
                    } while (this.buf[(this.readPos + i3) - hash3Pos] == this.buf[this.readPos + i3]);
                    if (i3 > i4) {
                        this.matches.len[this.matches.count] = i3;
                        this.matches.dist[this.matches.count] = hash3Pos - 1;
                        Matches matches2 = this.matches;
                        matches2.count++;
                        if (i3 >= i2) {
                            return this.matches;
                        }
                        i4 = i3;
                    } else {
                        continue;
                    }
                }
                int i6 = hash4Pos;
                hash4Pos = i;
                i = i6;
            }
        }
        return this.matches;
    }

    public void skip(int i) {
        while (true) {
            int i2 = i - 1;
            if (i > 0) {
                if (movePos() != 0) {
                    this.hash.calcHashes(this.buf, this.readPos);
                    this.chain[this.cyclicPos] = this.hash.getHash4Pos();
                    this.hash.updateTables(this.lzPos);
                }
                i = i2;
            } else {
                return;
            }
        }
    }
}
