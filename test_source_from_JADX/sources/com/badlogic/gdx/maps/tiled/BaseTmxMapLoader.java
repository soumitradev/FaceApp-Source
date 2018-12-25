package com.badlogic.gdx.maps.tiled;

import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;
import name.antonsmirnov.firmata.FormatHelper;

public abstract class BaseTmxMapLoader<P extends AssetLoaderParameters<TiledMap>> extends AsynchronousAssetLoader<TiledMap, P> {
    protected static final int FLAG_FLIP_DIAGONALLY = 536870912;
    protected static final int FLAG_FLIP_HORIZONTALLY = Integer.MIN_VALUE;
    protected static final int FLAG_FLIP_VERTICALLY = 1073741824;
    protected static final int MASK_CLEAR = -536870912;
    protected boolean convertObjectToTileSpace;
    protected boolean flipY = true;
    protected TiledMap map;
    protected int mapHeightInPixels;
    protected int mapTileHeight;
    protected int mapTileWidth;
    protected int mapWidthInPixels;
    protected Element root;
    protected XmlReader xml = new XmlReader();

    public static class Parameters extends AssetLoaderParameters<TiledMap> {
        public boolean convertObjectToTileSpace = false;
        public boolean flipY = true;
        public boolean generateMipMaps = false;
        public Texture$TextureFilter textureMagFilter = Texture$TextureFilter.Nearest;
        public Texture$TextureFilter textureMinFilter = Texture$TextureFilter.Nearest;
    }

    public BaseTmxMapLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    protected void loadTileLayer(TiledMap map, Element element) {
        BaseTmxMapLoader baseTmxMapLoader = this;
        Element element2 = element;
        if (element.getName().equals("layer")) {
            int width = element2.getIntAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0);
            int height = element2.getIntAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0);
            TiledMapTileLayer layer = new TiledMapTileLayer(width, height, element.getParent().getIntAttribute("tilewidth", 0), element.getParent().getIntAttribute("tileheight", 0));
            loadBasicLayerInfo(layer, element2);
            int[] ids = getTileIds(element2, width, height);
            TiledMapTileSets tilesets = map.getTileSets();
            int y = 0;
            while (y < height) {
                int x = 0;
                while (x < width) {
                    int width2;
                    int id = ids[(y * width) + x];
                    boolean flipDiagonally = true;
                    boolean flipHorizontally = (Integer.MIN_VALUE & id) != 0;
                    boolean flipVertically = (1073741824 & id) != 0;
                    if ((id & FLAG_FLIP_DIAGONALLY) == 0) {
                        flipDiagonally = false;
                    }
                    TiledMapTile tile = tilesets.getTile(id & 536870911);
                    if (tile != null) {
                        width2 = width;
                        width = createTileLayerCell(flipHorizontally, flipVertically, flipDiagonally);
                        width.setTile(tile);
                        layer.setCell(x, baseTmxMapLoader.flipY != null ? (height - 1) - y : y, width);
                    } else {
                        width2 = width;
                    }
                    x++;
                    width = width2;
                }
                y++;
            }
            Element properties = element2.getChildByName("properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            map.getLayers().add(layer);
        }
    }

    protected void loadObjectGroup(TiledMap map, Element element) {
        if (element.getName().equals("objectgroup")) {
            String name = element.getAttribute("name", null);
            MapLayer layer = new MapLayer();
            layer.setName(name);
            Element properties = element.getChildByName("properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            Iterator i$ = element.getChildrenByName("object").iterator();
            while (i$.hasNext()) {
                loadObject(map, layer, (Element) i$.next());
            }
            map.getLayers().add(layer);
        }
    }

    protected void loadImageLayer(TiledMap map, Element element, FileHandle tmxFile, ImageResolver imageResolver) {
        if (element.getName().equals("imagelayer")) {
            int x = Integer.parseInt(element.getAttribute("x", AppEventsConstants.EVENT_PARAM_VALUE_NO));
            int y = Integer.parseInt(element.getAttribute("y", AppEventsConstants.EVENT_PARAM_VALUE_NO));
            if (this.flipY) {
                y = this.mapHeightInPixels - y;
            }
            TextureRegion texture = null;
            Element image = element.getChildByName("image");
            if (image != null) {
                texture = imageResolver.getImage(getRelativeFileHandle(tmxFile, image.getAttribute("source")).path());
                y -= texture.getRegionHeight();
            }
            TiledMapImageLayer layer = new TiledMapImageLayer(texture, (float) x, (float) y);
            loadBasicLayerInfo(layer, element);
            Element properties = element.getChildByName("properties");
            if (properties != null) {
                loadProperties(layer.getProperties(), properties);
            }
            map.getLayers().add(layer);
        }
    }

    protected void loadBasicLayerInfo(MapLayer layer, Element element) {
        String name = element.getAttribute("name", null);
        float opacity = Float.parseFloat(element.getAttribute("opacity", "1.0"));
        boolean z = true;
        if (element.getIntAttribute("visible", 1) != 1) {
            z = false;
        }
        boolean visible = z;
        layer.setName(name);
        layer.setOpacity(opacity);
        layer.setVisible(visible);
    }

    protected void loadObject(TiledMap map, MapLayer layer, Element element) {
        BaseTmxMapLoader baseTmxMapLoader = this;
        Element element2 = element;
        if (element.getName().equals("object")) {
            MapObject object;
            String attribute;
            String gid;
            boolean flipHorizontally;
            boolean flipVertically;
            TextureRegion textureRegion;
            MapObject textureMapObject;
            MapObject mapObject;
            String str;
            String rotation;
            boolean z;
            Element properties;
            MapObject object2 = null;
            float scaleY = 1.0f;
            float scaleX = baseTmxMapLoader.convertObjectToTileSpace ? 1.0f / ((float) baseTmxMapLoader.mapTileWidth) : 1.0f;
            if (baseTmxMapLoader.convertObjectToTileSpace) {
                scaleY = 1.0f / ((float) baseTmxMapLoader.mapTileHeight);
            }
            float x = element2.getFloatAttribute("x", 0.0f) * scaleX;
            float y = (baseTmxMapLoader.flipY ? ((float) baseTmxMapLoader.mapHeightInPixels) - element2.getFloatAttribute("y", 0.0f) : element2.getFloatAttribute("y", 0.0f)) * scaleY;
            float width = element2.getFloatAttribute(SettingsJsonConstants.ICON_WIDTH_KEY, 0.0f) * scaleX;
            float height = element2.getFloatAttribute(SettingsJsonConstants.ICON_HEIGHT_KEY, 0.0f) * scaleY;
            int i = 0;
            if (element.getChildCount() > 0) {
                Element childByName = element2.getChildByName("polygon");
                Element child = childByName;
                String[] points;
                if (childByName != null) {
                    points = child.getAttribute("points").split(FormatHelper.SPACE);
                    float[] vertices = new float[(points.length * 2)];
                    int i2 = 0;
                    while (true) {
                        int i3 = i2;
                        if (i3 >= points.length) {
                            break;
                        }
                        String[] point = points[i3].split(",");
                        vertices[i3 * 2] = Float.parseFloat(point[i]) * scaleX;
                        vertices[(i3 * 2) + 1] = (Float.parseFloat(point[1]) * scaleY) * ((float) (baseTmxMapLoader.flipY ? -1 : 1));
                        i2 = i3 + 1;
                        i = 0;
                    }
                    Polygon polygon = new Polygon(vertices);
                    polygon.setPosition(x, y);
                    object2 = new PolygonMapObject(polygon);
                } else {
                    Element childByName2 = element2.getChildByName("polyline");
                    child = childByName2;
                    if (childByName2 != null) {
                        String[] points2 = child.getAttribute("points").split(FormatHelper.SPACE);
                        float[] vertices2 = new float[(points2.length * 2)];
                        int i4 = 0;
                        while (i4 < points2.length) {
                            points = points2[i4].split(",");
                            object = object2;
                            vertices2[i4 * 2] = Float.parseFloat(points[0]) * scaleX;
                            vertices2[(i4 * 2) + 1] = (Float.parseFloat(points[1]) * scaleY) * ((float) (baseTmxMapLoader.flipY ? -1 : 1));
                            i4++;
                            object2 = object;
                        }
                        Polyline polyline = new Polyline(vertices2);
                        polyline.setPosition(x, y);
                        object2 = new PolylineMapObject(polyline);
                    } else {
                        object = null;
                        Element childByName3 = element2.getChildByName("ellipse");
                        childByName2 = childByName3;
                        if (childByName3 != null) {
                            object2 = new EllipseMapObject(x, baseTmxMapLoader.flipY ? y - height : y, width, height);
                        }
                    }
                }
                if (object2 != null) {
                    attribute = element2.getAttribute("gid", null);
                    gid = attribute;
                    if (attribute == null) {
                        i = (int) Long.parseLong(gid);
                        flipHorizontally = (Integer.MIN_VALUE & i) == 0;
                        flipVertically = (1073741824 & i) == 0;
                        textureRegion = new TextureRegion(map.getTileSets().getTile(536870911 & i).getTextureRegion());
                        textureRegion.flip(flipHorizontally, flipVertically);
                        textureMapObject = new TextureMapObject(textureRegion);
                        textureMapObject.getProperties().put("gid", Integer.valueOf(i));
                        textureMapObject.setX(x);
                        textureMapObject.setY(baseTmxMapLoader.flipY ? y - height : y);
                        textureMapObject.setScaleX(scaleX);
                        textureMapObject.setScaleY(scaleY);
                        textureMapObject.setRotation(element2.getFloatAttribute("rotation", 0.0f));
                        object2 = textureMapObject;
                    } else {
                        mapObject = object2;
                        str = gid;
                        object2 = new RectangleMapObject(x, baseTmxMapLoader.flipY ? y - height : y, width, height);
                    }
                } else {
                    mapObject = object2;
                }
                object2.setName(element2.getAttribute("name", null));
                rotation = element2.getAttribute("rotation", null);
                if (rotation != null) {
                    object2.getProperties().put("rotation", Float.valueOf(Float.parseFloat(rotation)));
                }
                gid = element2.getAttribute("type", null);
                if (gid != null) {
                    object2.getProperties().put("type", gid);
                }
                i = element2.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID, 0);
                if (i != 0) {
                    object2.getProperties().put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(i));
                }
                object2.getProperties().put("x", Float.valueOf(x * scaleX));
                object2.getProperties().put("y", Float.valueOf((baseTmxMapLoader.flipY ? y - height : y) * scaleY));
                object2.getProperties().put(SettingsJsonConstants.ICON_WIDTH_KEY, Float.valueOf(width));
                object2.getProperties().put(SettingsJsonConstants.ICON_HEIGHT_KEY, Float.valueOf(height));
                z = true;
                if (element2.getIntAttribute("visible", 1) == 1) {
                    z = false;
                }
                object2.setVisible(z);
                properties = element2.getChildByName("properties");
                if (properties != null) {
                    loadProperties(object2.getProperties(), properties);
                }
                layer.getObjects().add(object2);
            }
            object = null;
            object2 = object;
            if (object2 != null) {
                mapObject = object2;
            } else {
                attribute = element2.getAttribute("gid", null);
                gid = attribute;
                if (attribute == null) {
                    mapObject = object2;
                    str = gid;
                    if (baseTmxMapLoader.flipY) {
                    }
                    object2 = new RectangleMapObject(x, baseTmxMapLoader.flipY ? y - height : y, width, height);
                } else {
                    i = (int) Long.parseLong(gid);
                    if ((Integer.MIN_VALUE & i) == 0) {
                    }
                    if ((1073741824 & i) == 0) {
                    }
                    textureRegion = new TextureRegion(map.getTileSets().getTile(536870911 & i).getTextureRegion());
                    textureRegion.flip(flipHorizontally, flipVertically);
                    textureMapObject = new TextureMapObject(textureRegion);
                    textureMapObject.getProperties().put("gid", Integer.valueOf(i));
                    textureMapObject.setX(x);
                    if (baseTmxMapLoader.flipY) {
                    }
                    textureMapObject.setY(baseTmxMapLoader.flipY ? y - height : y);
                    textureMapObject.setScaleX(scaleX);
                    textureMapObject.setScaleY(scaleY);
                    textureMapObject.setRotation(element2.getFloatAttribute("rotation", 0.0f));
                    object2 = textureMapObject;
                }
            }
            object2.setName(element2.getAttribute("name", null));
            rotation = element2.getAttribute("rotation", null);
            if (rotation != null) {
                object2.getProperties().put("rotation", Float.valueOf(Float.parseFloat(rotation)));
            }
            gid = element2.getAttribute("type", null);
            if (gid != null) {
                object2.getProperties().put("type", gid);
            }
            i = element2.getIntAttribute(ShareConstants.WEB_DIALOG_PARAM_ID, 0);
            if (i != 0) {
                object2.getProperties().put(ShareConstants.WEB_DIALOG_PARAM_ID, Integer.valueOf(i));
            }
            object2.getProperties().put("x", Float.valueOf(x * scaleX));
            if (baseTmxMapLoader.flipY) {
            }
            object2.getProperties().put("y", Float.valueOf((baseTmxMapLoader.flipY ? y - height : y) * scaleY));
            object2.getProperties().put(SettingsJsonConstants.ICON_WIDTH_KEY, Float.valueOf(width));
            object2.getProperties().put(SettingsJsonConstants.ICON_HEIGHT_KEY, Float.valueOf(height));
            z = true;
            if (element2.getIntAttribute("visible", 1) == 1) {
                z = false;
            }
            object2.setVisible(z);
            properties = element2.getChildByName("properties");
            if (properties != null) {
                loadProperties(object2.getProperties(), properties);
            }
            layer.getObjects().add(object2);
        }
    }

    protected void loadProperties(MapProperties properties, Element element) {
        if (element != null && element.getName().equals("properties")) {
            Iterator i$ = element.getChildrenByName("property").iterator();
            while (i$.hasNext()) {
                Element property = (Element) i$.next();
                String name = property.getAttribute("name", null);
                String value = property.getAttribute(FirebaseAnalytics$Param.VALUE, null);
                if (value == null) {
                    value = property.getText();
                }
                properties.put(name, value);
            }
        }
    }

    protected Cell createTileLayerCell(boolean flipHorizontally, boolean flipVertically, boolean flipDiagonally) {
        Cell cell = new Cell();
        if (!flipDiagonally) {
            cell.setFlipHorizontally(flipHorizontally);
            cell.setFlipVertically(flipVertically);
        } else if (flipHorizontally && flipVertically) {
            cell.setFlipHorizontally(true);
            cell.setRotation(3);
        } else if (flipHorizontally) {
            cell.setRotation(3);
        } else if (flipVertically) {
            cell.setRotation(1);
        } else {
            cell.setFlipVertically(true);
            cell.setRotation(3);
        }
        return cell;
    }

    public static int[] getTileIds(Element element, int width, int height) {
        int i = width;
        int i2 = height;
        Element data = element.getChildByName(ShareConstants.WEB_DIALOG_PARAM_DATA);
        String encoding = data.getAttribute("encoding", null);
        if (encoding == null) {
            throw new GdxRuntimeException("Unsupported encoding (XML) for TMX Layer Data");
        }
        int[] ids = new int[(i * i2)];
        if (encoding.equals("csv")) {
            String[] array = data.getText().split(",");
            int i3 = 0;
            while (true) {
                int i4 = i3;
                if (i4 >= array.length) {
                    break;
                }
                ids[i4] = (int) Long.parseLong(array[i4].trim());
                i3 = i4 + 1;
            }
        } else if (encoding.equals("base64")) {
            InputStream is = null;
            StringBuilder stringBuilder;
            try {
                String compression = data.getAttribute("compression", null);
                byte[] bytes = Base64Coder.decode(data.getText());
                if (compression == null) {
                    is = new ByteArrayInputStream(bytes);
                } else if (compression.equals(HttpRequest.ENCODING_GZIP)) {
                    is = new GZIPInputStream(new ByteArrayInputStream(bytes), bytes.length);
                } else if (compression.equals("zlib")) {
                    is = new InflaterInputStream(new ByteArrayInputStream(bytes));
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unrecognised compression (");
                    stringBuilder.append(compression);
                    stringBuilder.append(") for TMX Layer Data");
                    throw new GdxRuntimeException(stringBuilder.toString());
                }
                byte[] temp = new byte[4];
                int y = 0;
                while (y < i2) {
                    int x = 0;
                    while (x < i) {
                        int read = is.read(temp);
                        while (read < temp.length) {
                            int curr = is.read(temp, read, temp.length - read);
                            if (curr == -1) {
                                break;
                            }
                            read += curr;
                        }
                        if (read != temp.length) {
                            throw new GdxRuntimeException("Error Reading TMX Layer Data: Premature end of tile data");
                        }
                        ids[(y * i) + x] = ((unsignedByteToInt(temp[0]) | (unsignedByteToInt(temp[1]) << 8)) | (unsignedByteToInt(temp[2]) << 16)) | (unsignedByteToInt(temp[3]) << 24);
                        x++;
                        i = width;
                    }
                    y++;
                    i = width;
                }
                StreamUtils.closeQuietly(is);
            } catch (IOException e) {
                IOException e2 = e;
                stringBuilder = new StringBuilder();
                stringBuilder.append("Error Reading TMX Layer Data - IOException: ");
                stringBuilder.append(e2.getMessage());
                throw new GdxRuntimeException(stringBuilder.toString());
            } catch (Throwable th) {
                Throwable th2 = th;
                StreamUtils.closeQuietly(is);
            }
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Unrecognised encoding (");
            stringBuilder2.append(encoding);
            stringBuilder2.append(") for TMX Layer Data");
            throw new GdxRuntimeException(stringBuilder2.toString());
        }
        return ids;
    }

    protected static int unsignedByteToInt(byte b) {
        return b & 255;
    }

    protected static FileHandle getRelativeFileHandle(FileHandle file, String path) {
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
