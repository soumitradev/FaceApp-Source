package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.BaseTmxMapLoader.Parameters;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.IOException;
import java.util.Iterator;
import name.antonsmirnov.firmata.FormatHelper;

public class AtlasTmxMapLoader extends BaseTmxMapLoader<AtlasTiledMapLoaderParameters> {
    protected Array<Texture> trackedTextures = new Array();

    private interface AtlasResolver {

        public static class AssetManagerAtlasResolver implements AtlasResolver {
            private final AssetManager assetManager;

            public AssetManagerAtlasResolver(AssetManager assetManager) {
                this.assetManager = assetManager;
            }

            public TextureAtlas getAtlas(String name) {
                return (TextureAtlas) this.assetManager.get(name, TextureAtlas.class);
            }
        }

        public static class DirectAtlasResolver implements AtlasResolver {
            private final ObjectMap<String, TextureAtlas> atlases;

            public DirectAtlasResolver(ObjectMap<String, TextureAtlas> atlases) {
                this.atlases = atlases;
            }

            public TextureAtlas getAtlas(String name) {
                return (TextureAtlas) this.atlases.get(name);
            }
        }

        TextureAtlas getAtlas(String str);
    }

    public static class AtlasTiledMapLoaderParameters extends Parameters {
        public boolean forceTextureFilters = false;
    }

    public AtlasTmxMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public AtlasTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public TiledMap load(String fileName) {
        return load(fileName, new AtlasTiledMapLoaderParameters());
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {
        Array<AssetDescriptor> dependencies = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            Element properties = this.root.getChildByName("properties");
            if (properties != null) {
                Iterator i$ = properties.getChildrenByName("property").iterator();
                while (i$.hasNext()) {
                    Element property = (Element) i$.next();
                    String name = property.getAttribute("name");
                    String value = property.getAttribute(FirebaseAnalytics$Param.VALUE);
                    if (name.startsWith("atlas")) {
                        dependencies.add(new AssetDescriptor(BaseTmxMapLoader.getRelativeFileHandle(tmxFile, value), TextureAtlas.class));
                    }
                }
            }
            return dependencies;
        } catch (IOException e) {
            throw new GdxRuntimeException("Unable to parse .tmx file.");
        }
    }

    public TiledMap load(String fileName, AtlasTiledMapLoaderParameters parameter) {
        if (parameter != null) {
            try {
                this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
                this.flipY = parameter.flipY;
            } catch (IOException e) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Couldn't load tilemap '");
                stringBuilder.append(fileName);
                stringBuilder.append(FormatHelper.QUOTE);
                throw new GdxRuntimeException(stringBuilder.toString(), e);
            }
        }
        this.convertObjectToTileSpace = false;
        this.flipY = true;
        FileHandle e2 = resolve(fileName);
        this.root = this.xml.parse(e2);
        ObjectMap<String, TextureAtlas> atlases = new ObjectMap();
        FileHandle atlasFile = loadAtlas(this.root, e2);
        if (atlasFile == null) {
            throw new GdxRuntimeException("Couldn't load atlas");
        }
        atlases.put(atlasFile.path(), new TextureAtlas(atlasFile));
        TiledMap map = loadMap(this.root, e2, new DirectAtlasResolver(atlases));
        map.setOwnedResources(atlases.values().toArray());
        setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        return map;
    }

    protected FileHandle loadAtlas(Element root, FileHandle tmxFile) throws IOException {
        Element e = root.getChildByName("properties");
        FileHandle fileHandle = null;
        if (e != null) {
            Iterator i$ = e.getChildrenByName("property").iterator();
            while (i$.hasNext()) {
                Element property = (Element) i$.next();
                String name = property.getAttribute("name", null);
                String value = property.getAttribute(FirebaseAnalytics$Param.VALUE, null);
                if (name.equals("atlas")) {
                    if (value == null) {
                        value = property.getText();
                    }
                    if (value == null) {
                        continue;
                    } else if (value.length() != 0) {
                        return BaseTmxMapLoader.getRelativeFileHandle(tmxFile, value);
                    }
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(tmxFile.nameWithoutExtension());
        stringBuilder.append(".atlas");
        FileHandle atlasFile = tmxFile.sibling(stringBuilder.toString());
        if (atlasFile.exists()) {
            fileHandle = atlasFile;
        }
        return fileHandle;
    }

    private void setTextureFilters(Texture$TextureFilter min, Texture$TextureFilter mag) {
        Iterator i$ = this.trackedTextures.iterator();
        while (i$.hasNext()) {
            ((Texture) i$.next()).setFilter(min, mag);
        }
        this.trackedTextures.clear();
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle tmxFile, AtlasTiledMapLoaderParameters parameter) {
        this.map = null;
        if (parameter != null) {
            this.convertObjectToTileSpace = parameter.convertObjectToTileSpace;
            this.flipY = parameter.flipY;
        } else {
            this.convertObjectToTileSpace = false;
            this.flipY = true;
        }
        try {
            this.map = loadMap(this.root, tmxFile, new AssetManagerAtlasResolver(manager));
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load tilemap '");
            stringBuilder.append(fileName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    public TiledMap loadSync(AssetManager manager, String fileName, FileHandle file, AtlasTiledMapLoaderParameters parameter) {
        if (parameter != null) {
            setTextureFilters(parameter.textureMinFilter, parameter.textureMagFilter);
        }
        return this.map;
    }

    protected TiledMap loadMap(Element root, FileHandle tmxFile, AtlasResolver resolver) {
        FileHandle fileHandle;
        AtlasResolver atlasResolver;
        AtlasTmxMapLoader atlasTmxMapLoader = this;
        Element element = root;
        TiledMap map = new TiledMap();
        String mapOrientation = element.getAttribute("orientation", null);
        int mapWidth = element.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
        int mapHeight = element.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
        int tileWidth = element.getIntAttribute("tilewidth", 0);
        int tileHeight = element.getIntAttribute("tileheight", 0);
        String mapBackgroundColor = element.getAttribute("backgroundcolor", null);
        MapProperties mapProperties = map.getProperties();
        if (mapOrientation != null) {
            mapProperties.put("orientation", mapOrientation);
        }
        mapProperties.put(SettingsJsonConstants.ICON_WIDTH_KEY, Integer.valueOf(mapWidth));
        mapProperties.put(SettingsJsonConstants.ICON_HEIGHT_KEY, Integer.valueOf(mapHeight));
        mapProperties.put("tilewidth", Integer.valueOf(tileWidth));
        mapProperties.put("tileheight", Integer.valueOf(tileHeight));
        if (mapBackgroundColor != null) {
            mapProperties.put("backgroundcolor", mapBackgroundColor);
        }
        atlasTmxMapLoader.mapTileWidth = tileWidth;
        atlasTmxMapLoader.mapTileHeight = tileHeight;
        atlasTmxMapLoader.mapWidthInPixels = mapWidth * tileWidth;
        atlasTmxMapLoader.mapHeightInPixels = mapHeight * tileHeight;
        if (mapOrientation != null && "staggered".equals(mapOrientation) && mapHeight > 1) {
            atlasTmxMapLoader.mapWidthInPixels += tileWidth / 2;
            atlasTmxMapLoader.mapHeightInPixels = (atlasTmxMapLoader.mapHeightInPixels / 2) + (tileHeight / 2);
        }
        int i = 0;
        int j = root.getChildCount();
        while (i < j) {
            Element element2 = element.getChild(i);
            String elementName = element2.getName();
            if (elementName.equals("properties")) {
                loadProperties(map.getProperties(), element2);
                fileHandle = tmxFile;
                atlasResolver = resolver;
            } else if (elementName.equals("tileset")) {
                loadTileset(map, element2, tmxFile, resolver);
            } else {
                fileHandle = tmxFile;
                atlasResolver = resolver;
                if (elementName.equals("layer")) {
                    loadTileLayer(map, element2);
                } else if (elementName.equals("objectgroup")) {
                    loadObjectGroup(map, element2);
                }
            }
            i++;
            element = root;
        }
        fileHandle = tmxFile;
        atlasResolver = resolver;
        return map;
    }

    protected void loadTileset(TiledMap map, Element element, FileHandle tmxFile, AtlasResolver resolver) {
        IOException element2;
        IOException e;
        AtlasTmxMapLoader atlasTmxMapLoader = this;
        Element element3 = element;
        FileHandle fileHandle = tmxFile;
        if (element.getName().equals("tileset")) {
            int imageWidth;
            int imageHeight;
            FileHandle atlasFile;
            String name = element3.get("name", null);
            int firstgid = element3.getIntAttribute("firstgid", 1);
            int tilewidth = element3.getIntAttribute("tilewidth", 0);
            int tileheight = element3.getIntAttribute("tileheight", 0);
            int spacing = element3.getIntAttribute("spacing", 0);
            int margin = element3.getIntAttribute("margin", 0);
            String source = element3.getAttribute("source", null);
            int offsetX = 0;
            int offsetY = 0;
            String imageSource = "";
            int imageWidth2 = 0;
            int imageHeight2 = 0;
            String name2;
            if (source != null) {
                FileHandle tsx = BaseTmxMapLoader.getRelativeFileHandle(fileHandle, source);
                FileHandle tsx2;
                Element element4;
                int i;
                String str;
                try {
                    tsx2 = tsx;
                    try {
                        element3 = atlasTmxMapLoader.xml.parse(tsx2);
                        try {
                            name2 = name;
                            try {
                                name = element3.get("name", null);
                            } catch (IOException e2) {
                                element4 = element3;
                                element2 = e2;
                                i = tilewidth;
                                str = name2;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                        } catch (IOException e22) {
                            element4 = element3;
                            element2 = e22;
                            i = tilewidth;
                            str = name;
                            throw new GdxRuntimeException("Error parsing external tileset.");
                        }
                    } catch (IOException e222) {
                        element4 = element3;
                        i = tilewidth;
                        str = name;
                        element2 = e222;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                    try {
                        str = name;
                        try {
                            tilewidth = element3.getIntAttribute("tilewidth", 0);
                            try {
                                tileheight = element3.getIntAttribute("tileheight", 0);
                                spacing = element3.getIntAttribute("spacing", 0);
                                margin = element3.getIntAttribute("margin", 0);
                                Element offset = element3.getChildByName("tileoffset");
                                if (offset != null) {
                                    try {
                                        i = tilewidth;
                                    } catch (IOException e2222) {
                                        i = tilewidth;
                                        element4 = element3;
                                        element2 = e2222;
                                        throw new GdxRuntimeException("Error parsing external tileset.");
                                    }
                                    try {
                                        offsetX = offset.getIntAttribute("x", 0);
                                        offsetY = offset.getIntAttribute("y", 0);
                                    } catch (IOException e3) {
                                        e2222 = e3;
                                        element2 = e2222;
                                        throw new GdxRuntimeException("Error parsing external tileset.");
                                    }
                                }
                                i = tilewidth;
                                try {
                                    Element imageElement = element3.getChildByName("image");
                                    if (imageElement != null) {
                                        imageSource = imageElement.getAttribute("source");
                                        element4 = element3;
                                        try {
                                            imageWidth2 = imageElement.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
                                            imageHeight2 = imageElement.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
                                            Element image = BaseTmxMapLoader.getRelativeFileHandle(tsx2, imageSource);
                                        } catch (IOException e4) {
                                            e2222 = e4;
                                            element2 = e2222;
                                            throw new GdxRuntimeException("Error parsing external tileset.");
                                        }
                                    }
                                    element4 = element3;
                                    imageWidth = imageWidth2;
                                    imageHeight = imageHeight2;
                                    name = str;
                                    tilewidth = i;
                                    element3 = element4;
                                } catch (IOException e22222) {
                                    element4 = element3;
                                    element2 = e22222;
                                    throw new GdxRuntimeException("Error parsing external tileset.");
                                }
                            } catch (IOException e222222) {
                                element4 = element3;
                                i = tilewidth;
                                element2 = e222222;
                                throw new GdxRuntimeException("Error parsing external tileset.");
                            }
                        } catch (IOException e2222222) {
                            element4 = element3;
                            element2 = e2222222;
                            i = tilewidth;
                            throw new GdxRuntimeException("Error parsing external tileset.");
                        }
                    } catch (IOException e22222222) {
                        element4 = element3;
                        str = name;
                        element2 = e22222222;
                        i = tilewidth;
                        throw new GdxRuntimeException("Error parsing external tileset.");
                    }
                } catch (IOException e5) {
                    tsx2 = tsx;
                    element4 = element3;
                    i = tilewidth;
                    str = name;
                    throw new GdxRuntimeException("Error parsing external tileset.");
                }
            }
            name2 = name;
            name = element3.getChildByName("tileoffset");
            if (name != null) {
                offsetX = name.getIntAttribute("x", 0);
                offsetY = name.getIntAttribute("y", 0);
            }
            Element imageElement2 = element3.getChildByName("image");
            if (imageElement2 != null) {
                imageSource = imageElement2.getAttribute("source");
                imageWidth2 = imageElement2.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
                imageHeight2 = imageElement2.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
                FileHandle image2 = BaseTmxMapLoader.getRelativeFileHandle(fileHandle, imageSource);
            }
            imageWidth = imageWidth2;
            imageHeight = imageHeight2;
            name = name2;
            element3 = element;
            Element element5 = element3;
            int offsetY2 = offsetY;
            String atlasFilePath = (String) map.getProperties().get("atlas", String.class);
            if (atlasFilePath == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(tmxFile.nameWithoutExtension());
                stringBuilder.append(".atlas");
                atlasFile = fileHandle.sibling(stringBuilder.toString());
                if (atlasFile.exists()) {
                    atlasFilePath = atlasFile.name();
                }
            }
            if (atlasFilePath == null) {
                throw new GdxRuntimeException("The map is missing the 'atlas' property");
            }
            int i2;
            int i3;
            TiledMapTileSet tileset;
            int imageHeight3;
            int tileheight2;
            int spacing2;
            int margin2;
            MapProperties props;
            Element properties;
            int i4;
            atlasFile = resolve(BaseTmxMapLoader.getRelativeFileHandle(fileHandle, atlasFilePath).path());
            TextureAtlas atlas = resolver.getAtlas(atlasFile.path());
            atlasFilePath = atlasFile.nameWithoutExtension();
            Iterator i$ = atlas.getTextures().iterator();
            while (i$.hasNext()) {
                Iterator i$2 = i$;
                FileHandle atlasHandle = atlasFile;
                atlasTmxMapLoader.trackedTextures.add((Texture) i$.next());
                i$ = i$2;
                atlasFile = atlasHandle;
            }
            TiledMapTileSet tileset2 = new TiledMapTileSet();
            MapProperties props2 = tileset2.getProperties();
            tileset2.setName(name);
            TiledMapTileSet tileset3 = tileset2;
            props2.put("firstgid", Integer.valueOf(firstgid));
            props2.put("imagesource", imageSource);
            props2.put("imagewidth", Integer.valueOf(imageWidth));
            props2.put("imageheight", Integer.valueOf(imageHeight));
            props2.put("tilewidth", Integer.valueOf(tilewidth));
            props2.put("tileheight", Integer.valueOf(tileheight));
            props2.put("margin", Integer.valueOf(margin));
            props2.put("spacing", Integer.valueOf(spacing));
            i$ = atlas.findRegions(atlasFilePath).iterator();
            while (i$.hasNext()) {
                String regionsName;
                Iterator i$3;
                TextureRegion region = (AtlasRegion) i$.next();
                if (region != null) {
                    float f;
                    regionsName = atlasFilePath;
                    atlasFilePath = new StaticTiledMapTile(region);
                    i$3 = i$;
                    i$ = region.index + firstgid;
                    atlasFilePath.setId(i$);
                    atlasFilePath.setOffsetX((float) offsetX);
                    if (atlasTmxMapLoader.flipY) {
                        i2 = imageWidth;
                        i3 = offsetY2;
                        f = (float) (-i3);
                    } else {
                        i2 = imageWidth;
                        i3 = offsetY2;
                        f = (float) i3;
                    }
                    atlasFilePath.setOffsetY(f);
                    tileset = tileset3;
                    tileset.putTile(i$, atlasFilePath);
                } else {
                    regionsName = atlasFilePath;
                    i$3 = i$;
                    i2 = imageWidth;
                    i3 = offsetY2;
                    tileset = tileset3;
                }
                offsetY2 = i3;
                tileset3 = tileset;
                atlasFilePath = regionsName;
                i$ = i$3;
                imageWidth = i2;
            }
            i2 = imageWidth;
            i3 = offsetY2;
            tileset = tileset3;
            Element element6 = element5;
            Iterator i$4 = element6.getChildrenByName("tile").iterator();
            while (i$4.hasNext()) {
                TiledMapTile tile;
                TiledMapTile tile2;
                String terrain;
                String probability;
                Iterator i$5 = i$4;
                element3 = (Element) i$4.next();
                int tilewidth2 = tilewidth;
                imageHeight3 = imageHeight;
                tilewidth = element3.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID, 0) + firstgid;
                TiledMapTile tile3 = tileset.getTile(tilewidth);
                if (tile3 == null) {
                    tile = tile3;
                    tile3 = element3.getChildByName("image");
                    if (tile3 != null) {
                        tileheight2 = tileheight;
                        tileheight = tile3.getAttribute("source");
                        TiledMapTile imageElement3 = tile3;
                        spacing2 = spacing;
                        tile3 = tileheight.substring(0, tileheight.lastIndexOf(46));
                        TextureRegion tileheight3 = atlas.findRegion(tile3);
                        if (tileheight3 == 0) {
                            StringBuilder stringBuilder2 = new StringBuilder();
                            stringBuilder2.append("Tileset region not found: ");
                            stringBuilder2.append(tile3);
                            throw new GdxRuntimeException(stringBuilder2.toString());
                        }
                        margin2 = margin;
                        props = props2;
                        tile2 = new StaticTiledMapTile(tileheight3);
                        tile2.setId(tilewidth);
                        tile2.setOffsetX((float) offsetX);
                        tile2.setOffsetY(atlasTmxMapLoader.flipY ? (float) (-i3) : (float) i3);
                        tileset.putTile(tilewidth, tile2);
                        if (tile2 != null) {
                            terrain = element3.getAttribute("terrain", null);
                            if (terrain != null) {
                                tile2.getProperties().put("terrain", terrain);
                            }
                            probability = element3.getAttribute("probability", null);
                            if (probability != null) {
                                tile2.getProperties().put("probability", probability);
                            }
                            properties = element3.getChildByName("properties");
                            if (properties != null) {
                                loadProperties(tile2.getProperties(), properties);
                            }
                        }
                        i$4 = i$5;
                        tilewidth = tilewidth2;
                        imageHeight = imageHeight3;
                        tileheight = tileheight2;
                        spacing = spacing2;
                        margin = margin2;
                        props2 = props;
                    } else {
                        tileheight2 = tileheight;
                        spacing2 = spacing;
                        margin2 = margin;
                        props = props2;
                    }
                } else {
                    tile = tile3;
                    tileheight2 = tileheight;
                    spacing2 = spacing;
                    margin2 = margin;
                    props = props2;
                }
                tile2 = tile;
                if (tile2 != null) {
                    terrain = element3.getAttribute("terrain", null);
                    if (terrain != null) {
                        tile2.getProperties().put("terrain", terrain);
                    }
                    probability = element3.getAttribute("probability", null);
                    if (probability != null) {
                        tile2.getProperties().put("probability", probability);
                    }
                    properties = element3.getChildByName("properties");
                    if (properties != null) {
                        loadProperties(tile2.getProperties(), properties);
                    }
                }
                i$4 = i$5;
                tilewidth = tilewidth2;
                imageHeight = imageHeight3;
                tileheight = tileheight2;
                spacing = spacing2;
                margin = margin2;
                props2 = props;
            }
            imageHeight3 = imageHeight;
            tileheight2 = tileheight;
            spacing2 = spacing;
            margin2 = margin;
            props = props2;
            Array<Element> tileElements = element6.getChildrenByName("tile");
            Array<AnimatedTiledMapTile> animatedTiles = new Array();
            Iterator i$6 = tileElements.iterator();
            while (i$6.hasNext()) {
                Array<Element> array;
                Iterator it;
                properties = (Element) i$6.next();
                spacing = properties.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID, 0);
                TiledMapTile tile4 = tileset.getTile(firstgid + spacing);
                if (tile4 != null) {
                    Element animationElement = properties.getChildByName("animation");
                    int localtid;
                    if (animationElement != null) {
                        array = tileElements;
                        Array tileElements2 = new Array();
                        i4 = i3;
                        IntArray offsetY3 = new IntArray();
                        it = i$6;
                        i$6 = animationElement.getChildrenByName("frame").iterator();
                        while (i$6.hasNext()) {
                            Iterator i$7 = i$6;
                            Element frameElement = (Element) i$6.next();
                            localtid = spacing;
                            tileElements2.add((StaticTiledMapTile) tileset.getTile(frameElement.getIntAttribute("tileid") + firstgid));
                            offsetY3.add(frameElement.getIntAttribute("duration"));
                            i$6 = i$7;
                            spacing = localtid;
                        }
                        i$6 = new AnimatedTiledMapTile(offsetY3, tileElements2);
                        i$6.setId(tile4.getId());
                        animatedTiles.add(i$6);
                        tile4 = i$6;
                    } else {
                        array = tileElements;
                        i4 = i3;
                        it = i$6;
                        localtid = spacing;
                    }
                    tileElements = properties.getAttribute("terrain", null);
                    if (tileElements != null) {
                        tile4.getProperties().put("terrain", tileElements);
                    }
                    i$6 = properties.getAttribute("probability", null);
                    if (i$6 != null) {
                        tile4.getProperties().put("probability", i$6);
                    }
                    i3 = properties.getChildByName("properties");
                    if (i3 != 0) {
                        loadProperties(tile4.getProperties(), i3);
                    }
                } else {
                    array = tileElements;
                    i4 = i3;
                    it = i$6;
                }
                tileElements = array;
                i3 = i4;
                i$6 = it;
            }
            i4 = i3;
            i$4 = animatedTiles.iterator();
            while (i$4.hasNext()) {
                AnimatedTiledMapTile tile5 = (AnimatedTiledMapTile) i$4.next();
                tileset.putTile(tile5.getId(), tile5);
            }
            element3 = element6.getChildByName("properties");
            if (element3 != null) {
                loadProperties(tileset.getProperties(), element3);
            }
            map.getTileSets().addTileSet(tileset);
            Element element7 = element6;
            return;
        }
    }
}
