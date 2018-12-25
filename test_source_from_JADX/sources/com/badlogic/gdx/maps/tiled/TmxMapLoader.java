package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver.AssetManagerImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.facebook.share.internal.ShareConstants;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;

public class TmxMapLoader extends BaseTmxMapLoader<Parameters> {

    public static class Parameters extends com.badlogic.gdx.maps.tiled.BaseTmxMapLoader.Parameters {
    }

    public TmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public TmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public TiledMap load(String fileName) {
        return load(fileName, new Parameters());
    }

    public TiledMap load(String fileName, Parameters parameters) {
        try {
            this.convertObjectToTileSpace = parameters.convertObjectToTileSpace;
            this.flipY = parameters.flipY;
            FileHandle tmxFile = resolve(fileName);
            this.root = this.xml.parse(tmxFile);
            ObjectMap<String, Texture> textures = new ObjectMap();
            Array<FileHandle> textureFiles = loadTilesets(this.root, tmxFile);
            textureFiles.addAll(loadImages(this.root, tmxFile));
            Iterator i$ = textureFiles.iterator();
            while (i$.hasNext()) {
                FileHandle textureFile = (FileHandle) i$.next();
                Texture texture = new Texture(textureFile, parameters.generateMipMaps);
                texture.setFilter(parameters.textureMinFilter, parameters.textureMagFilter);
                textures.put(textureFile.path(), texture);
            }
            TiledMap map = loadTilemap(this.root, tmxFile, new DirectImageResolver(textures));
            map.setOwnedResources(textures.values().toArray());
            return map;
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load tilemap '");
            stringBuilder.append(fileName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, Parameters parameter) {
        this.map = null;
        if (parameter != null) {
            this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
            this.flipY = parameter.flipY;
        } else {
            this.convertObjectToTileSpace = false;
            this.flipY = true;
        }
        try {
            this.map = loadTilemap(this.root, tmxFile, new AssetManagerImageResolver(manager));
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load tilemap '");
            stringBuilder.append(fileName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, Parameters parameter) {
        return this.map;
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {
        Array<AssetDescriptor> dependencies = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            boolean generateMipMaps = parameter != null ? parameter.generateMipMaps : false;
            AssetLoaderParameters texParams = new TextureParameter();
            texParams.genMipMaps = generateMipMaps;
            if (parameter != null) {
                texParams.minFilter = parameter.textureMinFilter;
                texParams.magFilter = parameter.textureMagFilter;
            }
            Iterator i$ = loadTilesets(this.root, tmxFile).iterator();
            while (i$.hasNext()) {
                dependencies.add(new AssetDescriptor((FileHandle) i$.next(), Texture.class, texParams));
            }
            i$ = loadImages(this.root, tmxFile).iterator();
            while (i$.hasNext()) {
                dependencies.add(new AssetDescriptor((FileHandle) i$.next(), Texture.class, texParams));
            }
            return dependencies;
        } catch (IOException e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load tilemap '");
            stringBuilder.append(fileName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    protected TiledMap loadTilemap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        String mapBackgroundColor;
        TmxMapLoader tmxMapLoader = this;
        Element element = root;
        FileHandle fileHandle = tmxFile;
        ImageResolver imageResolver2 = imageResolver;
        TiledMap map = new TiledMap();
        String mapOrientation = element.getAttribute("orientation", null);
        int mapWidth = element.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
        int mapHeight = element.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
        int tileWidth = element.getIntAttribute("tilewidth", 0);
        int tileHeight = element.getIntAttribute("tileheight", 0);
        String mapBackgroundColor2 = element.getAttribute("backgroundcolor", null);
        MapProperties mapProperties = map.getProperties();
        if (mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(mapWidth));
        mapProperties.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(mapHeight));
        mapProperties.put("tilewidth", Integer.valueOf(tileWidth));
        mapProperties.put("tileheight", Integer.valueOf(tileHeight));
        if (mapBackgroundColor2 != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor2);
        }
        tmxMapLoader.mapTileWidth = tileWidth;
        tmxMapLoader.mapTileHeight = tileHeight;
        tmxMapLoader.mapWidthInPixels = mapWidth * tileWidth;
        tmxMapLoader.mapHeightInPixels = mapHeight * tileHeight;
        if (mapOrientation != null && "staggered".equals(mapOrientation) && mapHeight > 1) {
            tmxMapLoader.mapWidthInPixels += tileWidth / 2;
            tmxMapLoader.mapHeightInPixels = (tmxMapLoader.mapHeightInPixels / 2) + (tileHeight / 2);
        }
        Element properties = element.getChildByName("properties");
        if (properties != null) {
            loadProperties(map.getProperties(), properties);
        }
        Iterator i$ = element.getChildrenByName("tileset").iterator();
        while (i$.hasNext()) {
            Element element2 = (Element) i$.next();
            loadTileSet(map, element2, fileHandle, imageResolver2);
            element.removeChild(element2);
        }
        int i = 0;
        int j = root.getChildCount();
        while (i < j) {
            String mapOrientation2 = mapOrientation;
            mapOrientation = element.getChild(i);
            String name = mapOrientation.getName();
            mapBackgroundColor = mapBackgroundColor2;
            if (name.equals("layer")) {
                loadTileLayer(map, mapOrientation);
            } else if (name.equals("objectgroup")) {
                loadObjectGroup(map, mapOrientation);
            } else if (name.equals("imagelayer")) {
                loadImageLayer(map, mapOrientation, fileHandle, imageResolver2);
            }
            i++;
            mapOrientation = mapOrientation2;
            mapBackgroundColor2 = mapBackgroundColor;
            element = root;
        }
        mapBackgroundColor = mapBackgroundColor2;
        return map;
    }

    protected Array<FileHandle> loadTilesets(Element root, FileHandle tmxFile) throws IOException {
        Array<FileHandle> images = new Array();
        Iterator i$ = root.getChildrenByName("tileset").iterator();
        while (i$.hasNext()) {
            Element tileset = (Element) i$.next();
            String source = tileset.getAttribute("source", null);
            if (source != null) {
                FileHandle tsxFile = BaseTmxMapLoader.getRelativeFileHandle(tmxFile, source);
                tileset = this.xml.parse(tsxFile);
                if (tileset.getChildByName("image") != null) {
                    images.add(BaseTmxMapLoader.getRelativeFileHandle(tsxFile, tileset.getChildByName("image").getAttribute("source")));
                } else {
                    Iterator i$2 = tileset.getChildrenByName("tile").iterator();
                    while (i$2.hasNext()) {
                        images.add(BaseTmxMapLoader.getRelativeFileHandle(tsxFile, ((Element) i$2.next()).getChildByName("image").getAttribute("source")));
                    }
                }
            } else if (tileset.getChildByName("image") != null) {
                images.add(BaseTmxMapLoader.getRelativeFileHandle(tmxFile, tileset.getChildByName("image").getAttribute("source")));
            } else {
                Iterator i$3 = tileset.getChildrenByName("tile").iterator();
                while (i$3.hasNext()) {
                    images.add(BaseTmxMapLoader.getRelativeFileHandle(tmxFile, ((Element) i$3.next()).getChildByName("image").getAttribute("source")));
                }
            }
        }
        return images;
    }

    protected Array<FileHandle> loadImages(Element root, FileHandle tmxFile) throws IOException {
        Array<FileHandle> images = new Array();
        Iterator i$ = root.getChildrenByName("imagelayer").iterator();
        while (i$.hasNext()) {
            String source = ((Element) i$.next()).getChildByName("image").getAttribute("source", null);
            if (source != null) {
                FileHandle handle = BaseTmxMapLoader.getRelativeFileHandle(tmxFile, source);
                if (!images.contains(handle, false)) {
                    images.add(handle);
                }
            }
        }
        return images;
    }

    protected void loadTileSet(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        String name;
        IOException element2;
        TmxMapLoader tmxMapLoader = this;
        Element element3 = element;
        FileHandle fileHandle = tmxFile;
        ImageResolver imageResolver2 = imageResolver;
        if (element.getName().equals("tileset")) {
            Element offset;
            Element imageElement;
            String imageSource;
            int offsetX;
            int offsetY;
            int i;
            FileHandle image;
            String name2;
            TiledMapTileSet tileset;
            TiledMapTile tile;
            int offsetY2;
            Array<Element> tileElements;
            Element tileElement;
            int i2;
            String name3 = element3.get("name", null);
            int firstgid = element3.getIntAttribute("firstgid", 1);
            int imageHeight = element3.getIntAttribute("tilewidth", 0);
            int tilewidth = element3.getIntAttribute("tileheight", 0);
            int tileheight = element3.getIntAttribute("spacing", 0);
            int margin = element3.getIntAttribute("margin", 0);
            String source = element3.getAttribute("source", null);
            Element element4 = null;
            int spacing = 0;
            String imageSource2 = "";
            int imageWidth = 0;
            int imageHeight2 = 0;
            FileHandle image2 = null;
            String str;
            if (source != null) {
                FileHandle tsx = BaseTmxMapLoader.getRelativeFileHandle(fileHandle, source);
                FileHandle tsx2;
                Element element5;
                int i3;
                String str2;
                try {
                    int offsetX2;
                    tsx2 = tsx;
                    try {
                        element3 = tmxMapLoader.xml.parse(tsx2);
                        try {
                            name = name3;
                        } catch (IOException e) {
                            element5 = element3;
                            element2 = e;
                            i3 = imageHeight;
                            str2 = name3;
                            throw new GdxRuntimeException("Error parsing external tileset.");
                        }
                    } catch (IOException e2) {
                        element5 = element3;
                        i3 = imageHeight;
                        str2 = name3;
                        element2 = e2;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                    try {
                        name3 = element3.get("name", null);
                        try {
                            str2 = name3;
                            try {
                                imageHeight = element3.getIntAttribute("tilewidth", 0);
                            } catch (IOException e22) {
                                element5 = element3;
                                element2 = e22;
                                i3 = imageHeight;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                        } catch (IOException e222) {
                            element5 = element3;
                            str2 = name3;
                            element2 = e222;
                            i3 = imageHeight;
                            throw new GdxRuntimeException("Error parsing external tileset.");
                        }
                    } catch (IOException e2222) {
                        element5 = element3;
                        element2 = e2222;
                        i3 = imageHeight;
                        str2 = name;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                    try {
                        tilewidth = element3.getIntAttribute("tileheight", 0);
                        tileheight = element3.getIntAttribute("spacing", 0);
                        margin = element3.getIntAttribute("margin", 0);
                        offset = element3.getChildByName("tileoffset");
                        if (offset != null) {
                            try {
                                i3 = imageHeight;
                            } catch (IOException e22222) {
                                i3 = imageHeight;
                                element5 = element3;
                                element2 = e22222;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                            try {
                                offsetX2 = offset.getIntAttribute("x", 0);
                                spacing = offset.getIntAttribute("y", 0);
                            } catch (IOException e222222) {
                                element5 = element3;
                                element2 = e222222;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                        }
                        i3 = imageHeight;
                    } catch (IOException e2222222) {
                        element5 = element3;
                        i3 = imageHeight;
                        element2 = e2222222;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                    try {
                        imageElement = element3.getChildByName("image");
                        if (imageElement != null) {
                            imageSource = imageElement.getAttribute("source");
                            element5 = element3;
                            try {
                                imageWidth = imageElement.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
                                imageHeight2 = imageElement.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
                                image2 = BaseTmxMapLoader.getRelativeFileHandle(tsx2, imageSource);
                                imageSource2 = imageSource;
                            } catch (IOException e22222222) {
                                element2 = e22222222;
                                imageSource2 = imageSource;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                        }
                        element5 = element3;
                        str = source;
                        offsetX = offsetX2;
                        offsetY = spacing;
                        imageSource = imageSource2;
                        i = imageWidth;
                        imageHeight = imageHeight2;
                        image = image2;
                        name2 = str2;
                        element4 = element5;
                        spacing = tileheight;
                        tileheight = tilewidth;
                        tilewidth = i3;
                    } catch (IOException e222222222) {
                        element5 = element3;
                        element2 = e222222222;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                } catch (IOException e3) {
                    tsx2 = tsx;
                    element5 = element3;
                    i3 = imageHeight;
                    str2 = name3;
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            name = name3;
            offset = element3.getChildByName("tileoffset");
            if (offset != null) {
                element4 = offset.getIntAttribute("x", 0);
                spacing = offset.getIntAttribute("y", 0);
            }
            Element imageElement2 = element3.getChildByName("image");
            if (imageElement2 != null) {
                imageSource = imageElement2.getAttribute("source");
                imageWidth = imageElement2.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
                imageHeight2 = imageElement2.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
                image2 = BaseTmxMapLoader.getRelativeFileHandle(fileHandle, imageSource);
                str = source;
                offsetX = element4;
                offsetY = spacing;
            } else {
                offsetX = element4;
                offsetY = spacing;
                imageSource = imageSource2;
            }
            i = imageWidth;
            image = image2;
            name2 = name;
            element4 = element;
            spacing = tileheight;
            tileheight = tilewidth;
            tilewidth = imageHeight;
            imageHeight = imageHeight2;
            Element element6 = element4;
            TiledMapTileSet tileset2 = new TiledMapTileSet();
            tileset2.setName(name2);
            int offsetY3 = offsetY;
            tileset2.getProperties().put("firstgid", Integer.valueOf(firstgid));
            int imageWidth2;
            int imageHeight3;
            int spacing2;
            String imageSource3;
            int margin2;
            if (image != null) {
                TextureRegion texture = imageResolver2.getImage(image.path());
                MapProperties props = tileset2.getProperties();
                props.put("imagesource", imageSource);
                props.put("imagewidth", Integer.valueOf(i));
                props.put("imageheight", Integer.valueOf(imageHeight));
                props.put("tilewidth", Integer.valueOf(tilewidth));
                props.put("tileheight", Integer.valueOf(tileheight));
                props.put("margin", Integer.valueOf(margin));
                props.put("spacing", Integer.valueOf(spacing));
                offsetY = texture.getRegionWidth() - tilewidth;
                image = texture.getRegionHeight() - tileheight;
                imageWidth = firstgid;
                int y = margin;
                while (true) {
                    MapProperties props2 = props;
                    int y2 = y;
                    if (y2 > image) {
                        break;
                    }
                    int stopWidth;
                    TextureRegion texture2;
                    int stopHeight = image;
                    y = margin;
                    image = imageWidth;
                    while (true) {
                        imageWidth2 = i;
                        i = y;
                        if (i > offsetY) {
                            break;
                        }
                        float f;
                        stopWidth = offsetY;
                        imageHeight3 = imageHeight;
                        tileset = tileset2;
                        imageElement = element6;
                        spacing2 = spacing;
                        tile = new StaticTiledMapTile(new TextureRegion(texture, i, y2, tilewidth, tileheight));
                        tile.setId(image);
                        texture2 = texture;
                        tile.setOffsetX((float) offsetX);
                        imageSource3 = imageSource;
                        if (this.flipY != null) {
                            margin2 = margin;
                            offsetY2 = offsetY3;
                            f = (float) (-offsetY2);
                        } else {
                            margin2 = margin;
                            offsetY2 = offsetY3;
                            f = (float) offsetY2;
                        }
                        tile.setOffsetY(f);
                        margin = image + 1;
                        tileset.putTile(image, tile);
                        y = i + (tilewidth + spacing2);
                        tileset2 = tileset;
                        element6 = imageElement;
                        offsetY3 = offsetY2;
                        image = margin;
                        spacing = spacing2;
                        i = imageWidth2;
                        offsetY = stopWidth;
                        imageHeight = imageHeight3;
                        texture = texture2;
                        imageSource = imageSource3;
                        margin = margin2;
                    }
                    texture2 = texture;
                    stopWidth = offsetY;
                    imageHeight3 = imageHeight;
                    imageSource3 = imageSource;
                    margin2 = margin;
                    spacing2 = spacing;
                    tmxMapLoader = this;
                    y = y2 + (tileheight + spacing2);
                    imageWidth = image;
                    spacing = spacing2;
                    props = props2;
                    image = stopHeight;
                    i = imageWidth2;
                    offsetY = stopWidth;
                    imageHeight = imageHeight3;
                    texture = texture2;
                    imageSource = imageSource3;
                }
                imageHeight3 = imageHeight;
                imageSource3 = imageSource;
                margin2 = margin;
                tileset = tileset2;
                spacing2 = spacing;
                imageElement = element6;
                offsetY2 = offsetY3;
                tmxMapLoader = this;
                image = tmxFile;
            } else {
                FileHandle image3 = image;
                imageWidth2 = i;
                imageHeight3 = imageHeight;
                imageSource3 = imageSource;
                margin2 = margin;
                tileset = tileset2;
                spacing2 = spacing;
                imageElement = element6;
                offsetY2 = offsetY3;
                tmxMapLoader = this;
                tileElements = imageElement.getChildrenByName("tile");
                Iterator i$ = tileElements.iterator();
                while (i$.hasNext()) {
                    Array<Element> tileElements2;
                    Iterator i$2;
                    FileHandle image4;
                    tileElement = (Element) i$.next();
                    element4 = tileElement.getChildByName("image");
                    if (element4 != null) {
                        String imageSource4 = element4.getAttribute("source");
                        tileElements2 = tileElements;
                        i$2 = i$;
                        tileElements = element4.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
                        imageHeight3 = element4.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
                        imageSource3 = imageSource4;
                        i = tileElements;
                        image4 = BaseTmxMapLoader.getRelativeFileHandle(tmxFile, imageSource4);
                    } else {
                        tileElements2 = tileElements;
                        i$2 = i$;
                        image = tmxFile;
                        image4 = image3;
                    }
                    FileHandle image5 = image4;
                    TiledMapTile tile2 = new StaticTiledMapTile(imageResolver2.getImage(image4.path()));
                    tile2.setId(tileElement.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID) + firstgid);
                    tile2.setOffsetX((float) offsetX);
                    tile2.setOffsetY(tmxMapLoader.flipY ? (float) (-offsetY2) : (float) offsetY2);
                    tileset.putTile(tile2.getId(), tile2);
                    tileElements = tileElements2;
                    i$ = i$2;
                    image3 = image5;
                    imageResolver2 = imageResolver;
                }
                image = tmxFile;
                imageWidth2 = i;
            }
            tileElements = imageElement.getChildrenByName("tile");
            Array<AnimatedTiledMapTile> animatedTiles = new Array();
            Iterator i$3 = tileElements.iterator();
            while (i$3.hasNext()) {
                Array<Element> tileElements3;
                Iterator it;
                tileElement = (Element) i$3.next();
                tile = tileset.getTile(firstgid + tileElement.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID, 0));
                if (tile != null) {
                    tileElements3 = tileElements;
                    tileElements = tileElement.getChildByName("animation");
                    Array<Element> animationElement;
                    if (tileElements != null) {
                        Array staticTiles = new Array();
                        it = i$3;
                        IntArray i$4 = new IntArray();
                        i2 = offsetY2;
                        Iterator i$5 = tileElements.getChildrenByName("frame").iterator();
                        while (i$5.hasNext()) {
                            animationElement = tileElements;
                            element3 = (Element) i$5.next();
                            Iterator i$6 = i$5;
                            staticTiles.add((StaticTiledMapTile) tileset.getTile(element3.getIntAttribute("tileid") + firstgid));
                            i$4.add(element3.getIntAttribute("duration"));
                            tileElements = animationElement;
                            i$5 = i$6;
                        }
                        animationElement = tileElements;
                        tileElements = new AnimatedTiledMapTile(i$4, staticTiles);
                        tileElements.setId(tile.getId());
                        animatedTiles.add(tileElements);
                        tile = tileElements;
                    } else {
                        animationElement = tileElements;
                        it = i$3;
                        i2 = offsetY2;
                    }
                    tileElements = tileElement.getAttribute("terrain", null);
                    if (tileElements != null) {
                        tile.getProperties().put("terrain", tileElements);
                    }
                    i$3 = tileElement.getAttribute("probability", null);
                    if (i$3 != null) {
                        tile.getProperties().put("probability", i$3);
                    }
                    offset = tileElement.getChildByName("properties");
                    if (offset != null) {
                        tmxMapLoader.loadProperties(tile.getProperties(), offset);
                    }
                } else {
                    tileElements3 = tileElements;
                    it = i$3;
                    i2 = offsetY2;
                }
                tileElements = tileElements3;
                i$3 = it;
                offsetY2 = i2;
                image = tmxFile;
            }
            i2 = offsetY2;
            Iterator i$7 = animatedTiles.iterator();
            while (i$7.hasNext()) {
                AnimatedTiledMapTile tile3 = (AnimatedTiledMapTile) i$7.next();
                tileset.putTile(tile3.getId(), tile3);
            }
            element3 = imageElement.getChildByName("properties");
            if (element3 != null) {
                tmxMapLoader.loadProperties(tileset.getProperties(), element3);
            }
            map.getTileSets().addTileSet(tileset);
            Element element7 = imageElement;
            return;
        }
    }
}
