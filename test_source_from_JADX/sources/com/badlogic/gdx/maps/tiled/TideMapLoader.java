package com.badlogic.gdx.maps.tiled;

import ar.com.hjg.pngj.chunks.PngChunkTextVar;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.ImageResolver.AssetManagerImageResolver;
import com.badlogic.gdx.maps.ImageResolver.DirectImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.facebook.internal.ServerProtocol;
import java.io.IOException;
import java.util.Iterator;
import java.util.StringTokenizer;
import name.antonsmirnov.firmata.FormatHelper;

public class TideMapLoader extends SynchronousAssetLoader<TiledMap, Parameters> {
    private Element root;
    private XmlReader xml = new XmlReader();

    public static class Parameters extends AssetLoaderParameters<TiledMap> {
    }

    public TideMapLoader() {
        super(new InternalFileHandleResolver());
    }

    public TideMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public TiledMap load(String fileName) {
        try {
            FileHandle tideFile = resolve(fileName);
            this.root = this.xml.parse(tideFile);
            ObjectMap<String, Texture> textures = new ObjectMap();
            Iterator i$ = loadTileSheets(this.root, tideFile).iterator();
            while (i$.hasNext()) {
                FileHandle textureFile = (FileHandle) i$.next();
                textures.put(textureFile.path(), new Texture(textureFile));
            }
            TiledMap map = loadMap(this.root, tideFile, new DirectImageResolver(textures));
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

    public TiledMap load(AssetManager assetManager, String fileName, FileHandle tideFile, Parameters parameter) {
        try {
            return loadMap(this.root, tideFile, new AssetManagerImageResolver(assetManager));
        } catch (Exception e) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Couldn't load tilemap '");
            stringBuilder.append(fileName);
            stringBuilder.append(FormatHelper.QUOTE);
            throw new GdxRuntimeException(stringBuilder.toString(), e);
        }
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle tmxFile, Parameters parameter) {
        Array<AssetDescriptor> dependencies = new Array();
        try {
            this.root = this.xml.parse(tmxFile);
            Iterator i$ = loadTileSheets(this.root, tmxFile).iterator();
            while (i$.hasNext()) {
                dependencies.add(new AssetDescriptor(((FileHandle) i$.next()).path(), Texture.class));
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

    private TiledMap loadMap(Element root, FileHandle tmxFile, ImageResolver imageResolver) {
        TiledMap map = new TiledMap();
        Element properties = root.getChildByName("Properties");
        if (properties != null) {
            loadProperties(map.getProperties(), properties);
        }
        Iterator i$ = root.getChildByName("TileSheets").getChildrenByName("TileSheet").iterator();
        while (i$.hasNext()) {
            loadTileSheet(map, (Element) i$.next(), tmxFile, imageResolver);
        }
        Iterator i$2 = root.getChildByName("Layers").getChildrenByName("Layer").iterator();
        while (i$2.hasNext()) {
            loadLayer(map, (Element) i$2.next());
        }
        return map;
    }

    private Array<FileHandle> loadTileSheets(Element root, FileHandle tideFile) throws IOException {
        Array<FileHandle> images = new Array();
        Iterator i$ = root.getChildByName("TileSheets").getChildrenByName("TileSheet").iterator();
        while (i$.hasNext()) {
            images.add(getRelativeFileHandle(tideFile, ((Element) i$.next()).getChildByName("ImageSource").getText()));
        }
        return images;
    }

    private void loadTileSheet(TiledMap map, Element element, FileHandle tideFile, ImageResolver imageResolver) {
        Element element2 = element;
        if (element.getName().equals("TileSheet")) {
            int stopHeight;
            int stopWidth;
            int sheetSizeX;
            String id = element2.getAttribute("Id");
            String description = element2.getChildByName(PngChunkTextVar.KEY_Description).getText();
            String imageSource = element2.getChildByName("ImageSource").getText();
            Element alignment = element2.getChildByName("Alignment");
            String sheetSize = alignment.getAttribute("SheetSize");
            String tileSize = alignment.getAttribute("TileSize");
            String margin = alignment.getAttribute("Margin");
            String spacing = alignment.getAttribute("Spacing");
            String[] sheetSizeParts = sheetSize.split(" x ");
            int sheetSizeX2 = Integer.parseInt(sheetSizeParts[0]);
            int sheetSizeY = Integer.parseInt(sheetSizeParts[1]);
            String[] tileSizeParts = tileSize.split(" x ");
            int tileSizeX = Integer.parseInt(tileSizeParts[0]);
            int tileSizeY = Integer.parseInt(tileSizeParts[1]);
            String[] marginParts = margin.split(" x ");
            int marginX = Integer.parseInt(marginParts[0]);
            int marginY = Integer.parseInt(marginParts[1]);
            String[] spacingParts = margin.split(" x ");
            int spacingX = Integer.parseInt(spacingParts[0]);
            int spacingY = Integer.parseInt(spacingParts[1]);
            FileHandle image = getRelativeFileHandle(tideFile, imageSource);
            TextureRegion texture = imageResolver.getImage(image.path());
            TiledMapTileSets tilesets = map.getTileSets();
            Iterator i$ = tilesets.iterator();
            int firstgid = 1;
            while (true) {
                String margin2 = margin;
                Iterator i$2 = i$;
                if (!i$2.hasNext()) {
                    break;
                }
                Iterator i$3 = i$2;
                firstgid += ((TiledMapTileSet) i$2.next()).size();
                margin = margin2;
                i$ = i$3;
            }
            TiledMapTileSet tileset = new TiledMapTileSet();
            tileset.setName(id);
            tileset.getProperties().put("firstgid", Integer.valueOf(firstgid));
            int stopWidth2 = texture.getRegionWidth() - tileSizeX;
            int stopHeight2 = texture.getRegionHeight() - tileSizeY;
            int gid = firstgid;
            int y = marginY;
            while (y <= stopHeight2) {
                int firstgid2 = firstgid;
                firstgid = gid;
                gid = marginX;
                while (true) {
                    stopHeight = stopHeight2;
                    stopHeight2 = gid;
                    if (stopHeight2 > stopWidth2) {
                        break;
                    }
                    stopWidth = stopWidth2;
                    sheetSizeX = sheetSizeX2;
                    stopWidth2 = new StaticTiledMapTile(new TextureRegion(texture, stopHeight2, y, tileSizeX, tileSizeY));
                    stopWidth2.setId(firstgid);
                    sheetSizeX2 = firstgid + 1;
                    tileset.putTile(firstgid, stopWidth2);
                    gid = stopHeight2 + (tileSizeX + spacingX);
                    firstgid = sheetSizeX2;
                    stopHeight2 = stopHeight;
                    stopWidth2 = stopWidth;
                    sheetSizeX2 = sheetSizeX;
                }
                sheetSizeX = sheetSizeX2;
                y += tileSizeY + spacingY;
                gid = firstgid;
                firstgid = firstgid2;
                stopHeight2 = stopHeight;
                stopWidth2 = stopWidth2;
            }
            stopWidth = stopWidth2;
            stopHeight = stopHeight2;
            sheetSizeX = sheetSizeX2;
            Element properties = element2.getChildByName("Properties");
            if (properties != null) {
                loadProperties(tileset.getProperties(), properties);
            } else {
                TideMapLoader tideMapLoader = this;
            }
            tilesets.addTileSet(tileset);
            return;
        }
        tideMapLoader = this;
    }

    private void loadLayer(TiledMap map, Element element) {
        Element element2 = element;
        if (element.getName().equals("Layer")) {
            String layerSize;
            String tileSize;
            String[] layerSizeParts;
            int tileSizeX;
            int layerSizeX;
            int tileSizeY;
            int layerSizeY;
            String[] tileSizeParts;
            Element tileArray;
            String id = element2.getAttribute("Id");
            String visible = element2.getAttribute("Visible");
            Element dimensions = element2.getChildByName("Dimensions");
            String layerSize2 = dimensions.getAttribute("LayerSize");
            String tileSize2 = dimensions.getAttribute("TileSize");
            String[] layerSizeParts2 = layerSize2.split(" x ");
            int layerSizeX2 = Integer.parseInt(layerSizeParts2[0]);
            int layerSizeY2 = Integer.parseInt(layerSizeParts2[1]);
            String[] tileSizeParts2 = tileSize2.split(" x ");
            int tileSizeX2 = Integer.parseInt(tileSizeParts2[0]);
            int tileSizeY2 = Integer.parseInt(tileSizeParts2[1]);
            TiledMapTileLayer layer = new TiledMapTileLayer(layerSizeX2, layerSizeY2, tileSizeX2, tileSizeY2);
            layer.setName(id);
            layer.setVisible(visible.equalsIgnoreCase("True"));
            Element tileArray2 = element2.getChildByName("TileArray");
            Array<Element> rows = tileArray2.getChildrenByName("Row");
            TiledMapTileSets tilesets = map.getTileSets();
            TiledMapTileSet currentTileSet = null;
            int firstgid = 0;
            int rowCount = rows.size;
            int row = 0;
            while (row < rowCount) {
                Element dimensions2 = dimensions;
                dimensions = (Element) rows.get(row);
                int rowCount2 = rowCount;
                rowCount = (rowCount - 1) - row;
                int childCount = dimensions.getChildCount();
                layerSize = layerSize2;
                tileSize = tileSize2;
                layerSizeParts = layerSizeParts2;
                TiledMapTileSet currentTileSet2 = currentTileSet;
                int x = 0;
                int child = 0;
                while (true) {
                    tileSizeX = tileSizeX2;
                    tileSizeX2 = childCount;
                    if (child >= tileSizeX2) {
                        break;
                    }
                    int childCount2 = tileSizeX2;
                    Element currentChild = dimensions.getChild(child);
                    Element currentRow = dimensions;
                    dimensions = currentChild.getName();
                    layerSizeX = layerSizeX2;
                    if (dimensions.equals("TileSheet") != 0) {
                        currentTileSet2 = tilesets.getTileSet(currentChild.getAttribute("Ref"));
                        TiledMapTileSet currentTileSet3 = currentTileSet2;
                        tileSizeY = tileSizeY2;
                        firstgid = ((Integer) currentTileSet2.getProperties().get("firstgid", Integer.class)).intValue();
                        layerSizeY = layerSizeY2;
                        tileSizeParts = tileSizeParts2;
                        tileArray = tileArray2;
                        currentTileSet2 = currentTileSet3;
                    } else {
                        tileSizeY = tileSizeY2;
                        if (dimensions.equals("Null") != 0) {
                            x += currentChild.getIntAttribute("Count");
                        } else if (dimensions.equals("Static") != 0) {
                            layerSizeX2 = new Cell();
                            layerSizeX2.setTile(currentTileSet2.getTile(firstgid + currentChild.getIntAttribute("Index")));
                            tileSizeY2 = x + 1;
                            layer.setCell(x, rowCount, layerSizeX2);
                            x = tileSizeY2;
                        } else if (dimensions.equals("Animated") != 0) {
                            layerSizeX2 = currentChild.getInt("Interval");
                            Element frames = currentChild.getChildByName("Frames");
                            Element name = dimensions;
                            Array dimensions3 = new Array();
                            int frameChildCount = frames.getChildCount();
                            TiledMapTileSet currentTileSet4 = currentTileSet2;
                            int frameChild = 0;
                            while (true) {
                                layerSizeY = layerSizeY2;
                                layerSizeY2 = frameChildCount;
                                if (frameChild >= layerSizeY2) {
                                    break;
                                }
                                int frameChildCount2 = layerSizeY2;
                                layerSizeY2 = frames.getChild(frameChild);
                                Element frames2 = frames;
                                frames = layerSizeY2.getName();
                                tileSizeParts = tileSizeParts2;
                                if (frames.equals("TileSheet")) {
                                    currentTileSet4 = tilesets.getTileSet(layerSizeY2.getAttribute("Ref"));
                                    TiledMapTileSet currentTileSet5 = currentTileSet4;
                                    tileArray = tileArray2;
                                    firstgid = ((Integer) currentTileSet4.getProperties().get("firstgid", Integer.class)).intValue();
                                    currentTileSet4 = currentTileSet5;
                                } else {
                                    tileArray = tileArray2;
                                    if (frames.equals("Static")) {
                                        dimensions3.add((StaticTiledMapTile) currentTileSet4.getTile(firstgid + layerSizeY2.getIntAttribute("Index")));
                                    }
                                }
                                frameChild++;
                                layerSizeY2 = layerSizeY;
                                frameChildCount = frameChildCount2;
                                frames = frames2;
                                tileSizeParts2 = tileSizeParts;
                                tileArray2 = tileArray;
                            }
                            tileSizeParts = tileSizeParts2;
                            tileArray = tileArray2;
                            Cell cell = new Cell();
                            cell.setTile(new AnimatedTiledMapTile(((float) layerSizeX2) / 1148846080, dimensions3));
                            tileSizeY2 = x + 1;
                            layer.setCell(x, rowCount, cell);
                            currentTileSet2 = currentTileSet4;
                            x = tileSizeY2;
                        } else {
                            layerSizeY = layerSizeY2;
                            tileSizeParts = tileSizeParts2;
                            tileArray = tileArray2;
                        }
                        layerSizeY = layerSizeY2;
                        tileSizeParts = tileSizeParts2;
                        tileArray = tileArray2;
                    }
                    child++;
                    tileSizeX2 = tileSizeX;
                    childCount = childCount2;
                    dimensions = currentRow;
                    layerSizeX2 = layerSizeX;
                    tileSizeY2 = tileSizeY;
                    layerSizeY2 = layerSizeY;
                    tileSizeParts2 = tileSizeParts;
                    tileArray2 = tileArray;
                }
                tileSizeY = tileSizeY2;
                layerSizeY = layerSizeY2;
                tileSizeParts = tileSizeParts2;
                tileArray = tileArray2;
                row++;
                currentTileSet = currentTileSet2;
                dimensions = dimensions2;
                rowCount = rowCount2;
                layerSize2 = layerSize;
                tileSize2 = tileSize;
                layerSizeParts2 = layerSizeParts;
                tileSizeX2 = tileSizeX;
            }
            layerSize = layerSize2;
            tileSize = tileSize2;
            layerSizeParts = layerSizeParts2;
            tileSizeX = tileSizeX2;
            layerSizeX = layerSizeX2;
            tileSizeY = tileSizeY2;
            layerSizeY = layerSizeY2;
            tileSizeParts = tileSizeParts2;
            tileArray = tileArray2;
            Element properties = element2.getChildByName("Properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            } else {
                TideMapLoader tideMapLoader = this;
            }
            map.getLayers().add(layer);
            return;
        }
        tideMapLoader = this;
    }

    private void loadProperties(MapProperties properties, Element element) {
        if (element.getName().equals("Properties")) {
            Iterator i$ = element.getChildrenByName("Property").iterator();
            while (i$.hasNext()) {
                Element property = (Element) i$.next();
                String key = property.getAttribute("Key", null);
                String type = property.getAttribute("Type", null);
                String value = property.getText();
                if (type.equals("Int32")) {
                    properties.put(key, Integer.valueOf(Integer.parseInt(value)));
                } else if (type.equals("String")) {
                    properties.put(key, value);
                } else if (type.equals("Boolean")) {
                    properties.put(key, Boolean.valueOf(value.equalsIgnoreCase(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE)));
                } else {
                    properties.put(key, value);
                }
            }
        }
    }

    private static FileHandle getRelativeFileHandle(FileHandle file, String path) {
        StringTokenizer tokenizer = new StringTokenizer(path, "\\/");
        FileHandle result = file.parent();
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.equals("..")) {
                result = result.parent();
            } else {
                result = result.child(token);
            }
        }
        return result;
    }
}
