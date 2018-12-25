package com.badlogic.gdx.maps.tiled.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTile.BlendMode;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.TimeUtils;

public class AnimatedTiledMapTile implements TiledMapTile {
    private static final long initialTimeOffset = TimeUtils.millis();
    private static long lastTiledMapRenderTime = 0;
    private int[] animationIntervals;
    private BlendMode blendMode = BlendMode.ALPHA;
    private int frameCount;
    private StaticTiledMapTile[] frameTiles;
    private int id;
    private int loopDuration;
    private MapProperties properties;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BlendMode getBlendMode() {
        return this.blendMode;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

    public int getCurrentFrameIndex() {
        int currentTime = (int) (lastTiledMapRenderTime % ((long) this.loopDuration));
        for (int i = 0; i < this.animationIntervals.length; i++) {
            int animationInterval = this.animationIntervals[i];
            if (currentTime <= animationInterval) {
                return i;
            }
            currentTime -= animationInterval;
        }
        throw new GdxRuntimeException("Could not determine current animation frame in AnimatedTiledMapTile.  This should never happen.");
    }

    public TiledMapTile getCurrentFrame() {
        return this.frameTiles[getCurrentFrameIndex()];
    }

    public TextureRegion getTextureRegion() {
        return getCurrentFrame().getTextureRegion();
    }

    public void setTextureRegion(TextureRegion textureRegion) {
        throw new GdxRuntimeException("Cannot set the texture region of AnimatedTiledMapTile.");
    }

    public float getOffsetX() {
        return getCurrentFrame().getOffsetX();
    }

    public void setOffsetX(float offsetX) {
        throw new GdxRuntimeException("Cannot set offset of AnimatedTiledMapTile.");
    }

    public float getOffsetY() {
        return getCurrentFrame().getOffsetY();
    }

    public void setOffsetY(float offsetY) {
        throw new GdxRuntimeException("Cannot set offset of AnimatedTiledMapTile.");
    }

    public int[] getAnimationIntervals() {
        return this.animationIntervals;
    }

    public void setAnimationIntervals(int[] intervals) {
        if (intervals.length == this.animationIntervals.length) {
            this.animationIntervals = intervals;
            int i = 0;
            this.loopDuration = 0;
            while (i < intervals.length) {
                this.loopDuration += intervals[i];
                i++;
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot set ");
        stringBuilder.append(intervals.length);
        stringBuilder.append(" frame intervals. The given int[] must have a size of ");
        stringBuilder.append(this.animationIntervals.length);
        stringBuilder.append(".");
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    public MapProperties getProperties() {
        if (this.properties == null) {
            this.properties = new MapProperties();
        }
        return this.properties;
    }

    public static void updateAnimationBaseTime() {
        lastTiledMapRenderTime = TimeUtils.millis() - initialTimeOffset;
    }

    public AnimatedTiledMapTile(float interval, Array<StaticTiledMapTile> frameTiles) {
        int i = 0;
        this.frameCount = 0;
        this.frameTiles = new StaticTiledMapTile[frameTiles.size];
        this.frameCount = frameTiles.size;
        this.loopDuration = frameTiles.size * ((int) (interval * 1000.0f));
        this.animationIntervals = new int[frameTiles.size];
        while (i < frameTiles.size) {
            this.frameTiles[i] = (StaticTiledMapTile) frameTiles.get(i);
            this.animationIntervals[i] = (int) (interval * 1000.0f);
            i++;
        }
    }

    public AnimatedTiledMapTile(IntArray intervals, Array<StaticTiledMapTile> frameTiles) {
        int i = 0;
        this.frameCount = 0;
        this.frameTiles = new StaticTiledMapTile[frameTiles.size];
        this.frameCount = frameTiles.size;
        this.animationIntervals = intervals.toArray();
        this.loopDuration = 0;
        while (i < intervals.size) {
            this.frameTiles[i] = (StaticTiledMapTile) frameTiles.get(i);
            this.loopDuration += intervals.get(i);
            i++;
        }
    }

    public StaticTiledMapTile[] getFrameTiles() {
        return this.frameTiles;
    }
}
