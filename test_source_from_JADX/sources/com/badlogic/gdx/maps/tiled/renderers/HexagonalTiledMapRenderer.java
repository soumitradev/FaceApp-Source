package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;

public class HexagonalTiledMapRenderer extends BatchTiledMapRenderer {
    public HexagonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public HexagonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public HexagonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public HexagonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void renderTileLayer(com.badlogic.gdx.maps.tiled.TiledMapTileLayer r49) {
        /*
        r48 = this;
        r0 = r48;
        r1 = r0.batch;
        r1 = r1.getColor();
        r2 = r1.f4r;
        r3 = r1.f3g;
        r4 = r1.f2b;
        r5 = r1.f1a;
        r6 = r49.getOpacity();
        r5 = r5 * r6;
        r2 = com.badlogic.gdx.graphics.Color.toFloatBits(r2, r3, r4, r5);
        r3 = r49.getWidth();
        r4 = r49.getHeight();
        r5 = r49.getTileWidth();
        r6 = r0.unitScale;
        r5 = r5 * r6;
        r6 = r49.getTileHeight();
        r7 = r0.unitScale;
        r6 = r6 * r7;
        r7 = 1048576000; // 0x3e800000 float:0.25 double:5.180653787E-315;
        r7 = r7 * r5;
        r8 = 1061158912; // 0x3f400000 float:0.75 double:5.24282163E-315;
        r8 = r8 * r5;
        r9 = 1056964608; // 0x3f000000 float:0.5 double:5.222099017E-315;
        r9 = r9 * r6;
        r10 = 1069547520; // 0x3fc00000 float:1.5 double:5.28426686E-315;
        r10 = r10 * r6;
        r11 = r0.viewBounds;
        r11 = r11.f12x;
        r11 = r11 - r7;
        r11 = r11 / r8;
        r11 = (int) r11;
        r12 = 0;
        r11 = java.lang.Math.max(r12, r11);
        r13 = r0.viewBounds;
        r13 = r13.f12x;
        r14 = r0.viewBounds;
        r14 = r14.width;
        r13 = r13 + r14;
        r13 = r13 + r8;
        r13 = r13 / r8;
        r13 = (int) r13;
        r13 = java.lang.Math.min(r3, r13);
        r14 = r0.viewBounds;
        r14 = r14.f13y;
        r14 = r14 / r10;
        r14 = (int) r14;
        r14 = java.lang.Math.max(r12, r14);
        r15 = r0.viewBounds;
        r15 = r15.f13y;
        r12 = r0.viewBounds;
        r12 = r12.height;
        r15 = r15 + r12;
        r15 = r15 + r10;
        r15 = r15 / r6;
        r12 = (int) r15;
        r12 = java.lang.Math.min(r4, r12);
        r15 = r0.vertices;
        r17 = r14;
    L_0x007c:
        r18 = r17;
        r19 = r1;
        r1 = r18;
        if (r1 >= r12) goto L_0x021e;
    L_0x0084:
        r17 = r11;
    L_0x0086:
        r20 = r17;
        r21 = r3;
        r3 = r20;
        if (r3 >= r13) goto L_0x01ef;
    L_0x008e:
        r22 = r4;
        r4 = (float) r3;
        r4 = r4 * r8;
        r23 = r7;
        r7 = r3 % 2;
        r24 = r8;
        r8 = 1;
        if (r7 != r8) goto L_0x009e;
    L_0x009c:
        r7 = 0;
        goto L_0x009f;
    L_0x009e:
        r7 = r9;
    L_0x009f:
        r8 = (float) r1;
        r8 = r8 * r6;
        r7 = r7 + r8;
        r8 = r49;
        r26 = r6;
        r6 = r8.getCell(r3, r1);
        if (r6 != 0) goto L_0x00bc;
    L_0x00ad:
        r4 = r4 + r5;
        r46 = r2;
        r27 = r5;
    L_0x00b3:
        r28 = r9;
        r30 = r10;
        r36 = r11;
        r4 = 0;
        goto L_0x01d5;
    L_0x00bc:
        r27 = r5;
        r5 = r6.getTile();
        if (r5 == 0) goto L_0x01cc;
    L_0x00c4:
        r8 = r5 instanceof com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
        if (r8 == 0) goto L_0x00cb;
    L_0x00c8:
        r46 = r2;
        goto L_0x00b3;
    L_0x00cb:
        r8 = r6.getFlipHorizontally();
        r17 = r6.getFlipVertically();
        r28 = r9;
        r9 = r6.getRotation();
        r29 = r6;
        r6 = r5.getTextureRegion();
        r18 = r5.getOffsetX();
        r30 = r10;
        r10 = r0.unitScale;
        r18 = r18 * r10;
        r18 = r4 + r18;
        r10 = r5.getOffsetY();
        r31 = r4;
        r4 = r0.unitScale;
        r10 = r10 * r4;
        r10 = r10 + r7;
        r4 = r6.getRegionWidth();
        r4 = (float) r4;
        r32 = r5;
        r5 = r0.unitScale;
        r4 = r4 * r5;
        r4 = r18 + r4;
        r5 = r6.getRegionHeight();
        r5 = (float) r5;
        r33 = r7;
        r7 = r0.unitScale;
        r5 = r5 * r7;
        r5 = r5 + r10;
        r7 = r6.getU();
        r20 = r6.getV2();
        r34 = r6.getU2();
        r35 = r6.getV();
        r16 = 0;
        r15[r16] = r18;
        r25 = 1;
        r15[r25] = r10;
        r36 = r11;
        r11 = 2;
        r15[r11] = r2;
        r25 = 3;
        r15[r25] = r7;
        r37 = 4;
        r15[r37] = r20;
        r38 = 5;
        r15[r38] = r18;
        r38 = 6;
        r15[r38] = r5;
        r38 = 7;
        r15[r38] = r2;
        r38 = 8;
        r15[r38] = r7;
        r39 = 9;
        r15[r39] = r35;
        r40 = 10;
        r15[r40] = r4;
        r40 = 11;
        r15[r40] = r5;
        r40 = 12;
        r15[r40] = r2;
        r40 = 13;
        r15[r40] = r34;
        r41 = 14;
        r15[r41] = r35;
        r42 = 15;
        r15[r42] = r4;
        r42 = 16;
        r15[r42] = r10;
        r42 = 17;
        r15[r42] = r2;
        r42 = 18;
        r15[r42] = r34;
        r43 = 19;
        r15[r43] = r20;
        if (r8 == 0) goto L_0x0182;
    L_0x0172:
        r44 = r15[r25];
        r45 = r15[r40];
        r15[r25] = r45;
        r15[r40] = r44;
        r44 = r15[r38];
        r45 = r15[r42];
        r15[r38] = r45;
        r15[r42] = r44;
    L_0x0182:
        if (r17 == 0) goto L_0x0194;
    L_0x0184:
        r44 = r15[r37];
        r45 = r15[r41];
        r15[r37] = r45;
        r15[r41] = r44;
        r44 = r15[r39];
        r45 = r15[r43];
        r15[r39] = r45;
        r15[r43] = r44;
    L_0x0194:
        if (r9 != r11) goto L_0x01bb;
    L_0x0196:
        r11 = r15[r25];
        r44 = r15[r40];
        r15[r25] = r44;
        r15[r40] = r11;
        r11 = r15[r38];
        r25 = r15[r42];
        r15[r38] = r25;
        r15[r42] = r11;
        r25 = r15[r37];
        r38 = r15[r41];
        r15[r37] = r38;
        r15[r41] = r25;
        r25 = r15[r39];
        r37 = r15[r43];
        r15[r39] = r37;
        r15[r43] = r25;
        r46 = r2;
        r4 = 0;
        goto L_0x0202;
    L_0x01bb:
        r11 = r0.batch;
        r0 = r6.getTexture();
        r46 = r2;
        r2 = 20;
        r47 = r4;
        r4 = 0;
        r11.draw(r0, r15, r4, r2);
        goto L_0x01d5;
    L_0x01cc:
        r46 = r2;
        r28 = r9;
        r30 = r10;
        r36 = r11;
        r4 = 0;
    L_0x01d5:
        r17 = r3 + 1;
        r3 = r21;
        r4 = r22;
        r7 = r23;
        r8 = r24;
        r6 = r26;
        r5 = r27;
        r9 = r28;
        r10 = r30;
        r11 = r36;
        r2 = r46;
        r0 = r48;
        goto L_0x0086;
    L_0x01ef:
        r46 = r2;
        r22 = r4;
        r27 = r5;
        r26 = r6;
        r23 = r7;
        r24 = r8;
        r28 = r9;
        r30 = r10;
        r36 = r11;
        r4 = 0;
    L_0x0202:
        r17 = r1 + 1;
        r1 = r19;
        r3 = r21;
        r4 = r22;
        r7 = r23;
        r8 = r24;
        r6 = r26;
        r5 = r27;
        r9 = r28;
        r10 = r30;
        r11 = r36;
        r2 = r46;
        r0 = r48;
        goto L_0x007c;
    L_0x021e:
        r46 = r2;
        r21 = r3;
        r22 = r4;
        r27 = r5;
        r26 = r6;
        r23 = r7;
        r24 = r8;
        r28 = r9;
        r30 = r10;
        r36 = r11;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.maps.tiled.renderers.HexagonalTiledMapRenderer.renderTileLayer(com.badlogic.gdx.maps.tiled.TiledMapTileLayer):void");
    }
}
