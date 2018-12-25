package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

public class OrthogonalTiledMapRenderer extends BatchTiledMapRenderer {
    public OrthogonalTiledMapRenderer(TiledMap map) {
        super(map);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, Batch batch) {
        super(map, batch);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale) {
        super(map, unitScale);
    }

    public OrthogonalTiledMapRenderer(TiledMap map, float unitScale, Batch batch) {
        super(map, unitScale, batch);
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        int layerWidth;
        float color;
        int layerHeight;
        int col1;
        int col2;
        Color batchColor = this.batch.getColor();
        float color2 = Color.toFloatBits(batchColor.f4r, batchColor.f3g, batchColor.f2b, batchColor.f1a * layer.getOpacity());
        int layerWidth2 = layer.getWidth();
        int layerHeight2 = layer.getHeight();
        float layerTileWidth = layer.getTileWidth() * this.unitScale;
        float layerTileHeight = layer.getTileHeight() * this.unitScale;
        int col12 = Math.max(0, (int) (this.viewBounds.f12x / layerTileWidth));
        int col22 = Math.min(layerWidth2, (int) (((this.viewBounds.f12x + this.viewBounds.width) + layerTileWidth) / layerTileWidth));
        int row1 = Math.max(0, (int) (this.viewBounds.f13y / layerTileHeight));
        int row2 = Math.min(layerHeight2, (int) (((this.viewBounds.f13y + this.viewBounds.height) + layerTileHeight) / layerTileHeight));
        float y = ((float) row2) * layerTileHeight;
        float xStart = ((float) col12) * layerTileWidth;
        float[] vertices = this.vertices;
        float y2 = y;
        int row = row2;
        while (row >= row1) {
            float x = xStart;
            int col = col12;
            while (true) {
                int col3 = col;
                OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
                if (col3 < col22) {
                    Color batchColor2 = batchColor;
                    layerWidth = layerWidth2;
                    Cell cell = layer.getCell(col3, row);
                    if (cell == null) {
                        x += layerTileWidth;
                        color = color2;
                        layerHeight = layerHeight2;
                        col1 = col12;
                        col2 = col22;
                    } else {
                        batchColor = cell.getTile();
                        Color tile;
                        if (batchColor != null) {
                            float temp;
                            boolean flipX = cell.getFlipHorizontally();
                            boolean flipY = cell.getFlipVertically();
                            int rotations = cell.getRotation();
                            TextureRegion region = batchColor.getTextureRegion();
                            layerHeight = layerHeight2;
                            float x1 = x + (batchColor.getOffsetX() * orthogonalTiledMapRenderer.unitScale);
                            tile = batchColor;
                            layerHeight2 = (batchColor.getOffsetY() * orthogonalTiledMapRenderer.unitScale) + y2;
                            col1 = col12;
                            batchColor = x1 + (((float) region.getRegionWidth()) * orthogonalTiledMapRenderer.unitScale);
                            col2 = col22;
                            col12 = (((float) region.getRegionHeight()) * orthogonalTiledMapRenderer.unitScale) + layerHeight2;
                            col22 = region.getU();
                            float v1 = region.getV2();
                            float u2 = region.getU2();
                            float v2 = region.getV();
                            vertices[0] = x1;
                            vertices[1] = layerHeight2;
                            vertices[2] = color2;
                            vertices[3] = col22;
                            vertices[4] = v1;
                            vertices[5] = x1;
                            vertices[6] = col12;
                            vertices[7] = color2;
                            vertices[8] = col22;
                            vertices[9] = v2;
                            vertices[10] = batchColor;
                            vertices[11] = col12;
                            vertices[12] = color2;
                            vertices[13] = u2;
                            vertices[14] = v2;
                            vertices[15] = batchColor;
                            vertices[16] = layerHeight2;
                            vertices[17] = color2;
                            vertices[18] = u2;
                            vertices[19] = v1;
                            if (flipX) {
                                temp = vertices[3];
                                vertices[3] = vertices[13];
                                vertices[13] = temp;
                                temp = vertices[8];
                                vertices[8] = vertices[18];
                                vertices[18] = temp;
                            }
                            if (flipY) {
                                temp = vertices[4];
                                vertices[4] = vertices[14];
                                vertices[14] = temp;
                                temp = vertices[9];
                                vertices[9] = vertices[19];
                                vertices[19] = temp;
                            }
                            if (rotations != 0) {
                                float tempU;
                                switch (rotations) {
                                    case 1:
                                        temp = vertices[4];
                                        vertices[4] = vertices[9];
                                        vertices[9] = vertices[14];
                                        vertices[14] = vertices[19];
                                        vertices[19] = temp;
                                        tempU = vertices[3];
                                        vertices[3] = vertices[8];
                                        vertices[8] = vertices[13];
                                        vertices[13] = vertices[18];
                                        vertices[18] = tempU;
                                        break;
                                    case 2:
                                        temp = vertices[3];
                                        vertices[3] = vertices[13];
                                        vertices[13] = temp;
                                        float tempU2 = vertices[8];
                                        vertices[8] = vertices[18];
                                        vertices[18] = tempU2;
                                        float tempV = vertices[4];
                                        vertices[4] = vertices[14];
                                        vertices[14] = tempV;
                                        tempU = vertices[9];
                                        vertices[9] = vertices[19];
                                        vertices[19] = tempU;
                                        break;
                                    case 3:
                                        temp = vertices[4];
                                        vertices[4] = vertices[19];
                                        vertices[19] = vertices[14];
                                        vertices[14] = vertices[9];
                                        vertices[9] = temp;
                                        tempU = vertices[3];
                                        vertices[3] = vertices[18];
                                        vertices[18] = vertices[13];
                                        vertices[13] = vertices[8];
                                        vertices[8] = tempU;
                                        break;
                                    default:
                                        break;
                                }
                            }
                            float x2 = batchColor;
                            color = color2;
                            TextureRegion textureRegion = region;
                            orthogonalTiledMapRenderer.batch.draw(region.getTexture(), vertices, null, 20);
                        } else {
                            tile = batchColor;
                            color = color2;
                            Cell cell2 = cell;
                            layerHeight = layerHeight2;
                            col1 = col12;
                            col2 = col22;
                        }
                        x += layerTileWidth;
                    }
                    col = col3 + 1;
                    batchColor = batchColor2;
                    layerWidth2 = layerWidth;
                    layerHeight2 = layerHeight;
                    col12 = col1;
                    col22 = col2;
                    color2 = color;
                    orthogonalTiledMapRenderer = this;
                } else {
                    color = color2;
                    layerHeight = layerHeight2;
                    col1 = col12;
                    col2 = col22;
                    y2 -= layerTileHeight;
                    row--;
                    layerWidth2 = layerWidth2;
                    orthogonalTiledMapRenderer = this;
                }
            }
        }
        color = color2;
        layerWidth = layerWidth2;
        layerHeight = layerHeight2;
        col1 = col12;
        col2 = col22;
    }
}
