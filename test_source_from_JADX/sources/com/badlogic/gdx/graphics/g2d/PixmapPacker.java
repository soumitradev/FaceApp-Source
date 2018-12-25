package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap$Blending;
import com.badlogic.gdx.graphics.Pixmap$Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture$TextureFilter;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.OrderedMap;
import java.util.Iterator;

public class PixmapPacker implements Disposable {
    private static final String ANONYMOUS = "ANONYMOUS";
    private static final boolean debug = false;
    Page current;
    boolean disposed;
    final boolean duplicateBorder;
    boolean packToTexture;
    final int padding;
    final Pixmap$Format pageFormat;
    final int pageHeight;
    final int pageWidth;
    final Array<Page> pages = new Array();

    static final class Node {
        public String leafName;
        public Node leftChild;
        public Rectangle rect;
        public Node rightChild;

        public Node(int x, int y, int width, int height, Node leftChild, Node rightChild, String leafName) {
            this.rect = new Rectangle((float) x, (float) y, (float) width, (float) height);
            this.leftChild = leftChild;
            this.rightChild = rightChild;
            this.leafName = leafName;
        }

        public Node() {
            this.rect = new Rectangle();
        }
    }

    public static class Page {
        final Array<String> addedRects = new Array();
        boolean dirty;
        Pixmap image;
        OrderedMap<String, Rectangle> rects;
        Node root;
        Texture texture;

        public Pixmap getPixmap() {
            return this.image;
        }

        public OrderedMap<String, Rectangle> getRects() {
            return this.rects;
        }

        public Texture getTexture() {
            return this.texture;
        }

        public boolean updateTexture(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean useMipMaps) {
            if (this.texture == null) {
                this.texture = new Texture(new PixmapTextureData(this.image, this.image.getFormat(), useMipMaps, false, true)) {
                    public void dispose() {
                        super.dispose();
                        Page.this.image.dispose();
                    }
                };
                this.texture.setFilter(minFilter, magFilter);
            } else if (!this.dirty) {
                return false;
            } else {
                this.texture.load(this.texture.getTextureData());
            }
            this.dirty = false;
            return true;
        }
    }

    public PixmapPacker(int pageWidth, int pageHeight, Pixmap$Format pageFormat, int padding, boolean duplicateBorder) {
        this.pageWidth = pageWidth;
        this.pageHeight = pageHeight;
        this.pageFormat = pageFormat;
        this.padding = padding;
        this.duplicateBorder = duplicateBorder;
        newPage();
    }

    public synchronized Rectangle pack(Pixmap image) {
        return pack(null, image);
    }

    public synchronized Rectangle pack(String name, Pixmap image) {
        PixmapPacker pixmapPacker = this;
        String str = name;
        synchronized (this) {
            try {
                if (pixmapPacker.disposed) {
                    return null;
                }
                if (str != null) {
                    if (getRect(name) != null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Pixmap has already been packed with name: ");
                        stringBuilder.append(str);
                        throw new GdxRuntimeException(stringBuilder.toString());
                    }
                }
                int borderPixels = (pixmapPacker.padding + pixmapPacker.duplicateBorder) << 1;
                Rectangle rect = new Rectangle(0.0f, 0.0f, (float) (image.getWidth() + borderPixels), (float) (image.getHeight() + borderPixels));
                if (rect.getWidth() <= ((float) pixmapPacker.pageWidth)) {
                    if (rect.getHeight() <= ((float) pixmapPacker.pageHeight)) {
                        Node node = insert(pixmapPacker.current.root, rect);
                        if (node == null) {
                            newPage();
                            Rectangle pack = pack(name, image);
                            return pack;
                        }
                        int rectHeight;
                        int rectWidth;
                        int rectY;
                        node.leafName = str == null ? ANONYMOUS : str;
                        rect = new Rectangle(node.rect);
                        rect.width -= (float) borderPixels;
                        rect.height -= (float) borderPixels;
                        borderPixels >>= 1;
                        rect.f12x += (float) borderPixels;
                        rect.f13y += (float) borderPixels;
                        if (str != null) {
                            pixmapPacker.current.rects.put(str, rect);
                            pixmapPacker.current.addedRects.add(str);
                        }
                        int rectX = (int) rect.f12x;
                        int rectY2 = (int) rect.f13y;
                        int rectWidth2 = (int) rect.width;
                        int rectHeight2 = (int) rect.height;
                        if (!pixmapPacker.packToTexture || pixmapPacker.duplicateBorder || pixmapPacker.current.texture == null || pixmapPacker.current.dirty) {
                            rectHeight = rectHeight2;
                            rectWidth = rectWidth2;
                            rectY = rectY2;
                            pixmapPacker.current.dirty = true;
                        } else {
                            pixmapPacker.current.texture.bind();
                            rectHeight = rectHeight2;
                            rectWidth = rectWidth2;
                            rectY = rectY2;
                            Gdx.gl.glTexSubImage2D(pixmapPacker.current.texture.glTarget, 0, rectX, rectY2, rectWidth2, rectHeight, image.getGLFormat(), image.getGLType(), image.getPixels());
                        }
                        Pixmap$Blending blending = Pixmap.getBlending();
                        Pixmap.setBlending(Pixmap$Blending.None);
                        Pixmap pixmap = image;
                        rectWidth2 = rectY;
                        pixmapPacker.current.image.drawPixmap(pixmap, rectX, rectWidth2);
                        if (pixmapPacker.duplicateBorder) {
                            rectY = image.getWidth();
                            int imageHeight = image.getHeight();
                            int rectY3 = rectWidth2;
                            pixmapPacker.current.image.drawPixmap(pixmap, 0, 0, 1, 1, rectX - 1, rectWidth2 - 1, 1, 1);
                            pixmapPacker.current.image.drawPixmap(image, rectY - 1, 0, 1, 1, rectX + rectWidth, rectY3 - 1, 1, 1);
                            pixmapPacker.current.image.drawPixmap(image, 0, imageHeight - 1, 1, 1, rectX - 1, rectY3 + rectHeight, 1, 1);
                            pixmapPacker.current.image.drawPixmap(image, rectY - 1, imageHeight - 1, 1, 1, rectX + rectWidth, rectY3 + rectHeight, 1, 1);
                            pixmapPacker.current.image.drawPixmap(image, 0, 0, rectY, 1, rectX, rectY3 - 1, rectWidth, 1);
                            pixmapPacker.current.image.drawPixmap(image, 0, imageHeight - 1, rectY, 1, rectX, rectY3 + rectHeight, rectWidth, 1);
                            pixmapPacker.current.image.drawPixmap(image, 0, 0, 1, imageHeight, rectX - 1, rectY3, 1, rectHeight);
                            pixmapPacker.current.image.drawPixmap(image, rectY - 1, 0, 1, imageHeight, rectX + rectWidth, rectY3, 1, rectHeight);
                        }
                        Pixmap.setBlending(blending);
                        return rect;
                    }
                }
                if (str == null) {
                    throw new GdxRuntimeException("Page size too small for anonymous pixmap.");
                }
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Page size too small for pixmap: ");
                stringBuilder2.append(str);
                throw new GdxRuntimeException(stringBuilder2.toString());
            } catch (Throwable th) {
                Throwable th2 = th;
            }
        }
    }

    private void newPage() {
        Page page = new Page();
        page.image = new Pixmap(this.pageWidth, this.pageHeight, this.pageFormat);
        page.root = new Node(0, 0, this.pageWidth, this.pageHeight, null, null, null);
        page.rects = new OrderedMap();
        this.pages.add(page);
        this.current = page;
    }

    private Node insert(Node node, Rectangle rect) {
        if (node.leafName == null && node.leftChild != null && node.rightChild != null) {
            Node newNode = insert(node.leftChild, rect);
            if (newNode == null) {
                newNode = insert(node.rightChild, rect);
            }
            return newNode;
        } else if (node.leafName != null) {
            return null;
        } else {
            if (node.rect.width == rect.width && node.rect.height == rect.height) {
                return node;
            }
            if (node.rect.width >= rect.width) {
                if (node.rect.height >= rect.height) {
                    node.leftChild = new Node();
                    node.rightChild = new Node();
                    if (((int) node.rect.width) - ((int) rect.width) > ((int) node.rect.height) - ((int) rect.height)) {
                        node.leftChild.rect.f12x = node.rect.f12x;
                        node.leftChild.rect.f13y = node.rect.f13y;
                        node.leftChild.rect.width = rect.width;
                        node.leftChild.rect.height = node.rect.height;
                        node.rightChild.rect.f12x = node.rect.f12x + rect.width;
                        node.rightChild.rect.f13y = node.rect.f13y;
                        node.rightChild.rect.width = node.rect.width - rect.width;
                        node.rightChild.rect.height = node.rect.height;
                    } else {
                        node.leftChild.rect.f12x = node.rect.f12x;
                        node.leftChild.rect.f13y = node.rect.f13y;
                        node.leftChild.rect.width = node.rect.width;
                        node.leftChild.rect.height = rect.height;
                        node.rightChild.rect.f12x = node.rect.f12x;
                        node.rightChild.rect.f13y = node.rect.f13y + rect.height;
                        node.rightChild.rect.width = node.rect.width;
                        node.rightChild.rect.height = node.rect.height - rect.height;
                    }
                    return insert(node.leftChild, rect);
                }
            }
            return null;
        }
    }

    public Array<Page> getPages() {
        return this.pages;
    }

    public synchronized Rectangle getRect(String name) {
        Iterator i$ = this.pages.iterator();
        while (i$.hasNext()) {
            Rectangle rect = (Rectangle) ((Page) i$.next()).rects.get(name);
            if (rect != null) {
                return rect;
            }
        }
        return null;
    }

    public synchronized Page getPage(String name) {
        Iterator i$ = this.pages.iterator();
        while (i$.hasNext()) {
            Page page = (Page) i$.next();
            if (((Rectangle) page.rects.get(name)) != null) {
                return page;
            }
        }
        return null;
    }

    public synchronized int getPageIndex(String name) {
        for (int i = 0; i < this.pages.size; i++) {
            if (((Rectangle) ((Page) this.pages.get(i)).rects.get(name)) != null) {
                return i;
            }
        }
        return -1;
    }

    public synchronized void dispose() {
        Iterator i$ = this.pages.iterator();
        while (i$.hasNext()) {
            Page page = (Page) i$.next();
            if (page.texture == null) {
                page.image.dispose();
            }
        }
        this.disposed = true;
    }

    public synchronized TextureAtlas generateTextureAtlas(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean useMipMaps) {
        TextureAtlas atlas;
        atlas = new TextureAtlas();
        updateTextureAtlas(atlas, minFilter, magFilter, useMipMaps);
        return atlas;
    }

    public synchronized void updateTextureAtlas(TextureAtlas atlas, Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean useMipMaps) {
        PixmapPacker pixmapPacker = this;
        synchronized (this) {
            try {
                TextureAtlas textureAtlas;
                updatePageTextures(minFilter, magFilter, useMipMaps);
                Iterator i$ = pixmapPacker.pages.iterator();
                while (i$.hasNext()) {
                    Page page = (Page) i$.next();
                    if (page.addedRects.size > 0) {
                        Iterator i$2 = page.addedRects.iterator();
                        while (i$2.hasNext()) {
                            String name = (String) i$2.next();
                            Rectangle rect = (Rectangle) page.rects.get(name);
                            atlas.addRegion(name, new TextureRegion(page.texture, (int) rect.f12x, (int) rect.f13y, (int) rect.width, (int) rect.height));
                        }
                        textureAtlas = atlas;
                        page.addedRects.clear();
                        atlas.getTextures().add(page.texture);
                    } else {
                        textureAtlas = atlas;
                    }
                }
                textureAtlas = atlas;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public synchronized void updateTextureRegions(Array<TextureRegion> regions, Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean useMipMaps) {
        updatePageTextures(minFilter, magFilter, useMipMaps);
        while (regions.size < this.pages.size) {
            regions.add(new TextureRegion(((Page) this.pages.get(regions.size)).texture));
        }
    }

    public synchronized void updatePageTextures(Texture$TextureFilter minFilter, Texture$TextureFilter magFilter, boolean useMipMaps) {
        Iterator i$ = this.pages.iterator();
        while (i$.hasNext()) {
            ((Page) i$.next()).updateTexture(minFilter, magFilter, useMipMaps);
        }
    }

    public int getPageWidth() {
        return this.pageWidth;
    }

    public int getPageHeight() {
        return this.pageHeight;
    }

    public int getPadding() {
        return this.padding;
    }

    public boolean getDuplicateBorder() {
        return this.duplicateBorder;
    }

    public boolean getPackToTexture() {
        return this.packToTexture;
    }

    public void setPackToTexture(boolean packToTexture) {
        this.packToTexture = packToTexture;
    }
}
