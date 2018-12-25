package com.badlogic.gdx.maps.tiled.renderers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapImageLayer;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Disposable;
import java.util.Iterator;

public class OrthoCachedTiledMapRenderer implements TiledMapRenderer, Disposable {
    protected static final int NUM_VERTICES = 20;
    private static final float tolerance = 1.0E-5f;
    protected boolean blending;
    protected final Rectangle cacheBounds;
    protected boolean cached;
    protected boolean canCacheMoreE;
    protected boolean canCacheMoreN;
    protected boolean canCacheMoreS;
    protected boolean canCacheMoreW;
    protected int count;
    protected final TiledMap map;
    protected float maxTileHeight;
    protected float maxTileWidth;
    protected float overCache;
    protected final SpriteCache spriteCache;
    protected float unitScale;
    protected final float[] vertices;
    protected final Rectangle viewBounds;

    public OrthoCachedTiledMapRenderer(TiledMap map) {
        this(map, 1.0f, 2000);
    }

    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale) {
        this(map, unitScale, 2000);
    }

    public OrthoCachedTiledMapRenderer(TiledMap map, float unitScale, int cacheSize) {
        this.vertices = new float[20];
        this.viewBounds = new Rectangle();
        this.cacheBounds = new Rectangle();
        this.overCache = 0.5f;
        this.map = map;
        this.unitScale = unitScale;
        this.spriteCache = new SpriteCache(cacheSize, true);
    }

    public void setView(OrthographicCamera camera) {
        this.spriteCache.setProjectionMatrix(camera.combined);
        float width = (camera.viewportWidth * camera.zoom) + ((this.maxTileWidth * 2.0f) * this.unitScale);
        float height = (camera.viewportHeight * camera.zoom) + ((this.maxTileHeight * 2.0f) * this.unitScale);
        this.viewBounds.set(camera.position.f120x - (width / 2.0f), camera.position.f121y - (height / 2.0f), width, height);
        if ((this.canCacheMoreW && this.viewBounds.f12x < this.cacheBounds.f12x - tolerance) || ((this.canCacheMoreS && this.viewBounds.f13y < this.cacheBounds.f13y - tolerance) || ((this.canCacheMoreE && this.viewBounds.f12x + this.viewBounds.width > (this.cacheBounds.f12x + this.cacheBounds.width) + tolerance) || (this.canCacheMoreN && this.viewBounds.f13y + this.viewBounds.height > (this.cacheBounds.f13y + this.cacheBounds.height) + tolerance)))) {
            this.cached = false;
        }
    }

    public void setView(Matrix4 projection, float x, float y, float width, float height) {
        this.spriteCache.setProjectionMatrix(projection);
        this.viewBounds.set(x - (this.maxTileWidth * this.unitScale), y - (this.maxTileHeight * this.unitScale), width + ((this.maxTileWidth * 2.0f) * this.unitScale), height + ((this.maxTileHeight * 2.0f) * this.unitScale));
        if ((this.canCacheMoreW && this.viewBounds.f12x < this.cacheBounds.f12x - tolerance) || ((this.canCacheMoreS && this.viewBounds.f13y < this.cacheBounds.f13y - tolerance) || ((this.canCacheMoreE && this.viewBounds.f12x + this.viewBounds.width > (this.cacheBounds.f12x + this.cacheBounds.width) + tolerance) || (this.canCacheMoreN && this.viewBounds.f13y + this.viewBounds.height > (this.cacheBounds.f13y + this.cacheBounds.height) + tolerance)))) {
            this.cached = false;
        }
    }

    public void render() {
        if (!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float extraWidth = this.viewBounds.width * this.overCache;
            float extraHeight = this.viewBounds.height * this.overCache;
            this.cacheBounds.f12x = this.viewBounds.f12x - extraWidth;
            this.cacheBounds.f13y = this.viewBounds.f13y - extraHeight;
            this.cacheBounds.width = this.viewBounds.width + (extraWidth * 2.0f);
            this.cacheBounds.height = this.viewBounds.height + (2.0f * extraHeight);
            Iterator i$ = this.map.getLayers().iterator();
            while (i$.hasNext()) {
                MapLayer layer = (MapLayer) i$.next();
                this.spriteCache.beginCache();
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                } else if (layer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) layer);
                }
                this.spriteCache.endCache();
            }
        }
        if (this.blending) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        this.spriteCache.begin();
        MapLayers mapLayers = this.map.getLayers();
        int j = mapLayers.getCount();
        for (int i = 0; i < j; i++) {
            MapLayer layer2 = mapLayers.get(i);
            if (layer2.isVisible()) {
                this.spriteCache.draw(i);
                renderObjects(layer2);
            }
        }
        this.spriteCache.end();
        if (this.blending) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void render(int[] layers) {
        int i$ = 0;
        if (!this.cached) {
            this.cached = true;
            this.count = 0;
            this.spriteCache.clear();
            float extraWidth = this.viewBounds.width * this.overCache;
            float extraHeight = this.viewBounds.height * this.overCache;
            this.cacheBounds.f12x = this.viewBounds.f12x - extraWidth;
            this.cacheBounds.f13y = this.viewBounds.f13y - extraHeight;
            this.cacheBounds.width = this.viewBounds.width + (extraWidth * 2.0f);
            this.cacheBounds.height = this.viewBounds.height + (2.0f * extraHeight);
            Iterator i$2 = this.map.getLayers().iterator();
            while (i$2.hasNext()) {
                MapLayer layer = (MapLayer) i$2.next();
                this.spriteCache.beginCache();
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer) layer);
                } else if (layer instanceof TiledMapImageLayer) {
                    renderImageLayer((TiledMapImageLayer) layer);
                }
                this.spriteCache.endCache();
            }
        }
        if (this.blending) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        }
        this.spriteCache.begin();
        MapLayers mapLayers = this.map.getLayers();
        int[] arr$ = layers;
        int len$ = arr$.length;
        while (i$ < len$) {
            int i = arr$[i$];
            MapLayer layer2 = mapLayers.get(i);
            if (layer2.isVisible()) {
                this.spriteCache.draw(i);
                renderObjects(layer2);
            }
            i$++;
        }
        this.spriteCache.end();
        if (this.blending) {
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }
    }

    public void renderObjects(MapLayer layer) {
        Iterator i$ = layer.getObjects().iterator();
        while (i$.hasNext()) {
            renderObject((MapObject) i$.next());
        }
    }

    public void renderObject(MapObject object) {
    }

    public void renderTileLayer(TiledMapTileLayer layer) {
        OrthoCachedTiledMapRenderer orthoCachedTiledMapRenderer;
        int layerWidth;
        int layerHeight;
        int col1;
        float layerTileWidth;
        float layerTileHeight;
        int col2;
        int row1;
        int row2;
        float color;
        TiledMapTileLayer tiledMapTileLayer;
        float color2 = Color.toFloatBits(1.0f, 1.0f, 1.0f, layer.getOpacity());
        int layerWidth2 = layer.getWidth();
        int layerHeight2 = layer.getHeight();
        float layerTileWidth2 = layer.getTileWidth() * this.unitScale;
        float layerTileHeight2 = layer.getTileHeight() * this.unitScale;
        int col12 = Math.max(0, (int) (this.cacheBounds.f12x / layerTileWidth2));
        int col22 = Math.min(layerWidth2, (int) (((this.cacheBounds.f12x + this.cacheBounds.width) + layerTileWidth2) / layerTileWidth2));
        int row12 = Math.max(0, (int) (this.cacheBounds.f13y / layerTileHeight2));
        int row22 = Math.min(layerHeight2, (int) (((this.cacheBounds.f13y + this.cacheBounds.height) + layerTileHeight2) / layerTileHeight2));
        orthoCachedTiledMapRenderer.canCacheMoreN = row22 < layerHeight2;
        orthoCachedTiledMapRenderer.canCacheMoreE = col22 < layerWidth2;
        orthoCachedTiledMapRenderer.canCacheMoreW = col12 > 0;
        orthoCachedTiledMapRenderer.canCacheMoreS = row12 > 0;
        float[] vertices = orthoCachedTiledMapRenderer.vertices;
        int row = row22;
        while (row >= row12) {
            int col = col12;
            while (col < col22) {
                Cell cell = layer.getCell(col, row);
                if (cell != null) {
                    TiledMapTile tile = cell.getTile();
                    if (tile != null) {
                        float temp;
                        layerWidth = layerWidth2;
                        orthoCachedTiledMapRenderer.count++;
                        boolean flipX = cell.getFlipHorizontally();
                        boolean flipY = cell.getFlipVertically();
                        int rotations = cell.getRotation();
                        layerHeight = layerHeight2;
                        TextureRegion region = tile.getTextureRegion();
                        col1 = col12;
                        Texture texture = region.getTexture();
                        layerTileWidth = layerTileWidth2;
                        float x1 = (((float) col) * layerTileWidth2) + (tile.getOffsetX() * orthoCachedTiledMapRenderer.unitScale);
                        layerTileHeight = layerTileHeight2;
                        layerTileWidth2 = (((float) row) * layerTileHeight2) + (tile.getOffsetY() * orthoCachedTiledMapRenderer.unitScale);
                        col2 = col22;
                        layerTileHeight2 = (((float) region.getRegionWidth()) * orthoCachedTiledMapRenderer.unitScale) + x1;
                        row1 = row12;
                        float y2 = (((float) region.getRegionHeight()) * orthoCachedTiledMapRenderer.unitScale) + layerTileWidth2;
                        float adjustX = 0.5f / ((float) texture.getWidth());
                        row2 = row22;
                        float adjustY = 0.5f / ((float) texture.getHeight());
                        float u1 = region.getU() + adjustX;
                        float v1 = region.getV2() - adjustY;
                        float u2 = region.getU2() - adjustX;
                        float v2 = region.getV() + adjustY;
                        vertices[0] = x1;
                        vertices[1] = layerTileWidth2;
                        vertices[2] = color2;
                        vertices[3] = u1;
                        vertices[4] = v1;
                        vertices[5] = x1;
                        vertices[6] = y2;
                        vertices[7] = color2;
                        vertices[8] = u1;
                        vertices[9] = v2;
                        vertices[10] = layerTileHeight2;
                        vertices[11] = y2;
                        vertices[12] = color2;
                        vertices[13] = u2;
                        vertices[14] = v2;
                        vertices[15] = layerTileHeight2;
                        vertices[16] = layerTileWidth2;
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
                        color = color2;
                        boolean z = flipX;
                        orthoCachedTiledMapRenderer.spriteCache.add(texture, vertices, false, 20);
                        col++;
                        layerWidth2 = layerWidth;
                        layerHeight2 = layerHeight;
                        col12 = col1;
                        layerTileWidth2 = layerTileWidth;
                        layerTileHeight2 = layerTileHeight;
                        col22 = col2;
                        row12 = row1;
                        row22 = row2;
                        color2 = color;
                        orthoCachedTiledMapRenderer = this;
                    }
                }
                color = color2;
                layerWidth = layerWidth2;
                layerHeight = layerHeight2;
                layerTileWidth = layerTileWidth2;
                layerTileHeight = layerTileHeight2;
                col1 = col12;
                col2 = col22;
                row1 = row12;
                row2 = row22;
                col++;
                layerWidth2 = layerWidth;
                layerHeight2 = layerHeight;
                col12 = col1;
                layerTileWidth2 = layerTileWidth;
                layerTileHeight2 = layerTileHeight;
                col22 = col2;
                row12 = row1;
                row22 = row2;
                color2 = color;
                orthoCachedTiledMapRenderer = this;
            }
            tiledMapTileLayer = layer;
            color = color2;
            layerHeight = layerHeight2;
            layerTileWidth = layerTileWidth2;
            layerTileHeight = layerTileHeight2;
            col1 = col12;
            col2 = col22;
            row1 = row12;
            row2 = row22;
            row--;
            layerWidth2 = layerWidth2;
            orthoCachedTiledMapRenderer = this;
        }
        tiledMapTileLayer = layer;
        color = color2;
        layerWidth = layerWidth2;
        layerHeight = layerHeight2;
        layerTileWidth = layerTileWidth2;
        layerTileHeight = layerTileHeight2;
        col1 = col12;
        col2 = col22;
        row1 = row12;
        row2 = row22;
    }

    public void renderImageLayer(TiledMapImageLayer layer) {
        float color = Color.toFloatBits(1.0f, 1.0f, 1.0f, layer.getOpacity());
        float[] vertices = this.vertices;
        TextureRegion region = layer.getTextureRegion();
        if (region != null) {
            float x = layer.getX();
            float x1 = r0.unitScale * x;
            float y1 = r0.unitScale * layer.getY();
            float x2 = (((float) region.getRegionWidth()) * r0.unitScale) + x1;
            float y2 = (((float) region.getRegionHeight()) * r0.unitScale) + y1;
            float u1 = region.getU();
            float v1 = region.getV2();
            float u2 = region.getU2();
            float v2 = region.getV();
            vertices[0] = x1;
            vertices[1] = y1;
            vertices[2] = color;
            vertices[3] = u1;
            vertices[4] = v1;
            vertices[5] = x1;
            vertices[6] = y2;
            vertices[7] = color;
            vertices[8] = u1;
            vertices[9] = v2;
            vertices[10] = x2;
            vertices[11] = y2;
            vertices[12] = color;
            vertices[13] = u2;
            vertices[14] = v2;
            vertices[15] = x2;
            vertices[16] = y1;
            vertices[17] = color;
            vertices[18] = u2;
            vertices[19] = v1;
            r0.spriteCache.add(region.getTexture(), vertices, 0, 2.8E-44f);
        }
    }

    public void invalidateCache() {
        this.cached = false;
    }

    public boolean isCached() {
        return this.cached;
    }

    public void setOverCache(float overCache) {
        this.overCache = overCache;
    }

    public void setMaxTileSize(float maxPixelWidth, float maxPixelHeight) {
        this.maxTileWidth = maxPixelWidth;
        this.maxTileHeight = maxPixelHeight;
    }

    public void setBlending(boolean blending) {
        this.blending = blending;
    }

    public SpriteCache getSpriteCache() {
        return this.spriteCache;
    }

    public void dispose() {
        this.spriteCache.dispose();
    }
}
