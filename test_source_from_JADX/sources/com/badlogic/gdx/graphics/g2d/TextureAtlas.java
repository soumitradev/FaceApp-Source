package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.Texture$TextureWrap;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.StreamUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.Iterator;

public class TextureAtlas implements Disposable {
    static final Comparator<Region> indexComparator = new C03511();
    static final String[] tuple = new String[4];
    private final Array<AtlasRegion> regions;
    private final ObjectSet<Texture> textures;

    /* renamed from: com.badlogic.gdx.graphics.g2d.TextureAtlas$1 */
    static class C03511 implements Comparator<Region> {
        C03511() {
        }

        public int compare(Region region1, Region region2) {
            int i1 = region1.index;
            if (i1 == -1) {
                i1 = Integer.MAX_VALUE;
            }
            int i2 = region2.index;
            if (i2 == -1) {
                i2 = Integer.MAX_VALUE;
            }
            return i1 - i2;
        }
    }

    public static class TextureAtlasData {
        final Array<Page> pages = new Array();
        final Array<Region> regions = new Array();

        public static class Page {
            public final Pixmap$Format format;
            public final float height;
            public final Texture$TextureFilter magFilter;
            public final Texture$TextureFilter minFilter;
            public Texture texture;
            public final FileHandle textureFile;
            public final Texture$TextureWrap uWrap;
            public final boolean useMipMaps;
            public final Texture$TextureWrap vWrap;
            public final float width;

            public Page(FileHandle handle, float width, float height, boolean useMipMaps, Pixmap$Format format, Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, Texture$TextureWrap uWrap, Texture$TextureWrap vWrap) {
                this.width = width;
                this.height = height;
                this.textureFile = handle;
                this.useMipMaps = useMipMaps;
                this.format = format;
                this.minFilter = minFilter;
                this.magFilter = magFilter;
                this.uWrap = uWrap;
                this.vWrap = vWrap;
            }
        }

        public static class Region {
            public boolean flip;
            public int height;
            public int index;
            public int left;
            public String name;
            public float offsetX;
            public float offsetY;
            public int originalHeight;
            public int originalWidth;
            public int[] pads;
            public Page page;
            public boolean rotate;
            public int[] splits;
            public int top;
            public int width;
        }

        public TextureAtlasData(FileHandle packFile, FileHandle imagesDir, boolean flip) {
            Exception e;
            BufferedReader reader = new BufferedReader(new InputStreamReader(packFile.read()), 64);
            Page pageImage = null;
            while (true) {
                FileHandle fileHandle;
                try {
                    String line = reader.readLine();
                    if (line == null) {
                        StreamUtils.closeQuietly(reader);
                        r1.regions.sort(TextureAtlas.indexComparator);
                        return;
                    } else if (line.trim().length() == 0) {
                        pageImage = null;
                        fileHandle = imagesDir;
                    } else if (pageImage == null) {
                        try {
                            float width;
                            float height;
                            FileHandle file = imagesDir.child(line);
                            if (TextureAtlas.readTuple(reader) == 2) {
                                width = (float) Integer.parseInt(TextureAtlas.tuple[0]);
                                float width2 = (float) Integer.parseInt(TextureAtlas.tuple[1]);
                                TextureAtlas.readTuple(reader);
                                height = width2;
                            } else {
                                width = 0.0f;
                                height = 0.0f;
                            }
                            Pixmap$Format format = Pixmap$Format.valueOf(TextureAtlas.tuple[0]);
                            TextureAtlas.readTuple(reader);
                            Texture$TextureFilter min = Texture$TextureFilter.valueOf(TextureAtlas.tuple[0]);
                            Texture$TextureFilter max = Texture$TextureFilter.valueOf(TextureAtlas.tuple[1]);
                            String direction = TextureAtlas.readValue(reader);
                            Texture$TextureWrap repeatX = Texture$TextureWrap.ClampToEdge;
                            Texture$TextureWrap repeatY = Texture$TextureWrap.ClampToEdge;
                            if (direction.equals("x")) {
                                repeatX = Texture$TextureWrap.Repeat;
                            } else if (direction.equals("y")) {
                                repeatY = Texture$TextureWrap.Repeat;
                            } else if (direction.equals("xy")) {
                                repeatX = Texture$TextureWrap.Repeat;
                                repeatY = Texture$TextureWrap.Repeat;
                            }
                            Texture$TextureWrap repeatY2 = repeatY;
                            pageImage = new Page(file, width, height, min.isMipMap(), format, min, max, repeatX, repeatY2);
                            r1.pages.add(pageImage);
                        } catch (Exception e2) {
                            e = e2;
                        }
                    } else {
                        fileHandle = imagesDir;
                        boolean rotate = Boolean.valueOf(TextureAtlas.readValue(reader)).booleanValue();
                        TextureAtlas.readTuple(reader);
                        int left = Integer.parseInt(TextureAtlas.tuple[0]);
                        int top = Integer.parseInt(TextureAtlas.tuple[1]);
                        TextureAtlas.readTuple(reader);
                        int width3 = Integer.parseInt(TextureAtlas.tuple[0]);
                        int height2 = Integer.parseInt(TextureAtlas.tuple[1]);
                        Region region = new Region();
                        region.page = pageImage;
                        region.left = left;
                        region.top = top;
                        region.width = width3;
                        region.height = height2;
                        region.name = line;
                        region.rotate = rotate;
                        if (TextureAtlas.readTuple(reader) == 4) {
                            region.splits = new int[]{Integer.parseInt(TextureAtlas.tuple[0]), Integer.parseInt(TextureAtlas.tuple[1]), Integer.parseInt(TextureAtlas.tuple[2]), Integer.parseInt(TextureAtlas.tuple[3])};
                            if (TextureAtlas.readTuple(reader) == 4) {
                                region.pads = new int[]{Integer.parseInt(TextureAtlas.tuple[0]), Integer.parseInt(TextureAtlas.tuple[1]), Integer.parseInt(TextureAtlas.tuple[2]), Integer.parseInt(TextureAtlas.tuple[3])};
                                TextureAtlas.readTuple(reader);
                            }
                        }
                        region.originalWidth = Integer.parseInt(TextureAtlas.tuple[0]);
                        region.originalHeight = Integer.parseInt(TextureAtlas.tuple[1]);
                        TextureAtlas.readTuple(reader);
                        region.offsetX = (float) Integer.parseInt(TextureAtlas.tuple[0]);
                        region.offsetY = (float) Integer.parseInt(TextureAtlas.tuple[1]);
                        region.index = Integer.parseInt(TextureAtlas.readValue(reader));
                        if (flip) {
                            region.flip = true;
                        }
                        r1.regions.add(region);
                    }
                } catch (Exception e3) {
                    e = e3;
                    fileHandle = imagesDir;
                } catch (Throwable th) {
                    Throwable th2 = th;
                    fileHandle = imagesDir;
                }
            }
            Exception ex = e;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error reading pack file: ");
            } catch (Throwable th3) {
                th2 = th3;
                FileHandle fileHandle2 = packFile;
                Throwable th4 = th2;
                StreamUtils.closeQuietly(reader);
                throw th4;
            }
            try {
                stringBuilder.append(packFile);
                throw new GdxRuntimeException(stringBuilder.toString(), ex);
            } catch (Throwable th5) {
                th2 = th5;
                Throwable th42 = th2;
                StreamUtils.closeQuietly(reader);
                throw th42;
            }
        }

        public Array<Page> getPages() {
            return this.pages;
        }

        public Array<Region> getRegions() {
            return this.regions;
        }
    }

    public static class AtlasRegion extends TextureRegion {
        public int index;
        public String name;
        public float offsetX;
        public float offsetY;
        public int originalHeight;
        public int originalWidth;
        public int packedHeight;
        public int packedWidth;
        public int[] pads;
        public boolean rotate;
        public int[] splits;

        public AtlasRegion(Texture texture, int x, int y, int width, int height) {
            super(texture, x, y, width, height);
            this.originalWidth = width;
            this.originalHeight = height;
            this.packedWidth = width;
            this.packedHeight = height;
        }

        public AtlasRegion(AtlasRegion region) {
            setRegion(region);
            this.index = region.index;
            this.name = region.name;
            this.offsetX = region.offsetX;
            this.offsetY = region.offsetY;
            this.packedWidth = region.packedWidth;
            this.packedHeight = region.packedHeight;
            this.originalWidth = region.originalWidth;
            this.originalHeight = region.originalHeight;
            this.rotate = region.rotate;
            this.splits = region.splits;
        }

        public void flip(boolean x, boolean y) {
            super.flip(x, y);
            if (x) {
                this.offsetX = (((float) this.originalWidth) - this.offsetX) - getRotatedPackedWidth();
            }
            if (y) {
                this.offsetY = (((float) this.originalHeight) - this.offsetY) - getRotatedPackedHeight();
            }
        }

        public float getRotatedPackedWidth() {
            return (float) (this.rotate ? this.packedHeight : this.packedWidth);
        }

        public float getRotatedPackedHeight() {
            return (float) (this.rotate ? this.packedWidth : this.packedHeight);
        }

        public String toString() {
            return this.name;
        }
    }

    public static class AtlasSprite extends Sprite {
        float originalOffsetX;
        float originalOffsetY;
        final AtlasRegion region;

        public AtlasSprite(AtlasRegion region) {
            this.region = new AtlasRegion(region);
            this.originalOffsetX = region.offsetX;
            this.originalOffsetY = region.offsetY;
            setRegion(region);
            setOrigin(((float) region.originalWidth) / 2.0f, ((float) region.originalHeight) / 2.0f);
            int width = region.getRegionWidth();
            int height = region.getRegionHeight();
            if (region.rotate) {
                super.rotate90(true);
                super.setBounds(region.offsetX, region.offsetY, (float) height, (float) width);
            } else {
                super.setBounds(region.offsetX, region.offsetY, (float) width, (float) height);
            }
            setColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        public AtlasSprite(AtlasSprite sprite) {
            this.region = sprite.region;
            this.originalOffsetX = sprite.originalOffsetX;
            this.originalOffsetY = sprite.originalOffsetY;
            set(sprite);
        }

        public void setPosition(float x, float y) {
            super.setPosition(this.region.offsetX + x, this.region.offsetY + y);
        }

        public void setX(float x) {
            super.setX(this.region.offsetX + x);
        }

        public void setY(float y) {
            super.setY(this.region.offsetY + y);
        }

        public void setBounds(float x, float y, float width, float height) {
            float widthRatio = width / ((float) this.region.originalWidth);
            float heightRatio = height / ((float) this.region.originalHeight);
            this.region.offsetX = this.originalOffsetX * widthRatio;
            this.region.offsetY = this.originalOffsetY * heightRatio;
            super.setBounds(this.region.offsetX + x, this.region.offsetY + y, ((float) (this.region.rotate ? this.region.packedHeight : this.region.packedWidth)) * widthRatio, ((float) (this.region.rotate ? this.region.packedWidth : this.region.packedHeight)) * heightRatio);
        }

        public void setSize(float width, float height) {
            setBounds(getX(), getY(), width, height);
        }

        public void setOrigin(float originX, float originY) {
            super.setOrigin(originX - this.region.offsetX, originY - this.region.offsetY);
        }

        public void setOriginCenter() {
            super.setOrigin((this.width / 2.0f) - this.region.offsetX, (this.height / 2.0f) - this.region.offsetY);
        }

        public void flip(boolean x, boolean y) {
            if (this.region.rotate) {
                super.flip(y, x);
            } else {
                super.flip(x, y);
            }
            float oldOriginX = getOriginX();
            float oldOriginY = getOriginY();
            float oldOffsetX = this.region.offsetX;
            float oldOffsetY = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            this.region.offsetX = this.originalOffsetX;
            this.region.offsetY = this.originalOffsetY;
            this.region.flip(x, y);
            this.originalOffsetX = this.region.offsetX;
            this.originalOffsetY = this.region.offsetY;
            AtlasRegion atlasRegion = this.region;
            atlasRegion.offsetX *= widthRatio;
            atlasRegion = this.region;
            atlasRegion.offsetY *= heightRatio;
            translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
            setOrigin(oldOriginX, oldOriginY);
        }

        public void rotate90(boolean clockwise) {
            super.rotate90(clockwise);
            float oldOriginX = getOriginX();
            float oldOriginY = getOriginY();
            float oldOffsetX = this.region.offsetX;
            float oldOffsetY = this.region.offsetY;
            float widthRatio = getWidthRatio();
            float heightRatio = getHeightRatio();
            if (clockwise) {
                this.region.offsetX = oldOffsetY;
                this.region.offsetY = ((((float) this.region.originalHeight) * heightRatio) - oldOffsetX) - (((float) this.region.packedWidth) * widthRatio);
            } else {
                this.region.offsetX = ((((float) this.region.originalWidth) * widthRatio) - oldOffsetY) - (((float) this.region.packedHeight) * heightRatio);
                this.region.offsetY = oldOffsetX;
            }
            translate(this.region.offsetX - oldOffsetX, this.region.offsetY - oldOffsetY);
            setOrigin(oldOriginX, oldOriginY);
        }

        public float getX() {
            return super.getX() - this.region.offsetX;
        }

        public float getY() {
            return super.getY() - this.region.offsetY;
        }

        public float getOriginX() {
            return super.getOriginX() + this.region.offsetX;
        }

        public float getOriginY() {
            return super.getOriginY() + this.region.offsetY;
        }

        public float getWidth() {
            return (super.getWidth() / this.region.getRotatedPackedWidth()) * ((float) this.region.originalWidth);
        }

        public float getHeight() {
            return (super.getHeight() / this.region.getRotatedPackedHeight()) * ((float) this.region.originalHeight);
        }

        public float getWidthRatio() {
            return super.getWidth() / this.region.getRotatedPackedWidth();
        }

        public float getHeightRatio() {
            return super.getHeight() / this.region.getRotatedPackedHeight();
        }

        public AtlasRegion getAtlasRegion() {
            return this.region;
        }

        public String toString() {
            return this.region.toString();
        }
    }

    public TextureAtlas() {
        this.textures = new ObjectSet(4);
        this.regions = new Array();
    }

    public TextureAtlas(String internalPackFile) {
        this(Gdx.files.internal(internalPackFile));
    }

    public TextureAtlas(FileHandle packFile) {
        this(packFile, packFile.parent());
    }

    public TextureAtlas(FileHandle packFile, boolean flip) {
        this(packFile, packFile.parent(), flip);
    }

    public TextureAtlas(FileHandle packFile, FileHandle imagesDir) {
        this(packFile, imagesDir, false);
    }

    public TextureAtlas(FileHandle packFile, FileHandle imagesDir, boolean flip) {
        this(new TextureAtlasData(packFile, imagesDir, flip));
    }

    public TextureAtlas(TextureAtlasData data) {
        this.textures = new ObjectSet(4);
        this.regions = new Array();
        if (data != null) {
            load(data);
        }
    }

    private void load(TextureAtlasData data) {
        ObjectMap<Page, Texture> pageToTexture = new ObjectMap();
        Iterator i$ = data.pages.iterator();
        while (i$.hasNext()) {
            Texture texture;
            Page page = (Page) i$.next();
            if (page.texture == null) {
                texture = new Texture(page.textureFile, page.format, page.useMipMaps);
                texture.setFilter(page.minFilter, page.magFilter);
                texture.setWrap(page.uWrap, page.vWrap);
            } else {
                texture = page.texture;
                texture.setFilter(page.minFilter, page.magFilter);
                texture.setWrap(page.uWrap, page.vWrap);
            }
            this.textures.add(texture);
            pageToTexture.put(page, texture);
        }
        i$ = data.regions.iterator();
        while (i$.hasNext()) {
            Region region = (Region) i$.next();
            int width = region.width;
            int height = region.height;
            AtlasRegion atlasRegion = new AtlasRegion((Texture) pageToTexture.get(region.page), region.left, region.top, region.rotate ? height : width, region.rotate ? width : height);
            atlasRegion.index = region.index;
            atlasRegion.name = region.name;
            atlasRegion.offsetX = region.offsetX;
            atlasRegion.offsetY = region.offsetY;
            atlasRegion.originalHeight = region.originalHeight;
            atlasRegion.originalWidth = region.originalWidth;
            atlasRegion.rotate = region.rotate;
            atlasRegion.splits = region.splits;
            atlasRegion.pads = region.pads;
            if (region.flip) {
                atlasRegion.flip(false, true);
            }
            this.regions.add(atlasRegion);
        }
    }

    public AtlasRegion addRegion(String name, Texture texture, int x, int y, int width, int height) {
        this.textures.add(texture);
        AtlasRegion atlasRegion = new AtlasRegion(texture, x, y, width, height);
        atlasRegion.name = name;
        atlasRegion.originalWidth = width;
        atlasRegion.originalHeight = height;
        atlasRegion.index = -1;
        this.regions.add(atlasRegion);
        return atlasRegion;
    }

    public AtlasRegion addRegion(String name, TextureRegion textureRegion) {
        return addRegion(name, textureRegion.texture, textureRegion.getRegionX(), textureRegion.getRegionY(), textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    public Array<AtlasRegion> getRegions() {
        return this.regions;
    }

    public AtlasRegion findRegion(String name) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            if (((AtlasRegion) this.regions.get(i)).name.equals(name)) {
                return (AtlasRegion) this.regions.get(i);
            }
        }
        return null;
    }

    public AtlasRegion findRegion(String name, int index) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                if (region.index == index) {
                    return region;
                }
            }
        }
        return null;
    }

    public Array<AtlasRegion> findRegions(String name) {
        Array<AtlasRegion> matched = new Array();
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                matched.add(new AtlasRegion(region));
            }
        }
        return matched;
    }

    public Array<Sprite> createSprites() {
        Array sprites = new Array(this.regions.size);
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            sprites.add(newSprite((AtlasRegion) this.regions.get(i)));
        }
        return sprites;
    }

    public Sprite createSprite(String name) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            if (((AtlasRegion) this.regions.get(i)).name.equals(name)) {
                return newSprite((AtlasRegion) this.regions.get(i));
            }
        }
        return null;
    }

    public Sprite createSprite(String name, int index) {
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                if (region.index == index) {
                    return newSprite((AtlasRegion) this.regions.get(i));
                }
            }
        }
        return null;
    }

    public Array<Sprite> createSprites(String name) {
        Array<Sprite> matched = new Array();
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) this.regions.get(i);
            if (region.name.equals(name)) {
                matched.add(newSprite(region));
            }
        }
        return matched;
    }

    private Sprite newSprite(AtlasRegion region) {
        if (region.packedWidth != region.originalWidth || region.packedHeight != region.originalHeight) {
            return new AtlasSprite(region);
        }
        if (!region.rotate) {
            return new Sprite((TextureRegion) region);
        }
        Sprite sprite = new Sprite((TextureRegion) region);
        sprite.setBounds(0.0f, 0.0f, (float) region.getRegionHeight(), (float) region.getRegionWidth());
        sprite.rotate90(true);
        return sprite;
    }

    public NinePatch createPatch(String name) {
        String str = name;
        int n = this.regions.size;
        for (int i = 0; i < n; i++) {
            TextureRegion region = (AtlasRegion) r0.regions.get(i);
            if (region.name.equals(str)) {
                int[] splits = region.splits;
                if (splits == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Region does not have ninepatch splits: ");
                    stringBuilder.append(str);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                NinePatch patch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                if (region.pads != null) {
                    patch.setPadding(region.pads[0], region.pads[1], region.pads[2], region.pads[3]);
                }
                return patch;
            }
        }
        return null;
    }

    public ObjectSet<Texture> getTextures() {
        return this.textures;
    }

    public void dispose() {
        Iterator i$ = this.textures.iterator();
        while (i$.hasNext()) {
            ((Texture) i$.next()).dispose();
        }
        this.textures.clear();
    }

    static String readValue(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(58);
        if (colon != -1) {
            return line.substring(colon + 1).trim();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid line: ");
        stringBuilder.append(line);
        throw new GdxRuntimeException(stringBuilder.toString());
    }

    static int readTuple(BufferedReader reader) throws IOException {
        String line = reader.readLine();
        int colon = line.indexOf(58);
        if (colon == -1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid line: ");
            stringBuilder.append(line);
            throw new GdxRuntimeException(stringBuilder.toString());
        }
        int lastMatch = colon + 1;
        int i = 0;
        while (i < 3) {
            int comma = line.indexOf(44, lastMatch);
            if (comma == -1) {
                break;
            }
            tuple[i] = line.substring(lastMatch, comma).trim();
            lastMatch = comma + 1;
            i++;
        }
        tuple[i] = line.substring(lastMatch).trim();
        return i + 1;
    }
}
