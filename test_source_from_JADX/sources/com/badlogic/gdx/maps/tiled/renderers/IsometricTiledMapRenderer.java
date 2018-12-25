package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class IsometricTiledMapRenderer extends BatchTiledMapRenderer {
    private Vector2 bottomLeft = new Vector2();
    private Vector2 bottomRight = new Vector2();
    private Matrix4 invIsotransform;
    private Matrix4 isoTransform;
    private Vector3 screenPos = new Vector3();
    private Vector2 topLeft = new Vector2();
    private Vector2 topRight = new Vector2();

    public IsometricTiledMapRenderer(TiledMap map) {
        super(map);
        init();
    }

    public IsometricTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
        init();
    }

    public IsometricTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
        init();
    }

    public IsometricTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
        init();
    }

    private void init() {
        this.isoTransform = new Matrix4();
        this.isoTransform.idt();
        this.isoTransform.scale((float) (Math.sqrt(2.0d) / 2.0d), (float) (Math.sqrt(2.0d) / 4.0d), 1.0f);
        this.isoTransform.rotate(0.0f, 0.0f, 1.0f, -45.0f);
        this.invIsotransform = new Matrix4(this.isoTransform);
        this.invIsotransform.inv();
    }

    private Vector3 translateScreenToIso(Vector2 vec) {
        this.screenPos.set(vec.f16x, vec.f17y, 0.0f);
        this.screenPos.mul(this.invIsotransform);
        return this.screenPos;
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        float color;
        float tileWidth;
        float tileHeight;
        float halfTileHeight;
        float halfTileWidth;
        int row1;
        Color batchColor = this.batch.getColor();
        float color2 = Color.toFloatBits(batchColor.f4r, batchColor.f3g, batchColor.f2b, batchColor.f1a * layer.getOpacity());
        float tileWidth2 = layer.getTileWidth() * this.unitScale;
        float tileHeight2 = layer.getTileHeight() * this.unitScale;
        float halfTileWidth2 = tileWidth2 * 0.5f;
        float halfTileHeight2 = 0.5f * tileHeight2;
        this.topRight.set(this.viewBounds.f12x + this.viewBounds.width, this.viewBounds.f13y);
        this.bottomLeft.set(this.viewBounds.f12x, this.viewBounds.f13y + this.viewBounds.height);
        this.topLeft.set(this.viewBounds.f12x, this.viewBounds.f13y);
        this.bottomRight.set(this.viewBounds.f12x + this.viewBounds.width, this.viewBounds.f13y + this.viewBounds.height);
        int row12 = ((int) (translateScreenToIso(this.topLeft).f121y / tileWidth2)) - 2;
        int col1 = ((int) (translateScreenToIso(this.bottomLeft).f120x / tileWidth2)) - 2;
        int col2 = ((int) (translateScreenToIso(this.topRight).f120x / tileWidth2)) + 2;
        int row = ((int) (translateScreenToIso(this.bottomRight).f121y / tileWidth2)) + 2;
        while (row >= row12) {
            IsometricTiledMapRenderer isometricTiledMapRenderer;
            int col = col1;
            while (col <= col2) {
                float x = (((float) col) * halfTileWidth2) + (((float) row) * halfTileWidth2);
                float y = (((float) row) * halfTileHeight2) - (((float) col) * halfTileHeight2);
                Color batchColor2 = batchColor;
                batchColor = layer.getCell(col, row);
                if (batchColor == null) {
                    color = color2;
                    tileWidth = tileWidth2;
                    tileHeight = tileHeight2;
                    halfTileHeight = halfTileHeight2;
                    halfTileWidth = halfTileWidth2;
                    row1 = row12;
                } else {
                    tileWidth = tileWidth2;
                    TiledMapTile tile = batchColor.getTile();
                    if (tile != null) {
                        float temp;
                        boolean flipX = batchColor.getFlipHorizontally();
                        boolean flipY = batchColor.getFlipVertically();
                        int rotations = batchColor.getRotation();
                        Color cell = batchColor;
                        batchColor = tile.getTextureRegion();
                        tileHeight = tileHeight2;
                        float x1 = x + (tile.getOffsetX() * isometricTiledMapRenderer.unitScale);
                        tileHeight2 = (tile.getOffsetY() * isometricTiledMapRenderer.unitScale) + y;
                        halfTileHeight = halfTileHeight2;
                        tileWidth2 = x1 + (((float) batchColor.getRegionWidth()) * isometricTiledMapRenderer.unitScale);
                        halfTileWidth = halfTileWidth2;
                        halfTileHeight2 = (((float) batchColor.getRegionHeight()) * isometricTiledMapRenderer.unitScale) + tileHeight2;
                        halfTileWidth2 = batchColor.getU();
                        float v1 = batchColor.getV2();
                        float u2 = batchColor.getU2();
                        float v2 = batchColor.getV();
                        row1 = row12;
                        isometricTiledMapRenderer.vertices[0] = x1;
                        isometricTiledMapRenderer.vertices[1] = tileHeight2;
                        isometricTiledMapRenderer.vertices[2] = color2;
                        isometricTiledMapRenderer.vertices[3] = halfTileWidth2;
                        isometricTiledMapRenderer.vertices[4] = v1;
                        isometricTiledMapRenderer.vertices[5] = x1;
                        isometricTiledMapRenderer.vertices[6] = halfTileHeight2;
                        isometricTiledMapRenderer.vertices[7] = color2;
                        isometricTiledMapRenderer.vertices[8] = halfTileWidth2;
                        isometricTiledMapRenderer.vertices[9] = v2;
                        isometricTiledMapRenderer.vertices[10] = tileWidth2;
                        isometricTiledMapRenderer.vertices[11] = halfTileHeight2;
                        isometricTiledMapRenderer.vertices[12] = color2;
                        isometricTiledMapRenderer.vertices[13] = u2;
                        isometricTiledMapRenderer.vertices[14] = v2;
                        isometricTiledMapRenderer.vertices[15] = tileWidth2;
                        isometricTiledMapRenderer.vertices[16] = tileHeight2;
                        isometricTiledMapRenderer.vertices[17] = color2;
                        isometricTiledMapRenderer.vertices[18] = u2;
                        isometricTiledMapRenderer.vertices[19] = v1;
                        if (flipX) {
                            temp = isometricTiledMapRenderer.vertices[3];
                            color = color2;
                            isometricTiledMapRenderer.vertices[3] = isometricTiledMapRenderer.vertices[13];
                            isometricTiledMapRenderer.vertices[13] = temp;
                            color2 = isometricTiledMapRenderer.vertices[8];
                            isometricTiledMapRenderer.vertices[8] = isometricTiledMapRenderer.vertices[18];
                            isometricTiledMapRenderer.vertices[18] = color2;
                        } else {
                            color = color2;
                        }
                        if (flipY) {
                            color2 = isometricTiledMapRenderer.vertices[4];
                            isometricTiledMapRenderer.vertices[4] = isometricTiledMapRenderer.vertices[14];
                            isometricTiledMapRenderer.vertices[14] = color2;
                            color2 = isometricTiledMapRenderer.vertices[9];
                            isometricTiledMapRenderer.vertices[9] = isometricTiledMapRenderer.vertices[19];
                            isometricTiledMapRenderer.vertices[19] = color2;
                        }
                        if (rotations != 0) {
                            switch (rotations) {
                                case 1:
                                    color2 = isometricTiledMapRenderer.vertices[4];
                                    isometricTiledMapRenderer.vertices[4] = isometricTiledMapRenderer.vertices[9];
                                    isometricTiledMapRenderer.vertices[9] = isometricTiledMapRenderer.vertices[14];
                                    isometricTiledMapRenderer.vertices[14] = isometricTiledMapRenderer.vertices[19];
                                    isometricTiledMapRenderer.vertices[19] = color2;
                                    temp = isometricTiledMapRenderer.vertices[3];
                                    float tempV = color2;
                                    isometricTiledMapRenderer.vertices[3] = isometricTiledMapRenderer.vertices[8];
                                    isometricTiledMapRenderer.vertices[8] = isometricTiledMapRenderer.vertices[13];
                                    isometricTiledMapRenderer.vertices[13] = isometricTiledMapRenderer.vertices[18];
                                    isometricTiledMapRenderer.vertices[18] = temp;
                                    break;
                                case 2:
                                    color2 = isometricTiledMapRenderer.vertices[3];
                                    isometricTiledMapRenderer.vertices[3] = isometricTiledMapRenderer.vertices[13];
                                    isometricTiledMapRenderer.vertices[13] = color2;
                                    color2 = isometricTiledMapRenderer.vertices[8];
                                    isometricTiledMapRenderer.vertices[8] = isometricTiledMapRenderer.vertices[18];
                                    isometricTiledMapRenderer.vertices[18] = color2;
                                    temp = isometricTiledMapRenderer.vertices[4];
                                    float tempU = color2;
                                    isometricTiledMapRenderer.vertices[4] = isometricTiledMapRenderer.vertices[14];
                                    isometricTiledMapRenderer.vertices[14] = temp;
                                    color2 = isometricTiledMapRenderer.vertices[9];
                                    isometricTiledMapRenderer.vertices[9] = isometricTiledMapRenderer.vertices[19];
                                    isometricTiledMapRenderer.vertices[19] = color2;
                                    break;
                                case 3:
                                    color2 = isometricTiledMapRenderer.vertices[4];
                                    isometricTiledMapRenderer.vertices[4] = isometricTiledMapRenderer.vertices[19];
                                    isometricTiledMapRenderer.vertices[19] = isometricTiledMapRenderer.vertices[14];
                                    isometricTiledMapRenderer.vertices[14] = isometricTiledMapRenderer.vertices[9];
                                    isometricTiledMapRenderer.vertices[9] = color2;
                                    temp = isometricTiledMapRenderer.vertices[3];
                                    float tempV2 = color2;
                                    isometricTiledMapRenderer.vertices[3] = isometricTiledMapRenderer.vertices[18];
                                    isometricTiledMapRenderer.vertices[18] = isometricTiledMapRenderer.vertices[13];
                                    isometricTiledMapRenderer.vertices[13] = isometricTiledMapRenderer.vertices[8];
                                    isometricTiledMapRenderer.vertices[8] = temp;
                                    break;
                                default:
                                    break;
                            }
                        }
                        Color color3 = batchColor;
                        isometricTiledMapRenderer.batch.draw(batchColor.getTexture(), isometricTiledMapRenderer.vertices, null, 20);
                    } else {
                        color = color2;
                        tileHeight = tileHeight2;
                        halfTileHeight = halfTileHeight2;
                        halfTileWidth = halfTileWidth2;
                        row1 = row12;
                    }
                }
                col++;
                batchColor = batchColor2;
                tileWidth2 = tileWidth;
                tileHeight2 = tileHeight;
                halfTileHeight2 = halfTileHeight;
                halfTileWidth2 = halfTileWidth;
                row12 = row1;
                color2 = color;
                isometricTiledMapRenderer = this;
            }
            color = color2;
            tileWidth = tileWidth2;
            tileHeight = tileHeight2;
            halfTileHeight = halfTileHeight2;
            halfTileWidth = halfTileWidth2;
            row1 = row12;
            row--;
            isometricTiledMapRenderer = this;
        }
        color = color2;
        tileWidth = tileWidth2;
        tileHeight = tileHeight2;
        halfTileHeight = halfTileHeight2;
        halfTileWidth = halfTileWidth2;
        row1 = row12;
    }
}
