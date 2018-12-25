package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Colors;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import org.catrobat.catroid.common.Constants;

public class GlyphLayout implements Poolable {
    private static final Array<Color> colorStack = new Array(4);
    public float height;
    public final Array<GlyphRun> runs = new Array();
    public float width;

    public static class GlyphRun implements Poolable {
        public final Color color = new Color();
        public final Array<Glyph> glyphs = new Array();
        public float width;
        /* renamed from: x */
        public float f103x;
        public final FloatArray xAdvances = new FloatArray();
        /* renamed from: y */
        public float f104y;

        public void reset() {
            this.glyphs.clear();
            this.xAdvances.clear();
            this.width = 0.0f;
        }

        public String toString() {
            StringBuilder buffer = new StringBuilder(this.glyphs.size);
            Array<Glyph> glyphs = this.glyphs;
            int n = glyphs.size;
            for (int i = 0; i < n; i++) {
                buffer.append((char) ((Glyph) glyphs.get(i)).id);
            }
            buffer.append(", #");
            buffer.append(this.color);
            buffer.append(", ");
            buffer.append(this.f103x);
            buffer.append(", ");
            buffer.append(this.f104y);
            buffer.append(", ");
            buffer.append(this.width);
            return buffer.toString();
        }
    }

    public GlyphLayout(BitmapFont font, CharSequence str) {
        setText(font, str);
    }

    public GlyphLayout(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
        setText(font, str, color, targetWidth, halign, wrap);
    }

    public GlyphLayout(BitmapFont font, CharSequence str, int start, int end, Color color, float targetWidth, int halign, boolean wrap, String truncate) {
        setText(font, str, start, end, color, targetWidth, halign, wrap, truncate);
    }

    public void setText(BitmapFont font, CharSequence str) {
        setText(font, str, 0, str.length(), font.getColor(), 0.0f, 8, false, null);
    }

    public void setText(BitmapFont font, CharSequence str, Color color, float targetWidth, int halign, boolean wrap) {
        setText(font, str, 0, str.length(), color, targetWidth, halign, wrap, null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setText(com.badlogic.gdx.graphics.g2d.BitmapFont r40, java.lang.CharSequence r41, int r42, int r43, com.badlogic.gdx.graphics.Color r44, float r45, int r46, boolean r47, java.lang.String r48) {
        /*
        r39 = this;
        r7 = r39;
        r8 = r40;
        r9 = r41;
        r10 = r43;
        if (r48 == 0) goto L_0x000d;
    L_0x000a:
        r0 = 1;
    L_0x000b:
        r14 = r0;
        goto L_0x0019;
    L_0x000d:
        r0 = r8.data;
        r0 = r0.spaceWidth;
        r0 = (r45 > r0 ? 1 : (r45 == r0 ? 0 : -1));
        if (r0 > 0) goto L_0x0017;
    L_0x0015:
        r0 = 0;
        goto L_0x000b;
    L_0x0017:
        r14 = r47;
    L_0x0019:
        r15 = r8.data;
        r6 = r15.markupEnabled;
        r0 = com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun.class;
        r5 = com.badlogic.gdx.utils.Pools.get(r0);
        r4 = r7.runs;
        r5.freeAll(r4);
        r4.clear();
        r0 = 0;
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r8 = colorStack;
        r16 = r44;
        r17 = r0;
        r0 = r44;
        r8.add(r0);
        r0 = com.badlogic.gdx.graphics.Color.class;
        r0 = com.badlogic.gdx.utils.Pools.get(r0);
        r13 = r1;
        r18 = r3;
        r12 = r17;
        r3 = r44;
        r1 = r42;
        r17 = r2;
        r2 = r1;
    L_0x004c:
        r20 = -1;
        r21 = 0;
        if (r2 != r10) goto L_0x0070;
    L_0x0052:
        if (r1 != r10) goto L_0x0068;
    L_0x0054:
        r10 = r0;
        r22 = r1;
        r31 = r2;
        r24 = r3;
        r28 = r5;
        r29 = r6;
        r23 = r14;
        r11 = r17;
        r0 = r18;
        r14 = r4;
        goto L_0x0140;
    L_0x0068:
        r20 = r10;
        r23 = r14;
        r11 = r20;
        r14 = r2;
        goto L_0x00a0;
    L_0x0070:
        r11 = r2 + 1;
        r2 = r9.charAt(r2);
        r23 = r14;
        r14 = 10;
        if (r2 == r14) goto L_0x009b;
    L_0x007c:
        r14 = 91;
        if (r2 == r14) goto L_0x0081;
    L_0x0080:
        goto L_0x0097;
    L_0x0081:
        if (r6 == 0) goto L_0x0097;
    L_0x0083:
        r2 = r7.parseColorMarkup(r9, r11, r10, r0);
        if (r2 < 0) goto L_0x0097;
    L_0x0089:
        r20 = r11 + -1;
        r14 = r2 + 1;
        r11 = r11 + r14;
        r14 = r8.peek();
        r2 = r14;
        r2 = (com.badlogic.gdx.graphics.Color) r2;
        r16 = r2;
    L_0x0097:
        r14 = r11;
        r11 = r20;
        goto L_0x00a0;
    L_0x009b:
        r20 = r11 + -1;
        r21 = 1;
        goto L_0x0097;
    L_0x00a0:
        r2 = -1;
        if (r11 == r2) goto L_0x02d8;
    L_0x00a3:
        if (r11 == r1) goto L_0x02ad;
    L_0x00a5:
        r2 = r5.obtain();
        r2 = (com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun) r2;
        r4.add(r2);
        r24 = r0;
        r0 = r2.color;
        r0.set(r3);
        r2.f103x = r12;
        r2.f104y = r13;
        r15.getGlyphs(r2, r9, r1, r11);
        r0 = r2.xAdvances;
        r0 = r0.items;
        r20 = 0;
        r25 = r0;
        r0 = r2.xAdvances;
        r0 = r0.size;
        r38 = r13;
        r13 = r2;
        r2 = r17;
        r17 = r12;
        r12 = r20;
        r20 = r18;
        r18 = r38;
    L_0x00d5:
        if (r12 >= r0) goto L_0x0296;
    L_0x00d7:
        r26 = r25[r12];
        r17 = r17 + r26;
        if (r23 == 0) goto L_0x0260;
    L_0x00dd:
        r9 = 1;
        r22 = (r17 > r45 ? 1 : (r17 == r45 ? 0 : -1));
        if (r22 <= 0) goto L_0x0260;
    L_0x00e2:
        if (r12 <= r9) goto L_0x0260;
    L_0x00e4:
        r22 = r17 - r26;
        r9 = r13.glyphs;
        r28 = r0;
        r0 = r12 + -1;
        r0 = r9.get(r0);
        r0 = (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph) r0;
        r0 = r0.xoffset;
        r9 = r13.glyphs;
        r29 = r1;
        r1 = r12 + -1;
        r1 = r9.get(r1);
        r1 = (com.badlogic.gdx.graphics.g2d.BitmapFont.Glyph) r1;
        r1 = r1.width;
        r0 = r0 + r1;
        r0 = (float) r0;
        r1 = r15.scaleX;
        r0 = r0 * r1;
        r22 = r22 + r0;
        r0 = 953267991; // 0x38d1b717 float:1.0E-4 double:4.709769656E-315;
        r22 = r22 - r0;
        r0 = (r22 > r45 ? 1 : (r22 == r45 ? 0 : -1));
        if (r0 <= 0) goto L_0x024b;
    L_0x0113:
        if (r48 == 0) goto L_0x01e8;
    L_0x0115:
        r1 = r24;
        r19 = r28;
        r0 = r7;
        r10 = r1;
        r22 = r29;
        r1 = r15;
        r30 = r11;
        r11 = r2;
        r2 = r13;
        r24 = r3;
        r3 = r45;
        r31 = r14;
        r14 = r4;
        r4 = r48;
        r28 = r5;
        r5 = r12;
        r29 = r6;
        r6 = r28;
        r0.truncate(r1, r2, r3, r4, r5, r6);
        r0 = r13.f103x;
        r1 = r13.width;
        r0 = r0 + r1;
        r12 = r0;
        r13 = r18;
        r0 = r20;
    L_0x0140:
        r1 = java.lang.Math.max(r11, r12);
        r2 = 1;
        r3 = r8.size;
    L_0x0147:
        if (r2 >= r3) goto L_0x0153;
    L_0x0149:
        r4 = r8.get(r2);
        r10.free(r4);
        r2 = r2 + 1;
        goto L_0x0147;
    L_0x0153:
        r8.clear();
        r2 = r46 & 8;
        if (r2 != 0) goto L_0x01d9;
    L_0x015a:
        r2 = r46 & 1;
        if (r2 == 0) goto L_0x0161;
    L_0x015e:
        r27 = 1;
        goto L_0x0163;
    L_0x0161:
        r27 = 0;
    L_0x0163:
        r2 = r27;
        r3 = 0;
        r4 = -822083584; // 0xffffffffcf000000 float:-2.14748365E9 double:NaN;
        r5 = 0;
        r11 = r14.size;
        r32 = 0;
    L_0x016d:
        r33 = r32;
        r17 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r34 = r5;
        r5 = r33;
        if (r5 >= r11) goto L_0x01b9;
    L_0x0177:
        r18 = r14.get(r5);
        r6 = r18;
        r6 = (com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun) r6;
        r35 = r8;
        r8 = r6.f104y;
        r8 = (r8 > r4 ? 1 : (r8 == r4 ? 0 : -1));
        if (r8 == 0) goto L_0x01af;
    L_0x0187:
        r4 = r6.f104y;
        r8 = r45 - r3;
        if (r2 == 0) goto L_0x018f;
    L_0x018d:
        r8 = r8 / r17;
    L_0x018f:
        r17 = r8;
        r8 = r34;
    L_0x0193:
        if (r8 >= r5) goto L_0x01aa;
    L_0x0195:
        r18 = r8 + 1;
        r8 = r14.get(r8);
        r8 = (com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun) r8;
        r36 = r4;
        r4 = r8.f103x;
        r4 = r4 + r17;
        r8.f103x = r4;
        r8 = r18;
        r4 = r36;
        goto L_0x0193;
    L_0x01aa:
        r36 = r4;
        r3 = 0;
        r34 = r8;
    L_0x01af:
        r8 = r6.width;
        r3 = r3 + r8;
        r32 = r5 + 1;
        r5 = r34;
        r8 = r35;
        goto L_0x016d;
    L_0x01b9:
        r35 = r8;
        r5 = r45 - r3;
        if (r2 == 0) goto L_0x01c1;
    L_0x01bf:
        r5 = r5 / r17;
    L_0x01c1:
        r6 = r5;
        r5 = r34;
    L_0x01c4:
        if (r5 >= r11) goto L_0x01db;
    L_0x01c6:
        r8 = r5 + 1;
        r5 = r14.get(r5);
        r5 = (com.badlogic.gdx.graphics.g2d.GlyphLayout.GlyphRun) r5;
        r37 = r2;
        r2 = r5.f103x;
        r2 = r2 + r6;
        r5.f103x = r2;
        r5 = r8;
        r2 = r37;
        goto L_0x01c4;
    L_0x01d9:
        r35 = r8;
    L_0x01db:
        r7.width = r1;
        r2 = r15.capHeight;
        r3 = (float) r0;
        r4 = r15.lineHeight;
        r3 = r3 * r4;
        r2 = r2 + r3;
        r7.height = r2;
        return;
    L_0x01e8:
        r35 = r8;
        r30 = r11;
        r31 = r14;
        r10 = r24;
        r19 = r28;
        r22 = r29;
        r11 = r2;
        r24 = r3;
        r14 = r4;
        r28 = r5;
        r29 = r6;
        r0 = r13.glyphs;
        r1 = r12 + -1;
        r0 = r15.getWrapIndex(r0, r1);
        r1 = r13.f103x;
        r6 = 0;
        r1 = (r1 > r6 ? 1 : (r1 == r6 ? 0 : -1));
        if (r1 != 0) goto L_0x020d;
    L_0x020b:
        if (r0 == 0) goto L_0x0213;
    L_0x020d:
        r1 = r13.glyphs;
        r1 = r1.size;
        if (r0 < r1) goto L_0x0215;
    L_0x0213:
        r0 = r12 + -1;
    L_0x0215:
        r8 = r0;
        r0 = r7;
        r1 = r15;
        r2 = r13;
        r3 = r28;
        r4 = r8;
        r5 = r12;
        r0 = r0.wrap(r1, r2, r3, r4, r5);
        r14.add(r0);
        r1 = r13.f103x;
        r2 = r13.width;
        r1 = r1 + r2;
        r2 = java.lang.Math.max(r11, r1);
        r1 = 0;
        r3 = r15.down;
        r3 = r18 + r3;
        r20 = r20 + 1;
        r0.f103x = r6;
        r0.f104y = r3;
        r12 = -1;
        r4 = r0.xAdvances;
        r4 = r4.size;
        r5 = r0.xAdvances;
        r5 = r5.items;
        r13 = r0;
        r17 = r1;
        r18 = r3;
        r0 = r4;
        r25 = r5;
        goto L_0x027d;
    L_0x024b:
        r35 = r8;
        r30 = r11;
        r31 = r14;
        r10 = r24;
        r19 = r28;
        r22 = r29;
        r11 = r2;
        r24 = r3;
        r14 = r4;
        r28 = r5;
        r29 = r6;
        goto L_0x0274;
    L_0x0260:
        r19 = r0;
        r22 = r1;
        r28 = r5;
        r29 = r6;
        r35 = r8;
        r30 = r11;
        r31 = r14;
        r10 = r24;
        r11 = r2;
        r24 = r3;
        r14 = r4;
    L_0x0274:
        r0 = r13.width;
        r0 = r0 + r26;
        r13.width = r0;
        r2 = r11;
        r0 = r19;
    L_0x027d:
        r1 = 1;
        r12 = r12 + r1;
        r4 = r14;
        r1 = r22;
        r3 = r24;
        r5 = r28;
        r6 = r29;
        r11 = r30;
        r14 = r31;
        r8 = r35;
        r9 = r41;
        r24 = r10;
        r10 = r43;
        goto L_0x00d5;
    L_0x0296:
        r22 = r1;
        r28 = r5;
        r29 = r6;
        r35 = r8;
        r30 = r11;
        r31 = r14;
        r10 = r24;
        r11 = r2;
        r24 = r3;
        r14 = r4;
        r12 = r17;
        r13 = r18;
        goto L_0x02c1;
    L_0x02ad:
        r10 = r0;
        r22 = r1;
        r24 = r3;
        r28 = r5;
        r29 = r6;
        r35 = r8;
        r30 = r11;
        r31 = r14;
        r14 = r4;
        r11 = r17;
        r20 = r18;
    L_0x02c1:
        if (r21 == 0) goto L_0x02cd;
    L_0x02c3:
        r11 = java.lang.Math.max(r11, r12);
        r12 = 0;
        r0 = r15.down;
        r13 = r13 + r0;
        r20 = r20 + 1;
    L_0x02cd:
        r0 = r31;
        r1 = r16;
        r3 = r1;
        r17 = r11;
        r18 = r20;
        r1 = r0;
        goto L_0x02e6;
    L_0x02d8:
        r10 = r0;
        r22 = r1;
        r24 = r3;
        r28 = r5;
        r29 = r6;
        r35 = r8;
        r31 = r14;
        r14 = r4;
    L_0x02e6:
        r0 = r10;
        r4 = r14;
        r14 = r23;
        r5 = r28;
        r6 = r29;
        r2 = r31;
        r8 = r35;
        r9 = r41;
        r10 = r43;
        goto L_0x004c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.g2d.GlyphLayout.setText(com.badlogic.gdx.graphics.g2d.BitmapFont, java.lang.CharSequence, int, int, com.badlogic.gdx.graphics.Color, float, int, boolean, java.lang.String):void");
    }

    private void truncate(BitmapFontData fontData, GlyphRun run, float targetWidth, String truncate, int widthIndex, Pool<GlyphRun> glyphRunPool) {
        int i;
        GlyphRun truncateRun = (GlyphRun) glyphRunPool.obtain();
        fontData.getGlyphs(truncateRun, truncate, 0, truncate.length());
        float truncateWidth = 0.0f;
        for (i = 1; i < truncateRun.xAdvances.size; i++) {
            truncateWidth += truncateRun.xAdvances.get(i);
        }
        targetWidth -= truncateWidth;
        i = 0;
        float width = run.f103x;
        while (i < run.xAdvances.size) {
            float xAdvance = run.xAdvances.get(i);
            width += xAdvance;
            if (width > targetWidth) {
                run.width = (width - run.f103x) - xAdvance;
                break;
            }
            i++;
        }
        if (i > 1) {
            run.glyphs.truncate(i - 1);
            run.xAdvances.truncate(i);
            adjustLastGlyph(fontData, run);
            run.xAdvances.addAll(truncateRun.xAdvances, 1, truncateRun.xAdvances.size - 1);
        } else {
            run.glyphs.clear();
            run.xAdvances.clear();
            run.xAdvances.addAll(truncateRun.xAdvances);
            run.width += truncateRun.xAdvances.get(0);
        }
        run.glyphs.addAll(truncateRun.glyphs);
        run.width += truncateWidth;
        glyphRunPool.free(truncateRun);
    }

    private GlyphRun wrap(BitmapFontData fontData, GlyphRun first, Pool<GlyphRun> glyphRunPool, int wrapIndex, int widthIndex) {
        GlyphRun second = (GlyphRun) glyphRunPool.obtain();
        second.color.set(first.color);
        int glyphCount = first.glyphs.size;
        int endIndex = wrapIndex;
        while (endIndex > 0) {
            if (!fontData.isWhitespace((char) ((Glyph) first.glyphs.get(endIndex - 1)).id)) {
                break;
            }
            endIndex--;
        }
        int startIndex = wrapIndex;
        while (startIndex < glyphCount) {
            if (!fontData.isWhitespace((char) ((Glyph) first.glyphs.get(startIndex)).id)) {
                break;
            }
            startIndex++;
        }
        if (startIndex < glyphCount) {
            second.glyphs.addAll(first.glyphs, startIndex, glyphCount - startIndex);
            second.xAdvances.add((((float) (-((Glyph) second.glyphs.first()).xoffset)) * fontData.scaleX) - fontData.padLeft);
            second.xAdvances.addAll(first.xAdvances, startIndex + 1, first.xAdvances.size - (startIndex + 1));
        }
        while (widthIndex < endIndex) {
            int widthIndex2 = widthIndex + 1;
            first.width += first.xAdvances.get(widthIndex);
            widthIndex = widthIndex2;
        }
        while (widthIndex > endIndex + 1) {
            widthIndex--;
            first.width -= first.xAdvances.get(widthIndex);
        }
        if (endIndex == 0) {
            glyphRunPool.free(first);
            this.runs.pop();
        } else {
            first.glyphs.truncate(endIndex);
            first.xAdvances.truncate(endIndex + 1);
            adjustLastGlyph(fontData, first);
        }
        return second;
    }

    private void adjustLastGlyph(BitmapFontData fontData, GlyphRun run) {
        Glyph last = (Glyph) run.glyphs.peek();
        if (!fontData.isWhitespace((char) last.id)) {
            float width = (((float) (last.xoffset + last.width)) * fontData.scaleX) - fontData.padRight;
            run.width += width - run.xAdvances.peek();
            run.xAdvances.set(run.xAdvances.size - 1, width);
        }
    }

    private int parseColorMarkup(CharSequence str, int start, int end, Pool<Color> colorPool) {
        if (start == end) {
            return -1;
        }
        char charAt = str.charAt(start);
        int colorInt;
        int i;
        Color color;
        if (charAt == '#') {
            colorInt = 0;
            i = start + 1;
            while (i < end) {
                char ch = str.charAt(i);
                if (ch != Constants.REMIX_URL_SUFIX_INDICATOR) {
                    int i2;
                    if (ch >= '0' && ch <= '9') {
                        i2 = (colorInt * 16) + (ch - 48);
                    } else if (ch < 'a' || ch > 'f') {
                        if (ch < 'A' || ch > 'F') {
                            break;
                        }
                        i2 = (colorInt * 16) + (ch - 55);
                    } else {
                        i2 = (colorInt * 16) + (ch - 87);
                    }
                    colorInt = i2;
                    i++;
                } else if (i >= start + 2) {
                    if (i <= start + 9) {
                        if (i - start <= 7) {
                            for (int ii = 0; ii < 9 - (i - start); ii++) {
                                colorInt <<= 4;
                            }
                            colorInt |= 255;
                        }
                        color = (Color) colorPool.obtain();
                        colorStack.add(color);
                        Color.rgba8888ToColor(color, colorInt);
                        return i - start;
                    }
                }
            }
            return -1;
        } else if (charAt == Constants.REMIX_URL_PREFIX_INDICATOR) {
            return -1;
        } else {
            if (charAt != Constants.REMIX_URL_SUFIX_INDICATOR) {
                colorInt = start;
                i = start + 1;
                while (i < end) {
                    if (str.charAt(i) != Constants.REMIX_URL_SUFIX_INDICATOR) {
                        i++;
                    } else {
                        Color namedColor = Colors.get(str.subSequence(colorInt, i).toString());
                        if (namedColor == null) {
                            return -1;
                        }
                        color = (Color) colorPool.obtain();
                        colorStack.add(color);
                        color.set(namedColor);
                        return i - start;
                    }
                }
                return -1;
            }
            if (colorStack.size > 1) {
                colorPool.free(colorStack.pop());
            }
            return 0;
        }
    }

    public void reset() {
        Pools.get(GlyphRun.class).freeAll(this.runs);
        this.runs.clear();
        this.width = 0.0f;
        this.height = 0.0f;
    }

    public String toString() {
        if (this.runs.size == 0) {
            return "";
        }
        StringBuilder buffer = new StringBuilder(128);
        buffer.append(this.width);
        buffer.append('x');
        buffer.append(this.height);
        buffer.append('\n');
        int n = this.runs.size;
        for (int i = 0; i < n; i++) {
            buffer.append(((GlyphRun) this.runs.get(i)).toString());
            buffer.append('\n');
        }
        buffer.setLength(buffer.length() - 1);
        return buffer.toString();
    }
}
