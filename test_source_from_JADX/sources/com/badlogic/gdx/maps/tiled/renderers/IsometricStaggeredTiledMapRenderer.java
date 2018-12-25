package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class IsometricStaggeredTiledMapRenderer extends BatchTiledMapRenderer {
    public IsometricStaggeredTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public IsometricStaggeredTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        int layerWidth;
        float color;
        int layerHeight;
        float layerTileWidth;
        float layerTileHeight;
        float layerTileHeight50;
        float layerTileWidth50;
        Color batchColor = this.batch.getColor();
        float color2 = Color.toFloatBits(batchColor.f4r, batchColor.f3g, batchColor.f2b, batchColor.f1a * layer.getOpacity());
        int layerWidth2 = layer.getWidth();
        int layerHeight2 = layer.getHeight();
        float layerTileWidth2 = layer.getTileWidth() * this.unitScale;
        float layerTileHeight2 = layer.getTileHeight() * this.unitScale;
        float layerTileWidth502 = layerTileWidth2 * 0.5f;
        float layerTileHeight502 = 0.5f * layerTileHeight2;
        int minX = Math.max(0, (int) ((this.viewBounds.f12x - layerTileWidth502) / layerTileWidth2));
        int maxX = Math.min(layerWidth2, (int) ((((this.viewBounds.f12x + this.viewBounds.width) + layerTileWidth2) + layerTileWidth502) / layerTileWidth2));
        int minY = Math.max(0, (int) ((this.viewBounds.f13y - layerTileHeight2) / layerTileHeight2));
        int y = Math.min(layerHeight2, (int) (((this.viewBounds.f13y + this.viewBounds.height) + layerTileHeight2) / layerTileHeight502)) - 1;
        while (y >= minY) {
            float offsetX = y % 2 == 1 ? layerTileWidth502 : 0.0f;
            int x = maxX - 1;
            while (true) {
                int x2 = x;
                IsometricStaggeredTiledMapRenderer isometricStaggeredTiledMapRenderer;
                if (x2 >= minX) {
                    Color batchColor2 = batchColor;
                    layerWidth = layerWidth2;
                    Cell cell = layer.getCell(x2, y);
                    if (cell == null) {
                        color = color2;
                        layerHeight = layerHeight2;
                        layerTileWidth = layerTileWidth2;
                        layerTileHeight = layerTileHeight2;
                        layerTileHeight50 = layerTileHeight502;
                        layerTileWidth50 = layerTileWidth502;
                    } else {
                        batchColor = cell.getTile();
                        if (batchColor != null) {
                            boolean flipX = cell.getFlipHorizontally();
                            boolean flipY = cell.getFlipVertically();
                            int rotations = cell.getRotation();
                            TextureRegion region = batchColor.getTextureRegion();
                            layerHeight = layerHeight2;
                            layerTileWidth = layerTileWidth2;
                            layerHeight2 = ((((float) x2) * layerTileWidth2) - offsetX) + (batchColor.getOffsetX() * isometricStaggeredTiledMapRenderer.unitScale);
                            Color tile = batchColor;
                            layerTileWidth2 = (((float) y) * layerTileHeight502) + (batchColor.getOffsetY() * isometricStaggeredTiledMapRenderer.unitScale);
                            layerTileHeight = layerTileHeight2;
                            batchColor = (((float) region.getRegionWidth()) * isometricStaggeredTiledMapRenderer.unitScale) + layerHeight2;
                            layerTileHeight50 = layerTileHeight502;
                            layerTileHeight2 = (((float) region.getRegionHeight()) * isometricStaggeredTiledMapRenderer.unitScale) + layerTileWidth2;
                            layerTileHeight502 = region.getU();
                            float v1 = region.getV2();
                            float u2 = region.getU2();
                            float v2 = region.getV();
                            layerTileWidth50 = layerTileWidth502;
                            isometricStaggeredTiledMapRenderer.vertices[0] = layerHeight2;
                            isometricStaggeredTiledMapRenderer.vertices[1] = layerTileWidth2;
                            isometricStaggeredTiledMapRenderer.vertices[2] = color2;
                            isometricStaggeredTiledMapRenderer.vertices[3] = layerTileHeight502;
                            isometricStaggeredTiledMapRenderer.vertices[4] = v1;
                            isometricStaggeredTiledMapRenderer.vertices[5] = layerHeight2;
                            isometricStaggeredTiledMapRenderer.vertices[6] = layerTileHeight2;
                            isometricStaggeredTiledMapRenderer.vertices[7] = color2;
                            isometricStaggeredTiledMapRenderer.vertices[8] = layerTileHeight502;
                            isometricStaggeredTiledMapRenderer.vertices[9] = v2;
                            isometricStaggeredTiledMapRenderer.vertices[10] = batchColor;
                            isometricStaggeredTiledMapRenderer.vertices[11] = layerTileHeight2;
                            isometricStaggeredTiledMapRenderer.vertices[12] = color2;
                            isometricStaggeredTiledMapRenderer.vertices[13] = u2;
                            isometricStaggeredTiledMapRenderer.vertices[14] = v2;
                            isometricStaggeredTiledMapRenderer.vertices[15] = batchColor;
                            isometricStaggeredTiledMapRenderer.vertices[16] = layerTileWidth2;
                            isometricStaggeredTiledMapRenderer.vertices[17] = color2;
                            isometricStaggeredTiledMapRenderer.vertices[18] = u2;
                            isometricStaggeredTiledMapRenderer.vertices[19] = v1;
                            float x22;
                            if (flipX) {
                                layerTileWidth502 = isometricStaggeredTiledMapRenderer.vertices[3];
                                x22 = batchColor;
                                color = color2;
                                isometricStaggeredTiledMapRenderer.vertices[3] = isometricStaggeredTiledMapRenderer.vertices[13];
                                isometricStaggeredTiledMapRenderer.vertices[13] = layerTileWidth502;
                                batchColor = isometricStaggeredTiledMapRenderer.vertices[8];
                                isometricStaggeredTiledMapRenderer.vertices[8] = isometricStaggeredTiledMapRenderer.vertices[18];
                                isometricStaggeredTiledMapRenderer.vertices[18] = batchColor;
                            } else {
                                x22 = batchColor;
                                color = color2;
                            }
                            if (flipY) {
                                batchColor = isometricStaggeredTiledMapRenderer.vertices[4];
                                isometricStaggeredTiledMapRenderer.vertices[4] = isometricStaggeredTiledMapRenderer.vertices[14];
                                isometricStaggeredTiledMapRenderer.vertices[14] = batchColor;
                                batchColor = isometricStaggeredTiledMapRenderer.vertices[9];
                                isometricStaggeredTiledMapRenderer.vertices[9] = isometricStaggeredTiledMapRenderer.vertices[19];
                                isometricStaggeredTiledMapRenderer.vertices[19] = batchColor;
                            }
                            if (rotations != 0) {
                                switch (rotations) {
                                    case 1:
                                        batchColor = isometricStaggeredTiledMapRenderer.vertices[4];
                                        isometricStaggeredTiledMapRenderer.vertices[4] = isometricStaggeredTiledMapRenderer.vertices[9];
                                        isometricStaggeredTiledMapRenderer.vertices[9] = isometricStaggeredTiledMapRenderer.vertices[14];
                                        isometricStaggeredTiledMapRenderer.vertices[14] = isometricStaggeredTiledMapRenderer.vertices[19];
                                        isometricStaggeredTiledMapRenderer.vertices[19] = batchColor;
                                        color2 = isometricStaggeredTiledMapRenderer.vertices[3];
                                        float tempV = batchColor;
                                        isometricStaggeredTiledMapRenderer.vertices[3] = isometricStaggeredTiledMapRenderer.vertices[8];
                                        isometricStaggeredTiledMapRenderer.vertices[8] = isometricStaggeredTiledMapRenderer.vertices[13];
                                        isometricStaggeredTiledMapRenderer.vertices[13] = isometricStaggeredTiledMapRenderer.vertices[18];
                                        isometricStaggeredTiledMapRenderer.vertices[18] = color2;
                                        break;
                                    case 2:
                                        batchColor = isometricStaggeredTiledMapRenderer.vertices[3];
                                        isometricStaggeredTiledMapRenderer.vertices[3] = isometricStaggeredTiledMapRenderer.vertices[13];
                                        isometricStaggeredTiledMapRenderer.vertices[13] = batchColor;
                                        batchColor = isometricStaggeredTiledMapRenderer.vertices[8];
                                        isometricStaggeredTiledMapRenderer.vertices[8] = isometricStaggeredTiledMapRenderer.vertices[18];
                                        isometricStaggeredTiledMapRenderer.vertices[18] = batchColor;
                                        color2 = isometricStaggeredTiledMapRenderer.vertices[4];
                                        float tempU = batchColor;
                                        isometricStaggeredTiledMapRenderer.vertices[4] = isometricStaggeredTiledMapRenderer.vertices[14];
                                        isometricStaggeredTiledMapRenderer.vertices[14] = color2;
                                        batchColor = isometricStaggeredTiledMapRenderer.vertices[9];
                                        isometricStaggeredTiledMapRenderer.vertices[9] = isometricStaggeredTiledMapRenderer.vertices[19];
                                        isometricStaggeredTiledMapRenderer.vertices[19] = batchColor;
                                        break;
                                    case 3:
                                        batchColor = isometricStaggeredTiledMapRenderer.vertices[4];
                                        isometricStaggeredTiledMapRenderer.vertices[4] = isometricStaggeredTiledMapRenderer.vertices[19];
                                        isometricStaggeredTiledMapRenderer.vertices[19] = isometricStaggeredTiledMapRenderer.vertices[14];
                                        isometricStaggeredTiledMapRenderer.vertices[14] = isometricStaggeredTiledMapRenderer.vertices[9];
                                        isometricStaggeredTiledMapRenderer.vertices[9] = batchColor;
                                        color2 = isometricStaggeredTiledMapRenderer.vertices[3];
                                        float tempV2 = batchColor;
                                        isometricStaggeredTiledMapRenderer.vertices[3] = isometricStaggeredTiledMapRenderer.vertices[18];
                                        isometricStaggeredTiledMapRenderer.vertices[18] = isometricStaggeredTiledMapRenderer.vertices[13];
                                        isometricStaggeredTiledMapRenderer.vertices[13] = isometricStaggeredTiledMapRenderer.vertices[8];
                                        isometricStaggeredTiledMapRenderer.vertices[8] = color2;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            TextureRegion textureRegion = region;
                            isometricStaggeredTiledMapRenderer.batch.draw(region.getTexture(), isometricStaggeredTiledMapRenderer.vertices, null, 20);
                        } else {
                            color = color2;
                            layerHeight = layerHeight2;
                            layerTileWidth = layerTileWidth2;
                            layerTileHeight = layerTileHeight2;
                            layerTileHeight50 = layerTileHeight502;
                            layerTileWidth50 = layerTileWidth502;
                        }
                    }
                    x = x2 - 1;
                    batchColor = batchColor2;
                    layerWidth2 = layerWidth;
                    layerHeight2 = layerHeight;
                    layerTileWidth2 = layerTileWidth;
                    layerTileHeight2 = layerTileHeight;
                    layerTileHeight502 = layerTileHeight50;
                    layerTileWidth502 = layerTileWidth50;
                    color2 = color;
                    isometricStaggeredTiledMapRenderer = this;
                } else {
                    color = color2;
                    layerHeight = layerHeight2;
                    layerTileWidth = layerTileWidth2;
                    layerTileHeight = layerTileHeight2;
                    layerTileHeight50 = layerTileHeight502;
                    layerTileWidth50 = layerTileWidth502;
                    y--;
                    layerWidth2 = layerWidth2;
                    isometricStaggeredTiledMapRenderer = this;
                }
            }
        }
        color = color2;
        layerWidth = layerWidth2;
        layerHeight = layerHeight2;
        layerTileWidth = layerTileWidth2;
        layerTileHeight = layerTileHeight2;
        layerTileHeight50 = layerTileHeight502;
        layerTileWidth50 = layerTileWidth502;
    }
}
