package com.badlogic.gdx.graphics.g2d;

import android.support.v4.internal.view.SupportMenu;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import name.antonsmirnov.firmata.FormatHelper;
import org.catrobat.catroid.common.Constants;

public class BitmapFont implements Disposable {
    private static final int LOG2_PAGE_SIZE = 9;
    private static final int PAGES = 128;
    private static final int PAGE_SIZE = 512;
    private final BitmapFontCache cache;
    final BitmapFontData data;
    private boolean flipped;
    boolean integer;
    private boolean ownsTexture;
    Array<TextureRegion> regions;

    public static class BitmapFontData {
        public float ascent;
        public char[] breakChars;
        public char[] capChars;
        public float capHeight;
        public float descent;
        public float down;
        public boolean flipped;
        public FileHandle fontFile;
        public final Glyph[][] glyphs;
        public String[] imagePaths;
        public float lineHeight;
        public boolean markupEnabled;
        public float padBottom;
        public float padLeft;
        public float padRight;
        public float padTop;
        public float scaleX;
        public float scaleY;
        public float spaceWidth;
        public char[] xChars;
        public float xHeight;

        public BitmapFontData() {
            this.capHeight = 1.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1.0f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        }

        public BitmapFontData(FileHandle fontFile, boolean flip) {
            this.capHeight = 1.0f;
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            this.glyphs = new Glyph[128][];
            this.xHeight = 1.0f;
            this.xChars = new char[]{'x', 'e', 'a', 'o', 'n', 's', 'r', 'c', 'u', 'm', 'v', 'w', 'z'};
            this.capChars = new char[]{'M', 'N', 'B', 'D', 'C', 'E', 'F', 'K', 'A', 'G', 'H', 'I', 'J', 'L', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
            this.fontFile = fontFile;
            this.flipped = flip;
            load(fontFile, flip);
        }

        public void load(FileHandle fontFile, boolean flip) {
            Throwable th;
            Throwable th2;
            if (this.imagePaths != null) {
                throw new IllegalStateException("Already loaded.");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(fontFile.read()), 512);
            String line = reader.readLine();
            if (line == null) {
                throw new GdxRuntimeException("File is empty.");
            }
            line = line.substring(line.indexOf("padding=") + 8);
            int i = 4;
            String[] padding = line.substring(0, line.indexOf(32)).split(",", 4);
            if (padding.length != 4) {
                throw new GdxRuntimeException("Invalid padding.");
            }
            r1.padTop = (float) Integer.parseInt(padding[0]);
            int i2 = 1;
            r1.padLeft = (float) Integer.parseInt(padding[1]);
            int i3 = 2;
            r1.padBottom = (float) Integer.parseInt(padding[2]);
            r1.padRight = (float) Integer.parseInt(padding[3]);
            float padY = r1.padTop + r1.padBottom;
            line = reader.readLine();
            if (line == null) {
                throw new GdxRuntimeException("Missing common header.");
            }
            String[] common = line.split(FormatHelper.SPACE, 7);
            if (common.length < 3) {
                throw new GdxRuntimeException("Invalid common header.");
            } else if (common[1].startsWith("lineHeight=")) {
                r1.lineHeight = (float) Integer.parseInt(common[1].substring(11));
                if (common[2].startsWith("base=")) {
                    int pageCount;
                    int p;
                    String line2;
                    String[] pageLine;
                    String[] padding2;
                    int pageID;
                    StringBuilder stringBuilder;
                    String fileName;
                    int pageCount2;
                    Glyph glyph;
                    StringTokenizer tokens;
                    StringTokenizer tokens2;
                    Glyph glyph2;
                    Glyph spaceGlyph;
                    Glyph xGlyph;
                    Glyph capGlyph;
                    Glyph[][] arr$;
                    Glyph[] page;
                    Glyph spaceGlyph2;
                    Glyph[][] arr$2;
                    Glyph xGlyph2;
                    int i$;
                    int i$2;
                    Glyph[] arr$3;
                    int len$;
                    float baseLine = (float) Integer.parseInt(common[2].substring(5));
                    if (common.length >= 6 && common[5] != null && common[5].startsWith("pages=")) {
                        try {
                            pageCount = Math.max(1, Integer.parseInt(common[5].substring(6)));
                        } catch (NumberFormatException e) {
                        }
                        r1.imagePaths = new String[pageCount];
                        p = 0;
                        while (p < pageCount) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                throw new GdxRuntimeException("Missing additional page definitions.");
                            }
                            pageLine = line2.split(FormatHelper.SPACE, i);
                            if (pageLine[i3].startsWith("file=")) {
                                throw new GdxRuntimeException("Missing: file");
                            }
                            if (pageLine[i2].startsWith("id=")) {
                                padding2 = padding;
                            } else {
                                pageID = Integer.parseInt(pageLine[i2].substring(3));
                                if (pageID == p) {
                                    stringBuilder = new StringBuilder();
                                    stringBuilder.append("Page IDs must be indices starting at 0: ");
                                    stringBuilder.append(pageLine[1].substring(3));
                                    throw new GdxRuntimeException(stringBuilder.toString());
                                }
                                padding2 = padding;
                            }
                            if (pageLine[i3].endsWith("\"")) {
                                fileName = pageLine[i3].substring(5, pageLine[i3].length());
                            } else {
                                fileName = pageLine[i3].substring(6, pageLine[i3].length() - 1);
                            }
                            r1.imagePaths[p] = fontFile.parent().child(fileName).path().replaceAll("\\\\", "/");
                            p++;
                            padding = padding2;
                            i2 = 1;
                            i = 4;
                            i3 = 2;
                        }
                        r1.descent = 0.0f;
                        while (true) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                break;
                            } else if (line2.startsWith("kernings ")) {
                                break;
                            } else {
                                pageCount2 = pageCount;
                                if (!line2.startsWith("char ")) {
                                    glyph = new Glyph();
                                    tokens = new StringTokenizer(line2, " =");
                                    tokens.nextToken();
                                    tokens.nextToken();
                                    i2 = Integer.parseInt(tokens.nextToken());
                                    if (i2 > SupportMenu.USER_MASK) {
                                        setGlyph(i2, glyph);
                                        glyph.id = i2;
                                        tokens.nextToken();
                                        glyph.srcX = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.srcY = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.width = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.height = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.xoffset = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        if (flip) {
                                            glyph.yoffset = -(glyph.height + Integer.parseInt(tokens.nextToken()));
                                        } else {
                                            glyph.yoffset = Integer.parseInt(tokens.nextToken());
                                        }
                                        tokens.nextToken();
                                        glyph.xadvance = Integer.parseInt(tokens.nextToken());
                                        if (tokens.hasMoreTokens()) {
                                            tokens.nextToken();
                                        }
                                        if (tokens.hasMoreTokens()) {
                                            try {
                                                glyph.page = Integer.parseInt(tokens.nextToken());
                                            } catch (NumberFormatException e2) {
                                            }
                                        }
                                        if (glyph.width > 0 && glyph.height > 0) {
                                            r1.descent = Math.min(((float) glyph.yoffset) + baseLine, r1.descent);
                                        }
                                    } else {
                                        continue;
                                    }
                                }
                                pageCount = pageCount2;
                            }
                        }
                        r1.descent += r1.padBottom;
                        while (true) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                break;
                            } else if (!line2.startsWith("kerning ")) {
                                break;
                            } else {
                                pageCount2 = pageCount;
                                tokens2 = new StringTokenizer(line2, " =");
                                tokens2.nextToken();
                                tokens2.nextToken();
                                pageCount = Integer.parseInt(tokens2.nextToken());
                                tokens2.nextToken();
                                i2 = Integer.parseInt(tokens2.nextToken());
                                if (pageCount >= 0 && pageCount <= SupportMenu.USER_MASK && i2 >= 0) {
                                    if (i2 > SupportMenu.USER_MASK) {
                                        glyph2 = getGlyph((char) pageCount);
                                        tokens2.nextToken();
                                        i3 = Integer.parseInt(tokens2.nextToken());
                                        if (glyph2 != null) {
                                            glyph2.setKerning(i2, i3);
                                        }
                                    }
                                }
                                pageCount = pageCount2;
                            }
                        }
                        spaceGlyph = getGlyph(' ');
                        if (spaceGlyph == null) {
                            spaceGlyph = new Glyph();
                            spaceGlyph.id = 32;
                            glyph = getGlyph('l');
                            if (glyph == null) {
                                glyph = getFirstGlyph();
                            }
                            spaceGlyph.xadvance = glyph.xadvance;
                            setGlyph(32, spaceGlyph);
                        }
                        if (spaceGlyph.width == 0) {
                            spaceGlyph.width = (int) (((float) spaceGlyph.xadvance) + r1.padRight);
                        }
                        r1.spaceWidth = (float) spaceGlyph.width;
                        xGlyph = null;
                        for (char glyph3 : r1.xChars) {
                            xGlyph = getGlyph(glyph3);
                            if (xGlyph != null) {
                                break;
                            }
                        }
                        if (xGlyph == null) {
                            xGlyph = getFirstGlyph();
                        }
                        r1.xHeight = ((float) xGlyph.height) - padY;
                        capGlyph = null;
                        for (char glyph4 : r1.capChars) {
                            capGlyph = getGlyph(glyph4);
                            if (capGlyph != null) {
                                break;
                            }
                        }
                        if (capGlyph != null) {
                            arr$ = r1.glyphs;
                            i = arr$.length;
                            i3 = 0;
                            while (i3 < i) {
                                page = arr$[i3];
                                if (page != null) {
                                    spaceGlyph2 = spaceGlyph;
                                    arr$2 = arr$;
                                    xGlyph2 = xGlyph;
                                    pageCount2 = pageCount;
                                } else {
                                    spaceGlyph2 = spaceGlyph;
                                    arr$2 = arr$;
                                    spaceGlyph = page;
                                    pageID = spaceGlyph.length;
                                    i$ = 0;
                                    while (true) {
                                        xGlyph2 = xGlyph;
                                        i$2 = i$;
                                        if (i$2 < pageID) {
                                            break;
                                        }
                                        arr$3 = spaceGlyph;
                                        spaceGlyph = spaceGlyph[i$2];
                                        if (spaceGlyph == null) {
                                            len$ = pageID;
                                            if (spaceGlyph.height != 0) {
                                                pageCount2 = pageCount;
                                            } else if (spaceGlyph.width != 0) {
                                                pageCount2 = pageCount;
                                            } else {
                                                pageCount2 = pageCount;
                                                r1.capHeight = Math.max(r1.capHeight, (float) spaceGlyph.height);
                                            }
                                        } else {
                                            len$ = pageID;
                                            pageCount2 = pageCount;
                                        }
                                        i$ = i$2 + 1;
                                        xGlyph = xGlyph2;
                                        spaceGlyph = arr$3;
                                        pageID = len$;
                                        pageCount = pageCount2;
                                    }
                                    pageCount2 = pageCount;
                                }
                                i3++;
                                spaceGlyph = spaceGlyph2;
                                arr$ = arr$2;
                                xGlyph = xGlyph2;
                                pageCount = pageCount2;
                            }
                            xGlyph2 = xGlyph;
                            pageCount2 = pageCount;
                        } else {
                            xGlyph2 = xGlyph;
                            pageCount2 = pageCount;
                            r1.capHeight = (float) capGlyph.height;
                        }
                        r1.capHeight -= padY;
                        r1.ascent = baseLine - r1.capHeight;
                        r1.down = -r1.lineHeight;
                        if (flip) {
                            r1.ascent = -r1.ascent;
                            r1.down = -r1.down;
                        }
                        StreamUtils.closeQuietly(reader);
                        return;
                    }
                    pageCount = 1;
                    try {
                        r1.imagePaths = new String[pageCount];
                        p = 0;
                        while (p < pageCount) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                pageLine = line2.split(FormatHelper.SPACE, i);
                                if (pageLine[i3].startsWith("file=")) {
                                    if (pageLine[i2].startsWith("id=")) {
                                        padding2 = padding;
                                    } else {
                                        pageID = Integer.parseInt(pageLine[i2].substring(3));
                                        if (pageID == p) {
                                            padding2 = padding;
                                        } else {
                                            stringBuilder = new StringBuilder();
                                            stringBuilder.append("Page IDs must be indices starting at 0: ");
                                            stringBuilder.append(pageLine[1].substring(3));
                                            throw new GdxRuntimeException(stringBuilder.toString());
                                        }
                                    }
                                    if (pageLine[i3].endsWith("\"")) {
                                        fileName = pageLine[i3].substring(5, pageLine[i3].length());
                                    } else {
                                        fileName = pageLine[i3].substring(6, pageLine[i3].length() - 1);
                                    }
                                    r1.imagePaths[p] = fontFile.parent().child(fileName).path().replaceAll("\\\\", "/");
                                    p++;
                                    padding = padding2;
                                    i2 = 1;
                                    i = 4;
                                    i3 = 2;
                                } else {
                                    throw new GdxRuntimeException("Missing: file");
                                }
                            }
                            throw new GdxRuntimeException("Missing additional page definitions.");
                        }
                        r1.descent = 0.0f;
                        while (true) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                if (line2.startsWith("kernings ")) {
                                    break;
                                }
                                pageCount2 = pageCount;
                                if (!line2.startsWith("char ")) {
                                    glyph = new Glyph();
                                    tokens = new StringTokenizer(line2, " =");
                                    tokens.nextToken();
                                    tokens.nextToken();
                                    i2 = Integer.parseInt(tokens.nextToken());
                                    if (i2 > SupportMenu.USER_MASK) {
                                        continue;
                                    } else {
                                        setGlyph(i2, glyph);
                                        glyph.id = i2;
                                        tokens.nextToken();
                                        glyph.srcX = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.srcY = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.width = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.height = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        glyph.xoffset = Integer.parseInt(tokens.nextToken());
                                        tokens.nextToken();
                                        if (flip) {
                                            glyph.yoffset = -(glyph.height + Integer.parseInt(tokens.nextToken()));
                                        } else {
                                            glyph.yoffset = Integer.parseInt(tokens.nextToken());
                                        }
                                        tokens.nextToken();
                                        glyph.xadvance = Integer.parseInt(tokens.nextToken());
                                        if (tokens.hasMoreTokens()) {
                                            tokens.nextToken();
                                        }
                                        if (tokens.hasMoreTokens()) {
                                            glyph.page = Integer.parseInt(tokens.nextToken());
                                        }
                                        r1.descent = Math.min(((float) glyph.yoffset) + baseLine, r1.descent);
                                    }
                                }
                                pageCount = pageCount2;
                            } else {
                                break;
                            }
                        }
                        r1.descent += r1.padBottom;
                        while (true) {
                            line2 = reader.readLine();
                            if (line2 != null) {
                                if (!line2.startsWith("kerning ")) {
                                    break;
                                }
                                pageCount2 = pageCount;
                                tokens2 = new StringTokenizer(line2, " =");
                                tokens2.nextToken();
                                tokens2.nextToken();
                                pageCount = Integer.parseInt(tokens2.nextToken());
                                tokens2.nextToken();
                                i2 = Integer.parseInt(tokens2.nextToken());
                                if (i2 > SupportMenu.USER_MASK) {
                                    glyph2 = getGlyph((char) pageCount);
                                    tokens2.nextToken();
                                    i3 = Integer.parseInt(tokens2.nextToken());
                                    if (glyph2 != null) {
                                        glyph2.setKerning(i2, i3);
                                    }
                                }
                                pageCount = pageCount2;
                            } else {
                                break;
                            }
                        }
                        spaceGlyph = getGlyph(' ');
                        if (spaceGlyph == null) {
                            spaceGlyph = new Glyph();
                            spaceGlyph.id = 32;
                            glyph = getGlyph('l');
                            if (glyph == null) {
                                glyph = getFirstGlyph();
                            }
                            spaceGlyph.xadvance = glyph.xadvance;
                            setGlyph(32, spaceGlyph);
                        }
                        if (spaceGlyph.width == 0) {
                            spaceGlyph.width = (int) (((float) spaceGlyph.xadvance) + r1.padRight);
                        }
                        r1.spaceWidth = (float) spaceGlyph.width;
                        xGlyph = null;
                        while (pageID < r1.xChars.length) {
                            xGlyph = getGlyph(glyph3);
                            if (xGlyph != null) {
                                break;
                            }
                        }
                        if (xGlyph == null) {
                            xGlyph = getFirstGlyph();
                        }
                        r1.xHeight = ((float) xGlyph.height) - padY;
                        capGlyph = null;
                        while (pageID < r1.capChars.length) {
                            capGlyph = getGlyph(glyph4);
                            if (capGlyph != null) {
                                break;
                            }
                        }
                        if (capGlyph != null) {
                            xGlyph2 = xGlyph;
                            pageCount2 = pageCount;
                            r1.capHeight = (float) capGlyph.height;
                        } else {
                            arr$ = r1.glyphs;
                            i = arr$.length;
                            i3 = 0;
                            while (i3 < i) {
                                page = arr$[i3];
                                if (page != null) {
                                    spaceGlyph2 = spaceGlyph;
                                    arr$2 = arr$;
                                    spaceGlyph = page;
                                    pageID = spaceGlyph.length;
                                    i$ = 0;
                                    while (true) {
                                        xGlyph2 = xGlyph;
                                        i$2 = i$;
                                        if (i$2 < pageID) {
                                            break;
                                        }
                                        arr$3 = spaceGlyph;
                                        spaceGlyph = spaceGlyph[i$2];
                                        if (spaceGlyph == null) {
                                            len$ = pageID;
                                            pageCount2 = pageCount;
                                        } else {
                                            len$ = pageID;
                                            if (spaceGlyph.height != 0) {
                                                pageCount2 = pageCount;
                                            } else if (spaceGlyph.width != 0) {
                                                pageCount2 = pageCount;
                                                r1.capHeight = Math.max(r1.capHeight, (float) spaceGlyph.height);
                                            } else {
                                                pageCount2 = pageCount;
                                            }
                                        }
                                        i$ = i$2 + 1;
                                        xGlyph = xGlyph2;
                                        spaceGlyph = arr$3;
                                        pageID = len$;
                                        pageCount = pageCount2;
                                    }
                                    pageCount2 = pageCount;
                                } else {
                                    spaceGlyph2 = spaceGlyph;
                                    arr$2 = arr$;
                                    xGlyph2 = xGlyph;
                                    pageCount2 = pageCount;
                                }
                                i3++;
                                spaceGlyph = spaceGlyph2;
                                arr$ = arr$2;
                                xGlyph = xGlyph2;
                                pageCount = pageCount2;
                            }
                            xGlyph2 = xGlyph;
                            pageCount2 = pageCount;
                        }
                        r1.capHeight -= padY;
                        r1.ascent = baseLine - r1.capHeight;
                        r1.down = -r1.lineHeight;
                        if (flip) {
                            r1.ascent = -r1.ascent;
                            r1.down = -r1.down;
                        }
                        StreamUtils.closeQuietly(reader);
                        return;
                    } catch (NumberFormatException e3) {
                        NumberFormatException ex = e3;
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append("Invalid page id: ");
                        stringBuilder2.append(pageLine[1].substring(3));
                        throw new GdxRuntimeException(stringBuilder2.toString(), ex);
                    } catch (Exception e4) {
                        ex = e4;
                        try {
                            Exception ex2;
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append("Error loading font file: ");
                            stringBuilder3.append(fontFile);
                            throw new GdxRuntimeException(stringBuilder3.toString(), ex2);
                        } catch (Throwable th3) {
                            th = th3;
                            th2 = th;
                            StreamUtils.closeQuietly(reader);
                            throw th2;
                        }
                    }
                }
                throw new GdxRuntimeException("Missing: base");
            } else {
                throw new GdxRuntimeException("Missing: lineHeight");
            }
        }

        public void setGlyphRegion(Glyph glyph, TextureRegion region) {
            Glyph glyph2 = glyph;
            TextureRegion textureRegion = region;
            Texture texture = region.getTexture();
            float invTexWidth = 1.0f / ((float) texture.getWidth());
            float invTexHeight = 1.0f / ((float) texture.getHeight());
            float offsetX = 0.0f;
            float offsetY = 0.0f;
            float u = textureRegion.f5u;
            float v = textureRegion.f6v;
            float regionWidth = (float) region.getRegionWidth();
            float regionHeight = (float) region.getRegionHeight();
            if (textureRegion instanceof AtlasRegion) {
                AtlasRegion atlasRegion = (AtlasRegion) textureRegion;
                offsetX = atlasRegion.offsetX;
                offsetY = ((float) (atlasRegion.originalHeight - atlasRegion.packedHeight)) - atlasRegion.offsetY;
            }
            float x = (float) glyph2.srcX;
            float x2 = (float) (glyph2.srcX + glyph2.width);
            float y = (float) glyph2.srcY;
            float y2 = (float) (glyph2.srcY + glyph2.height);
            if (offsetX > 0.0f) {
                x -= offsetX;
                if (x < 0.0f) {
                    glyph2.width = (int) (((float) glyph2.width) + x);
                    glyph2.xoffset = (int) (((float) glyph2.xoffset) - x);
                    x = 0.0f;
                }
                x2 -= offsetX;
                if (x2 > regionWidth) {
                    glyph2.width = (int) (((float) glyph2.width) - (x2 - regionWidth));
                    x2 = regionWidth;
                }
            }
            if (offsetY > 0.0f) {
                y -= offsetY;
                if (y < 0.0f) {
                    glyph2.height = (int) (((float) glyph2.height) + y);
                    y = 0.0f;
                }
                y2 -= offsetY;
                if (y2 > regionHeight) {
                    float amount = y2 - regionHeight;
                    glyph2.height = (int) (((float) glyph2.height) - amount);
                    glyph2.yoffset = (int) (((float) glyph2.yoffset) + amount);
                    y2 = regionHeight;
                }
            }
            glyph2.f61u = (x * invTexWidth) + u;
            glyph2.u2 = (x2 * invTexWidth) + u;
            if (this.flipped) {
                glyph2.f62v = (y * invTexHeight) + v;
                glyph2.v2 = (y2 * invTexHeight) + v;
                return;
            }
            glyph2.v2 = (y * invTexHeight) + v;
            glyph2.f62v = (y2 * invTexHeight) + v;
        }

        public void setLineHeight(float height) {
            this.lineHeight = this.scaleY * height;
            this.down = this.flipped ? this.lineHeight : -this.lineHeight;
        }

        public void setGlyph(int ch, Glyph glyph) {
            Glyph[] page = this.glyphs[ch / 512];
            if (page == null) {
                Glyph[] glyphArr = new Glyph[512];
                page = glyphArr;
                this.glyphs[ch / 512] = glyphArr;
            }
            page[ch & 511] = glyph;
        }

        public Glyph getFirstGlyph() {
            for (Glyph[] page : this.glyphs) {
                if (page != null) {
                    for (Glyph glyph : page) {
                        if (!(glyph == null || glyph.height == 0)) {
                            if (glyph.width != 0) {
                                return glyph;
                            }
                        }
                    }
                    continue;
                }
            }
            throw new GdxRuntimeException("No glyphs found.");
        }

        public boolean hasGlyph(char ch) {
            return getGlyph(ch) != null;
        }

        public Glyph getGlyph(char ch) {
            Glyph[] page = this.glyphs[ch / 512];
            if (page != null) {
                return page[ch & 511];
            }
            return null;
        }

        public void getGlyphs(GlyphRun run, CharSequence str, int ch, int end) {
            boolean markupEnabled = this.markupEnabled;
            float scaleX = this.scaleX;
            Array<Glyph> glyphs = run.glyphs;
            FloatArray xAdvances = run.xAdvances;
            Glyph lastGlyph = null;
            char ch2;
            while (ch2 < end) {
                char start = ch2 + 1;
                ch2 = str.charAt(ch2);
                Glyph glyph = getGlyph(ch2);
                if (glyph == null) {
                    ch2 = start;
                } else {
                    glyphs.add(glyph);
                    if (lastGlyph == null) {
                        xAdvances.add((((float) (-glyph.xoffset)) * scaleX) - this.padLeft);
                    } else {
                        xAdvances.add(((float) (lastGlyph.xadvance + lastGlyph.getKerning(ch2))) * scaleX);
                    }
                    lastGlyph = glyph;
                    if (markupEnabled && ch2 == Constants.REMIX_URL_PREFIX_INDICATOR && start < end && str.charAt(start) == Constants.REMIX_URL_PREFIX_INDICATOR) {
                        start++;
                    }
                    ch2 = start;
                }
            }
            if (lastGlyph != null) {
                xAdvances.add((((float) (lastGlyph.xoffset + lastGlyph.width)) * scaleX) - this.padRight);
            }
        }

        public int getWrapIndex(Array<Glyph> glyphs, int start) {
            if (isWhitespace((char) ((Glyph) glyphs.get(start)).id)) {
                return start;
            }
            for (int i = start - 1; i >= 1; i--) {
                char ch = (char) ((Glyph) glyphs.get(i)).id;
                if (isWhitespace(ch)) {
                    return i;
                }
                if (isBreakChar(ch)) {
                    return i + 1;
                }
            }
            return 0;
        }

        public boolean isBreakChar(char c) {
            if (this.breakChars == null) {
                return false;
            }
            for (char br : this.breakChars) {
                if (c == br) {
                    return true;
                }
            }
            return false;
        }

        public boolean isWhitespace(char c) {
            if (!(c == '\r' || c == ' ')) {
                switch (c) {
                    case '\t':
                    case '\n':
                        break;
                    default:
                        return false;
                }
            }
            return true;
        }

        public String getImagePath(int index) {
            return this.imagePaths[index];
        }

        public String[] getImagePaths() {
            return this.imagePaths;
        }

        public FileHandle getFontFile() {
            return this.fontFile;
        }

        public void setScale(float scaleX, float scaleY) {
            if (scaleX == 0.0f) {
                throw new IllegalArgumentException("scaleX cannot be 0.");
            } else if (scaleY == 0.0f) {
                throw new IllegalArgumentException("scaleY cannot be 0.");
            } else {
                float x = scaleX / this.scaleX;
                float y = scaleY / this.scaleY;
                this.lineHeight *= y;
                this.spaceWidth *= x;
                this.xHeight *= y;
                this.capHeight *= y;
                this.ascent *= y;
                this.descent *= y;
                this.down *= y;
                this.padTop *= y;
                this.padLeft *= y;
                this.padBottom *= y;
                this.padRight *= y;
                this.scaleX = scaleX;
                this.scaleY = scaleY;
            }
        }

        public void setScale(float scaleXY) {
            setScale(scaleXY, scaleXY);
        }

        public void scale(float amount) {
            setScale(this.scaleX + amount, this.scaleY + amount);
        }
    }

    public static class Glyph {
        public int height;
        public int id;
        public byte[][] kerning;
        public int page = 0;
        public int srcX;
        public int srcY;
        /* renamed from: u */
        public float f61u;
        public float u2;
        /* renamed from: v */
        public float f62v;
        public float v2;
        public int width;
        public int xadvance;
        public int xoffset;
        public int yoffset;

        public int getKerning(char ch) {
            if (this.kerning != null) {
                byte[] page = this.kerning[ch >>> 9];
                if (page != null) {
                    return page[ch & 511];
                }
            }
            return 0;
        }

        public void setKerning(int ch, int value) {
            if (this.kerning == null) {
                this.kerning = new byte[128][];
            }
            byte[] page = this.kerning[ch >>> 9];
            if (page == null) {
                byte[] bArr = new byte[512];
                page = bArr;
                this.kerning[ch >>> 9] = bArr;
            }
            page[ch & 511] = (byte) value;
        }

        public String toString() {
            return Character.toString((char) this.id);
        }
    }

    public BitmapFont() {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), false, true);
    }

    public BitmapFont(boolean flip) {
        this(Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.fnt"), Gdx.files.classpath("com/badlogic/gdx/utils/arial-15.png"), flip, true);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region) {
        this(fontFile, region, false);
    }

    public BitmapFont(FileHandle fontFile, TextureRegion region, boolean flip) {
        this(new BitmapFontData(fontFile, flip), region, true);
    }

    public BitmapFont(FileHandle fontFile) {
        this(fontFile, false);
    }

    public BitmapFont(FileHandle fontFile, boolean flip) {
        this(new BitmapFontData(fontFile, flip), (TextureRegion) null, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip) {
        this(fontFile, imageFile, flip, true);
    }

    public BitmapFont(FileHandle fontFile, FileHandle imageFile, boolean flip, boolean integer) {
        this(new BitmapFontData(fontFile, flip), new TextureRegion(new Texture(imageFile, false)), integer);
        this.ownsTexture = true;
    }

    public BitmapFont(BitmapFontData data, TextureRegion region, boolean integer) {
        Array with;
        if (region != null) {
            with = Array.with(new TextureRegion[]{region});
        } else {
            with = null;
        }
        this(data, with, integer);
    }

    public BitmapFont(BitmapFontData data, Array<TextureRegion> pageRegions, boolean integer) {
        if (pageRegions != null) {
            if (pageRegions.size != 0) {
                this.regions = pageRegions;
                this.ownsTexture = false;
                this.cache = newFontCache();
                this.flipped = data.flipped;
                this.data = data;
                this.integer = integer;
                load(data);
            }
        }
        int n = data.imagePaths.length;
        this.regions = new Array(n);
        for (int i = 0; i < n; i++) {
            FileHandle file;
            if (data.fontFile == null) {
                file = Gdx.files.internal(data.imagePaths[i]);
            } else {
                file = Gdx.files.getFileHandle(data.imagePaths[i], data.fontFile.type());
            }
            this.regions.add(new TextureRegion(new Texture(file, false)));
        }
        this.ownsTexture = true;
        this.cache = newFontCache();
        this.flipped = data.flipped;
        this.data = data;
        this.integer = integer;
        load(data);
    }

    protected void load(BitmapFontData data) {
        for (Glyph[] page : data.glyphs) {
            if (page != null) {
                for (Glyph glyph : page) {
                    if (glyph != null) {
                        TextureRegion region = (TextureRegion) this.regions.get(glyph.page);
                        if (region == null) {
                            throw new IllegalArgumentException("BitmapFont texture region array cannot contain null elements.");
                        }
                        data.setGlyphRegion(glyph, region);
                    }
                }
                continue;
            }
        }
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y) {
        this.cache.clear();
        GlyphLayout layout = this.cache.addText(str, x, y);
        this.cache.draw(batch);
        return layout;
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, float targetWidth, int halign, boolean wrap) {
        this.cache.clear();
        GlyphLayout layout = this.cache.addText(str, x, y, targetWidth, halign, wrap);
        this.cache.draw(batch);
        return layout;
    }

    public GlyphLayout draw(Batch batch, CharSequence str, float x, float y, int start, int end, float targetWidth, int halign, boolean wrap) {
        this.cache.clear();
        GlyphLayout layout = this.cache.addText(str, x, y, start, end, targetWidth, halign, wrap);
        this.cache.draw(batch);
        return layout;
    }

    public void draw(Batch batch, GlyphLayout layout, float x, float y) {
        this.cache.clear();
        this.cache.addText(layout, x, y);
        this.cache.draw(batch);
    }

    public Color getColor() {
        return this.cache.getColor();
    }

    public void setColor(Color color) {
        this.cache.getColor().set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.cache.getColor().set(r, g, b, a);
    }

    public float getScaleX() {
        return this.data.scaleX;
    }

    public float getScaleY() {
        return this.data.scaleY;
    }

    public TextureRegion getRegion() {
        return (TextureRegion) this.regions.first();
    }

    public Array<TextureRegion> getRegions() {
        return this.regions;
    }

    public TextureRegion getRegion(int index) {
        return (TextureRegion) this.regions.get(index);
    }

    public float getLineHeight() {
        return this.data.lineHeight;
    }

    public float getSpaceWidth() {
        return this.data.spaceWidth;
    }

    public float getXHeight() {
        return this.data.xHeight;
    }

    public float getCapHeight() {
        return this.data.capHeight;
    }

    public float getAscent() {
        return this.data.ascent;
    }

    public float getDescent() {
        return this.data.descent;
    }

    public boolean isFlipped() {
        return this.flipped;
    }

    public void dispose() {
        if (this.ownsTexture) {
            for (int i = 0; i < this.regions.size; i++) {
                ((TextureRegion) this.regions.get(i)).getTexture().dispose();
            }
        }
    }

    public void setFixedWidthGlyphs(CharSequence glyphs) {
        int index;
        BitmapFontData data = this.data;
        int maxAdvance = 0;
        int end = glyphs.length();
        for (index = 0; index < end; index++) {
            Glyph g = data.getGlyph(glyphs.charAt(index));
            if (g != null && g.xadvance > maxAdvance) {
                maxAdvance = g.xadvance;
            }
        }
        end = glyphs.length();
        for (index = 0; index < end; index++) {
            g = data.getGlyph(glyphs.charAt(index));
            if (g != null) {
                g.xoffset += (maxAdvance - g.xadvance) / 2;
                g.xadvance = maxAdvance;
                g.kerning = (byte[][]) null;
            }
        }
    }

    public void setUseIntegerPositions(boolean integer) {
        this.integer = integer;
        this.cache.setUseIntegerPositions(integer);
    }

    public boolean usesIntegerPositions() {
        return this.integer;
    }

    public BitmapFontCache getCache() {
        return this.cache;
    }

    public BitmapFontData getData() {
        return this.data;
    }

    public boolean ownsTexture() {
        return this.ownsTexture;
    }

    public void setOwnsTexture(boolean ownsTexture) {
        this.ownsTexture = ownsTexture;
    }

    public BitmapFontCache newFontCache() {
        return new BitmapFontCache(this, this.integer);
    }

    public String toString() {
        if (this.data.fontFile != null) {
            return this.data.fontFile.nameWithoutExtension();
        }
        return super.toString();
    }

    static int indexOf(CharSequence text, char ch, int start) {
        int n = text.length();
        while (start < n) {
            if (text.charAt(start) == ch) {
                return start;
            }
            start++;
        }
        return n;
    }
}
